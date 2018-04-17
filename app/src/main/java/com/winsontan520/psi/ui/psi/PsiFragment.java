package com.winsontan520.psi.ui.psi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.winsontan520.psi.R;
import com.winsontan520.psi.data.model.DataWrapper;
import com.winsontan520.psi.data.model.Item;
import com.winsontan520.psi.data.model.RegionMeta;
import com.winsontan520.psi.data.source.remote.DataGovRemoteDataSource;
import com.winsontan520.psi.util.PsiUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Winson Tan on 17/4/18.
 */

public class PsiFragment extends Fragment {

    public static PsiFragment newInstance() {
        Bundle args = new Bundle();
        PsiFragment fragment = new PsiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SupportMapFragment mMapFragment;
    private DataWrapper mDataWrapper;
    private Disposable mDisposable;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.psi_fragment, container, false);
        ButterKnife.bind(this, view);

        getPsiData();

        return view;
    }

    private void getPsiData() {
        DataGovRemoteDataSource service = new DataGovRemoteDataSource();
        service.getPSI().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DataWrapper>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(DataWrapper dataWrappers) {
                Timber.d("onNext " + dataWrappers);
                mDataWrapper = dataWrappers;
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                promptRetryDialog();
            }

            @Override
            public void onComplete() {
                setUpMapIfNeeded();
            }
        });

    }

    private void promptRetryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.alert_dialog_message)
                .setTitle(R.string.alert_dialog_title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPsiData();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMapFragment == null) {
            mMapFragment = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map));
            // Check if we were successful in obtaining the map.
            if (mMapFragment != null) {
                mMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap map) {
                        loadMapWithData(map, mDataWrapper);
                    }
                });
            }
        }
    }

    protected void loadMapWithData(GoogleMap map, DataWrapper data) {
        if (map != null && data != null) {
            List<RegionMeta> regionMetaList = data.getRegionMetadata();
            List<Item> items = data.getItems();

            for (RegionMeta region : regionMetaList) {
                // I have no idea why the PSI use direction name as key
                // TODO: something wrong with this
                int index = 0;
                switch (region.getName()) {
                    case "north":
                        index = items.get(0).getReadings().getPsiTwentyFourHourly().getNorth();
                        break;
                    case "west":
                        index = items.get(0).getReadings().getPsiTwentyFourHourly().getWest();
                        break;
                    case "central":
                        index = items.get(0).getReadings().getPsiTwentyFourHourly().getCentral();
                        break;
                    case "east":
                        index = items.get(0).getReadings().getPsiTwentyFourHourly().getEast();
                        break;
                    case "south":
                        index = items.get(0).getReadings().getPsiTwentyFourHourly().getSouth();
                        break;
                    default:
                        break;
                }

                String title = region.getName() + " " + index;
                String snippet = String.valueOf(index);

                IconGenerator iconGenerator = new IconGenerator(getActivity());

                iconGenerator.setStyle(PsiUtil.getIconStyleByIndex(index));
                Bitmap bitmap = iconGenerator.makeIcon(title);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

                LatLng listingPosition = new LatLng(region.getLabelLocation().getLatitude(), region.getLabelLocation().getLongitude());
                map.addMarker(new MarkerOptions()
                        .position(listingPosition)
                        .title(title)
                        .snippet(snippet)
                        .icon(icon));
            }

            // dismiss progress bar
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }
}

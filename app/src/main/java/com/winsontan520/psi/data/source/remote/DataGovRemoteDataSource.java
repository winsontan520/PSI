package com.winsontan520.psi.data.source.remote;

import com.winsontan520.psi.data.model.DataWrapper;
import com.winsontan520.psi.data.source.DataGovDataSource;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Winson Tan on 16/4/18.
 */

public class DataGovRemoteDataSource implements DataGovDataSource {

    private DataGovDataSource api;
    public static String BASE_URL = "https://api.data.gov.sg/v1/";

    public DataGovRemoteDataSource() {
        // default implementation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        this.api = retrofit.create(DataGovDataSource.class);
    }

    public DataGovRemoteDataSource(Retrofit retrofit) {
        this.api = retrofit.create(DataGovDataSource.class);
    }

    @Override
    public Observable<DataWrapper> getPSI() {
        return this.api.getPSI();
    }
}

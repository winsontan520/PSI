package com.winsontan520.psi.ui.psi;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.winsontan520.psi.R;
import com.winsontan520.psi.ui.base.BaseActivity;

public class PsiActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PsiFragment psiFragment = (PsiFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (psiFragment == null) {
            psiFragment = PsiFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content_frame, psiFragment);
            transaction.commit();
        }

    }

}

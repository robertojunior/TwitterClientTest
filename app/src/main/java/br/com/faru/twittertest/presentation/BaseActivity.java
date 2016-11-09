package br.com.faru.twittertest.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected void replaceFragmentWithTag(Fragment fragment, int containerId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerId, fragment, fragment.getClass().getCanonicalName());
        fragmentTransaction.commit();
    }

    protected Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}

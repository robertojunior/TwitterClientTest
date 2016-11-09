package br.com.faru.twittertest.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.presentation.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        showLoginFragment();
    }

    private void showLoginFragment() {
        Fragment currentFragment = getFragmentByTag(LoginFragment.class.getCanonicalName());
        if (currentFragment == null) {
            currentFragment = LoginFragment.newInstance();
        }

        replaceFragmentWithTag(currentFragment, R.id.container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragment = getSupportFragmentManager();
        if (fragment != null) {
            fragment.findFragmentByTag(LoginFragment.class.getCanonicalName()).onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d("Twitter", "fragment is null");
        }
    }
}

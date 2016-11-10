package br.com.faru.twittertest.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.di.Injector;
import br.com.faru.twittertest.util.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment implements LoginContract.View {

    @BindView(R.id.twitter_login_button)
    TwitterLoginButton loginButton;

    @Inject
    LoginPresenter presenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        Injector.getComponent().inject(this);

        presenter.setView(this);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                presenter.loginSuccess(result);
            }

            @Override
            public void failure(TwitterException exception) {
                presenter.loginFailure(exception);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void goToHome() {
        Navigation.goToHome(getContext());
        getActivity().finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}

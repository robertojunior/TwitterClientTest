package br.com.faru.twittertest.presentation.login;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public class LoginPresenter implements LoginContract.UserInteraction {

    private LoginContract.View view;

    @Override
    public void setView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void loginSuccess(Result<TwitterSession> result) {
        view.goToHome();
    }

    @Override
    public void loginFailure(TwitterException exception) {
        view.showMessage(exception.getMessage());
    }

}

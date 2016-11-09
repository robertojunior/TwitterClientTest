package br.com.faru.twittertest.presentation.login;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public interface LoginContract {

    interface View {
        void goToHome();

        void showMessage(String message);
    }

    interface UserInteraction {
        void setView(View view);

        void loginSuccess(Result<TwitterSession> result);

        void loginFailure(TwitterException exception);
    }

}

package br.com.faru.twittertest.presentation.home;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

public class HomePresenter implements HomeContract.UserInteraction {

    private HomeContract.View view;

    @Override
    public void setView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void checkTwitterCredentials() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        if (session == null) {
            view.goToLogin();
        } else {
            view.showHomeTimelineFragment();
        }
    }

}

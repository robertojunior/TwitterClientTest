package br.com.faru.twittertest.presentation.home;

public interface HomeContract {

    interface View {
        void goToLogin();

        void showHomeTimelineFragment();
    }

    interface UserInteraction {

        void setView(View view);

        void checkTwitterCredentials();

    }

}

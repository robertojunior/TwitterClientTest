package br.com.faru.twittertest.presentation.home;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import javax.inject.Inject;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.di.Injector;
import br.com.faru.twittertest.presentation.BaseActivity;
import br.com.faru.twittertest.presentation.about.AboutFragment;
import br.com.faru.twittertest.presentation.timeline.TimelineFragment;
import br.com.faru.twittertest.presentation.timeline.TimelineType;
import br.com.faru.twittertest.util.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeContract.View, HomeActivityListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Inject
    HomePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Injector.getComponent().inject(this);

        presenter.setView(this);
        presenter.checkTwitterCredentials();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_timeline:
                    showTimelineFragment(TimelineType.HOME);
                    break;

                case R.id.action_favorites:
                    showTimelineFragment(TimelineType.FAVORITE);
                    break;

                case R.id.action_account:
                    showAboutFragment();
            }

            return true;
        });

    }

    @Override
    public void goToLogin() {
        Navigation.goToLogin(this);
        finish();
    }

    @Override
    public void showHomeTimelineFragment() {
        showTimelineFragment(TimelineType.HOME);
    }


    @Override
    public void setToolbarTitle(int resId) {
        getSupportActionBar().setTitle(resId);
    }

    private void showTimelineFragment(TimelineType type) {
        replaceFragmentWithTag(TimelineFragment.newInstance(type), R.id.container);
    }

    private void showAboutFragment() {
        setToolbarTitle(R.string.about);
        replaceFragmentWithTag(AboutFragment.newInstance(), R.id.container);
    }

}

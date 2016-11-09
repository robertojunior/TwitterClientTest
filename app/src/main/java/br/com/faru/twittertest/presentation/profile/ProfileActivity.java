package br.com.faru.twittertest.presentation.profile;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.model.view.ProfileViewModel;
import br.com.faru.twittertest.presentation.BaseActivity;
import br.com.faru.twittertest.presentation.timeline.TimelineFragment;
import br.com.faru.twittertest.presentation.timeline.TimelineType;
import br.com.faru.twittertest.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.banner_image)
    SimpleDraweeView bannerImage;

    @BindView(R.id.profile_image)
    SimpleDraweeView profileImage;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.screen_name)
    TextView screenName;

    @BindView(R.id.followers_count)
    TextView followersCount;

    @BindView(R.id.tweets_count)
    TextView tweetsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getIntent().getExtras().containsKey(Constants.PROFILE)) {
            ProfileViewModel profile = (ProfileViewModel) getIntent().getExtras().getSerializable(Constants.PROFILE);
            fillProfile(profile);
            initToolbar(profile);
            showProfileFragment(profile);
        } else {
            throw new IllegalArgumentException("Bundle must contains a \"profile\" key");
        }
    }

    private void showProfileFragment(ProfileViewModel profile) {
        Fragment currentFragment = getFragmentByTag(TimelineFragment.class.getCanonicalName());
        if (currentFragment == null) {
            currentFragment = TimelineFragment.newInstance(TimelineType.PROFILE, profile);
        }

        replaceFragmentWithTag(currentFragment, R.id.container);
    }

    private void fillProfile(ProfileViewModel profile) {
        bannerImage.setImageURI(profile.getProfileBannerUrl());
        profileImage.setImageURI(profile.getProfileImageUrl());
        screenName.setText(profile.getFormattedScreenName());
        name.setText(profile.getName());
        tweetsCount.setText(String.valueOf(profile.getFormattedStatusesCount()));
        followersCount.setText(String.valueOf(profile.getFormattedFollowersCount()));
    }

    private void initToolbar(ProfileViewModel profile) {
        if (getSupportActionBar() != null) {
            collapsingToolbar.setTitle(profile.getFormattedScreenName());
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

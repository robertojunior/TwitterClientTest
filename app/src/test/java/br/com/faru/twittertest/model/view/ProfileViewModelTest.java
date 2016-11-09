package br.com.faru.twittertest.model.view;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.faru.twittertest.model.Profile;

@RunWith(JUnit4.class)
public class ProfileViewModelTest {

    ProfileViewModel profileViewModel;

    private static final int FOLLOWERS_COUNT = 300000;
    private static final String FORMATTED_FOLLOWERS_COUNT = "30K";
    private static final String FORMATTED_STATUSES_COUNT = "90K";
    private static final int STATUSES_COUNT = 900000;
    private static final String FORMATTED_SCREEN_NAME = "@vaifaru";
    private static final String SCREEN_NAME = "vaifaru";

    @Before
    public void setup() {
        Profile profile = new Profile();
        profile.setScreenName(SCREEN_NAME);
        profile.setFollowersCount(FOLLOWERS_COUNT);
        profile.setStatusesCount(STATUSES_COUNT);
        this.profileViewModel = new ProfileViewModel(profile);
    }

    @Test
    public void shouldReturnFormattedScreenName() {
        Assert.assertEquals(profileViewModel.getFormattedScreenName(), FORMATTED_SCREEN_NAME);
    }

    @Test
    public void shouldReturnFormattedFollowersCount() {
        Assert.assertEquals(profileViewModel.getFormattedFollowersCount(), FORMATTED_FOLLOWERS_COUNT);
    }

    @Test
    public void shouldReturnFormattedStatusesCount() {
        Assert.assertEquals(profileViewModel.getFormattedStatusesCount(), FORMATTED_STATUSES_COUNT);
    }

}
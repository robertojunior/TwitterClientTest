package br.com.faru.twittertest.model.view;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.model.Profile;
import br.com.faru.twittertest.model.Tweet;

@RunWith(JUnit4.class)
public class TweetViewModelTest {

    TweetViewModel tweetViewModel;

    @Before
    public void setup() {
        Tweet tweet = new Tweet();
        tweet.setProfile(new Profile());
        tweetViewModel = new TweetViewModel(tweet);
    }

    @Test
    public void shouldReturnResourceHeart() {
        tweetViewModel.setFavorite(true);
        Assert.assertEquals(R.drawable.heart, tweetViewModel.getImageResourceFavorite());
    }

    @Test
    public void shouldReturnResourceHeartOutline() {
        tweetViewModel.setFavorite(false);
        Assert.assertEquals(R.drawable.heart_outline, tweetViewModel.getImageResourceFavorite());
    }

}
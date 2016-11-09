package br.com.faru.twittertest.model.view;

import com.twitter.sdk.android.core.models.Tweet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.util.FormatterUtils;

public class TweetViewModel implements Serializable, Comparable<TweetViewModel> {

    private long id;
    private String text;
    private String createdAt;
    private ProfileViewModel profile;
    private boolean favorite;

    public TweetViewModel(Tweet tweet) {
        this.id = tweet.id;
        this.text = tweet.text;
        this.createdAt = tweet.createdAt;
        this.profile = new ProfileViewModel(tweet.user);
    }

    public TweetViewModel(br.com.faru.twittertest.model.Tweet tweet) {
        this.id = tweet.getId();
        this.text = tweet.getText();
        this.createdAt = tweet.getCreatedAt();
        this.profile = new ProfileViewModel(tweet.getProfile());
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getTimeAgo() {
        return FormatterUtils.getTimeAgo(createdAt);
    }

    public ProfileViewModel getProfile() {
        return profile;
    }

    public int getImageResourceFavorite() {
        return favorite ? R.drawable.heart : R.drawable.heart_outline;
    }

    public static List<TweetViewModel> getList(List<Tweet> tweets) {
        final List<TweetViewModel> tweetViewModels = new ArrayList<>();
        for (Tweet tweet : tweets) {
            tweetViewModels.add(new TweetViewModel(tweet));
        }
        return tweetViewModels;
    }

    @Override
    public int compareTo(TweetViewModel tweetResult) {
        final Date date1 = FormatterUtils.toDate(this.createdAt);
        final Date date2 = FormatterUtils.toDate(tweetResult.createdAt);

        if (date1.after(date2)) {
            return -1;
        } else if (date1.before(date2)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((TweetViewModel)obj).getId();
    }
}

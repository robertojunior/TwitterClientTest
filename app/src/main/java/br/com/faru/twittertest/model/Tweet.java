package br.com.faru.twittertest.model;

import br.com.faru.twittertest.model.view.TweetViewModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tweet extends RealmObject {

    @PrimaryKey
    private long id;
    private String text;
    private String createdAt;
    private Profile profile;

    public Tweet() {
        super();
    }

    public Tweet(TweetViewModel tweet) {
        this.id = tweet.getId();
        this.text = tweet.getText();
        this.createdAt = tweet.getCreatedAt();
        this.profile = new Profile(tweet.getProfile());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}

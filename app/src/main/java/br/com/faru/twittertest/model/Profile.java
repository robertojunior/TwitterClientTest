package br.com.faru.twittertest.model;

import java.io.Serializable;

import br.com.faru.twittertest.model.view.ProfileViewModel;
import io.realm.RealmObject;

public class Profile extends RealmObject implements Serializable {

    private String createdAt;
    private String description;
    private String profileBannerUrl;
    private String profileImageUrl;
    private String name;
    private String screenName;
    private int followersCount;
    private int statusesCount;

    public Profile() {
        super();
    }

    public Profile(ProfileViewModel profile) {
        this.createdAt = profile.getCreatedAt();
        this.description = profile.getDescription();
        this.profileImageUrl = profile.getProfileImageUrl();
        this.profileBannerUrl = profile.getProfileBannerUrl();
        this.name = profile.getName();
        this.screenName = profile.getScreenName();
        this.followersCount = profile.getFollowersCount();
        this.statusesCount = profile.getStatusesCount();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public void setProfileBannerUrl(String profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }
}

package br.com.faru.twittertest.model.view;

import android.net.Uri;

import com.twitter.sdk.android.core.models.User;

import java.io.Serializable;

import br.com.faru.twittertest.model.Profile;
import br.com.faru.twittertest.util.FormatterUtils;

public class ProfileViewModel implements Serializable {

    private String createdAt;
    private String description;
    private String profileBannerUrl;
    private String profileImageUrl;
    private String name;
    private String screenName;
    private int followersCount;
    private int statusesCount;

    public ProfileViewModel(User user) {
        this.createdAt = user.createdAt;
        this.description = user.description;
        this.profileBannerUrl = user.profileBannerUrl;
        this.profileImageUrl = user.profileImageUrl;
        this.name = user.name;
        this.screenName = user.screenName;
        this.followersCount = user.followersCount;
        this.statusesCount = user.statusesCount;
    }

    public ProfileViewModel(Profile profile) {
        this.createdAt = profile.getCreatedAt();
        this.description = profile.getDescription();
        this.profileBannerUrl = profile.getProfileBannerUrl();
        this.profileImageUrl = profile.getProfileImageUrl();
        this.name = profile.getName();
        this.screenName = profile.getScreenName();
        this.followersCount = profile.getFollowersCount();
        this.statusesCount = profile.getStatusesCount();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public Uri getProfileBannerUri() {
        return Uri.parse(profileBannerUrl);
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Uri getProfileImageUri() {
        return Uri.parse(profileImageUrl);
    }

    public String getName() {
        return name;
    }

    public String getFormattedScreenName() {
        return String.format("@%s", screenName);
    }

    public String getScreenName() {
        return screenName;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public String getFormattedStatusesCount() {
        return FormatterUtils.prettyDouble(statusesCount);
    }

    public String getFormattedFollowersCount() {
        return FormatterUtils.prettyDouble(followersCount);
    }

}

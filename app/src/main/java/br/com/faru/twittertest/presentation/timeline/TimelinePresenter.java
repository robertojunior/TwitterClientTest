package br.com.faru.twittertest.presentation.timeline;

import android.os.Bundle;
import android.text.TextUtils;

import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.client.TwitterClient;
import br.com.faru.twittertest.client.callback.GetTimelineCallback;
import br.com.faru.twittertest.model.Tweet;
import br.com.faru.twittertest.model.view.ProfileViewModel;
import br.com.faru.twittertest.model.view.TweetViewModel;
import br.com.faru.twittertest.presentation.widget.EndlessGridScrollListener;
import br.com.faru.twittertest.repository.favorite.FavoriteRepository;
import br.com.faru.twittertest.repository.favorite.FavoritesCallback;
import br.com.faru.twittertest.util.Constants;

public class TimelinePresenter implements
        TimelineContract.UserInteraction,
        GetTimelineCallback,
        FavoritesCallback,
        EndlessGridScrollListener.OnEndlessGridScrollListener {

    TwitterClient twitterClient;
    FavoriteRepository favoriteRepository;

    private TimelineContract.View view;
    private long sinceId;
    private boolean isDownloading;
    private long maxId;

    @Inject
    public TimelinePresenter(TwitterClient client, FavoriteRepository favoriteRepository) {
        this.twitterClient = client;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(TimelineContract.View view) {
        this.view = view;
    }

    @Override
    public void init(Bundle arguments) {
        if (arguments != null && arguments.containsKey(Constants.TYPE)) {
            final TimelineType timelineType = (TimelineType) arguments.getSerializable(Constants.TYPE);
            switch (timelineType) {
                case PROFILE:
                    if (arguments.containsKey(Constants.PROFILE)) {
                        ProfileViewModel profile = (ProfileViewModel) arguments.getSerializable(Constants.PROFILE);
                        view.addOnScrollListener(this, profile.getScreenName());
                        this.getProfileTimeline(profile.getScreenName(), null, null, true);
                    }
                    break;

                case HOME:
                    view.setToolbarTitle(R.string.timeline);
                    view.addOnScrollListener(this, null);
                    this.getHomeTimeline(null, null, true);
                    break;

                case FAVORITE:
                    view.setToolbarTitle(R.string.favorites);
                    this.getFavoriteTimeline(true);
                    break;
            }
        }
    }

    @Override
    public void swipeRefresh(Bundle arguments) {
        if (arguments != null && arguments.containsKey(Constants.TYPE)) {
            final TimelineType timelineType = (TimelineType) arguments.getSerializable(Constants.TYPE);
            switch (timelineType) {
                case PROFILE:
                    if (arguments.containsKey(Constants.PROFILE)) {
                        ProfileViewModel profile = (ProfileViewModel) arguments.getSerializable(Constants.PROFILE);
                        this.getProfileTimeline(profile.getScreenName(), sinceId, null, false);
                    }
                    break;

                case HOME:
                    this.getHomeTimeline(sinceId, null, false);
                    break;

                case FAVORITE:
                    this.getFavoriteTimeline(false);
                    break;
            }
        }
    }

    @Override
    public void saveFavorite(TweetViewModel tweet) {
        if (favoriteRepository.exists(tweet.getId())) {
            favoriteRepository.remove(tweet, this);
        } else {
            favoriteRepository.save(tweet, this);
        }
    }

    @Override
    public void onGetTimelineSuccess(List<TweetViewModel> tweets) {
        if (tweets != null && tweets.size() > 0) {
            for (TweetViewModel tweet : tweets) {
                tweet.setFavorite(favoriteRepository.exists(tweet.getId()));
            }

            sinceId = tweets.get(0).getId();
            maxId = tweets.get(tweets.size() - 1).getId();
            view.showResults(tweets);
        }

        isDownloading = false;
        view.setProgressIndicator(isDownloading);
    }

    @Override
    public void onGetTimelineFailure(TwitterException exception) {
        view.showMessage(R.string.error_ocurred);
        view.setProgressIndicator(false);
    }

    @Override
    public void loadMoreIfItIsPossible(String screenName) {
        if (!isDownloading()) {
            isDownloading = true;
            if (TextUtils.isEmpty(screenName)) {
                getHomeTimeline(null, maxId, true);
            } else {
                getProfileTimeline(screenName, null, maxId, true);
            }
        }
    }

    @Override
    public boolean isDownloading() {
        return isDownloading;
    }

    @Override
    public void onGetFavoritesSuccess(List<Tweet> tweets) {
        final List<TweetViewModel> tweetViewModels = new ArrayList<>();
        for (Tweet tweet : tweets) {
            TweetViewModel tweetViewModel = new TweetViewModel(tweet);
            tweetViewModel.setFavorite(true);
            tweetViewModels.add(tweetViewModel);
        }
        view.showResults(tweetViewModels);
        view.setProgressIndicator(false);
    }

    @Override
    public void onGetFavoritesFailure(Exception exception) {
        view.showMessage(R.string.error_ocurred);
    }

    @Override
    public void onSaveFavoriteSuccess(TweetViewModel tweetViewModel) {
        tweetViewModel.setFavorite(true);
        view.showMessage(R.string.tweet_favorited_success);
        view.notifyDataSetChanged();
    }

    @Override
    public void onSaveFavoriteFailure(Exception exception) {
        view.showMessage(R.string.error_ocurred);
    }

    @Override
    public void onRemoveFavoriteSuccess(TweetViewModel tweetViewModel) {
        tweetViewModel.setFavorite(false);
        view.showMessage(R.string.tweet_unfavorited_success);
        view.notifyDataSetChanged();
    }

    public void getHomeTimeline(Long sinceId, Long maxId, boolean showProgress) {
        view.setProgressIndicator(showProgress);
        twitterClient.getHomeTimeline(sinceId, maxId, this);
    }

    public void getProfileTimeline(String screenName, Long sinceId, Long maxId, boolean showProgress) {
        view.setProgressIndicator(showProgress);
        twitterClient.getUserTimeline(screenName, sinceId, maxId, this);
    }

    public void getFavoriteTimeline(boolean showProgress) {
        view.setProgressIndicator(showProgress);
        favoriteRepository.findAll(this);
    }

}

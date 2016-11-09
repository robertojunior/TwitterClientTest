package br.com.faru.twittertest.client.callback;

import com.twitter.sdk.android.core.TwitterException;

import java.util.List;

import br.com.faru.twittertest.model.view.TweetViewModel;

public interface GetTimelineCallback {

    void onGetTimelineSuccess(List<TweetViewModel> tweets);

    void onGetTimelineFailure(TwitterException exception);

}

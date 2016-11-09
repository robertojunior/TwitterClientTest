package br.com.faru.twittertest.client;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

import br.com.faru.twittertest.client.callback.GetTimelineCallback;
import br.com.faru.twittertest.model.view.TweetViewModel;
import retrofit2.Call;

public class TwitterClient {

    public void getHomeTimeline(Long sinceId, Long maxId, final GetTimelineCallback callback) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<List<Tweet>> call = statusesService.homeTimeline(null, sinceId, maxId, false, false, false, false);
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                callback.onGetTimelineSuccess(TweetViewModel.getList(result.data));
            }

            public void failure(TwitterException exception) {
                callback.onGetTimelineFailure(exception);
            }
        });
    }

    public void getUserTimeline(String screenName, Long sinceId, Long maxId, final GetTimelineCallback callback) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<List<Tweet>> call = statusesService.userTimeline(null, screenName, null, sinceId, maxId, false, false, false, false);
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                callback.onGetTimelineSuccess(TweetViewModel.getList(result.data));
            }

            public void failure(TwitterException exception) {
                callback.onGetTimelineFailure(exception);
            }
        });
    }
}

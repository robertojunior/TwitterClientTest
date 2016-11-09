package br.com.faru.twittertest.presentation.timeline;

import android.os.Bundle;

import java.util.List;

import br.com.faru.twittertest.model.view.TweetViewModel;
import br.com.faru.twittertest.presentation.widget.EndlessGridScrollListener;

public interface TimelineContract {

    interface View {

        void setProgressIndicator(boolean show);

        void showResults(List<TweetViewModel> tweets);

        void notifyDataSetChanged();

        void showMessage(int message);

        void setToolbarTitle(int title);

        void addOnScrollListener(EndlessGridScrollListener.OnEndlessGridScrollListener
                                         onEndlessGridScrollListener, String screenName);

        void showEmptyView(boolean show);

        int tweetCount();
    }

    interface UserInteraction {

        void setView(View view);

        void init(Bundle arguments);

        void saveFavorite(TweetViewModel tweet);

        void swipeRefresh(Bundle arguments);

    }

}

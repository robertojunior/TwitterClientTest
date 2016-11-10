package br.com.faru.twittertest.dao.favorite;

import java.util.List;

import br.com.faru.twittertest.model.Tweet;
import br.com.faru.twittertest.model.view.TweetViewModel;

public interface FavoritesCallback {

    void onGetFavoritesSuccess(List<Tweet> tweets);

    void onGetFavoritesFailure(Exception exception);

    void onSaveFavoriteSuccess(TweetViewModel tweetViewModel);

    void onSaveFavoriteFailure(Exception exception);

    void onRemoveFavoriteSuccess(TweetViewModel tweetViewModel);

}

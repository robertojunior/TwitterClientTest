package br.com.faru.twittertest.repository.favorite;

import br.com.faru.twittertest.model.Tweet;
import br.com.faru.twittertest.model.view.TweetViewModel;
import br.com.faru.twittertest.util.Constants;
import io.realm.Realm;

public class FavoriteRepository {

    private Realm realm;

    public FavoriteRepository(Realm realm) {
        this.realm = realm;
    }

    public void findAll(FavoritesCallback callback) {
        try {
            callback.onGetFavoritesSuccess(realm.copyFromRealm(realm.where(Tweet.class).findAll()));
        } catch (Exception e) {
            callback.onGetFavoritesFailure(e);
        }
    }

    public boolean exists(Long id) {
        return realm.where(Tweet.class).equalTo(Constants.ID, id).findFirst() != null;
    }

    public void save(TweetViewModel tweetViewModel, FavoritesCallback callback) {
        try {
            realm.beginTransaction();
            realm.copyToRealm(new Tweet(tweetViewModel));
            callback.onSaveFavoriteSuccess(tweetViewModel);
            realm.commitTransaction();
            callback.onSaveFavoriteSuccess(tweetViewModel);
        } catch (Exception e) {
            callback.onSaveFavoriteFailure(e);
        }
    }

    public void remove(TweetViewModel tweetViewModel, FavoritesCallback callback) {
        try {
            final Tweet tweetRealm = findById(tweetViewModel.getId());
            realm.beginTransaction();
            tweetRealm.deleteFromRealm();
            realm.commitTransaction();
            callback.onRemoveFavoriteSuccess(tweetViewModel);
        } catch (Exception e) {
            callback.onSaveFavoriteFailure(e);
        }
    }

    public Tweet findById(Long id) {
        return realm.where(Tweet.class).equalTo(Constants.ID, id).findFirst();
    }
}

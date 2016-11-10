package br.com.faru.twittertest.di.module;


import br.com.faru.twittertest.dao.favorite.FavoriteDAO;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class DAOModule {

    @Provides
    public FavoriteDAO provideFavoriteDAO() {
        return new FavoriteDAO(Realm.getDefaultInstance());
    }

}

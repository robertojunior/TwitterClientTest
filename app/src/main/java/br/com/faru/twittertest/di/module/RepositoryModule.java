package br.com.faru.twittertest.di.module;


import br.com.faru.twittertest.repository.favorite.FavoriteRepository;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RepositoryModule {

    @Provides
    public FavoriteRepository provideFavoriteRepository() {
        return new FavoriteRepository(Realm.getDefaultInstance());
    }

}

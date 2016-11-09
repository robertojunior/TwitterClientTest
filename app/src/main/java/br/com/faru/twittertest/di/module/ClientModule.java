package br.com.faru.twittertest.di.module;


import br.com.faru.twittertest.client.TwitterClient;
import dagger.Module;
import dagger.Provides;

@Module
public class ClientModule {

    @Provides
    public TwitterClient provideTwitterClient() {
        return new TwitterClient();
    }

}

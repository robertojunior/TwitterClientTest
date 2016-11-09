package br.com.faru.twittertest.di.module;

import br.com.faru.twittertest.presentation.home.HomePresenter;
import br.com.faru.twittertest.presentation.login.LoginPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    public HomePresenter provideMainPresenter() {
        return new HomePresenter();
    }

    @Provides
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenter();
    }

}

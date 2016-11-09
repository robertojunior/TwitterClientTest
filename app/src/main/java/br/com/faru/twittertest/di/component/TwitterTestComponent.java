package br.com.faru.twittertest.di.component;

import br.com.faru.twittertest.di.module.ClientModule;
import br.com.faru.twittertest.di.module.PresenterModule;
import br.com.faru.twittertest.di.module.RepositoryModule;
import br.com.faru.twittertest.di.scope.ActivityScope;
import br.com.faru.twittertest.presentation.home.HomeActivity;
import br.com.faru.twittertest.presentation.login.LoginFragment;
import br.com.faru.twittertest.presentation.timeline.TimelineFragment;
import dagger.Component;

@ActivityScope
@Component(modules = {PresenterModule.class, ClientModule.class, RepositoryModule.class})
public interface TwitterTestComponent {

    void inject(HomeActivity mainActivity);

    void inject(LoginFragment loginFragment);

    void inject(TimelineFragment timelineFragment);

}

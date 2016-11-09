package br.com.faru.twittertest.app;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import br.com.faru.twittertest.BuildConfig;
import br.com.faru.twittertest.di.Injector;
import br.com.faru.twittertest.di.component.DaggerTwitterTestComponent;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class TwitterTestApplication extends Application {

    public static TwitterTestApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY,
                BuildConfig.TWITTER_SECRET);

        Fabric.with(this, new Twitter(authConfig));

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, config);

        application = this;

        Injector.initialize(DaggerTwitterTestComponent.builder().build());

        Realm.init(this);

    }

    public static TwitterTestApplication getApplication() {
        return application;
    }

}

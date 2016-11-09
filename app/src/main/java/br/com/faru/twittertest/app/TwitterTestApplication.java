package br.com.faru.twittertest.app;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.di.component.DaggerTwitterTestComponent;
import br.com.faru.twittertest.di.component.TwitterTestComponent;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class TwitterTestApplication extends Application {

    public static TwitterTestApplication application;
    public static TwitterTestComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.twitter_key),
                getString(R.string.twitter_secret));

        Fabric.with(this, new Twitter(authConfig));

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, config);

        application = this;

        component = DaggerTwitterTestComponent.builder().build();

        Realm.init(this);

    }

    public static TwitterTestApplication getApplication() {
        return application;
    }

    public static TwitterTestComponent getComponent() {
        return component;
    }

}

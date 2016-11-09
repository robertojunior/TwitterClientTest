package br.com.faru.twittertest.util;

import android.content.Context;
import android.content.Intent;

import br.com.faru.twittertest.model.view.ProfileViewModel;
import br.com.faru.twittertest.presentation.home.HomeActivity;
import br.com.faru.twittertest.presentation.login.LoginActivity;
import br.com.faru.twittertest.presentation.profile.ProfileActivity;

public class Navigation {

    public static void goToHome(Context ctx) {
        ctx.startActivity(new Intent(ctx, HomeActivity.class));
    }

    public static void goToLogin(Context ctx) {
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }

    public static void goToProfile(Context ctx, ProfileViewModel profile) {
        Intent intent = new Intent(ctx, ProfileActivity.class);
        intent.putExtra(Constants.PROFILE, profile);
        ctx.startActivity(intent);
    }
}

package br.com.faru.twittertest.presentation.timeline;

import br.com.faru.twittertest.model.view.ProfileViewModel;
import br.com.faru.twittertest.model.view.TweetViewModel;

public interface TimelineAdapterListener {

    void onClickProfile(ProfileViewModel profile);

    void onClickFavorite(TweetViewModel tweet);

    void showEmptyView(boolean show);

}

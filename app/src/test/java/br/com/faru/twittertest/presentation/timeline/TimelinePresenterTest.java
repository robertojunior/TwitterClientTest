package br.com.faru.twittertest.presentation.timeline;

import android.os.Bundle;
import android.text.TextUtils;

import com.twitter.sdk.android.core.TwitterException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.client.TwitterClient;
import br.com.faru.twittertest.client.callback.GetTimelineCallback;
import br.com.faru.twittertest.model.view.ProfileViewModel;
import br.com.faru.twittertest.model.view.TweetViewModel;
import br.com.faru.twittertest.presentation.widget.EndlessGridScrollListener;
import br.com.faru.twittertest.repository.favorite.FavoriteRepository;
import br.com.faru.twittertest.repository.favorite.FavoritesCallback;
import br.com.faru.twittertest.util.Constants;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, Bundle.class})
public class TimelinePresenterTest {

    @Mock
    TwitterClient twitterClientMock;

    @Mock
    FavoriteRepository favoriteRepositoryMock;

    @Mock
    TimelineContract.View viewMock;

    @Mock
    Bundle bundle;

    private TimelinePresenter timelinePresenter;

    private static final String SCREEN_NAME = "vaifaru";

    @Before
    public void setup() {
        timelinePresenter = new TimelinePresenter(twitterClientMock, favoriteRepositoryMock);
        timelinePresenter.setView(viewMock);
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Test
    public void shouldAddScrollListenerWhenInitProfile() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.PROFILE);
        when(bundle.containsKey(Constants.PROFILE)).thenReturn(true);
        when(bundle.getSerializable(Constants.PROFILE)).thenReturn(mock(ProfileViewModel.class));

        timelinePresenter.init(bundle);
        verify(viewMock).addOnScrollListener(any(EndlessGridScrollListener.OnEndlessGridScrollListener.class), anyString());
    }

    @Test
    public void shouldGetProfileTimelineWhenInitProfile() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.PROFILE);
        when(bundle.containsKey(Constants.PROFILE)).thenReturn(true);

        ProfileViewModel profileViewModelMock = mock(ProfileViewModel.class);
        when(profileViewModelMock.getScreenName()).thenReturn(SCREEN_NAME);
        when(bundle.getSerializable(Constants.PROFILE)).thenReturn(profileViewModelMock);

        timelinePresenter.init(bundle);
        verify(twitterClientMock).getUserTimeline(eq(profileViewModelMock.getScreenName()), eq(null), eq(null), any(GetTimelineCallback.class));
    }

    @Test
    public void shouldShowProgressWhenInitProfile() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.PROFILE);
        when(bundle.containsKey(Constants.PROFILE)).thenReturn(true);

        ProfileViewModel profileViewModelMock = mock(ProfileViewModel.class);
        when(profileViewModelMock.getScreenName()).thenReturn(SCREEN_NAME);
        when(bundle.getSerializable(Constants.PROFILE)).thenReturn(profileViewModelMock);

        timelinePresenter.init(bundle);
        verify(viewMock).setProgressIndicator(true);
    }

    @Test
    public void shouldAddScrollListenerWhenInitHome() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.HOME);

        timelinePresenter.init(bundle);
        verify(viewMock).addOnScrollListener(any(EndlessGridScrollListener.OnEndlessGridScrollListener.class), anyString());
    }

    @Test
    public void shouldSetToolbarTitleWhenInitHome() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.HOME);

        timelinePresenter.init(bundle);
        verify(viewMock).setToolbarTitle(R.string.timeline);
    }

    @Test
    public void shouldShowProgressWhenInitHome() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.HOME);

        timelinePresenter.init(bundle);
        verify(viewMock).setProgressIndicator(true);
    }

    @Test
    public void shouldGetHomeTimelineWhenInitHome() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.HOME);

        timelinePresenter.init(bundle);
        verify(twitterClientMock).getHomeTimeline(eq(null), eq(null), any(GetTimelineCallback.class));
    }

    @Test
    public void shouldSetToolbarTitleWhenInitFavorite() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.FAVORITE);

        timelinePresenter.init(bundle);
        verify(viewMock).setToolbarTitle(R.string.favorites);
    }

    @Test
    public void shouldShowProgressWhenInitFavorite() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.FAVORITE);

        timelinePresenter.init(bundle);
        verify(viewMock).setProgressIndicator(true);
    }

    @Test
    public void shouldGetFavoriteTimelineWhenInitFavorite() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.FAVORITE);

        timelinePresenter.init(bundle);
        verify(favoriteRepositoryMock).findAll(any(FavoritesCallback.class));
    }

    @Test
    public void shouldGetProfileTimelineWhenSwipeRefreshProfile() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.PROFILE);
        when(bundle.containsKey(Constants.PROFILE)).thenReturn(true);

        ProfileViewModel profileViewModelMock = mock(ProfileViewModel.class);
        when(profileViewModelMock.getScreenName()).thenReturn(SCREEN_NAME);
        when(bundle.getSerializable(Constants.PROFILE)).thenReturn(profileViewModelMock);

        timelinePresenter.swipeRefresh(bundle);
        verify(twitterClientMock).getUserTimeline(eq(SCREEN_NAME), anyLong(), eq(null), any(GetTimelineCallback.class));
    }

    @Test
    public void shouldNotShowProgressWhenSwipeRefreshProfile() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.PROFILE);
        when(bundle.containsKey(Constants.PROFILE)).thenReturn(true);

        ProfileViewModel profileViewModelMock = mock(ProfileViewModel.class);
        when(profileViewModelMock.getScreenName()).thenReturn(SCREEN_NAME);
        when(bundle.getSerializable(Constants.PROFILE)).thenReturn(profileViewModelMock);

        timelinePresenter.swipeRefresh(bundle);
        verify(viewMock, never()).setProgressIndicator(true);
        verify(viewMock).setProgressIndicator(false);
    }

    @Test
    public void shouldGetHomeTimelineWhenSwipeRefreshHome() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.HOME);

        timelinePresenter.swipeRefresh(bundle);
        verify(twitterClientMock).getHomeTimeline(anyLong(), eq(null), any(GetTimelineCallback.class));
    }

    @Test
    public void shouldNotShowProgressWhenSwipeRefreshHome() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.HOME);

        timelinePresenter.swipeRefresh(bundle);
        verify(viewMock, never()).setProgressIndicator(true);
        verify(viewMock).setProgressIndicator(false);
    }

    @Test
    public void shouldGetFavoriteTimelineWhenSwipeRefreshFavorite() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.FAVORITE);

        timelinePresenter.swipeRefresh(bundle);
        verify(favoriteRepositoryMock).findAll(any(FavoritesCallback.class));
    }

    @Test
    public void shouldNotShowProgressWhenSwipeRefreshFavorite() {
        when(bundle.containsKey(Constants.TYPE)).thenReturn(true);
        when(bundle.getSerializable(Constants.TYPE)).thenReturn(TimelineType.FAVORITE);

        timelinePresenter.swipeRefresh(bundle);
        verify(viewMock, never()).setProgressIndicator(true);
        verify(viewMock).setProgressIndicator(false);
    }

    @Test
    public void shouldRemoveWhenSaveFavorite() {
        when(favoriteRepositoryMock.exists(anyLong())).thenReturn(true);
        final TweetViewModel tweetViewModelMock = mock(TweetViewModel.class);
        timelinePresenter.saveFavorite(tweetViewModelMock);
        verify(favoriteRepositoryMock).remove(eq(tweetViewModelMock), any(FavoritesCallback.class));
    }

    @Test
    public void shouldSaveWhenSaveFavorite() {
        when(favoriteRepositoryMock.exists(anyLong())).thenReturn(false);
        TweetViewModel tweetViewModelMock = mock(TweetViewModel.class);
        timelinePresenter.saveFavorite(tweetViewModelMock);
        verify(favoriteRepositoryMock).save(eq(tweetViewModelMock), any(FavoritesCallback.class));
    }

    @Test
    public void shouldNotShowResultsWhenGetTimelineSuccessWithEmptyList() {
        timelinePresenter.onGetTimelineSuccess(mock(ArrayList.class));
        verify(viewMock, never()).showResults(anyListOf(TweetViewModel.class));
    }

    @Test
    public void shouldShowEmptyViewWhenGetTimelineSuccessWithEmptyList() {
        when(viewMock.tweetCount()).thenReturn(0);
        timelinePresenter.onGetTimelineSuccess(mock(ArrayList.class));
        verify(viewMock).showEmptyView(true);
    }

    @Test
    public void shouldHideEmptyViewWhenGetTimelineSuccessWithEmptyList() {
        when(viewMock.tweetCount()).thenReturn(1);
        timelinePresenter.onGetTimelineSuccess(mock(ArrayList.class));
        verify(viewMock).showEmptyView(false);
    }

    @Test
    public void shouldHideProgressWhenGetTimelineSuccess() {
        timelinePresenter.onGetTimelineSuccess(mock(ArrayList.class));
        verify(viewMock).setProgressIndicator(false);
    }

    @Test
    public void shouldHideProgressWhenGetTimelineFailure() {
        timelinePresenter.onGetTimelineFailure(any(TwitterException.class));
        verify(viewMock).setProgressIndicator(false);
    }

    @Test
    public void shouldShowMessageWhenGetTimelineFailure() {
        timelinePresenter.onGetTimelineFailure(any(TwitterException.class));
        verify(viewMock).showMessage(R.string.error_ocurred);
    }

    @Test
    public void shouldGetHomeTimelineWhenLoadMoreIfItIsPossible() {
        when(TextUtils.isEmpty(null)).thenReturn(true);
        timelinePresenter.loadMoreIfItIsPossible(null);
        verify(twitterClientMock).getHomeTimeline(anyLong(), anyLong(), any(GetTimelineCallback.class));
    }

    @Test
    public void shouldGetProfileTimelineWhenLoadMoreIfItIsPossible() {
        timelinePresenter.loadMoreIfItIsPossible(SCREEN_NAME);
        verify(twitterClientMock).getUserTimeline(eq(SCREEN_NAME), anyLong(), anyLong(), any(GetTimelineCallback.class));
    }

    @Test
    public void shouldShowProgressWhenLoadingProfileTimeline() {
        timelinePresenter.getProfileTimeline(SCREEN_NAME, null, null, true);
        verify(viewMock).setProgressIndicator(true);
    }

    @Test
    public void shouldShowProgressWhenLoadingHomeTimeline() {
        timelinePresenter.getFavoriteTimeline(true);
        verify(viewMock).setProgressIndicator(true);
    }

    @Test
    public void shouldShowProgressWhenLoadingFavoriteTimeline() {
        timelinePresenter.getHomeTimeline(null, null, true);
        verify(viewMock).setProgressIndicator(true);
    }

}

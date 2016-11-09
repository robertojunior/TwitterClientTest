package br.com.faru.twittertest.presentation.timeline;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

import javax.inject.Inject;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.di.Injector;
import br.com.faru.twittertest.model.view.ProfileViewModel;
import br.com.faru.twittertest.model.view.TweetViewModel;
import br.com.faru.twittertest.presentation.home.HomeActivityListener;
import br.com.faru.twittertest.presentation.widget.EndlessGridScrollListener;
import br.com.faru.twittertest.presentation.widget.VerticalRecyclerView;
import br.com.faru.twittertest.util.Constants;
import br.com.faru.twittertest.util.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineFragment extends Fragment implements TimelineContract.View, TimelineAdapterListener {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.timeline_list)
    VerticalRecyclerView timelineList;

    @BindView(R.id.progress_view)
    CircularProgressView progressView;

    @BindView(R.id.empty_view)
    TextView emptyView;

    @Inject
    TimelinePresenter presenter;

    private TimelineType type;
    private TimelineAdapter timelineAdapter;
    private HomeActivityListener homeActivityListener;

    public static TimelineFragment newInstance(TimelineType type, ProfileViewModel profile) {
        TimelineFragment fragment = new TimelineFragment();
        if (profile != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.TYPE, type);
            bundle.putSerializable(Constants.PROFILE, profile);
            fragment.setArguments(bundle);
        } else {
            fragment = newInstance(type);
        }

        return fragment;
    }

    public static TimelineFragment newInstance(TimelineType type) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivityListener) {
            homeActivityListener = (HomeActivityListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null
                && getArguments().containsKey(Constants.TYPE)) {
            type = (TimelineType) getArguments().getSerializable(Constants.TYPE);
        }

        Injector.getComponent().inject(this);

        timelineAdapter = new TimelineAdapter(this);
        timelineList.setAdapter(timelineAdapter);

        presenter.setView(this);
        presenter.init(getArguments());

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.swipeRefresh(getArguments());
        });

        return view;
    }

    @Override
    public void showResults(List<TweetViewModel> tweets) {
        timelineAdapter.addTweets(tweets);
    }

    @Override
    public void notifyDataSetChanged() {
        timelineAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setToolbarTitle(int resId) {
        if (homeActivityListener != null) {
            homeActivityListener.setToolbarTitle(resId);
        } else {
            throw new IllegalArgumentException("Activity must implement " + HomeActivityListener.class.getCanonicalName());
        }
    }

    @Override
    public void addOnScrollListener(EndlessGridScrollListener.OnEndlessGridScrollListener onEndlessGridScrollListener, String screenName) {
        timelineList.addOnScrollListener(new EndlessGridScrollListener(onEndlessGridScrollListener,
                timelineList.getLinearLayoutManager(), screenName));
    }

    @Override
    public void onClickProfile(ProfileViewModel profile) {
        if (!type.equals(TimelineType.PROFILE)) {
            Navigation.goToProfile(getContext(), profile);
        }
    }

    @Override
    public void onClickFavorite(TweetViewModel tweet) {
        presenter.saveFavorite(tweet);
    }

    @Override
    public void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public int tweetCount() {
        return timelineAdapter.getItemCount();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        progressView.setVisibility(active ? View.VISIBLE : View.GONE);
        if (!active)
            swipeRefreshLayout.setRefreshing(false);
    }

}

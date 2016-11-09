package br.com.faru.twittertest.presentation.timeline;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.faru.twittertest.R;
import br.com.faru.twittertest.model.view.TweetViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private List<TweetViewModel> tweets = new ArrayList<>();
    private TimelineAdapterListener listener;

    public TimelineAdapter(TimelineAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TweetViewModel tweet = tweets.get(position);
        holder.layout.setBackgroundColor(position % 2 == 1 ? Color.parseColor("#F0F0F0") : Color.WHITE);
        holder.profileImage.setImageURI(tweet.getProfile().getProfileImageUri());
        holder.text.setText(tweet.getText());
        holder.name.setText(tweet.getProfile().getName());
        holder.screenName.setText(tweet.getProfile().getFormattedScreenName());
        holder.btnFavorite.setImageResource(tweet.getImageResourceFavorite());
        holder.createdAt.setText(tweet.getTimeAgo());
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void addTweets(List<TweetViewModel> tweetViewModels) {
        if (tweetViewModels != null && !tweetViewModels.isEmpty()) {
            tweets.addAll(tweetViewModels);
            removeDuplicates(tweets);
            Collections.sort(tweets);
            notifyDataSetChanged();
        }
    }

    //workaround to remove duplicates tweets, sorry! :(
    public void removeDuplicates(List<TweetViewModel> list) {
        int size = list.size();
        int out = 0;
        {
            final Set<TweetViewModel> encountered = new HashSet<>();
            for (int in = 0; in < size; in++) {
                final TweetViewModel t = list.get(in);
                final boolean first = encountered.add(t);
                if (first) {
                    list.set(out++, t);
                }
            }
        }
        while (out < size) {
            list.remove(--size);
        }
    }

    public boolean isEmpty() {
        return tweets == null || tweets.isEmpty();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        public View layout;

        @BindView(R.id.profile_image)
        public SimpleDraweeView profileImage;

        @BindView(R.id.text)
        public TextView text;

        @BindView(R.id.screen_name)
        public TextView screenName;

        @BindView(R.id.created_at)
        public TextView createdAt;

        @BindView(R.id.name)
        public TextView name;

        @BindView(R.id.favorite)
        public ImageButton btnFavorite;

        private TimelineAdapterListener listener;

        ViewHolder(View view, TimelineAdapterListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.listener = listener;
        }

        @OnClick(R.id.layout)
        public void onClickProfile() {
            TweetViewModel tweet = tweets.get(getAdapterPosition());
            listener.onClickProfile(tweet.getProfile());
        }

        @OnClick(R.id.favorite)
        public void onClickFavorite() {
            TweetViewModel tweet = tweets.get(getAdapterPosition());
            listener.onClickFavorite(tweet);
        }

    }

}

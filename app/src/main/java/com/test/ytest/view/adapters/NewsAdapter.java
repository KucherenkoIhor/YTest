package com.test.ytest.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.test.ytest.R;
import com.test.ytest.model.NewsItem;
import com.test.ytest.shared.CustomTabActivityHelper;
import com.test.ytest.shared.DateUtil;
import com.test.ytest.shared.WebViewFallback;
import com.test.ytest.view.activities.DetailInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igor on 29.10.16.
 */

public class NewsAdapter extends Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> dataSource = null;

    public void setDataSource(List<NewsItem> dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsItem newsItem = dataSource.get(position);
        holder.bind(newsItem);
        holder.itemView.setOnClickListener(view -> {
            Context context = holder.itemView.getContext();
            int primaryColor = ContextCompat.getColor(
                    context,
                    R.color.colorPrimary);
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(primaryColor)
                    .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right)
                    .build();

            CustomTabActivityHelper.openCustomTab(
                    holder.itemView.getContext(), customTabsIntent, newsItem.getWebURL(), new WebViewFallback());
        });

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    static class NewsViewHolder extends ViewHolder {

        @BindView(R.id.image_view)
        protected ImageView imageView;
        @BindView(R.id.headLineTextView)
        protected TextView headLineTextView;
        @BindView(R.id.agencyTextView)
        protected TextView agencyTextView;
        @BindView(R.id.dateTextView)
        protected TextView dateTextView;
        @BindView(R.id.captionTextView)
        protected TextView captionTextView;

        NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(NewsItem newsItem) {
            headLineTextView.setText(newsItem.getHeadLine());
            agencyTextView.setText(itemView.getResources().getString(R.string.view_news_item_agency, newsItem.getAgency()));
            dateTextView.setText(DateUtil.formatDate(newsItem.getDateLine()));
            captionTextView.setText(newsItem.getCaption());
            Glide.with(itemView.getContext())
                    .load(newsItem.getImage().getThumb())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable bitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(
                                            itemView.getResources(),
                                            resource);
                            bitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(bitmapDrawable);
                        }
                    });
        }
    }

}

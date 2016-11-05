package com.test.ytest.view.adapters;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.test.ytest.R;
import com.test.ytest.model.NewsItem;
import com.test.ytest.shared.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.test.ytest.R.id.agencyTextView;
import static com.test.ytest.R.id.captionTextView;
import static com.test.ytest.R.id.dateTextView;
import static com.test.ytest.R.id.headLineTextView;

/**
 * Created by igor on 29.10.16.
 */

public class NewsAdapter extends Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> mDataSource = null;

    public void setDataSource(List<NewsItem> dataSource) {
        this.mDataSource = dataSource;
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
        NewsItem newsItem = mDataSource.get(position);
        holder.bind(newsItem);
        holder.itemView.setOnClickListener(mOnItemClickListener);
    }

    private OnClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if(mDataSource == null)
            return 0;
        return mDataSource.size();
    }

    public static class NewsViewHolder extends ViewHolder {

        private NewsItem mNewsItem = null;

        @BindView(R.id.image_view)
        protected ImageView mImageView;
        @BindView(headLineTextView)
        protected TextView mHeadLineTextView;
        @BindView(agencyTextView)
        protected TextView mAgencyTextView;
        @BindView(dateTextView)
        protected TextView mDateTextView;

        public NewsItem getNewsItem() {
            return mNewsItem;
        }

        @BindView(captionTextView)
        protected TextView mCaptionTextView;

        NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void bind(NewsItem newsItem) {
            mNewsItem = newsItem;
            mHeadLineTextView.setText(newsItem.getHeadLine());

            if(newsItem.getAgency() == null)
                mAgencyTextView.setVisibility(View.GONE);
            else
                mAgencyTextView.setText(itemView.getResources().getString(
                        R.string.view_news_item_agency,
                        newsItem.getAgency()));

            mDateTextView.setText(DateUtil.formatDate(newsItem.getDateLine()));

            mCaptionTextView.setText(newsItem.getCaption());

            Glide.with(itemView.getContext())
                    .load(newsItem.getImage().getThumb())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(mImageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable bitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(
                                            itemView.getResources(),
                                            resource);
                            bitmapDrawable.setCircular(true);
                            mImageView.setImageDrawable(bitmapDrawable);
                        }
                    });
        }
    }

}

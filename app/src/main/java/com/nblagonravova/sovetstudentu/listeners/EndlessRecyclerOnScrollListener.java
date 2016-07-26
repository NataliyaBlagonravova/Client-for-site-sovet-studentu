package com.nblagonravova.sovetstudentu.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int mPreviousTotal = 0;
    private boolean isLoading = true;
    private int mVisibleThreshold = 3;

    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private int mTotalItemCount;

    private int mCurrentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mLinearLayoutManager.getItemCount();
        mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (isLoading) {
            if (mTotalItemCount > mPreviousTotal) {
                isLoading = false;
                mPreviousTotal = mTotalItemCount;
            }
        }
        if (!isLoading && (mTotalItemCount - mVisibleItemCount)
                <= (mFirstVisibleItem + mVisibleThreshold)) {
            // End has been reached

            // Do something
            mCurrentPage++;

            onLoadMore(mCurrentPage);

            isLoading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}

package com.android.browser;


import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.provider.Browser;
import android.provider.BrowserContract;
import android.provider.BrowserContract.Combined;

public class QuickNavigationScreenRight extends LinearLayout implements LoaderCallbacks<Cursor>, OnChildClickListener{

    static final int LOADER_HISTORY = 1;
    static final int LOADER_MOST_VISITED = 2;
    private LayoutInflater inflater;
    private View mRoot ;
    private Activity mActivity;
    private HistoryAdapter mAdapter;
    private UiController mController;
    private BaseUi mBaseUi;
    private ExpandableListView mHistoryList;
    String mMostVisitsLimit;
    public QuickNavigationScreenRight(Activity activity, UiController controller, BaseUi baseUi) {
        super(activity);
        mActivity = activity;
        mController = controller;
        mBaseUi = baseUi;
        this.setBackgroundColor(activity.getResources().getColor(R.color.homepage_color));
        int mvlimit = getResources().getInteger(R.integer.most_visits_limit);
        mMostVisitsLimit = Integer.toString(mvlimit);

        inflater = LayoutInflater.from(activity);
        mRoot = inflater.inflate(R.layout.home_right_history, this);
        mAdapter = new HistoryAdapter(activity);
        mHistoryList = (ExpandableListView) mRoot.findViewById(R.id.history);
        mHistoryList.setAdapter(mAdapter);
        mHistoryList.setOnChildClickListener(this);
        mHistoryList.expandGroup(0);
        mHistoryList.setDivider(activity.getResources().getDrawable(R.drawable.system_pressed));
        mHistoryList.setDividerHeight(4);
        LoaderManager lm = activity.getLoaderManager();
        lm.restartLoader(LOADER_HISTORY, null, this);
        lm.restartLoader(LOADER_MOST_VISITED, null, this);

    }

    static interface HistoryQuery {
        static final String[] PROJECTION = new String[] {
                Combined._ID, // 0
                Combined.DATE_LAST_VISITED, // 1
                Combined.TITLE, // 2
                Combined.URL, // 3
                Combined.FAVICON, // 4
                Combined.VISITS, // 5
                Combined.IS_BOOKMARK, // 6
        };

        static final int INDEX_ID = 0;
        static final int INDEX_DATE_LAST_VISITED = 1;
        static final int INDEX_TITE = 2;
        static final int INDEX_URL = 3;
        static final int INDEX_FAVICON = 4;
        static final int INDEX_VISITS = 5;
        static final int INDEX_IS_BOOKMARK = 6;
    }
    
    private class HistoryAdapter extends DateSortedExpandableListAdapter {

        private Cursor mMostVisited, mHistoryCursor;
        Drawable mFaviconBackground;

        HistoryAdapter(Context context) {
            super(context, HistoryQuery.INDEX_DATE_LAST_VISITED);
            mFaviconBackground = BookmarkUtils.createListFaviconBackground(context);
        }

        @Override
        public void changeCursor(Cursor cursor) {
            mHistoryCursor = cursor;
            super.changeCursor(cursor);
        }

        void changeMostVisitedCursor(Cursor cursor) {
            if (mMostVisited == cursor) {
                return;
            }
            if (mMostVisited != null) {
                mMostVisited.unregisterDataSetObserver(mDataSetObserver);
                mMostVisited.close();
            }
            mMostVisited = cursor;
            if (mMostVisited != null) {
                mMostVisited.registerDataSetObserver(mDataSetObserver);
            }
            notifyDataSetChanged();
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            if (moveCursorToChildPosition(groupPosition, childPosition)) {
                Cursor cursor = getCursor(groupPosition);
                return cursor.getLong(HistoryQuery.INDEX_ID);
            }
            return 0;
        }

        @Override
        public int getGroupCount() {
            return super.getGroupCount() + (!isMostVisitedEmpty() ? 1 : 0);
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (groupPosition >= super.getGroupCount()) {
                if (isMostVisitedEmpty()) {
                    return 0;
                }
                return mMostVisited.getCount();
            }
            return super.getChildrenCount(groupPosition);
        }

        @Override
        public boolean isEmpty() {
            if (!super.isEmpty()) {
                return false;
            }
            return isMostVisitedEmpty();
        }

        private boolean isMostVisitedEmpty() {
            return mMostVisited == null
                    || mMostVisited.isClosed()
                    || mMostVisited.getCount() == 0;
        }

        Cursor getCursor(int groupPosition) {
            if (groupPosition >= super.getGroupCount()) {
                return mMostVisited;
            }
            return mHistoryCursor;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
            LinearLayout item;
            ImageView image;
            TextView text;
            if (groupPosition >= super.getGroupCount()) {
                if (mMostVisited == null || mMostVisited.isClosed()) {
                    throw new IllegalStateException("Data is not valid");
                }
                if (null == convertView || !(convertView instanceof TextView)) {
                    LayoutInflater factory = LayoutInflater.from(getContext());
                    item = (LinearLayout) factory.inflate(R.layout.history_home_right, null);
                } else {
                    item = (LinearLayout) convertView;
                }
                image = (ImageView)item.findViewById(R.id.expandImage);
                if(isExpanded){
                    image.setImageResource(R.drawable.expanded);
                } else {
                    image.setImageResource(R.drawable.collapsed);
                }
                text = (TextView)item.findViewById(R.id.groupName);
                text.setText(R.string.tab_most_visited);
                return item;
            }
            
            if (!mDataValid) throw new IllegalStateException("Data is not valid"); 

            if (null == convertView || !(convertView instanceof TextView)) {
                LayoutInflater factory = LayoutInflater.from(getContext());
                item = (LinearLayout) factory.inflate(R.layout.history_home_right, null);
            } else {
                item = (LinearLayout) convertView;
            }
            String label = getLabel(groupPositionToBin(groupPosition));
            image = (ImageView)item.findViewById(R.id.expandImage);
            if(isExpanded){
                image.setImageResource(R.drawable.expanded);
            } else {
                image.setImageResource(R.drawable.collapsed);
            }
            text = (TextView)item.findViewById(R.id.groupName);
            text.setText(label);
            return item;
        }

        @Override
        boolean moveCursorToChildPosition(
                int groupPosition, int childPosition) {
            if (groupPosition >= super.getGroupCount()) {
                if (mMostVisited != null && !mMostVisited.isClosed()) {
                    mMostVisited.moveToPosition(childPosition);
                    return true;
                }
                return false;
            }
            return super.moveCursorToChildPosition(groupPosition, childPosition);
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            HistoryItem item;
            if (null == convertView || !(convertView instanceof HistoryItem)) {
                item = new HistoryItem(getContext());
                // Add padding on the left so it will be indented from the
                // arrows on the group views.
                item.setPadding(item.getPaddingLeft() + 10,
                        item.getPaddingTop(),
                        item.getPaddingRight(),
                        item.getPaddingBottom());
                item.setFaviconBackground(mFaviconBackground);
            } else {
                item = (HistoryItem) convertView;
            }

            // Bail early if the Cursor is closed.
            if (!moveCursorToChildPosition(groupPosition, childPosition)) {
                return item;
            }

            Cursor cursor = getCursor(groupPosition);
            item.setName(cursor.getString(HistoryQuery.INDEX_TITE));
            String url = cursor.getString(HistoryQuery.INDEX_URL);
            item.setUrl(url);
            byte[] data = cursor.getBlob(HistoryQuery.INDEX_FAVICON);
            if (data != null) {
                item.setFavicon(BitmapFactory.decodeByteArray(data, 0,
                        data.length));
            }
            item.setIsBookmark(cursor.getInt(HistoryQuery.INDEX_IS_BOOKMARK) == 1);
            return item;
        }
    }

    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {
        // TODO Auto-generated method stub
        openUrl(((HistoryItem) v).getUrl());
        return false;
    }

    public void openUrl(String url) {
        if(url!=null && url.length()!=0){
            //  Tab tab = ((UiController)mController).openTab(url, false, true, false, ((UiController)mController).getCurrentTab());
              Tab tab = ((UiController)mController).openTab(url, false, true, false);
              Controller.homepage = false;
              Controller.homepageBack = false;
              mBaseUi.hideHomePage();
          }
    }
    
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO Auto-generated method stub

        Uri.Builder combinedBuilder = Combined.CONTENT_URI.buildUpon();

        switch (id) {
            case LOADER_HISTORY: {
                String sort = Combined.DATE_LAST_VISITED + " DESC";
                String where = Combined.VISITS + " > 0";
                CursorLoader loader = new CursorLoader(mActivity, combinedBuilder.build(),
                        HistoryQuery.PROJECTION, where, null, sort);
                return loader;
            }

            case LOADER_MOST_VISITED: {
                Uri uri = combinedBuilder
                        .appendQueryParameter(BrowserContract.PARAM_LIMIT, mMostVisitsLimit)
                        .build();
                String where = Combined.VISITS + " > 0";
                CursorLoader loader = new CursorLoader(mActivity, uri,
                        HistoryQuery.PROJECTION, where, null, Combined.VISITS + " DESC");
                return loader;
            }
        }
    
        return null;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // TODO Auto-generated method stub
        switch (loader.getId()) {
            case LOADER_HISTORY: {
                mAdapter.changeCursor(data);
                checkIfEmpty();
                break;
            }

            case LOADER_MOST_VISITED: {
                mAdapter.changeMostVisitedCursor(data);

                checkIfEmpty();
                break;
            }

            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub
        
    }
    
    void checkIfEmpty() {
        if (mAdapter.mMostVisited != null && mAdapter.mHistoryCursor != null) {
            // Both cursors have loaded - check to see if we have data
            if (mAdapter.isEmpty()) {
            } else {
                mRoot.findViewById(R.id.history).setVisibility(View.VISIBLE);
            }
        }
    }
}

android.widget.Adapter

    /* AdapterView和其数据之间的桥梁
        Adapter负责
            1. 为每个ui item提供数据
            2. 为每个ui item创建view */
    public interface Adapter {
        {
            /**
             * Register an observer that is called when changes happen to the data used by this adapter.
             * @param observer the object that gets notified when the data set changes.  */
            void registerDataSetObserver(DataSetObserver observer);
            void unregisterDataSetObserver(DataSetObserver observer);
        }

        // 多少条数据
        {
            int getCount();
            boolean isEmpty();
        }

        {
            // 获取对应项目的数据元素
            Object getItem(int position);

            // 获取对应项目的raw id
            long getItemId(int position);

            /**
             * Indicates whether the item ids are stable across changes to the
             * underlying data.
             * @return True if the same id always refers to the same object.
             */
            boolean hasStableIds();
        }

        {
            // 获取对应项的view
            //  @param convertView The old view to reuse, if possible.
            //      Note: You should check that this view is non-null and of an appropriate type before using.
            View getView(int position, View convertView, ViewGroup parent);

            int getItemViewType(int position);
        }
    }

    // 为list view提供数据
    // bridge between a ListView and the data that backs the list
    // Frequently that data comes from a Cursor, but that is not required
    public interface ListAdapter extends Adapter {

        // 是否所有的item都是enable的(可选的和可点击的)
        // (there is no separator. separator是disable的)
        public boolean areAllItemsEnabled();

        // Returns true if the item at the specified position is not a separator.
        // (A separator is a non-selectable, non-clickable item).
        boolean isEnabled(int position);
    }


    // 为spinner提供数据
    // bridge between a android.widget.Spinner and its data
    public interface SpinnerAdapter extends Adapter  {
        /* A spinner adapter allows to define two different views: 
            one that shows the data in the spinner itself and
            one that shows the data in the drop down list when the spinner is pressed */

        /** <p>Get a {@link android.view.View} that displays in the drop down popup
         * the data at the specified position in the data set.</p>
        */
        public View getDropDownView(int position, View convertView, ViewGroup parent);
    }


    // 为list view/spinner提供数据
    // 实现了DataSetObserver相关接口
    public abstract class BaseAdapter implements ListAdapter, SpinnerAdapter 
    {
        // 实现了DataSetObserver相关接口
        {
            private final DataSetObservable mDataSetObservable = new DataSetObservable();
            public void registerDataSetObserver(DataSetObserver observer)
                mDataSetObservable.registerObserver(observer);

            public void unregisterDataSetObserver(DataSetObserver observer)
                mDataSetObservable.unregisterObserver(observer);

            /** 底层数据改变，需要更新ui
             * Notifies the attached observers that the underlying data has been changed
             * and any View reflecting the data set should refresh itself.
             */
            public void notifyDataSetChanged()
                mDataSetObservable.notifyChanged();

            /** 数据已经失效了(比如cursor被设置成了空), 本adapter也没有用了
             * Notifies the attached observers that the underlying data is no longer valid
             * or available. Once invoked this adapter is no longer valid and should
             * not report further data set changes.
             */
            public void notifyDataSetInvalidated()
                mDataSetObservable.notifyInvalidated();
        }
    }

    /** 继承了BaseAdapter, 数据源为cursor
     * Adapter that exposes data from a {@link android.database.Cursor Cursor} to a 
     * {@link android.widget.ListView ListView} widget. The Cursor must include 
     * a column named "_id" or this class will not work.
     */
    public abstract class CursorAdapter extends BaseAdapter implements Filterable,
           CursorFilter.CursorFilterClient {

        protected Context mContext;

        protected Cursor mCursor;
        protected int mRowIDColumn; // "_id"的列号
        protected CursorFilter mCursorFilter;
        protected FilterQueryProvider mFilterQueryProvider;

        // 当content改变的通知到来时，自动调用cursor的requery()
        @Deprecated
        public static final int FLAG_AUTO_REQUERY = 0x01;

        {
           // 是否注册一个content observer到cursor上, 并且当notification到来时调用onNotificationChanged
           public static final int FLAG_REGISTER_CONTENT_OBSERVER = 0x02;

           // 当设置FLAG_REGISTER_CONTENT_OBSERVER时，创建下面的observer
           protected ChangeObserver mChangeObserver;
           protected DataSetObserver mDataSetObserver;
        }

        // 调整cursor到position, 并返回cursor
        public Object getItem(int position)
            mCursor.moveToPosition(position);
            return mCursor;

        // 调整cursor到position, 并返回_id
        public long getItemId(int position)
            if (mCursor.moveToPosition(position))
                return mCursor.getLong(mRowIDColumn);

        public View getView(int position, View convertView, ViewGroup parent) {
            mCursor.moveToPosition(position);
           
            View v;
            if (convertView == null) {
                v = newView(mContext, mCursor, parent);
            } else {
                v = convertView;
            }
            bindView(v, mContext, mCursor);
            return v;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            mCursor.moveToPosition(position);
            View v;
            if (convertView == null) {
                v = newDropDownView(mContext, mCursor, parent);
            } else {
                v = convertView;
            }
            bindView(v, mContext, mCursor);
            return v;
        }

        // 创建item view
        public abstract View newView(Context context, Cursor cursor, ViewGroup parent);

        // 创建drop down view
        public View newDropDownView(Context context, Cursor cursor, ViewGroup parent);

        // 将cursor的数据绑定到view
        public abstract void bindView(View view, Context context, Cursor cursor);

        // Filterable 方法
        public CharSequence convertToString(Cursor cursor)
            return cursor == null ? "" : cursor.toString();

        // 在后台线程执行查询并返回cursor
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (mFilterQueryProvider != null) {
                return mFilterQueryProvider.runQuery(constraint);
            }
            return mCursor;
        }
    }

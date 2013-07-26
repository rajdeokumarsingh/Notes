做一件事情如果什么都对，却得不到正确的结果
    考虑是否设置了正确的权限。

close operation:
    open cursor, close cursor
    open file, close file FIXME: file好像没有close函数

    // ContentProvider.query()
        // before return the cursor
        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);

    // ContentProvider.insert/delete/update
        // before return uri
        // notify observers
        getContext().getContentResolver().notifyChange(noteUri, null);


初始化:
    listView中的每个item都是可以重用的
        在getChildView中获取的item可能是，新创建的或是以前的cache的item。
        如果是cache的，一定要初始化该item
        public View getChildView(int groupPosition, int childPosition, 
            boolean isLastChild, View convertView, ViewGroup parent) {

[Reviewed at 2011-09-05]

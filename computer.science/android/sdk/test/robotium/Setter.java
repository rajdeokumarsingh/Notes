
// 设置DataPicker的时间
public void setDatePicker(final DatePicker datePicker, final int year, final int monthOfYear, final int dayOfMonth)
    activityUtils.getCurrentActivity(false).runOnUiThread(new Runnable() {
            public void run() { datePicker.updateDate(year, monthOfYear, dayOfMonth); });
    }

// 设置timePicker的时间
public void setTimePicker(final TimePicker timePicker, final int hour, final int minute)
    activityUtils.getCurrentActivity(false).runOnUiThread(new Runnable() {
            public void run() { timePicker.setCurrentHour(hour); timePicker.setCurrentMinute(minute);
            });
    }


// Sets the progress of a given {@link ProgressBar}. Examples are SeekBar and RatingBar.
public void setProgressBar(final ProgressBar progressBar,final int progress) {
    activityUtils.getCurrentActivity(false).runOnUiThread(new Runnable() {
            public void run() { progressBar.setProgress(progress); });
    }

// 开启或关闭SlidingDrawer
public void setSlidingDrawer(final SlidingDrawer slidingDrawer, final int status){
    activityUtils.getCurrentActivity(false).runOnUiThread(new Runnable() {
        public void run() {
            switch (status) {
                case CLOSED: slidingDrawer.close(); break;
                case OPENED: slidingDrawer.open(); break;
            });
        }
    }


package com.sdk.example.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.sdk.example.R;

public class ActionBarUsageActivity extends Activity implements OnQueryTextListener {
    TextView mSearchText;
    int mSortMode = -1;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchText = new TextView(this);
        setContentView(mSearchText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
        if (mSortMode != -1) {
            Drawable icon = menu.findItem(mSortMode).getIcon();
            menu.findItem(R.id.action_sort).setIcon(icon);
        }
        return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
        newText = newText.isEmpty() ? "" : "Query so far: " + newText;
        mSearchText.setText(newText);
        return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        return true;
	}
	
    // This method is specified as an onClick handler in the menu xml and will
    // take precedence over the Activity's onOptionsItemSelected method.
    // See res/menu/actions.xml for more info.
    public void onSort(MenuItem item) {
        mSortMode = item.getItemId();
        // Request a call to onPrepareOptionsMenu so we can change the sort icon
        invalidateOptionsMenu();
    }

}

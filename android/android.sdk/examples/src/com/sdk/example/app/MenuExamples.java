package com.sdk.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sdk.example.R;

/**
 * A Menu xml has "menu", "group" and "item" elements.
 * 	<menu>
 * 		<group>
 * 			<item .../>
 * 			<item .../>
 * 			<item>
 * 				<!-- submenu -->
 * 				<menu>
 * 					<group>
 * 					</group>
 * 				</menu>
 * 			</item>
 * 		</group>
 * </menu>
 * 
 * file:///home/jiangrui/android/android-sdk/docs/guide/topics/resources/menu-resource.html
 * file:///home/jiangrui/android/android-sdk/docs/guide/topics/ui/menus.html
 * 
 * @author jiangrui
 *
 */
public class MenuExamples extends Activity {
	/**
	 * Different example menu resources.
	 */
	private static final int sMenuExampleResources[] = { R.menu.title_only,
			R.menu.title_icon, R.menu.submenu, R.menu.groups, R.menu.checkable,
			R.menu.shortcuts, R.menu.order, R.menu.category_order,
			R.menu.visible, R.menu.disabled };

	/**
	 * Names corresponding to the different example menu resources.
	 */
	private static final String sMenuExampleNames[] = { "Title only",
			"Title and Icon", "Submenu", "Groups", "Checkable", "Shortcuts",
			"Order", "Category and Order", "Visible", "Disabled" };

	/**
	 * Lets the user choose a menu resource.
	 */
	private Spinner mSpinner;

	/**
	 * Shown as instructions.
	 */
	private TextView mInstructionsText;

	/**
	 * Safe to hold on to this.
	 */
	private Menu mMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, sMenuExampleNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner = new Spinner(this);
		mSpinner.setId(R.id.spinner);
		mSpinner.setAdapter(adapter);

		layout.addView(mSpinner, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		// Create help text
		mInstructionsText = new TextView(this);
		mInstructionsText.setText(getResources().getString(
				R.string.menu_from_xml_instructions_press_menu));

		// Add the help, make it look decent
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(10, 10, 10, 10);
		layout.addView(mInstructionsText, lp);

		// Set the layout as our content view
		setContentView(layout);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(
//				sMenuExampleResources[mSpinner.getSelectedItemPosition()], menu);
//
//		mMenu = menu;
//		
//		return true;
//	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(
				sMenuExampleResources[mSpinner.getSelectedItemPosition()], menu);

		mMenu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // For "Title only": Examples of matching an ID with one assigned in
            //                   the XML
            case R.id.jump:
                Toast.makeText(this, "Jump up in the air!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.dive:
                Toast.makeText(this, "Dive into the water!", Toast.LENGTH_SHORT).show();
                return true;

            // For "Groups": Toggle visibility of grouped menu items with
            //               nongrouped menu items
            case R.id.browser_visibility:
                // The refresh item is part of the browser group
                final boolean shouldShowBrowser = !mMenu.findItem(R.id.refresh).isVisible();
                mMenu.setGroupVisible(R.id.browser, shouldShowBrowser);
                break;
                
            case R.id.email_visibility:
                // The reply item is part of the email group
                final boolean shouldShowEmail = !mMenu.findItem(R.id.reply).isVisible();
                mMenu.setGroupVisible(R.id.email, shouldShowEmail);
                break;
                
            // Generic catch all for all the other menu resources
            default:
                // Don't toast text when a submenu is clicked
                if (!item.hasSubMenu()) {
                    Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }
        return false;
	}

}

package android.sdk.example;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jiangrui on 7/1/13.
 */
public class PrefActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefence_activity);

        final EditText editName = (EditText) findViewById(R.id.edit_txt_name);
        final EditText editScore = (EditText) findViewById(R.id.edit_txt_score);

        TextView textView = (TextView)findViewById(R.id.txt_view);
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        textView.setText(
            getString(R.string.best_player) + sp.getString(getString(R.string.pref_key_name), "") + ", " +
            getString(R.string.best_score) + sp.getInt(getString(R.string.pref_key_score), 0));

        Button btn = (Button) findViewById(R.id.btn_pref_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Caution: If you create a shared preferences file with MODE_WORLD_READABLE
                // or MODE_WORLD_WRITEABLE, then any other apps that know the file
                // identifier can access your data.

                SharedPreferences defaultPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = defaultPref.edit();
                editor.putString(getString(R.string.pref_key_name),
                        editName.getText().toString());

                int score = 0;
                try {
                    score = Integer.valueOf(editScore.getText().toString());
                } catch (NumberFormatException ne) {
                   ne.printStackTrace();
                }
                editor.putInt(getString(R.string.pref_key_score), score);
                editor.commit();

                Toast.makeText(PrefActivity.this, getString(R.string.data_saved), Toast.LENGTH_SHORT).show();
                // SharedPreferences namePref = getSharedPreferences(
                        // getString(R.string.preference_file_key), MODE_PRIVATE);
            }
        });
    }
}
package android.sdk.training;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edit = (EditText) findViewById(R.id.edit_view);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,
                        edit.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Button btnActivity = (Button) findViewById(R.id.button_activity);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                intent.putExtra("message", edit.getText().toString());
                startActivity(intent);
            }
        });
    }
}

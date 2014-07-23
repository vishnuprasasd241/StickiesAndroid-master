package com.projects.stickies.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.stickies.R;
import com.projects.stickies.dbmanager.DBHelper;
import com.projects.stickies.models.Stickies;

import java.text.SimpleDateFormat;

/**
 * Created by admin on 21/07/14.
 */
public class StickieMainActivity extends Activity {

    private TextView textViewTime;
    private EditText editTextNote;
    private Stickies stickies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickie_main);

        stickies = getIntent().getParcelableExtra("stickie");
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        textViewTime.setText(new SimpleDateFormat("dd MMM yyyy hh:mm:ss").format(stickies.getTime()));
        editTextNote.setText(stickies.getText());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stickie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {

        stickies.setText(editTextNote.getText().toString());
        DBHelper helper = new DBHelper(getApplicationContext());

        Stickies existing = helper.getStickie(stickies.getTime());
        if (existing == null)
            helper.insertStickie(stickies);
        else
            helper.updateStickie(stickies);

        Toast toast = Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}

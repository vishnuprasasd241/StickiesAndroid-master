package com.projects.stickies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.projects.stickies.activities.StickieMainActivity;
import com.projects.stickies.adapters.StickiesListAdapter;
import com.projects.stickies.dbmanager.DBHelper;
import com.projects.stickies.models.Stickies;

import java.util.ArrayList;
import java.util.Calendar;


public class ListActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private StickiesListAdapter adapter;
    private DBHelper helper;
    ArrayList<Stickies> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        helper = new DBHelper(getApplicationContext());

        list = helper.getAllStickie();
        adapter = new StickiesListAdapter(list, getApplicationContext());
        listView = (ListView) findViewById(R.id.listViewStickies);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        list = helper.getAllStickie();
        adapter.setList(list);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, StickieMainActivity.class);
            Stickies stickies = new Stickies(Calendar.getInstance().getTimeInMillis());
            intent.putExtra("stickie", stickies);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, StickieMainActivity.class);
        intent.putExtra("stickie", list.get(i));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(helper.deleteStickie(list.get(i).getTime()) == 1) {
            list = helper.getAllStickie();
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }
        return true;
    }

}

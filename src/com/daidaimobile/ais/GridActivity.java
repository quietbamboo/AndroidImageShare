package com.daidaimobile.ais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.daidaimobile.ais.model.ImageAdapter;

public class GridActivity extends Activity {
	
	GridView grid = null;
	ImageAdapter adapter = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        
        adapter = new ImageAdapter(GridActivity.this, "grid");
        
        grid = (GridView)findViewById(R.id.gridView);
        grid.setAdapter(adapter);
        
        grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long id) {
				Intent i = new Intent(GridActivity.this, GalleryActivity.class);
				i.putExtra("selectedIntex", pos);
				startActivity(i);
			}
		});
    }
}
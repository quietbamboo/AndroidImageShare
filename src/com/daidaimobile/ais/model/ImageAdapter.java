package com.daidaimobile.ais.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.daidaimobile.ais.R;

/**
 * hardcoded image adapter to showcase the use of grid and gallery project 
 */
public class ImageAdapter extends BaseAdapter{
	
	private static LayoutInflater inflater = null;
	private Activity activity;
	
	private String mode = "";
	
	int[] images = {
			R.drawable.buds, R.drawable.cherry_34,
			R.drawable.clouds_2, R.drawable.coffee_beans_2,
			R.drawable.death_valley_sand_dunes, R.drawable.morning_glory_pool,
			R.drawable.pink_flowers, R.drawable.sun_flower,
			R.drawable.sunrise_3, R.drawable.yellow_rose_3,
			};
	
	public ImageAdapter(Activity act, String mode){
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		activity = act;
		this.mode = mode;
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return new Integer(images[position]);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(mode.equalsIgnoreCase("grid")){
			if (view == null) {
				view = inflater.inflate(R.layout.each_image, null);
			}
			ImageView iv = (ImageView)view.findViewById(R.id.imageView);
			iv.setImageResource(images[position]);
		} else if(mode.equalsIgnoreCase("gallery")){
			if (view == null) {
				view = inflater.inflate(R.layout.each_image_gallery, null);
			}
			ImageView iv = (ImageView)view.findViewById(R.id.imageView);
			iv.setImageResource(images[position]);
		}
		return view;
	}

}

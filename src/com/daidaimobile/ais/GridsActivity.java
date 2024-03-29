package com.daidaimobile.ais;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daidaimobile.ais.Constants.Extra;
import com.daidaimobile.ais.photo.PhotoActivity;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.OnScrollSmartOptions;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class GridsActivity extends BaseActivity {


	private AdView adView;
	static String[] urls;
	static String[] descriptions;

	OnScrollSmartOptions smartOptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);

		Bundle bundle = getIntent().getExtras();
		urls = bundle.getStringArray(Extra.URLS);
		descriptions = bundle.getStringArray(Extra.DESCRIPTIONS);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.stub_image)
			.showImageForEmptyUri(R.drawable.image_for_empty_url)
			.cacheInMemory()
			.cacheOnDisc()
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		smartOptions = new OnScrollSmartOptions(options);

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter());
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImageGalleryActivity(position);
			}
		});
		gridView.setOnScrollListener(smartOptions);
		
		// Admob
		adView = new AdView(this, AdSize.BANNER, Def.ADMOB_ID);
		LinearLayout layout = (LinearLayout)findViewById(R.id.grid);
		layout.addView(adView);
		adView.loadAd(new AdRequest());
	}

	private void startImageGalleryActivity(int position) {
		Intent intent = new Intent(this, PhotoActivity.class);
		intent.putExtra(Extra.URLS, urls);
		intent.putExtra(Extra.DESCRIPTIONS, descriptions);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return urls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageView;
			if (convertView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
			} else {
				imageView = (ImageView) convertView;
			}

			imageLoader.displayImage(urls[position], imageView, smartOptions.getOptions());

			return imageView;
		}
	}
}
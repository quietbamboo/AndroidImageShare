package com.daidaimobile.ais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.daidaimobile.ais.Constants.Extra;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class HomeActivity extends BaseActivity {

	private AdView adView;
	
	public ListLoader ll_recent_like;
	public ListLoader ll_recent;
	public ListLoader ll_top; 
	public ListLoader ll_viral_hour;
	public ListLoader ll_viral_day;   
	public ListLoader ll_viral_week; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_home);

		// Admob
		adView = new AdView(this, AdSize.BANNER, Def.ADMOB_ID);
		LinearLayout layout = (LinearLayout)findViewById(R.id.home);
		layout.addView(adView);
		adView.loadAd(new AdRequest());
	}


	public void openGridView(String url) {
		ListLoader.start(url);
		Intent intent = new Intent(this, GridsActivity.class);
		intent.putExtra(Extra.URLS, ListLoader.urls);
		intent.putExtra(Extra.DESCRIPTIONS, ListLoader.descriptions);
		startActivity(intent);
	}
	
	public void onRecentLikeClick(View view) {
		openGridView(ListLoader.RECENT_LIKE_URL);
	}
	
	public void onRecentClick(View view) {
		openGridView(ListLoader.RECENT_URL);
	}
	
	public void onTopClick(View view) {
		openGridView(ListLoader.TOP_URL);
	}
	
	public void onViralHourClick(View view) {
		openGridView(ListLoader.VIRAL_HOUR_URL);
	}
	
	public void onViralDayClick(View view) {
		openGridView(ListLoader.VIRAL_DAY_URL);
	}
	
	public void onViralWeekClick(View view) {
		openGridView(ListLoader.VIRAL_WEEK_URL);
	}

	/*public void onImageListClick(View view) {
		Intent intent = new Intent(this, ImageListActivity.class);
		intent.putExtra(Extra.URLS, Constants.IMAGES);
		startActivity(intent);
	}

	public void onImagePagerClick(View view) {
		Intent intent = new Intent(this, PhotoActivity.class);
		intent.putExtra(Extra.URLS, Constants.IMAGES);
		startActivity(intent);
	}

	public void onImageGalleryClick(View view) {
		Intent intent = new Intent(this, ImageGalleryActivity.class);
		intent.putExtra(Extra.URLS, Constants.IMAGES);
		startActivity(intent);
	}*/
}
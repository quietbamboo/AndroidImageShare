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
	public ListLoader ll; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ll = new ListLoader(ListLoader.RECENT_LIKE_URL);
		ll.start();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_home);

		// Admob
		adView = new AdView(this, AdSize.BANNER, Def.ADMOB_ID);
		LinearLayout layout = (LinearLayout)findViewById(R.id.home);
		layout.addView(adView);
		adView.loadAd(new AdRequest());
	}


	public void onImageGridClick(View view) {
		try {
			ll.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Intent intent = new Intent(this, GridsActivity.class);
		intent.putExtra(Extra.IMAGES, ll.urls);
		startActivity(intent);
	}

	/*public void onImageListClick(View view) {
		Intent intent = new Intent(this, ImageListActivity.class);
		intent.putExtra(Extra.IMAGES, Constants.IMAGES);
		startActivity(intent);
	}

	public void onImagePagerClick(View view) {
		Intent intent = new Intent(this, PhotoActivity.class);
		intent.putExtra(Extra.IMAGES, Constants.IMAGES);
		startActivity(intent);
	}

	public void onImageGalleryClick(View view) {
		Intent intent = new Intent(this, ImageGalleryActivity.class);
		intent.putExtra(Extra.IMAGES, Constants.IMAGES);
		startActivity(intent);
	}*/
}
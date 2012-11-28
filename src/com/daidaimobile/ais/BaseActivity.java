package com.daidaimobile.ais;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public abstract class BaseActivity extends Activity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private boolean instanceStateSaved;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_clear_cache:
				imageLoader.clearMemoryCache();
				imageLoader.clearDiscCache();
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Your device now has more free space", Toast.LENGTH_SHORT);
				toast.show();
				return true;
			case R.id.item_rate_us:
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, 
						Uri.parse("https://play.google.com/store/apps/details?id=com.daidaimobile.ais"));
				startActivity(browserIntent);
				return true;
			case R.id.item_twitter:
				Intent twitterIntent = new Intent(Intent.ACTION_VIEW, 
						Uri.parse("https://twitter.com/AsianHots"));
				startActivity(twitterIntent);
				return true;
			default:
				return false;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}

	@Override
	protected void onDestroy() {
		if (!instanceStateSaved) {
			imageLoader.stop();
		}
		super.onDestroy();
	}
}

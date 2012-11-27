package com.daidaimobile.ais;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daidaimobile.ais.Constants.Extra;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class PhotoActivity extends BaseActivity {

	DisplayImageOptions options;
	private AdView adView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_pager);

		Bundle bundle = getIntent().getExtras();
		String[] urls = bundle.getStringArray(Extra.URLS);
		String[] descriptions = bundle.getStringArray(Extra.DESCRIPTIONS);
		int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);

		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.image_for_empty_url)
		.cacheOnDisc()
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(urls, descriptions));
		pager.setCurrentItem(pagerPosition);

		// Admob
		LinearLayout layout = (LinearLayout) findViewById(R.id.photo);
		adView = new AdView(this, AdSize.BANNER, Def.ADMOB_ID);
		layout.addView(adView);
		adView.loadAd(new AdRequest());

	}
	
	private class HjxImageLoadingListener extends SimpleImageLoadingListener {
		
		private ProgressBar spinner;
		private ImageView imageView;
		private String desc;
		
		public HjxImageLoadingListener(ProgressBar spinner, ImageView imageView, String desc) {
			this.spinner = spinner;
			this.imageView = imageView;
			this.desc = desc;
		}
		
		@Override
		public void onLoadingStarted() {
			spinner.setVisibility(View.VISIBLE);
		}

		@Override
		public void onLoadingFailed(FailReason failReason) {
			String message = null;
			switch (failReason) {
			case IO_ERROR:
				message = "Input/Output error";
				break;
			case OUT_OF_MEMORY:
				message = "Out Of Memory error";
				break;
			case UNKNOWN:
				message = "Unknown error";
				break;
			}
			Toast.makeText(PhotoActivity.this, message, Toast.LENGTH_SHORT).show();
			spinner.setVisibility(View.GONE);
			imageView.setImageResource(android.R.drawable.ic_delete);
		}

		@Override
		public void onLoadingComplete(Bitmap loadedImage) {
			spinner.setVisibility(View.GONE);
			//show descriptions here
			Toast.makeText(PhotoActivity.this, desc, Toast.LENGTH_LONG).show();
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] urls;
		private String[] descriptions;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] urls, String[] descriptions) {
			this.urls = urls;
			this.descriptions = descriptions;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return urls.length;
		}

		@Override
		public Object instantiateItem(View view, int position) {
			final View imageLayout = inflater.inflate(R.layout.item_pager_image, null);
			final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			imageLoader.displayImage(urls[position].replaceFirst("thumbnail", "upload"),
					imageView, options, 
					new HjxImageLoadingListener(spinner, imageView, descriptions[position]));
			((ViewPager) view).addView(imageLayout, 0);

			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}

	}
}
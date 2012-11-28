package com.daidaimobile.ais.photo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daidaimobile.ais.BaseActivity;
import com.daidaimobile.ais.Constants.Extra;
import com.daidaimobile.ais.Def;
import com.daidaimobile.ais.R;
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
	private int currentPosition;
	private QuickAction quickAction;
	

	private class MyPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
		private String[] descriptions;

		public MyPageChangeListener(String[] descriptions) {
			this.descriptions = descriptions;
		}

		@Override
		public void onPageSelected(int position) {
			//show descriptions here
			//haven't sort out the description problem
			//Toast.makeText(PhotoActivity.this, descriptions[position], Toast.LENGTH_LONG).show();
			currentPosition = position;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_pager);

		Bundle bundle = getIntent().getExtras();
		String[] urls = bundle.getStringArray(Extra.URLS);
		String[] descriptions = bundle.getStringArray(Extra.DESCRIPTIONS);
		int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);
		currentPosition = pagerPosition;

		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.image_for_empty_url)
		.cacheOnDisc()
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(urls));
		pager.setOnPageChangeListener(new MyPageChangeListener(descriptions));
		pager.setCurrentItem(pagerPosition);
		//Toast.makeText(PhotoActivity.this, descriptions[pagerPosition], Toast.LENGTH_LONG).show();

		// Admob
		LinearLayout layout = (LinearLayout) findViewById(R.id.photo);
		adView = new AdView(this, AdSize.BANNER, Def.ADMOB_ID);
		layout.addView(adView);
		adView.loadAd(new AdRequest());
		

		ActionItem bgAction = new ActionItem();
		bgAction.setTitle("Set background");
		bgAction.setIcon(getResources().getDrawable(R.drawable.ic_bg));
		
		ActionItem downAction = new ActionItem();
		downAction.setTitle("Download");
		downAction.setIcon(getResources().getDrawable(R.drawable.ic_down));
		
		quickAction = new QuickAction(this);
		
		quickAction.addActionItem(bgAction);
		quickAction.addActionItem(downAction);
		
		//setup the action item click listener
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
			@Override
			public void onItemClick(int pos) {
				if (pos == 0) { //Add item selected
					Toast.makeText(PhotoActivity.this, "Add item selected", Toast.LENGTH_SHORT).show();
				} else if (pos == 1) { //Accept item selected
					Toast.makeText(PhotoActivity.this, "Accept item selected", Toast.LENGTH_SHORT).show();
				} else if (pos == 2) { //Upload item selected
					Toast.makeText(PhotoActivity.this, "Upload items selected", Toast.LENGTH_SHORT).show();
				}	
			}
		});
	}


	private class ImagePagerAdapter extends PagerAdapter {

		private String[] urls;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] urls) {
			this.urls = urls;
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
			
			imageView.setLongClickable(true);
			imageView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					System.out.println("LONG CLICK " + currentPosition);
					Toast.makeText(PhotoActivity.this, "long click " + currentPosition, Toast.LENGTH_SHORT).show();
					
					quickAction.show(v);
					quickAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
					return true;
				}
			});


			imageLoader.displayImage(urls[position].replaceFirst("thumbnail", "upload"),
					imageView, options, new SimpleImageLoadingListener () {

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
				}
			});
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
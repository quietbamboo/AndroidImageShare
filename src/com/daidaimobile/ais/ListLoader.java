/****************************
 *
 * @Date: Nov 26, 2012
 * @Time: 5:08:56 AM
 * @Author: Junxian Huang
 *
 ****************************/
package com.daidaimobile.ais;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListLoader extends Thread {

	public static final String RECENT_LIKE_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=recent.like&prefix=hjx";
	public static final String RECENT_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=recent&prefix=hjx";
	public static final String TOP_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=top&prefix=hjx";
	public static final String VIRAL_HOUR_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=viral.hour&prefix=hjx";
	public static final String VIRAL_DAY_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=viral.day&prefix=hjx";
	public static final String VIRAL_WEEK_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=viral.week&prefix=hjx";
	
	public static final String SEPARATOR_MAIN = "_-_-_";
	public static final String SEPARATOR_SUB = "-----";
	
	public String listUrl;
	public String[] urls;
	
	public ListLoader(String url) {
		this.listUrl = url;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(listUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			readStream(con.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readStream(InputStream in) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			String[] items = sb.toString().split(SEPARATOR_MAIN);
			urls = new String[items.length];
			for (int i = 0 ; i < items.length ; i++) {
				urls[i] = items[i].split(SEPARATOR_SUB)[0].replaceFirst("upload", "thumbnail");
			}
			GridsActivity.imageUrls = urls;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	} 
}

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
	public static final String RECENT_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=all&prefix=hjx";
	public static final String TOP_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=top&prefix=hjx";
	public static final String VIRAL_HOUR_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=viral.hour&prefix=hjx";
	public static final String VIRAL_DAY_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=viral.day&prefix=hjx";
	public static final String VIRAL_WEEK_URL = "http://iphone.dotaart.com/asian/new/list.php?tag=viral.week&prefix=hjx";
	
	public static final int GROUPID_RECENT_LIKE = 0;
	public static final int GROUPID_RECENT = 1;
	public static final int GROUPID_TOP = 2;
	public static final int GROUPID_VIRAL_HOUR = 3;
	public static final int GROUPID_VIRAL_DAY = 4;
	public static final int GROUPID_VIRAL_WEK = 5;
	
	public static final String SEPARATOR_MAIN = "_-_-_";
	public static final String SEPARATOR_SUB = "-----";
	
	public String listUrl;
	public String[] urls;
	public String[] descriptions;
	
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
			descriptions = new String[items.length]; 
			for (int i = 0 ; i < items.length ; i++) {
				String[] parts = items[i].split(SEPARATOR_SUB);
				urls[i] = parts[0].replaceFirst("upload", "thumbnail");
				if (parts.length == 2) {
					descriptions[i] = parts[1];
					System.out.println(descriptions[i]);
				}
				else
					descriptions[i] = "";
			}
			//GridsActivity.urls = urls;
			
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

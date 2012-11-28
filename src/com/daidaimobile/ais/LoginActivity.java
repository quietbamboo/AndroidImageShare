/****************************
 *
 * @Date: Nov 27, 2012
 * @Time: 9:19:31 PM
 * @Author: Junxian Huang
 *
 ****************************/
package com.daidaimobile.ais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml
		setContentView(R.layout.login);

		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		registerScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
		
		TextView forget = (TextView) findViewById(R.id.forget_password);
		forget.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
				String emailList[] = { "asianhots@gmail.com"};  
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailList);  
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[Forget Password] Asian Hots Pro Android");  
				emailIntent.setType("plain/text");  
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "*** TYPE IN YOUR ACCOUNT EMAIL HERE AND YOUR PASSWORD WILL BE SENT TO THAT EMAIL ADDRESS *** ");  
				startActivity(emailIntent);  
			}
		});
	}
}
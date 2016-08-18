/*******************************************************************************
 * Copyright (c) 2015 btows.com.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.cleanwiz.applock.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.cleanwiz.applock.R;
import com.cleanwiz.applock.ui.BaseActivity;
import com.cleanwiz.applock.utils.SharedPreferenceUtil;

public class SplashActivity extends BaseActivity {

	public static final int SPLASH_DONE = 0;
	private SplashHandler handler;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handler = new SplashHandler();
		Message msg = new Message();
		msg.what = SPLASH_DONE;
		handler.sendMessage(msg);
		if (SharedPreferenceUtil.readEnterFlag()) {
			startService(new Intent("com.cleanwiz.applock.service.LockService")
					.setPackage("com.cleanwiz.applock"));
		}
	}

	private void goToPassword() {
		if (SharedPreferenceUtil.readIsFirst()) {
			startActivity(new Intent(this, StepActivity.class));
			finish();
		} else if (SharedPreferenceUtil.readIsNumModel()) {
			startActivity(new Intent(this, NumberCheckActivity.class));
			finish();
		} else {
			startActivity(new Intent(this, GestureCheckActivity.class));
			finish();
		}
	}

	@SuppressLint("HandlerLeak")
	class SplashHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SPLASH_DONE: {
				goToPassword();
				break;
			}
			default:
				break;
			}
			super.handleMessage(msg);
		}

	}

	public void createDeskShortCut() {
		SharedPreferenceUtil.editShortCut(true);
		Intent shortcutIntent = new Intent();
		shortcutIntent.setClass(this, SplashActivity.class);
		shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		shortcutIntent.setAction("android.intent.action.MAIN");
		shortcutIntent.addCategory("android.intent.category.LAUNCHER");

		Intent resultIntent = new Intent();
		resultIntent.putExtra("duplicate", false);
		resultIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(this,
						R.drawable.ic_launcher));
		resultIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));
		resultIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

		resultIntent
				.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
		sendBroadcast(resultIntent);
		resultIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		sendBroadcast(resultIntent);
	}

}

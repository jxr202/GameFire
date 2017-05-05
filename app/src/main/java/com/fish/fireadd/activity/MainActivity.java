package com.fish.fireadd.activity;

import com.fish.fireadd.view.GameView;
import com.fish.fireadd.constant.Constant;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class MainActivity extends Activity
{
	
	public Handler hd = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		goToStartView();
	}
	
	
	/**
	 *  进入游戏开始页面
	 */
	private void goToStartView()
	{
		GameView game = new GameView(this);
		setContentView(game);
	}
}

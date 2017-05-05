package com.fish.fireadd.activity;

import com.fish.fireadd.view.FlashView;
import com.fish.fireadd.view.GameView;
import com.fish.fireadd.view.WelcomeView;
import com.fish.fireadd.constant.AudioUtil;
import com.fish.fireadd.constant.Constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity
{
	private MediaPlayer media;
	
	public Handler hd = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				goToWelcomeView();
				break;
			case Constant.GAME_START:
				goToGameingView();
				break;
			case Constant.GAME_HELP:
				goToHelpView();
				break;
			case Constant.GAME_ABOUT:
				goToAboutView();
				break;
			case Constant.GAME_EXIT:
				goToExitView();
				break;
			case Constant.GAME_PASS_ALL:
				goToPassView();
				break;
			case Constant.GAME_OVER:
				goToFailView();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		goToFlashView();
	}
	
	
	/**
	 *  进入播放动画View
	 */
	private void goToFlashView()
	{
		FlashView flashView = new FlashView(this);
		setContentView(flashView);
		//一进来就播放音乐
		media = AudioUtil.PlayMusicLoop(this, R.raw.powerup);
	}

	/**
	 *  进入欢迎页面
	 */
	private void goToWelcomeView()
	{
		WelcomeView wel = new WelcomeView(this);
		setContentView(wel);
		//播放音乐
		media.stop();
		media = AudioUtil.PlayMusicLoop(this, R.raw.bg_welcome);
	}

	/**
	 *  进入游戏开始页面
	 */
	private void goToGameingView()
	{
		GameView game = new GameView(this);
		setContentView(game);
	}

	/**
	 * 进入帮助页面
	 */
	private void goToHelpView()
	{
		setContentView(R.layout.game_help);
		View v = findViewById(R.id.ll_bg);
		v.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				goToWelcomeView();
			}
		});
	}

	/**
	 * 进入关于页面
	 */
	private void goToAboutView()
	{
		setContentView(R.layout.game_about);
		View v = findViewById(R.id.ll_bg);
		v.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				goToWelcomeView();
			}
		});
	}
	
	/**
	 * 通过了所有关卡
	 */
	private void goToPassView()
	{
		setContentView(R.layout.game_pass);
		View v = findViewById(R.id.ll_bg);
		v.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//这里要返回到欢迎页面
				new AlertDialog.Builder(MainActivity.this).setTitle("提示")
				.setMessage("返回上一级页面?").setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						goToWelcomeView();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						return;
					}
				}).show();
			}
		});
	}
	
	/**
	 * 游戏失败，进入失败画面
	 */
	private void goToFailView()
	{
		setContentView(R.layout.game_over);
		View v = findViewById(R.id.ll_bg);
		v.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//这里要返回到欢迎页面
				new AlertDialog.Builder(MainActivity.this).setTitle("提示")
				.setMessage("返回上一级页面?").setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						goToWelcomeView();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						return;
					}
				}).show();
			}
		});
	}
	
	/**
	 * 退出游戏
	 */
	private void goToExitView()
	{
		//这里要返回到欢迎页面
		new AlertDialog.Builder(MainActivity.this).setTitle("提示")
		.setMessage("是否退出程序?").setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				System.exit(0);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				return;
			}
		}).show();
		
	}
}

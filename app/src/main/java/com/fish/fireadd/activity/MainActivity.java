package com.fish.fireadd.activity;

import com.fish.fireadd.view.FlashView;
import com.fish.fireadd.view.GameView;
import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.constant.MediaPlayerUtil;
import com.fish.fireadd.constant.Sound;
import com.fish.fireadd.constant.SoundPoolUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity
{

	public SoundPoolUtil soundPool;
	public MediaPlayerUtil mediaPlayer;
	public boolean playMusic = false;
	public boolean deepPage = false;

	public Handler hd = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			switch (message.what)
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
			return false;
		}
	});

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//初始化SoundPoolUtil并加载资源
		soundPool = SoundPoolUtil.getInstance(this);
		//初始化MediaPlayerUtil
		mediaPlayer = MediaPlayerUtil.getInstance(this);
		//设置当前调整音量大小只是针对媒体音乐进行调整
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		goToFlashView();
	}


	/**
	 *  进入播放动画View
	 */
	private void goToFlashView()
	{
		FlashView flashView = new FlashView(this);
		setContentView(flashView);
		//一进来就播放音乐 无效？
		soundPool.play(Sound.flash);
	}

	/**
	 *  进入欢迎页面
	 */
	private void goToWelcomeView()
	{
		//WelcomeView wel = new WelcomeView(this);
		//setContentView(wel);
		deepPage = false;
		setContentView(R.layout.welcome);
		//播放音乐
		if (playMusic)
		{
			mediaPlayer.playLoop(R.raw.bg_welcome);
		}
	}

	public void start(View view)
	{
		hd.sendEmptyMessage(Constant.GAME_START);
	}

	public void help(View view)
	{
		hd.sendEmptyMessage(Constant.GAME_HELP);
	}

	public void about(View view)
	{
		hd.sendEmptyMessage(Constant.GAME_ABOUT);
	}

	public void exit(View view)
	{
		hd.sendEmptyMessage(Constant.GAME_EXIT);
	}

	/**
	 *  进入游戏开始页面
	 */
	private void goToGameingView()
	{
		deepPage = true;
		GameView game = new GameView(this);
		setContentView(game);
	}

	/**
	 * 进入帮助页面
	 */
	private void goToHelpView()
	{
		deepPage = true;
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
		deepPage = true;
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
		//播放背景音乐
		if (playMusic)
		{
			mediaPlayer.playLoop(R.raw.bg_welcome);
		}
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
				}).setNegativeButton("取消", null).show();
			}
		});
	}

	/**
	 * 游戏失败，进入失败画面
	 */
	private void goToFailView()
	{
		//播放背景音乐
		if (playMusic)
		{
			mediaPlayer.playLoop(R.raw.bg_welcome);
		}
		deepPage = true;
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
				}).setNegativeButton("取消", null).show();
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
		}).setNegativeButton("取消", null).show();

	}


	/**
	 * 拦截返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (deepPage)
			{
				goToWelcomeView();
			}
			else
			{
				goToExitView();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null)
		{
			mediaPlayer.release();
		}
        super.onDestroy();
    }
}

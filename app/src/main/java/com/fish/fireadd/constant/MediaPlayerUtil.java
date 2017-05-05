package com.fish.fireadd.constant;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerUtil
{
	private static MediaPlayerUtil instance;
	private Context context;
	private MediaPlayer mediaPlayer;
	
	private boolean isPlaying;
	private int playingId;
	
	/**
	 * 在私有构造方法中初始化数据，
	 * 以便于使用时已经加载过
	 * @param context
	 */
	private MediaPlayerUtil(Context context)
	{
		this.context = context;
	}
	
	/**
	 * 取得MediaPlayerUtil的实例
	 * @param context
	 * @return
	 */
	public static MediaPlayerUtil getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new MediaPlayerUtil(context);
		}
		return instance;
	}
	
	
	/**
	 * 播放对应资源的声音
	 * @param sound
	 */
	public void play(int resId)
	{
		//如果已经在播放了，则跳出
		if (playingId == resId)
		{
			return;
		}
		try
		{
			if (mediaPlayer != null)
			{
				mediaPlayer.stop();
			}
			mediaPlayer = MediaPlayer.create(context, resId);
			if (mediaPlayer != null)
			{
				mediaPlayer.start();
				isPlaying = true;
				playingId = resId;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 循环播放对应资源的声音
	 * @param sound
	 */
	public void playLoop(int resId)
	{
		if (playingId == resId)
		{
			//如果已经在播放了，则跳出
			return;
		}
		try
		{
			mediaPlayer = MediaPlayer.create(context, resId);
			mediaPlayer.setLooping(true);
			if (mediaPlayer != null)
			{
				mediaPlayer.start();
				isPlaying = true;
				playingId = resId;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	
}

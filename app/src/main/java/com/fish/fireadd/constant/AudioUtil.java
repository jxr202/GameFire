package com.fish.fireadd.constant;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;

public class AudioUtil
{
	MediaPlayer mMediaPlayer;
	HashMap<Integer, Integer> soundPoolMap;
	Context myContext;

	/**
	 * 播放指定声音
	 * @param context context
	 * @param musicid 音乐ID
	 */
	public static MediaPlayer PlayMusic(Context context, int musicid)
	{
		MediaPlayer mediaplay;
		mediaplay = MediaPlayer.create(context, musicid);
		if (mediaplay != null)
		{
			mediaplay.start();
		}
		return mediaplay;
	}

	/**
	 * 重复播放
	 * @param context context
	 * @param musicid 音乐ID
	 */
	public static MediaPlayer PlayMusicLoop(Context context, int musicid)
	{
		MediaPlayer mediaplay;
		mediaplay = MediaPlayer.create(context, musicid);
		mediaplay.setLooping(true);
		mediaplay.start();
		return mediaplay;
	}

	/**
	 * 使用SoundPool来播放短促的声音
	 * @param context
	 * @param musicid
	 */
	public static void PlaySoundPool(Context context, int musicid)
	{
		AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);// 声音
		soundPool.play(soundPool.load(context, musicid, 1),
				mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
				mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 
				1, 0, 1f);// 播放声音
	}
	
	/**
	 * 震动
	 * @param context
	 */
	public static void getVibrator(Context context)
	{
		Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
		long[] pattern = { 800, 40, 400, 30 }; // OFF/ON/OFF/ON...
		vibrator.vibrate(pattern, 2);// -1不重复，非-1为从pattern的指定下标开始重复
	}
}

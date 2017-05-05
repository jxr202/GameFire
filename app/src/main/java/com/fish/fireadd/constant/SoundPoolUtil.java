package com.fish.fireadd.constant;

import java.util.HashMap;
import java.util.Map;

import com.fish.fireadd.activity.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPoolUtil
{
	private static SoundPoolUtil instance;
	private Context context;
	private SoundPool soundPool;//用于播放声音	
	
	private HashMap<Sound, Integer> soundMap;
	private int[] soundPlayedIds;	//raw文件中声音的ID
	private int[] streamIds;//加载之后的声音流文件数据ID
	
	private boolean soundOn;
	
	/**
	 * 在私有构造方法中初始化数据，
	 * 以便于使用时已经加载过
	 * @param context
	 */
	private SoundPoolUtil(Context context)
	{
		this.context = context;
		this.soundMap = new HashMap<Sound, Integer>();
		soundMap.put(Sound.myPlaneBoom, R.raw.boom_my_plane);
		soundMap.put(Sound.enemyPlaneBoom, R.raw.boom_enemy_plane);
		soundMap.put(Sound.rockBoom, R.raw.boom_rock);
		soundMap.put(Sound.bossBoom, R.raw.boom_boss);
		soundMap.put(Sound.shot, R.raw.shot);
		soundMap.put(Sound.star, R.raw.star);
		soundMap.put(Sound.hit, R.raw.hit);
		soundMap.put(Sound.flash, R.raw.flash);
		soundMap.put(Sound.powerUp, R.raw.power_up);
		this.soundPlayedIds = new int[soundMap.size()];
		this.streamIds = new int[soundMap.size()];
		this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		this.soundOn = true;
		this.load();
	}
	
	/**
	 * 取得SoundPoolUtil的实例
	 * @param context
	 * @return
	 */
	public static SoundPoolUtil getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new SoundPoolUtil(context);
		}
		return instance;
	}
	
	/**
	 * 加载声音资源
	 */
	private void load()
	{
		for (Map.Entry<Sound, Integer> entry : soundMap.entrySet())
		{
			//获取枚举的序号
			int index = entry.getKey().ordinal();
			streamIds[index] = soundPool.load(context, entry.getValue(), 1);
		}
		
	}
	
	/**
	 * 播放对应资源的声音
	 * @param sound
	 */
	public void play(Sound sound)
	{
		int index = sound.ordinal();
		try
		{
			if (this.soundOn)
			{
				soundPool.stop(soundPlayedIds[index]);
				soundPlayedIds[index] = soundPool.play(streamIds[index], 1f, 1f, 0, 0, 1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	
}

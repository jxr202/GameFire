package com.fish.fireadd.bean;

import android.util.Log;

/**
 * 矩阵模型,便于检测碰撞
 * @author JXR22
 */
public class Rect
{
	public int x;
	public int y;
	public int width;
	public int height;
	
	public boolean live;
	
	
	public Rect(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void doLogic()
	{
		Log.i("Rect...", "doLogic.....");
	}
	
	
	/**
	 * 判断一个矩阵是否与其他矩阵发生碰撞
	 * @param rect	其他矩阵
	 * @return 是否碰撞
	 */
	public boolean hitOtherRect(Rect rect)
	{
		if (this.x >= rect.x + rect.width)
		{
			return false;
		}
		if (this.x + this.width <= rect.x)
		{
			return false;
		}
		if (this.y >= rect.y + rect.height)
		{
			return false;
		}
		if (this.y + this.height <= rect.y)
		{
			return false;
		}
		return true;
	}
	
	
}

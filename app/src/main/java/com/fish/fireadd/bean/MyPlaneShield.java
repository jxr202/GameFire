package com.fish.fireadd.bean;

import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlaneShield extends Rect
{

	public Bitmap bmpMyPlaneShield;
	public GameView gameView;
	
	public MyPlaneShield(int x, int y, GameView gameView)
	{
		super(x, y);
		this.gameView = gameView;
		this.bmpMyPlaneShield = gameView.bmpMyPlaneShield;
		this.live = true;
	}
	
	
	/**
	 * 罩子的绘制，现将判断放进了玩家飞机中
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(bmpMyPlaneShield, x, y, paint);
	}
	
	/**
	 * 罩子跟着玩家飞机一起移动
	 * @param pointX 飞机的X坐标
	 * @param pointY 飞机的Y坐标
	 */
	public void move(int pointX, int pointY)
	{
		this.x = pointX;
		this.y = pointY;
	}
	
}

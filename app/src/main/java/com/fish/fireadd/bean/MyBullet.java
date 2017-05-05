package com.fish.fireadd.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyBullet extends Rect
{
	
	private Bitmap bmpMyBullet;
	private int speed = 30;
	
	
	public MyBullet(int x, int y, Bitmap bmpMyBullet)
	{
		super(x, y);
		this.bmpMyBullet = bmpMyBullet;
		this.width = bmpMyBullet.getWidth();
		this.height = bmpMyBullet.getHeight();
		this.live = true;
	}
	
	/**
	 * 画子弹
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(bmpMyBullet, x, y, paint);
	}
	
	public void move()
	{
		this.y -= this.speed;
	}
	
	public void doLogic()
	{
		this.move();
		if(this.y + this.height <= 0)
		{
			this.live = false;
		}
	}

}

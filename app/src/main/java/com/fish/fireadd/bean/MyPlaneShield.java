package com.fish.fireadd.bean;

import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlaneShield extends Rect
{

	public Bitmap bmpMyPlaneShield;
	private GameView gameView;
	
	public MyPlaneShield(int x, int y, GameView gameView)
	{
		super(x, y);
		this.gameView = gameView;
		this.bmpMyPlaneShield = gameView.bmpMyPlaneShield;
		this.live = true;
	}
	
	
	
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(bmpMyPlaneShield, x, y, paint);
	}
	
	
	public void move(int pointX, int pointY)
	{
		this.x = pointX;
		this.y = pointY;
	}
	
	public void doLogic()
	{
		
	}
}

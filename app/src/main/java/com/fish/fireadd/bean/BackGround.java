package com.fish.fireadd.bean;

import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BackGround
{
	
	private int bg1X;
	private int bg1Y;
	private int bg2X;
	private int bg2Y;
	
	private Bitmap bmpBackGround1;
	private Bitmap bmpBackGround2;
	
	private int height;
	private int speed = 5;
	
	private GameView gameView;
	
	public BackGround(Bitmap bmpBackGround, GameView gameView)
	{
		this.bmpBackGround1 = bmpBackGround;
		this.bmpBackGround2 = bmpBackGround;
		
		this.height = bmpBackGround.getHeight();
		this.gameView = gameView;
		//让第一张图的底部与屏幕对齐
		bg1Y = gameView.height - height;
		//第二张图在底部与第一张的顶部对齐
		bg2Y = bg1Y - height + 31;
	}
	
	/**
	 * 画背景图
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(bmpBackGround1, bg1X, bg1Y, paint);
		canvas.drawBitmap(bmpBackGround2, bg2X, bg2Y, paint);
	}
	
	/**
	 * 游戏超屏的逻辑处理
	 */
	public void doLogic()
	{
		bg1Y += speed;
		bg2Y += speed;
		//如果第一张超出屏幕,立即将它放入第二张的
		if (bg1Y > gameView.height)
		{
			bg1Y = bg2Y - this.height + 31;
		}
		if (bg2Y > gameView.height)
		{
			bg2Y = bg1Y - this.height + 31;
		}
	}
	
	
}

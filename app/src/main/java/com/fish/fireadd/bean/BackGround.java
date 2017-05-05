package com.fish.fireadd.bean;

import com.fish.fireadd.view.GameView;

import android.graphics.*;
import android.graphics.Rect;

public class BackGround
{
	private Bitmap bmpBackGround1;
	private Bitmap bmpBackGround2;
	private android.graphics.Rect mRect1, mRect2;
	private int bg1Y, bg2Y;
	private int bgHeight;
	private int gvHeight;

	public BackGround(Bitmap bmpBackGround, GameView gameView)
	{
		this.bmpBackGround1 = bmpBackGround;
		this.bmpBackGround2 = bmpBackGround;

		gvHeight = gameView.getHeight();
		bgHeight = bmpBackGround.getHeight();
		//让第一张图的底部与屏幕对齐
		bg1Y = gameView.height - bgHeight;
		//第二张图在底部与第一张的顶部对齐
		bg2Y = bg1Y - bgHeight + 31;

		mRect1 = new Rect(0, bg1Y, gameView.getWidth(), gameView.getHeight() + bg1Y);
		mRect2 = new Rect(0, bg2Y, gameView.getWidth(), gameView.getHeight() + bg2Y);
	}
	
	/**
	 * 画背景图
	 * @param canvas c
	 * @param paint p
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		//canvas.drawBitmap(bmpBackGround1, 0, bg1Y, paint);
		//canvas.drawBitmap(bmpBackGround2, 0, bg2Y, paint);
		canvas.drawBitmap(bmpBackGround1, null, mRect1, paint);
		canvas.drawBitmap(bmpBackGround2, null, mRect2, paint);
	}
	
	/**
	 * 游戏超屏的逻辑处理
	 */
	public void doLogic()
	{
		bg1Y += 5;
		bg2Y += 5;
		//如果第一张超出屏幕,立即将它放入第二张的
		if (bg1Y > gvHeight)
		{
			bg1Y = bg2Y - bgHeight + 31;
		}
		if (bg2Y > gvHeight)
		{
			bg2Y = bg1Y - bgHeight + 31;
		}
		mRect1.offsetTo(0, bg1Y);
		mRect2.offsetTo(0, bg2Y);
	}
	
	
}

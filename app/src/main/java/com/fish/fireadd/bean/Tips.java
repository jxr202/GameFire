package com.fish.fireadd.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.fish.fireadd.view.GameView;

public class Tips extends Rect
{
	//游戏的提示类型，1为游戏开始，2为游戏结束
	public static final int TIPS_START = 1;
	public static final int TIPS_END = 2;
	
	private int tipsType;	//提示类型
	private Bitmap bmpTips;	//提示的图片
	private GameView gameView;
	
	private int speed;	
	
	
	public Tips(int x, int y, int tipsType, GameView gameView)
	{
		super(x, y);
		this.tipsType = tipsType;
		this.gameView = gameView;
		switch (tipsType)
		{
		case TIPS_START:
			bmpTips = gameView.bmpTips[0];
			speed = 10;
			break;
		case TIPS_END:
			bmpTips = gameView.bmpTips[1];
			speed = 30;
			break;
		}
		this.live = true;
	}
	
	/**
	 * 提示图片的绘制
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		if (this.live)
		{
			canvas.drawBitmap(bmpTips, x, y, paint);
		}
	}
	
	/**
	 * 提示的移动，加入了动态变量
	 */
	public void move()
	{
		this.speed --;
		this.y += speed;
	}
	
	/**
	 * 提示的逻辑处理，包括
	 * 1.提示的移动
	 * 2.当游戏开始时，如果超出屏幕，将提示弄死
	 * 3.当游戏结束时，如果超出屏幕，将进行画面跳转
	 */
	public void doLogic()
	{
		if (!this.live)
		{
			return;
		}
		this.move();
		if (tipsType == TIPS_START)
		{
			if (this.y < -10)
			{
				this.live = false;
			}
		}
		else if (tipsType == TIPS_END)
		{
			if (this.speed < 0 && this.y < -50)
			{
				//游戏真正的结束
				gameView.gameOver = true;
			}

		}

	}
}

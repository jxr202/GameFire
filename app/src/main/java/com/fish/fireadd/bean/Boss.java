package com.fish.fireadd.bean;

import java.util.Random;
import java.util.Vector;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boss extends Rect
{
	private int bossType;
	private Bitmap bmpBoss;
	private int speed = 5;
	public int leftValue;
	
	private GameView gameView;
	private static Random rand = new Random();
	
	private int step = 0;
	
	
	public Boss(int x, int y, int bossType, GameView gameView)
	{
		super(x, y);
		this.bossType = bossType;
		this.gameView = gameView;
		switch (bossType) {
		case 1:
			this.bmpBoss = gameView.bmpBoss1;
			break;
		case 2:
			this.bmpBoss = gameView.bmpBoss2;
			break;
		case 3:
			this.bmpBoss = gameView.bmpBoss3;
			break;
		case 4:
			this.bmpBoss = gameView.bmpBoss4;
			break;
		}
		this.width = bmpBoss.getWidth();
		this.height = bmpBoss.getHeight();
		this.leftValue = 1000;
		this.live = true;
	}
	
	/**
	 * 画BOSS
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(this.bmpBoss, x, y, paint);
	}
	
	/**
	 * BOSS的移动
	 */
	public void move()
	{
		this.step ++;
		if (this.step != 5)
		{
			return;
		}
		double rad = Math.PI * 2 * Math.random() - Math.PI;
		this.x += this.speed * Math.cos(rad);
		this.y += this.speed * Math.sin(rad);
		this.step = 0;
		
		if (this.x <= 0)
		{
			this.x = 0;
		}
		else if (this.x + this.width >= Constant.WIDTH)
		{
			this.x = Constant.WIDTH - width;
		}
		else if (this.y <= 10)
		{
			this.y = 10;
		}
		else if (this.y >= Constant.HEIGHT - Constant.YG_HEIGHT)
		{
			this.y = Constant.HEIGHT - Constant.YG_HEIGHT;
		}
	}
	
	/**
	 * BOSS开火,一次发五粒
	 */
	public void fire()
	{

	}
	
	/**
	 * BOSS的逻辑处理
	 */
	public void doLogic()
	{
		this.move();
		if (this.rand.nextInt(100) > 80)
		{
			this.fire();
		}
	}
	
	/**
	 * 判断是否击中Boss,只有击中BOSS的头才算击中
	 * @param rect 玩家的子弹
	 * @return 是否击中BOSS的头部
	 */
	public boolean isHitted(Rect rect)
	{
		Rect bossRect = new Rect(this.x + this.width / 2 - 20, this.y + this.height - 20);
		bossRect.width = 40;
		bossRect.height = 20;
		return rect.hitOtherRect(bossRect);
	}
	
}

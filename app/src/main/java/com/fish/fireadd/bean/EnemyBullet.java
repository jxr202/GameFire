package com.fish.fireadd.bean;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class EnemyBullet extends Rect
{

	private int bulletType;
	private Bitmap bmpBullet;
	private int speed = 10;
	private GameView gameView;
	
	public double rad;
	
	
	public EnemyBullet(int x, int y, int bulletType, GameView gameView)
	{
		super(x, y);
		this.bulletType = bulletType;
		this.gameView = gameView;
		switch (bulletType) 
		{
		case 0:
			this.bmpBullet = gameView.bmpEnemyBullet1;
			break;
		case 1:
			this.bmpBullet = gameView.bmpEnemyBullet2;
			break;
		case 2:
			this.bmpBullet = gameView.bmpEnemyBullet3;
			break;
		case 3:
			this.bmpBullet = gameView.bmpEnemyBullet4;
			break;
		case 4:
			this.bmpBullet = gameView.bmpEnemyBullet5;
			this.rad = gameView.getRad(this.x, this.y, gameView.myPlane.x, gameView.myPlane.y);
			break;
		case Constant.BOSS_TYPE:
			this.bmpBullet = gameView.bmpEnemyBullet5;
			break;
		}
		this.width = bmpBullet.getWidth();
		this.height = bmpBullet.getHeight();
		this.live = true;
	}
	
	/**
	 * 画子弹
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(bmpBullet, x, y, paint);
	}
	
	/**
	 * 子弹的移动
	 */
	public void move()
	{
		this.y += this.speed;
	}
	
	/**
	 * 增强版子弹的移动
	 */
	public void move(double rad)
	{
		this.x += this.speed * Math.cos(rad);
		this.y += this.speed *  Math.sin(rad);
		
		if (this.x <= 0)
		{
			this.live = false;
		}
		else if (this.x + this.width > Constant.WIDTH)
		{
			this.live = false;
		}
		else if (this.y <= 0)
		{
			this.live = false;
		}
		else if (this.y >= 800 - Constant.YG_HEIGHT + 5)
		{
			this.live = false;
		}
	}
	
	/**
	 * 逻辑处理,包括移动和出界处理
	 */
	public void doLogic()
	{
		if (this.bulletType == 4 || this.bulletType == Constant.BOSS_TYPE)
		{
			this.move(this.rad);
		}
		else 
		{
			this.move();
		}
	}

}

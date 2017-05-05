package com.fish.fireadd.bean;

import java.util.Random;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boss extends Rect
{
	
	private static final int BOSS_1 = 10;
	private static final int BOSS_2 = 11;
	private static final int BOSS_3 = 12;
	
	public int bossType;
	public Bitmap bmpBoss;
	public int speed;
	public int score;
	public int lifeValue;	//敌机现在的生命值
	
	public GameView gameView;
	
	private int step = 0;	//用于调整BOSS的移动
	
	private static Random rand = new Random();
	
	public Boss(int x, int y, int bossType, GameView gameView)
	{
		super(x, y);
		this.bossType = bossType;
		this.gameView = gameView;
		switch (bossType) 
		{
		case BOSS_1:
			this.bmpBoss = gameView.bmpBoss1;
			break;
		case BOSS_2:
			this.bmpBoss = gameView.bmpBoss2;
			break;
		case BOSS_3:
			this.bmpBoss = gameView.bmpBoss3;
			break;
		}
		this.width = bmpBoss.getWidth();
		this.height = bmpBoss.getHeight();
		this.lifeValue = 100;
		this.speed = 4;
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
		if (this.step != 10)
		{
			return;
		}
		this.step = 0;
		
		//BOSS的左右移动
		if (Math.random() >= 0.5)
		{
			this.x += this.speed;
		} 
		else 
		{
			this.x -= this.speed;
		}
		
		//BOSS的出界出处理
		if (this.x <= 0)
		{
			this.x = 0;
		}
		else if (this.x + this.width >= Constant.WIDTH)
		{
			this.x = Constant.WIDTH - width;
		}
	}
	
	/**
	 * BOSS的开火,全是跟踪子弹
	 */
	public void fire()
	{
		EnemyBullet bullet= new EnemyBullet(x + width / 2 - 10, 190, bossType, gameView);
		gameView.enemyBulletVector.add(bullet);
	}

	/**
	 * BOSS的逻辑处理
	 */
	public void doLogic()
	{
		this.move();
		if (rand.nextInt(100) > 80)
		{
			this.fire();
		}
	}
	
}

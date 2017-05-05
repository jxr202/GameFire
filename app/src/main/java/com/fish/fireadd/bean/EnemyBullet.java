package com.fish.fireadd.bean;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class EnemyBullet extends Rect
{

	public static final int TYPE_BULLET_1 = 1;
	public static final int TYPE_BULLET_2 = 2;
	public static final int TYPE_BULLET_3 = 3;
	public static final int TYPE_BULLET_4 = 4;
	public static final int TYPE_BULLET_5 = 5;
	public static final int TYPE_BULLET_6 = 6;
	public static final int TYPE_BULLET_BIG_1 = 7;
	public static final int TYPE_BULLET_BIG_2 = 8;
	public static final int TYPE_BULLET_BIG_3 = 9;
	
	private int bulletType;
	private Bitmap bmpBullet;
	private int speed = 8;
	
	private GameView gameView;
	
	private double rad;	//微型跟踪子弹的弧度
	
	
	public EnemyBullet(int x, int y, int bulletType, GameView gameView)
	{
		super(x, y);
		this.bulletType = bulletType;
		this.gameView = gameView;
		switch (bulletType) 
		{
		case TYPE_BULLET_1:
			this.bmpBullet = gameView.bmpEnemyBullet1;
			break;
		case TYPE_BULLET_2:
			this.bmpBullet = gameView.bmpEnemyBullet2;
			break;
		case TYPE_BULLET_3:
			this.bmpBullet = gameView.bmpEnemyBullet3;
			break;
		case TYPE_BULLET_4:
			this.bmpBullet = gameView.bmpEnemyBullet4;
			this.rad = gameView.getRad(x + 5, y + 5, gameView.myPlane.x + 27, gameView.myPlane.y - 30);
			break;
		case TYPE_BULLET_5:
			this.bmpBullet = gameView.bmpEnemyBullet5;
			break;
		case TYPE_BULLET_6:
			this.bmpBullet = gameView.bmpEnemyBullet6;
			this.rad = gameView.getRad(x + 5, y + 5, gameView.myPlane.x + 27, gameView.myPlane.y - 30);
			break;
		case TYPE_BULLET_BIG_1:
			this.bmpBullet = gameView.bmpEnemyBulletBig1;
			break;
		case TYPE_BULLET_BIG_2:
			this.bmpBullet = gameView.bmpEnemyBulletBig2;
			break;
		case TYPE_BULLET_BIG_3:
			this.bmpBullet = gameView.bmpEnemyBulletBig3;
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
		this.y += this.speed * Math.sin(rad);
	}
	
	public void hitMyPlane()
	{
		MyPlane plane = gameView.myPlane;
		if (!live || plane.unBeatable)
		{
			return;
		}
		
		if (plane.shield.live)
		{
			if (this.hitOtherRect(plane))
			{
				this.live = false;
				gameView.myPlane.shield.live = false;
				//罩子产生爆炸
				Boom shieldBoom = new Boom(x, y, Boom.TYPE_BOOM_MY_PLANE_SHIELD, gameView);
				gameView.boomVector.add(shieldBoom);
			}	
		}
		else 
		{
			Rect rect = new Rect(plane.x + 16, plane.y + 10);
			rect.width = 38;
			rect.height = 40;
			if (this.hitOtherRect(rect))
			{
				//玩家飞机死掉
				gameView.myPlane.live = false;
				//玩家飞机的爆炸
				Boom planeBoom = new Boom(plane.x - 35, plane.y - 35, Boom.TYPE_BOOM_MY_PLANE, gameView);
				gameView.boomVector.add(planeBoom);
			}
		}
		
	}
	
	/**
	 * 敌人子弹的逻辑处理，包括：
	 * 1.子弹碰撞到玩家飞机
	 * 2.子弹的移动
	 * 3.子弹的出界处理
	 */
	public void doLogic()
	{
		this.hitMyPlane();
		//敌人飞机子弹的移动
		if (bulletType == 4 || bulletType == 6)
		{
			this.move(rad);
		}
		else  
		{
			this.move();
		}
		
		//子弹出界的逻辑处理
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
		else if (this.y >= 800)
		{
			this.live = false;
		}
	}

}

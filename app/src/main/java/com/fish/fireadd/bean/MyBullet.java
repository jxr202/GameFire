package com.fish.fireadd.bean;

import com.fish.fireadd.constant.Sound;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyBullet extends Rect
{
	
	public static final int MY_BULLET_BASIC = 1;
	public static final int MY_BULLET_S = 2;
	public static final int MY_BULLET_S_LEFT = 3;
	public static final int MY_BULLET_S_RIGHT = 4;
	public static final int MY_BULLET_F = 5;
	public static final int MY_BULLET_L = 6;
	public static final int MY_BULLET_L_LEFT = 7;
	public static final int MY_BULLET_L_RIGHT = 8;
	
	public int bulletType = 1;
	private Bitmap bmpMyBullet;
	private int speed = 8;
	private GameView gameView;
	
	private double rad;	//微型跟踪子弹的弧度
	
	//F型子弹的播放帧数
	private int totalFrame = 8;
	private int currFrame = 0;
	
	public MyBullet(int x, int y, int bulletType, GameView gameView)
	{
		super(x, y);
		this.bulletType = bulletType;
		this.gameView = gameView;
		switch (bulletType)
		{
		case MY_BULLET_BASIC:
			this.bmpMyBullet = gameView.bmpMyBulletBasic;
			break;
		case MY_BULLET_S:
			this.bmpMyBullet = gameView.bmpMyBulletS;
			break;
		case MY_BULLET_S_LEFT:
			this.bmpMyBullet = gameView.bmpMyBulletSLeft;
			this.rad = Math.PI * 3 / 4;
			break;
		case MY_BULLET_S_RIGHT:
			this.bmpMyBullet = gameView.bmpMyBulletSRight;
			this.rad = Math.PI / 4;
			break;
		case MY_BULLET_F:
			this.bmpMyBullet = gameView.bmpMyBulletF;
			//F子弹的移动速度快一些
			//this.speed = 15;
			break;
		case MY_BULLET_L:
			this.bmpMyBullet = gameView.bmpMyBulletL;
			break;
		case MY_BULLET_L_LEFT:
			this.bmpMyBullet = gameView.bmpMyBulletLLeft;
			break;
		case MY_BULLET_L_RIGHT:
			this.bmpMyBullet = gameView.bmpMyBulletLRight;
			break;
		}
		this.width = bmpMyBullet.getWidth();
		if (bulletType == MY_BULLET_F)
		{
			width = width / totalFrame;
		}
		this.height = bmpMyBullet.getHeight();
		this.live = true;
		//子弹创建时播放声音
		gameView.soundPool.play(Sound.shot);
	}
	
	/**
	 * 画子弹
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		if (bulletType == MY_BULLET_F)
		{
			canvas.save();	//在设置剪裁区域前保存canvas
			canvas.clipRect(x, y, x + width, y + height);	//设置剪裁区域
			canvas.drawBitmap(bmpMyBullet, x - currFrame * width, y, paint);
			canvas.restore();
			return;
		}
		canvas.drawBitmap(bmpMyBullet, x, y, paint);
	}
	
	public void move()
	{
		this.y -= this.speed;
	}
	
	/**
	 * 增强版子弹的移动
	 */
	public void move(double rad)
	{
		this.x += this.speed * Math.cos(rad);
		this.y -= this.speed * Math.sin(rad);
	}
	
	/**
	 * 玩家子弹和敌机碰撞的处理
	 * 1.如果BOSS是活动的，检测与BOSS的碰撞
	 * 2.如果子弹和飞机都是活的，才会碰撞
	 */
	public void hitEnemyPlane()
	{
		if (gameView.isBossLive)
		{
			//只有在击中BOSS的头才有效
			Rect rect = new Rect(gameView.boss.x + 56, gameView.boss.y + 170);
			rect.width = 150;
			rect.height = 22;
			if (this.live && this.hitOtherRect(rect))
			{
				this.live = false;
				gameView.boss.lifeValue --;
				//产生爆炸
				Boom boom = new Boom(this.x - 11, this.y - 11, Boom.TYPE_BOOM_MY_PLANE_SHIELD, gameView);
				gameView.boomVector.add(boom);
				
				//播放声音
				gameView.soundPool.play(Sound.hit);
				return;
			}
			
		}
		for (EnemyPlane plane : gameView.enemyPlaneVector)
		{
			if (plane.live && this.live && this.hitOtherRect(plane))
			{
				this.live = false;
				plane.lifeValue --;
				return;
			}
		}
	}
	
	/**
	 * 子弹的逻辑处理，包括：
	 * 1.子弹碰撞到其他飞机
	 * 2.子弹的移动
	 * 3.子弹的出界处理
	 */
	public void doLogic()
	{
		this.hitEnemyPlane();
		if (this.rad != 0)
		{
			this.move(rad);
		}
		else 
		{
			this.move();
		}
		if (this.x < 0 || this.x > 480)
		{
			this.live = false;
		}
		if(this.y + this.height <= 0 || this.y > 800)
		{
			this.live = false;
		}
		//如果是F型子弹，则进行相关的自转
		if (bulletType == MY_BULLET_F)
		{
			if (currFrame < totalFrame)
			{
				currFrame ++;
			}
			else 
			{
				currFrame = 0;
			}
		}
	}
	

}

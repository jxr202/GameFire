package com.fish.fireadd.bean;

import com.fish.fireadd.constant.Sound;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyBullet extends Rect
{
	
	public static final int TYPE_BULLET_BASIC = 1;
	public static final int TYPE_BULLET_BASIC_BACK = 2;
	public static final int TYPE_BULLET_DOUBLE = 3;
	public static final int TYPE_BULLET_DOUBLE_BACK = 4;
	public static final int TYPE_BULLET_POWER = 5;
	public static final int TYPE_BULLET_POWER_N = 6;
	
	public int bulletType = 1;
	private Bitmap bmpMyBullet;
	private int speed = 10;
	private GameView gameView;
	
	public MyBullet(int x, int y, int bulletType, GameView gameView)
	{
		super(x, y);
		this.bulletType = bulletType;
		this.gameView = gameView;
		switch (bulletType)
		{
		case TYPE_BULLET_BASIC:
			this.bmpMyBullet = gameView.bmpMyBulletBasic;
			break;
		case TYPE_BULLET_BASIC_BACK:
			this.bmpMyBullet = gameView.bmpMyBulletBasicBack;
			break;
		case TYPE_BULLET_DOUBLE:
			this.bmpMyBullet = gameView.bmpMyBulletDouble;
			break;
		case TYPE_BULLET_DOUBLE_BACK:
			this.bmpMyBullet = gameView.bmpMyBulletDoubleBack;
			break;
		case TYPE_BULLET_POWER:
			this.bmpMyBullet = gameView.bmpMyBulletPower;
			break;
		case TYPE_BULLET_POWER_N:
			this.bmpMyBullet = gameView.bmpMyBulletPowerN;
			break;
		}
		this.width = bmpMyBullet.getWidth();
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
		canvas.drawBitmap(bmpMyBullet, x, y, paint);
	}
	
	public void move()
	{
		this.y -= this.speed;
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
		this.move();
		if (this.x < 0 || this.x > 480)
		{
			this.live = false;
		}
		if(this.y + this.height <= 0 || this.y > 800)
		{
			this.live = false;
		}
	}
	

}

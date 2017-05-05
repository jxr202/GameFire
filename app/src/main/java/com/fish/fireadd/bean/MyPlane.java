package com.fish.fireadd.bean;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlane extends Rect
{

	private int noCollisionCount = 0;	//无敌计时器
	private int noCollisionTime = 50;	//因为无敌时间
	public boolean unBeatable;
	
	public Bitmap bmpMyPlane;
	public Bitmap bmpMyPlaneLight;
	private GameView gameView;
	
	public MyPlaneShield shield;
	public int myBulletType = 1;
	
	public MyPlane(int x, int y, GameView gameView)
	{
		super(x, y);
		this.gameView = gameView;
		this.bmpMyPlane = gameView.bmpMyPlane;
		this.bmpMyPlaneLight = gameView.bmpMyPlaneLight;
		this.width = bmpMyPlane.getWidth();
		this.height = bmpMyPlane.getHeight();
		this.live = true;
		this.unBeatable = false;
		this.shield = new MyPlaneShield(x, y, gameView);
	}

	/**
	 * 画飞机
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		if (!this.live)
		{
			return;
		}
		
		
		if (unBeatable)
		{
			canvas.drawBitmap(bmpMyPlaneLight, x, y, paint);
		}
		else 
		{
			canvas.drawBitmap(bmpMyPlane, x, y, paint);
		}
		
		
		if (this.shield.live)
		{
			shield.draw(canvas, paint);
		}
	}
	
	/**
	 * 飞机的移动
	 * @param pointX 移动后对应的X坐标
	 * @param pointY 移动后对应的Y坐标
	 */
	public void move(int pointX, int pointY)
	{
		if (pointX - this.x <= -2)
		{
			System.out.println("left..., is unBeatable ? " + unBeatable);
			if (unBeatable)
			{
				bmpMyPlane = gameView.bmpMyPlaneLightLeft;
			}
			else 
			{
				bmpMyPlane = gameView.bmpMyPlaneLeft;
			}
		}
		else if (pointX - this.x >= 2)
		{
			System.out.println("right..., is unBeatable ? " + unBeatable);
			if (unBeatable)
			{
				System.out.println("---------------------------------------");
				bmpMyPlane = gameView.bmpMyPlaneLightRight;
			}
			else 
			{
				bmpMyPlane = gameView.bmpMyPlaneRight;
			}
		}
		else 
		{
			System.out.println("not move..., is unBeatable ? " + unBeatable);
			if (unBeatable)
			{
				bmpMyPlane = gameView.bmpMyPlaneLight;
			}
			else 
			{
				bmpMyPlane = gameView.bmpMyPlane;
			}
		}
		
		this.x = pointX;
		this.y = pointY;
		
		if (this.x < 0)
		{
			this.x = 0;
		}
		if (this.y < 10)
		{
			this.y = 10;
		}
		if (this.x + this.width > Constant.WIDTH)
		{
			this.x = Constant.WIDTH - this.width;
		}
		if (this.y + this.height > gameView.height)
		{
			this.y = gameView.height - this.height;
		}
		
		if (shield != null)
		{
			shield.move(this.x, this.y);
		}
		
	}

	/**
	 * 开火
	 */
	public void fire()
	{
		if (!live)
		{
			return;
		}
		//根据不同的子弹类型创建子弹
		switch (myBulletType)
		{
		case MyBullet.MY_BULLET_BASIC:
		{
			MyBullet bullet = new MyBullet(this.x + width / 2 - gameView.bmpMyBulletBasic.getWidth() / 2, this.y, myBulletType, gameView);
			gameView.bulletVector.add(bullet);
			break;
		}
		case MyBullet.MY_BULLET_S:
		{
			MyBullet bullet = new MyBullet(this.x + width / 2 - gameView.bmpMyBulletS.getWidth() / 2, this.y, MyBullet.MY_BULLET_S, gameView);
			MyBullet bulletLeft = new MyBullet(this.x + width / 2 - gameView.bmpMyBulletS.getWidth() / 2, this.y, MyBullet.MY_BULLET_S_LEFT, gameView);
			MyBullet bulletRight = new MyBullet(this.x + width / 2 - gameView.bmpMyBulletS.getWidth() / 2, this.y, MyBullet.MY_BULLET_S_RIGHT, gameView);
			gameView.bulletVector.add(bullet);
			gameView.bulletVector.add(bulletLeft);
			gameView.bulletVector.add(bulletRight);
			break;
		}
		case MyBullet.MY_BULLET_F:
		{
			MyBullet bullet = new MyBullet(this.x + width / 2 - gameView.bmpMyBulletF.getWidth() / 16, this.y, MyBullet.MY_BULLET_F, gameView);
			gameView.bulletVector.add(bullet);
			break;
		}
		case MyBullet.MY_BULLET_L:
		{
			MyBullet bullet = new MyBullet(this.x + width / 2 - gameView.bmpMyBulletL.getWidth() / 2, this.y, MyBullet.MY_BULLET_L, gameView);
			gameView.bulletVector.add(bullet);
			break;
		}
		}
	}
	
	/**
	 * 被放弃的方法
	 */
	public void hitEnemyBullet()
	{
		
	}
	
	/**
	 * 碰撞之后的逻辑处理
	 */
	public void doLogic()
	{
		if (unBeatable)
		{
			noCollisionCount ++;
			if (noCollisionCount >= noCollisionTime)
			{
				unBeatable = false;
				noCollisionCount = 0;
			}
		}
		
	}
	


}

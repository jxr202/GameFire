package com.fish.fireadd.bean;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlane extends Rect
{

	private int noCollisionCount = 0;	//无敌计时器
	private int noCollisionTime = 10;	//因为无敌时间
	public boolean isCollision;
	
	public Bitmap bmpMyPlane;
	private GameView gameView;
	
	public MyPlaneShield shield;
	
	
	public MyPlane(int x, int y, GameView gameView)
	{
		super(x, y);
		this.gameView = gameView;
		this.bmpMyPlane = gameView.bmpMyPlane;
		this.width = bmpMyPlane.getWidth();
		this.height = bmpMyPlane.getHeight();
		this.live = true;
		
		this.shield = new MyPlaneShield(x, y, gameView);
	}

	/**
	 * 画飞机
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		if (isCollision)
		{
			if (noCollisionCount % 2 == 0)
			{
				canvas.drawBitmap(bmpMyPlane, x, y, paint);
			}
		}
		else 
		{
			canvas.drawBitmap(bmpMyPlane, x, y, paint);
		}
		
		
		if (this.shield != null)
		{
			shield.draw(canvas, paint);
		}
	}
	
	/**
	 * 根据弧度来控制飞机移动
	 * @param rad 弧度数
	 */
	public void move(int pointX, int pointY)
	{
		if (pointX < this.x)
		{
			bmpMyPlane = gameView.bmpMyPlaneLeft;
		}
		else if (pointX > this.x)
		{
			bmpMyPlane = gameView.bmpMyPlaneRight;
		}
		else 
		{
			bmpMyPlane = gameView.bmpMyPlane;
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
		MyBullet bullet = new MyBullet(this.x + width / 2 - gameView.bmpMyBulletBasic.getWidth() / 2, this.y, gameView.bmpMyBulletBasic);
		gameView.bulletVector.add(bullet);
	}
	
	/**
	 * 碰撞之后的逻辑处理
	 */
	public void doLogic()
	{
		if (isCollision)
		{
			noCollisionCount ++;
			if (noCollisionCount >= noCollisionTime)
			{
				isCollision = false;
				noCollisionCount = 0;
			}
		}
		
		this.shield.doLogic();
	}
	


}

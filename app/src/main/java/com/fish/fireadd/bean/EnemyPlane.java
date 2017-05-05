package com.fish.fireadd.bean;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.fish.fireadd.view.GameView;

public class EnemyPlane extends Rect
{

	public static final int TYPE_ENEMY_1 = 1;
	public static final int TYPE_ENEMY_2 = 2;
	public static final int TYPE_ENEMY_3 = 3;
	public static final int TYPE_ENEMY_4 = 4;
	public static final int TYPE_ENEMY_5 = 5;
	public static final int TYPE_ENEMY_6 = 6;
	public static final int TYPE_ENEMY_BIG_1 = 7;
	public static final int TYPE_ENEMY_BIG_2 = 8;
	public static final int TYPE_ENEMY_BIG_3 = 9;
	
	public int enemyType; //敌机类型
	private Bitmap bmpEnemyPlane;
	private int speed;
	public int score;
	
	private boolean isNew = true;	//飞机是否变过方向,只适用于4号飞机
	private boolean isLeft = true;
	
	private GameView gameView;

	private static Random rand = new Random();

	public EnemyPlane(int x, int y, int enemyType, GameView gameView)
	{
		super(x, y);
		this.enemyType = enemyType;
		this.gameView = gameView;
		switch (enemyType) 
		{
		case TYPE_ENEMY_1:
			this.bmpEnemyPlane = gameView.bmpEnemy1;
			this.speed = 2;
			this.score = 10;
			break;
		case TYPE_ENEMY_2:
			this.bmpEnemyPlane = gameView.bmpEnemy2;
			this.speed = 2;
			this.score = 10;
			break;
		case TYPE_ENEMY_3:
			this.bmpEnemyPlane = gameView.bmpEnemy3;
			this.speed = 2;
			this.score = 10;
			break;
		case TYPE_ENEMY_4:
			this.bmpEnemyPlane = gameView.bmpEnemy4;
			this.speed = 2;
			this.score = 10;
			break;
		case TYPE_ENEMY_5:
			this.bmpEnemyPlane = gameView.bmpEnemy5;
			this.speed = 2;
			this.score = 10;
			break;
		case TYPE_ENEMY_6:
			this.bmpEnemyPlane = gameView.bmpEnemy6;
			this.speed = 2;
			this.score = 10;
			break;
		case TYPE_ENEMY_BIG_1:
			this.bmpEnemyPlane = gameView.bmpEnemyBig1;
			this.speed = 1;
			this.score = 20;
			break;
		case TYPE_ENEMY_BIG_2:
			this.bmpEnemyPlane = gameView.bmpEnemyBig2;
			this.speed = 1;
			this.score = 20;
			break;
		case TYPE_ENEMY_BIG_3:
			this.bmpEnemyPlane = gameView.bmpEnemyBig3;
			this.speed = 1;
			this.score = 20;
			break;
		}
		this.width = bmpEnemyPlane.getWidth();
		this.height = bmpEnemyPlane.getHeight();
		this.live = true;
		
	}

	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawBitmap(bmpEnemyPlane, x, y, paint);
	}
	
	public void move()
	{
		if (this.enemyType == 4)
		{
			//到达一定程序就向左或向右走
			if (this.y >= gameView.height / 3)
			{
				//如果飞机在左右则向右走，否则向左走
				if (isNew)
				{
					if (this.x < gameView.width / 2)
					{
						this.bmpEnemyPlane = gameView.bmpEnemy4Right;
						this.isLeft = false;	
					}
					else 
					{
						this.bmpEnemyPlane = gameView.bmpEnemy4Left;
					}
					this.isNew = false;
				}
				else 
				{
					//如果确定飞机向左
					if (isLeft)
					{
						this.speed = 2;
						this.x -= speed;
					}
					else
					{
						this.speed = 2;
						this.x += speed;
					}
				}
			}
			else 
			{
				this.y += speed;
			}
		}
		else 
		{
			this.y += speed;
		}
	}

	public void doLogic()
	{
		this.move();
		if (this.y > 800)
		{
			//this.live = false;
		}
		if (rand.nextInt(100) > 96)
		{
			this.fire();
		}
	}

	public void fire()
	{
		
		/*EnemyBullet bullet = new EnemyBullet(this.x + width / 2 - gameView.bmpMyBullet.getWidth()
				/ 2, y + height - gameView.bmpMyBullet.getHeight() / 2, tempType, this.gameView);
		this.gameView.enemyBulletVector.add(bullet); */
	}
	
}

package com.fish.fireadd.bean;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.fish.fireadd.constant.Sound;
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
	public int lifeValue;	//敌机现在的生命值
	private GameView gameView;
	
	private boolean isNew = true;	//飞机是否变过方向,只适用于4号飞机
	private boolean isLeft = true;	//飞机是否向左飞行,只适用于4号飞机
	

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
			this.lifeValue = 1;
			break;
		case TYPE_ENEMY_2:
			this.bmpEnemyPlane = gameView.bmpEnemy2;
			this.speed = 2;
			this.score = 10;
			this.lifeValue = 1;
			break;
		case TYPE_ENEMY_3:
			this.bmpEnemyPlane = gameView.bmpEnemy3;
			this.speed = 2;
			this.score = 10;
			this.lifeValue = 1;
			break;
		case TYPE_ENEMY_4:
			this.bmpEnemyPlane = gameView.bmpEnemy4;
			this.speed = 2;
			this.score = 10;
			this.lifeValue = 1;
			break;
		case TYPE_ENEMY_5:
			this.bmpEnemyPlane = gameView.bmpEnemy5;
			this.speed = 2;
			this.score = 10;
			this.lifeValue = 1;
			break;
		case TYPE_ENEMY_6:
			this.bmpEnemyPlane = gameView.bmpEnemy6;
			this.speed = 2;
			this.score = 10;
			this.lifeValue = 2;
			break;
		case TYPE_ENEMY_BIG_1:
			this.bmpEnemyPlane = gameView.bmpEnemyBig1;
			this.speed = 1;
			this.score = 20;
			this.lifeValue = 50;
			break;
		case TYPE_ENEMY_BIG_2:
			this.bmpEnemyPlane = gameView.bmpEnemyBig2;
			this.speed = 1;
			this.score = 20;
			this.lifeValue = 50;
			break;
		case TYPE_ENEMY_BIG_3:
			this.bmpEnemyPlane = gameView.bmpEnemyBig3;
			this.speed = 1;
			this.score = 20;
			this.lifeValue = 50;
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

	public void fire()
	{
		EnemyBullet bullet;
		if (enemyType <= 6)
		{
			bullet= new EnemyBullet(x + width / 2 - 5, y + height - 5, enemyType, gameView);
		}
		else 
		{
			//大飞机的子弹大一些
			bullet= new EnemyBullet(x + width / 2 - 20, y + height - 15, enemyType, gameView);
		}
		gameView.enemyBulletVector.add(bullet);
	}
	
	public void doLogic()
	{
		this.move();
		if (this.y > 800 || this.x < 0 || this.x > 480)
		{
			this.live = false;
		}
		if (rand.nextInt(100) > 96)
		{
			this.fire();
		}
		
		//如果敌机的生命值变成了0,飞机死掉并产生爆炸
		if (lifeValue <= 0)
		{
			this.live = false;	//飞机死掉
			//敌人飞机产生爆炸,其半径为32,调整为40
			Boom boom;
			if (this.enemyType <= 6)
			{
				boom = new Boom(x + width / 2 - 40, y + height / 2 - 40, Boom.TYPE_BOOM_ENEMY_PLANE, gameView);
				//有一定机率产生奖品
				if (rand.nextInt(100) > 50)
				{
					Prize prize = new Prize(x + width / 2 - 15, y + height / 2 - 15, rand.nextInt(5) + 2, gameView);
					gameView.prizeVector.add(prize);
				}
				//播放小飞机爆炸的声音
				gameView.soundPool.play(Sound.enemyPlaneBoom);
			}
			else 
			{
				boom = new Boom(x + width / 2 - 150, y + height / 2 - 158, Boom.TYPE_BOOM_ENEMY_PLANE_BIG, gameView);
				//大飞机产生奖品的机率更大
				if (rand.nextInt(100) > 10)
				{
					Prize prize = new Prize(x + width / 2 - 15, y + height / 2 - 15, rand.nextInt(5) + 2, gameView);
					gameView.prizeVector.add(prize);
				}
				//播放大飞机爆炸的声音
				gameView.soundPool.play(Sound.rockBoom);
			}
			gameView.boomVector.add(boom);
			//玩家加分
			gameView.gameScore += this.score;
		}
	}
}

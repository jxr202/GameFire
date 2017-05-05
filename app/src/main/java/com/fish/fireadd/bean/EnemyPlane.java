package com.fish.fireadd.bean;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.view.GameView;

public class EnemyPlane extends Rect
{
	private int planeType;
	private Bitmap bmpEnemyPlane;
	private int speed = 2;
	public int score;
	
	private GameView gameView;

	private static Random rand = new Random();

	public EnemyPlane(int x, int y, int planeType, GameView gameView)
	{
		super(x, y);
		this.planeType = planeType;
		this.gameView = gameView;
		switch (planeType) 
		{
		case 0:
			this.bmpEnemyPlane = gameView.bmpEnemy1;
			this.score = 10;
			break;
		case 1:
			this.bmpEnemyPlane = gameView.bmpEnemy2;
			this.score = 10;
			break;
		case 2:
			this.bmpEnemyPlane = gameView.bmpEnemy3;
			this.score = 10;
			break;
		case 3:
			this.bmpEnemyPlane = gameView.bmpEnemy4;
			this.score = 10;
			break;
		case 4:
			this.bmpEnemyPlane = gameView.bmpEnemy5;
			this.score = 20;
			break;
		case 5:
			this.bmpEnemyPlane = gameView.bmpEnemy6;
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
		this.y += speed;
	}

	public void doLogic()
	{
		this.move();
		if (this.y > 800 - Constant.YG_HEIGHT)
		{
			this.live = false;
		}
		if (this.rand.nextInt(100) > 96)
		{
			this.fire();
		}
	}

	public void fire()
	{
		
		/*EnemyBullet bullet = new EnemyBullet(this.x + width / 2 - gameView.bmpMyBullet.getWidth()
				/ 2, y + height - gameView.bmpMyBullet.getHeight() / 2, tempType, this.gameView);
		this.gameView.enemyBulletVector.add(bullet);
*/
		/*
		 * switch(type){ case 0:epb = new EnemyPlaneBullet(3,x,y);break; case
		 * 1:epb = new EnemyPlaneBullet(0,x,y);break; case 2:epb = new
		 * EnemyPlaneBullet(2,x,y);break; case 3:epb = new
		 * EnemyPlaneBullet(0,x,y);break; case 4:epb = new
		 * EnemyPlaneBullet(3,x,y);break; case 5:epb = new
		 * EnemyPlaneBullet(2,x,y);break; case 6:epb = new
		 * EnemyPlaneBullet(1,x,y);break; }
		 */
	}

}

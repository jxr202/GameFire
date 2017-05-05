package com.fish.fireadd.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.constant.Sound;
import com.fish.fireadd.view.GameView;

public class Prize extends Rect
{

	//为了和子弹的类型相匹配，设置成这样
	public static final int PRIZE_LIGHT = 4;	//无敌
	public static final int PRIZE_P = 3;	//罩子
	
	public static final int PRIZE_S = 2;	//S子弹
	public static final int PRIZE_F = 5;	//F子弹
	public static final int PRIZE_L = 6;	//L子弹
	
	private Bitmap bmpPrize;
	private GameView gameView;

	public int prizeType; //敌机类型
	public int speed;
	public int score;
	
	private int step = 0;	//奖品左右移动时的调整参数
	
	public Prize(int x, int y, int prizeType, GameView gameView)
	{
		super(x, y);
		this.prizeType = prizeType;
		this.gameView = gameView;
		switch (prizeType) 
		{
		case PRIZE_LIGHT:
			this.bmpPrize = gameView.bmpPrizeLight;
			break;
		case PRIZE_P:
			this.bmpPrize = gameView.bmpPrizeP;
			break;
		case PRIZE_S:
			this.bmpPrize = gameView.bmpPrizeS;
			break;
		case PRIZE_F:
			this.bmpPrize = gameView.bmpPrizeF;
			break;
		case PRIZE_L:
			this.bmpPrize = gameView.bmpPrizeL;
			break;
		}
		this.speed = 1;
		this.width = bmpPrize.getWidth();
		this.height = bmpPrize.getHeight();
		this.live = true;
	}
	
	public void draw(Canvas canvas, Paint paint)
	{
		if (this.live)
		{
			canvas.drawBitmap(bmpPrize, x, y, paint);
		}
	}
	
	public void move()
	{
		this.y += speed;
		this.step ++;	
		//如果奖品达到条件，则左右移动
		if (step >= 10)
		{
			step = 0;
			if (gameView.rand.nextInt(100) >= 50)
			{
				this.x += 2;
			}
			else 
			{
				this.x -= 2;
			}
		}
		if (this.x + this.width > Constant.WIDTH)
		{
			this.x = Constant.WIDTH - this.width;
		}
		if (this.y + this.height > gameView.height)
		{
			this.y = gameView.height - this.height;
		}
	}
	
	public void hitMyPlane()
	{
		MyPlane plane = gameView.myPlane;
		if (this.live && plane.live && this.hitOtherRect(plane))
		{
			this.live = false;
			switch (prizeType)
			{
			case PRIZE_LIGHT:
			{
				plane.unBeatable = true;
				break;
			}
			case PRIZE_P:
			{
				if (!plane.shield.live)
				{
					plane.shield.live = true;
				}
				break;
			}
			default:
			{
				plane.myBulletType = this.prizeType;
				break;
			}
			}
			//吃到奖品后播放音效
			gameView.soundPool.play(Sound.powerUp);
		}
	}
	
	public void doLogic()
	{
		this.move();
		this.hitMyPlane();
		if (this.y > 800)
		{
			this.live = false;
		}
	}

}

package com.fish.fireadd.bean;

import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boom extends Rect
{
	
	public static final int TYPE_BOOM_MY_PLANE = 1;
	public static final int TYPE_BOOM_MY_PLANE_SHIELD = 2;
	public static final int TYPE_BOOM_ENEMY_PLANE = 3;
	public static final int TYPE_BOOM_ENEMY_PLANE_BIG = 4;
	public static final int TYPE_BOOM_ENEMY_PLANE_BIG_N = 5;
	
	private int boomType;
	private Bitmap[] bmpPlaneBoom;
	private int totalFrame;	//总帧数
	private int currFrame;	//当前帧,从0开始
	
	private GameView gameView;
	
	
	/**
	 * 构造方法
	 * @param x	爆炸的X坐标
	 * @param y	爆炸的Y坐标
	 */
	public Boom(int x, int y, int boomType, GameView gameView)
	{
		super(x, y);
		this.boomType = boomType;
		this.gameView = gameView;
		switch (boomType)
		{
		case TYPE_BOOM_MY_PLANE:
			bmpPlaneBoom = gameView.bmpMyPlaneBoom;
			break;
		case TYPE_BOOM_MY_PLANE_SHIELD:
			bmpPlaneBoom = gameView.bmpMyShieldBoom;
			break;
		case TYPE_BOOM_ENEMY_PLANE:
			bmpPlaneBoom = gameView.bmpEnemyBoom;
			break;
		case TYPE_BOOM_ENEMY_PLANE_BIG:
			bmpPlaneBoom = gameView.bmpEnemyBoomBig;
			break;
		case TYPE_BOOM_ENEMY_PLANE_BIG_N:
			bmpPlaneBoom = gameView.bmpEnemyBoomBigN;
			break;
		}
		this.live = true;
		this.currFrame = 0;
		this.totalFrame = bmpPlaneBoom.length;
	}
	
	/**
	 * 画效果
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		if (this.live == true)
		{
			canvas.drawBitmap(bmpPlaneBoom[currFrame], x, y, paint);
		}
	}
	
	/**
	 * 逻辑处理,放完了则让炸消失
	 */
	public void doLogic()
	{
		if (currFrame < totalFrame - 1)
		{
			currFrame ++;
		}
		else 
		{
			this.live = false;
		}
		
	}
}

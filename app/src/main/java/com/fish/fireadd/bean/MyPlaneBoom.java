package com.fish.fireadd.bean;

import com.fish.fireadd.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlaneBoom extends Rect
{
	
	private int totalFrame;	//总帧数
	private int currFrame;	//当前帧,从0开始
	
	private Bitmap[] bmpMyPlaneBoom;
	
	/**
	 * 构造方法
	 * @param x	爆炸的X坐标
	 * @param y	爆炸的Y坐标
	 */
	public MyPlaneBoom(int x, int y, GameView gameView)
	{
		super(x, y);
		this.live = true;
		this.currFrame = 0;
		this.bmpMyPlaneBoom = gameView.bmpMyPlaneBoom;
		this.totalFrame = bmpMyPlaneBoom.length;
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
			canvas.drawBitmap(bmpMyPlaneBoom[currFrame], x, y, paint);
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

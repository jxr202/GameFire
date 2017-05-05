package com.fish.fireadd.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boom extends Rect
{
	
	private Bitmap bmpBoom;	//爆炸的图片
	private int totalFrame;	//总帧数
	private int currFrame;	//当前帧
	
	/**
	 * 构造方法
	 * @param x	爆炸的X坐标
	 * @param y	爆炸的Y坐标
	 * @param bmpBoom	爆炸的效果图
	 * @param totalFrame	爆炸的总帧数
	 */
	public Boom(int x, int y, Bitmap bmpBoom, int totalFrame)
	{
		super(x, y);
		this.bmpBoom = bmpBoom;
		this.totalFrame = totalFrame;
		this.width = bmpBoom.getWidth() / totalFrame;
		this.height = bmpBoom.getHeight();
		this.live = true;
	}
	
	/**
	 * 画效果
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.save();	//在设置剪裁区域前保存canvas
		canvas.clipRect(x, y, x + width, y + height);	//设置剪裁区域
		canvas.drawBitmap(bmpBoom, x - currFrame * width, y, paint);
		canvas.restore();
	}
	
	/**
	 * 逻辑处理,放完了则让炸消失
	 */
	public void doLogic()
	{
		if (currFrame < totalFrame)
		{
			currFrame ++;
		}
		else 
		{
			this.live = false;
		}
		
	}
}

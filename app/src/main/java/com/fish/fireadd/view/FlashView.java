package com.fish.fireadd.view;

import com.fish.fireadd.activity.MainActivity;
import com.fish.fireadd.activity.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

//开头动画
public class FlashView extends SurfaceView implements Callback
{

	private SurfaceHolder holder;
	private MainActivity activity;
	private Paint paint;
	private Bitmap backGround;
	private int pictureX; // 图片X坐标
	private int pictureY; // 图片Y坐标

	private int currentAlpha = 0; // 当前的不透明值
	private int sleepTime = 50; // 线程睡觉时间

	public FlashView(MainActivity activity)
	{
		super(activity);
		this.activity = activity;
		this.holder = getHolder();
		holder.addCallback(this); // 设置生命周期回调接口的实现者

		paint = new Paint(); // 创建画笔
		paint.setAntiAlias(true); // 打开抗锯齿

		// 加载图片
		backGround = BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_flash);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		paint.setColor(Color.BLACK); // 设置画笔颜色
		paint.setAlpha(255);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		paint.setAlpha(currentAlpha);
		canvas.drawBitmap(backGround, pictureX, pictureY, paint);
	}

	@Override
	public void surfaceCreated(SurfaceHolder face)
	{
		new MyThread().start();
	}

	/** 用于播放动画的线程 **/
	private class MyThread extends Thread
	{
		public void run()
		{
			// 动态更改图片的透明度值并不断重绘
			for (int i = 255; i > -10; i = i - 10)
			{
				currentAlpha = i;
				if (currentAlpha < 0)
				{
					currentAlpha = 0;
				}
				Canvas canvas = holder.lockCanvas(); // 获取画布
				try
				{
					synchronized (holder)
					{
						onDraw(canvas); // 绘制
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (canvas != null)
					{
						holder.unlockCanvasAndPost(canvas);
					}
				}

				try
				{
					if (i == 255)
					{
						Thread.sleep(500); // 若是新图片，多等待一会
					}
					Thread.sleep(sleepTime);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			activity.hd.sendEmptyMessage(0);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{

	}

}

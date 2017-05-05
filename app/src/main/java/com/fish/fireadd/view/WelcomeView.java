package com.fish.fireadd.view;

import com.fish.fireadd.activity.MainActivity;
import com.fish.fireadd.activity.R;
import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.constant.Sound;
import com.fish.fireadd.constant.SoundPoolUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

//欢迎页面
public class WelcomeView extends SurfaceView implements Callback
{

	private SurfaceHolder holder;
	private MainActivity activity;
	private Paint paint;
	private SoundPoolUtil soundPool;

	private Bitmap backGround;
	private Bitmap startButton;
	private Bitmap helpButton;
	private Bitmap aboutButton;
	private Bitmap exitButton;
	
	private Bitmap gameStart, gameStartDown;
	private Bitmap gameHelp, gameHelpDown;
	private Bitmap gameAbout, gameAboutDown;
	private Bitmap gameExit, gameExitDown;

	private int startX, startY; // 图片X,Y坐标
	private int helpY, aboutY, exitY; // 图片Y坐标
	private int imgWidth, imgHeight;

	public WelcomeView(MainActivity activity)
	{
		super(activity);
		this.activity = activity;
		this.holder = getHolder();
		holder.addCallback(this); // 设置生命周期回调接口的实现者

		paint = new Paint(); // 创建画笔
		paint.setAntiAlias(true); // 打开抗锯齿

		soundPool = SoundPoolUtil.getInstance(activity);//初始化声音播放工具类
		
		initPicButton();// 加载图片
		initXY(); // 初始化坐标
	}

	public void initPicButton()
	{
		backGround = BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_welcome);
		startButton = gameStart = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_start);
		gameStartDown = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_start_down);
		helpButton = gameHelp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_help);
		gameHelpDown = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_help_down);
		aboutButton = gameAbout = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_about);
		gameAboutDown = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_about_down);
		exitButton = gameExit = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_exit);
		gameExitDown = BitmapFactory.decodeResource(activity.getResources(), R.drawable.game_exit_down);
	}

	// 初始化坐标
	public void initXY()
	{
		imgWidth = gameStart.getWidth();
		imgHeight = gameStart.getHeight();
		startX = Constant.Welcome.LEFT_X;
		startY = Constant.Welcome.UP_Y;
		helpY = startY + Constant.Welcome.ADD;
		aboutY = helpY + Constant.Welcome.ADD;
		exitY = aboutY + Constant.Welcome.ADD;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(backGround, 0, 0, null);
		// 开始游戏
		canvas.drawBitmap(startButton, startX, startY, null);
		// 帮助
		canvas.drawBitmap(helpButton, startX, helpY, null);
		// 关于
		canvas.drawBitmap(aboutButton, startX, aboutY, null);
		// 退出
		canvas.drawBitmap(exitButton, startX, exitY, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		int x = (int) (e.getX());
		int y = (int) (e.getY());
		// System.out.println(loginY + "|" + helpY + "|" + aboutY + "|" +

		if (e.getAction() == MotionEvent.ACTION_UP)
		{			
			if (x > startX && x < startX + imgWidth)
			{
				if (y > startY && y < startY + imgHeight)
				{
					startButton = gameStartDown;
					this.repaint();
					//播放点击的音乐
					soundPool.play(Sound.powerUp);
					try
					{
						Thread.sleep(200);
						activity.hd.sendEmptyMessage(Constant.GAME_START);
					}
					catch (InterruptedException e1)
					{
						e1.printStackTrace();
					}
				}
				else if (y > helpY && y < helpY + imgHeight)
				{
					helpButton = gameHelpDown;
					this.repaint();
					//播放点击的音乐
					soundPool.play(Sound.powerUp);
					try
					{
						Thread.sleep(200);
						activity.hd.sendEmptyMessage(Constant.GAME_HELP);
					}
					catch (InterruptedException e1)
					{
						e1.printStackTrace();
					}
				}
				else if (y > aboutY && y < aboutY + imgHeight)
				{
					aboutButton = gameAboutDown;
					this.repaint();
					//播放点击的音乐
					soundPool.play(Sound.powerUp);
					try
					{
						Thread.sleep(200);
						activity.hd.sendEmptyMessage(Constant.GAME_ABOUT);
					}
					catch (InterruptedException e1)
					{
						e1.printStackTrace();
					}
				}
				else if (y > exitY && y < exitY + imgHeight)
				{
					exitButton = gameExitDown;
					this.repaint();
					//播放点击的音乐
					soundPool.play(Sound.powerUp);
					try
					{
						Thread.sleep(200);
						activity.hd.sendEmptyMessage(Constant.GAME_EXIT);
					}
					catch (InterruptedException e1)
					{
						e1.printStackTrace();
					}
				}
				
			}
		}
		
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder face)
	{
		this.repaint();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{

	}

	// 画图
	public void repaint()
	{
		SurfaceHolder holder = this.getHolder();
		Canvas canvas = holder.lockCanvas();
		try
		{
			synchronized (holder)
			{
				onDraw(canvas);
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
	}

}

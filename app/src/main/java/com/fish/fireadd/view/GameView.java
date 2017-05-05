package com.fish.fireadd.view;

import java.util.Random;
import java.util.Vector;

import com.fish.fireadd.activity.MainActivity;
import com.fish.fireadd.activity.R;
import com.fish.fireadd.bean.BackGround;
import com.fish.fireadd.bean.Boom;
import com.fish.fireadd.bean.Boss;
import com.fish.fireadd.bean.EnemyBullet;
import com.fish.fireadd.bean.EnemyPlane;
import com.fish.fireadd.bean.MyBullet;
import com.fish.fireadd.bean.MyPlane;
import com.fish.fireadd.bean.MyPlaneBoom;
import com.fish.fireadd.bean.Rect;
import com.fish.fireadd.constant.Constant;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView
{

	public int width;
	public int height;
	
	private Object onTouch;
	private long sleepTime = 50; 
	private int step = 0;
	
	private MainActivity activity;
	private SurfaceHolder holder;	//GameView是通过holder来控制View的
	private Paint paint;
	
	private Resources res;
	public Bitmap bmpBackGround;
	public Bitmap bmpMyPlane;
	public Bitmap bmpMyPlaneLeft;
	public Bitmap bmpMyPlaneRight;
	public Bitmap bmpMyPlaneShield;
	public Bitmap bmpMyBulletBasic;
	public Bitmap[] bmpMyPlaneBoom;
	
	public Bitmap bmpEnemy1;
	public Bitmap bmpEnemy1n;
	public Bitmap bmpEnemy2;
	public Bitmap bmpEnemy2n;
	public Bitmap bmpEnemy3;
	public Bitmap bmpEnemy3n;
	public Bitmap bmpEnemy4;
	public Bitmap bmpEnemy4Left;
	public Bitmap bmpEnemy4Right;
	public Bitmap bmpEnemy4n;
	public Bitmap bmpEnemy4nLeft;
	public Bitmap bmpEnemy4nRight;
	public Bitmap bmpEnemy5;
	public Bitmap bmpEnemy5n;
	public Bitmap bmpEnemy6;
	public Bitmap bmpEnemy6n;
	public Bitmap bmpEnemyBig1;
	public Bitmap bmpEnemyBig1n;
	public Bitmap bmpEnemyBig2;
	public Bitmap bmpEnemyBig2n;
	public Bitmap bmpEnemyBig3;
	public Bitmap bmpEnemyBig3n;

	public Bitmap bmpEnemyBullet1;
	public Bitmap bmpEnemyBullet2;
	public Bitmap bmpEnemyBullet3;
	public Bitmap bmpEnemyBullet4;
	public Bitmap bmpEnemyBullet5;
	
	public Bitmap bmpBoss1;
	public Bitmap bmpBoss2;
	public Bitmap bmpBoss3;
	public Bitmap bmpBoss4;
	public Bitmap bmpBoom;
	
	private boolean gameOver = false;
	private boolean isBossLive = false;
	
	public BackGround backGround;
	public MyPlane myPlane;
	public MyPlaneBoom myPlaneBoom;
	public Boss boss;
	public Vector<MyBullet> bulletVector = new Vector<MyBullet>();
	public Vector<EnemyPlane> enemyPlaneVector = new Vector<EnemyPlane>();
	public Vector<EnemyBullet> enemyBulletVector = new Vector<EnemyBullet>();
	public Vector<Boom> boomVector = new Vector<Boom>();	
	
	
	private int enemyArray[][] = {
			{1, 2}, {1, 3}, {1, 5},{1, 4}, {1, 7}, {2, 3}, {5, 7}, {3, 3}, {5, 7, 9},
			{1, 2}, {1, 3}, {1, 5},{1, 4}, {1, 7}, {2, 3}, {5, 7}, {3, 3}, {5, 7, 9},
			{1, 2}, {1, 3}, {1, 5},{1, 4}, {1, 7}, {2, 3}, {5, 7}, {3, 3}, {5, 7, 9},
			{1, 2}, {1, 3}, {1, 5},{1, 4}, {1, 7}, {2, 3}, {5, 7}, {3, 3}, {5, 7, 9},
			{1, 2}, {1, 3}, {1, 5},{1, 4}, {1, 7}, {2, 3}, {5, 7}, {3, 3}, {5, 7, 9}
	};
	private int enemyArrayIndex = 0;
	private int createEnemyTime = 150;
	private int createEnemyCount = 0;
	private Random random;
	
	
	Random rand = new Random();
	
	private int gameScore = 0;
	private int life = 3;
	public int level = 1;
	
	//触屏点与飞机的距离
	private int dx = 0;
	private int dy = 0;
	
	
	
	/**
	 * 在构造方法中初始化holder和paint,
	 * 并给holder添加回调接口,用于管理SurfaceView
	 */
	public GameView(MainActivity activity)
	{
		super(activity);
		this.activity = activity;
		holder = getHolder();
		holder.addCallback(new GameCallback());
		paint = new Paint();
		onTouch = new Object();
	}
	
	/**
	 * 初始化游戏数据
	 */
	public void initGame()
	{
		width = getWidth();
		height = getHeight();
		res = getResources();
		bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.bg_gameing);
		bmpMyPlane = BitmapFactory.decodeResource(res, R.drawable.my_plane);
		bmpMyPlaneLeft = BitmapFactory.decodeResource(res, R.drawable.my_plane_left);
		bmpMyPlaneRight = BitmapFactory.decodeResource(res, R.drawable.my_plane_right);
		bmpMyPlaneShield = BitmapFactory.decodeResource(res, R.drawable.my_plane_shield);
		bmpMyBulletBasic = BitmapFactory.decodeResource(res, R.drawable.my_bullet_basic);
		bmpMyPlaneBoom = new Bitmap[30];
		bmpMyPlaneBoom[0] = BitmapFactory.decodeResource(res, R.drawable.plane_explode00);
		bmpMyPlaneBoom[1] = BitmapFactory.decodeResource(res, R.drawable.plane_explode01);
		bmpMyPlaneBoom[2] = BitmapFactory.decodeResource(res, R.drawable.plane_explode02);
		bmpMyPlaneBoom[3] = BitmapFactory.decodeResource(res, R.drawable.plane_explode03);
		bmpMyPlaneBoom[4] = BitmapFactory.decodeResource(res, R.drawable.plane_explode04);
		bmpMyPlaneBoom[5] = BitmapFactory.decodeResource(res, R.drawable.plane_explode05);
		bmpMyPlaneBoom[6] = BitmapFactory.decodeResource(res, R.drawable.plane_explode06);
		bmpMyPlaneBoom[7] = BitmapFactory.decodeResource(res, R.drawable.plane_explode07);
		bmpMyPlaneBoom[8] = BitmapFactory.decodeResource(res, R.drawable.plane_explode08);
		bmpMyPlaneBoom[9] = BitmapFactory.decodeResource(res, R.drawable.plane_explode09);
		bmpMyPlaneBoom[10] = BitmapFactory.decodeResource(res, R.drawable.plane_explode10);
		bmpMyPlaneBoom[11] = BitmapFactory.decodeResource(res, R.drawable.plane_explode11);
		bmpMyPlaneBoom[12] = BitmapFactory.decodeResource(res, R.drawable.plane_explode12);
		bmpMyPlaneBoom[13] = BitmapFactory.decodeResource(res, R.drawable.plane_explode13);
		bmpMyPlaneBoom[14] = BitmapFactory.decodeResource(res, R.drawable.plane_explode14);
		bmpMyPlaneBoom[15] = BitmapFactory.decodeResource(res, R.drawable.plane_explode15);
		bmpMyPlaneBoom[16] = BitmapFactory.decodeResource(res, R.drawable.plane_explode16);
		bmpMyPlaneBoom[17] = BitmapFactory.decodeResource(res, R.drawable.plane_explode17);
		bmpMyPlaneBoom[18] = BitmapFactory.decodeResource(res, R.drawable.plane_explode18);
		bmpMyPlaneBoom[19] = BitmapFactory.decodeResource(res, R.drawable.plane_explode19);
		bmpMyPlaneBoom[20] = BitmapFactory.decodeResource(res, R.drawable.plane_explode20);
		bmpMyPlaneBoom[21] = BitmapFactory.decodeResource(res, R.drawable.plane_explode21);
		bmpMyPlaneBoom[22] = BitmapFactory.decodeResource(res, R.drawable.plane_explode22);
		bmpMyPlaneBoom[23] = BitmapFactory.decodeResource(res, R.drawable.plane_explode23);
		bmpMyPlaneBoom[24] = BitmapFactory.decodeResource(res, R.drawable.plane_explode24);
		bmpMyPlaneBoom[25] = BitmapFactory.decodeResource(res, R.drawable.plane_explode25);
		bmpMyPlaneBoom[26] = BitmapFactory.decodeResource(res, R.drawable.plane_explode26);
		bmpMyPlaneBoom[27] = BitmapFactory.decodeResource(res, R.drawable.plane_explode27);
		bmpMyPlaneBoom[28] = BitmapFactory.decodeResource(res, R.drawable.plane_explode28);
		bmpMyPlaneBoom[29] = BitmapFactory.decodeResource(res, R.drawable.plane_explode29);
		
		bmpEnemy1 = BitmapFactory.decodeResource(res, R.drawable.enemy_1);
		bmpEnemy1n = BitmapFactory.decodeResource(res, R.drawable.enemy_1n);
		bmpEnemy2 = BitmapFactory.decodeResource(res, R.drawable.enemy_2);
		bmpEnemy2n = BitmapFactory.decodeResource(res, R.drawable.enemy_2n);
		bmpEnemy3 = BitmapFactory.decodeResource(res, R.drawable.enemy_3);
		bmpEnemy3n = BitmapFactory.decodeResource(res, R.drawable.enemy_3n);
		bmpEnemy4 = BitmapFactory.decodeResource(res, R.drawable.enemy_4);
		bmpEnemy4Left = BitmapFactory.decodeResource(res, R.drawable.enemy_4_left);
		bmpEnemy4Right = BitmapFactory.decodeResource(res, R.drawable.enemy_4_right);
		bmpEnemy4n = BitmapFactory.decodeResource(res, R.drawable.enemy_4n);
		bmpEnemy4nLeft = BitmapFactory.decodeResource(res, R.drawable.enemy_4n_left);
		bmpEnemy4nRight = BitmapFactory.decodeResource(res, R.drawable.enemy_4n_right);
		bmpEnemy5 = BitmapFactory.decodeResource(res, R.drawable.enemy_5);
		bmpEnemy5n = BitmapFactory.decodeResource(res, R.drawable.enemy_5n);
		bmpEnemy6 = BitmapFactory.decodeResource(res, R.drawable.enemy_6);
		bmpEnemy6n = BitmapFactory.decodeResource(res, R.drawable.enemy_6n);
		bmpEnemyBig1 = BitmapFactory.decodeResource(res, R.drawable.enemy_big1);
		bmpEnemyBig1n = BitmapFactory.decodeResource(res, R.drawable.enemy_big1n);
		bmpEnemyBig2 = BitmapFactory.decodeResource(res, R.drawable.enemy_big2);
		bmpEnemyBig2n = BitmapFactory.decodeResource(res, R.drawable.enemy_big2n);
		bmpEnemyBig3 = BitmapFactory.decodeResource(res, R.drawable.enemy_big3);
		bmpEnemyBig3n = BitmapFactory.decodeResource(res, R.drawable.enemy_big3n);
		
		
//		bmpEnemyBullet1 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_1);
//		bmpEnemyBullet2 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_2);
//		bmpEnemyBullet3 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_3);
//		bmpEnemyBullet4 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_4);
//		bmpEnemyBullet5 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_5);
//		bmpEnemy7 = BitmapFactory.decodeResource(res, R.drawable.enemy_7);
//		bmpBoss1 = BitmapFactory.decodeResource(res, R.drawable.boss_1);
//		bmpBoss2 = BitmapFactory.decodeResource(res, R.drawable.boss_2);
//		bmpBoss3 = BitmapFactory.decodeResource(res, R.drawable.boss_3);
//		bmpBoss4 = BitmapFactory.decodeResource(res, R.drawable.boss_4);
//		bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
		
		random = new Random();
		myPlane = new MyPlane(240, 600, this);
		myPlaneBoom = new MyPlaneBoom(240, 50, this);
		backGround = new BackGround(bmpBackGround, this);
	}
	
	/**
	 * 游戏线程开始
	 */
	public void startGame()
	{
		GameThread gameThread = new GameThread();
		gameThread.start();
	}
	
	/**
	 * 游戏的绘画
	 */
	public void doPaint()
	{
		Canvas canvas = holder.lockCanvas();
		if (canvas != null)
		{
			backGround.draw(canvas, paint);	//画背景
			
			myPlane.draw(canvas, paint);	//画玩家飞机
			
			if (myPlaneBoom != null)
			{
				myPlaneBoom.draw(canvas, paint);
			}
				
			//Log.i("gameView", "bullet.size = " + bulletVector.size());
			synchronized (bulletVector)
			{
				for(MyBullet bullet : bulletVector)	//画子弹
				{
					bullet.draw(canvas, paint);
				}
			}
			
				
			if (this.isBossLive)	//画BOSS
			{
				boss.draw(canvas, paint);
				paint.setColor(Color.YELLOW);
				canvas.drawText("Boss血量:" + boss.leftValue + "/1000", 200, 15, paint);
				paint.setColor(Color.RED);
				canvas.drawRect(200, 20, 200 + boss.leftValue / 5, 35, paint);
			}
			
			Log.i("gameView", "enemyPlane.size = " + enemyPlaneVector.size());
			for(EnemyPlane plane : enemyPlaneVector)	//画敌人飞机
			{
				plane.draw(canvas, paint);
			}
			
			//Log.i("gameView", "enemyBullet.size = " + enemyBulletVector.size());
			for(EnemyBullet bullet : enemyBulletVector)	//画敌人子弹
			{
				bullet.draw(canvas, paint);
			}
			
			
			for(Boom boom : boomVector)	//画爆炸
			{
				if(boom.live)
				{
					boom.draw(canvas, paint);
				}
			}
			
			//让文字在飞机的上面
			paint.setColor(Color.YELLOW);
			canvas.drawText("当前关数:" + this.level, 1, 15, paint);
			canvas.drawText("游戏得分:" + this.gameScore, 1, 30, paint);
			canvas.drawText("生命条数:" + this.life, 1, 45, paint);
			
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	/**
	 * 游戏的逻辑处理
	 */
	public void doLogic()
	{
		//背景图片的逻辑处理
		backGround.doLogic();
		//飞机爆炸的逻辑处理
		myPlaneBoom.doLogic();
		
		//子弹出界的逻辑处理
		this.doRectLogic(bulletVector);
		//敌机产生的逻辑处理
		this.createEnemy();
		
		//敌机出界的逻辑处理
		this.doRectLogic(enemyPlaneVector);
		//敌人子弹出界的逻辑处理
		this.doRectLogic(enemyBulletVector);
		//爆炸的逻辑处理
		this.doRectLogic(boomVector);
		
	}
	
	
	/**
	 * 矩阵的逻辑处理,如果矩阵没死,则调用自己的逻辑,如果死了则移除矩阵
	 * @param rectVector 矩阵的集合
	 */
	public void doRectLogic(Vector<? extends Rect> rectVector)
	{
		for(int position = rectVector.size() - 1; position >= 0; position --)
		{
			Rect rect = rectVector.elementAt(position);
			if (rect.live)
			{
				rect.doLogic();
			}
			else 
			{
				rectVector.remove(rect);
			}
		}
	}
	
	/**
	 * 敌机产生的逻辑
	 */
	public void createEnemy()
	{
		createEnemyCount ++;
		if (createEnemyCount >= createEnemyTime)
		{
			int enemyTemp[] = enemyArray[enemyArrayIndex];
			for (int i = 0; i < enemyTemp.length; i ++)
			{
				int x;
				if (enemyTemp[i] <= 6)
				{
					x = random.nextInt(width - 150) + 50;
				}
				else
				{
					//如果是大飞机,则...
					x = random.nextInt(width - 250) + 50;
				}
				EnemyPlane plane = new EnemyPlane(x, -20, enemyTemp[i], this);
				enemyPlaneVector.add(plane);
			}
			createEnemyCount = 0;
			enemyArrayIndex ++;
		}
	}
	
	/**
	 * 判断新产生的飞机是否与原有的飞机重叠了
	 * @param rect 新产生的飞机
	 * @return 是否重叠
	 */
	public boolean inOtherRect(Rect rect)
	{
		for (EnemyPlane plane : enemyPlaneVector)
		{
			if(plane.live && plane.hitOtherRect(rect))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 *	玩家通过了最后一关
	 */
	public void passAllGame()
	{
		gameOver = true;
		activity.hd.sendEmptyMessage(Constant.GAME_PASS);
	}
	
	/**
	 *	GAME OVER
	 */
	public void gameOver()
	{
		gameOver = true;
		activity.hd.sendEmptyMessage(Constant.GAME_OVER);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.step ++;
		if (step >= 2)
		{
			myPlane.fire();
			step = 0;
		}
		
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				int pointX = (int) event.getX();
				int pointY = (int) event.getY();
				
				Log.i("tag", "click....|dx:"  + dx + ",dy:" + dy);
				this.dx = pointX - myPlane.x;
				this.dy = pointY - myPlane.y;
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				int pointX = (int) event.getX();
				int pointY = (int) event.getY();
				Log.i("tag", "move....|x:"  + myPlane.x + ",y:" + myPlane.y);
				myPlane.move(pointX - dx, pointY - dy);
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				myPlane.bmpMyPlane = bmpMyPlane;
				break;
			}
		}
		//锁定，不让它一直触发
		synchronized (onTouch)
		{
			try
			{
				onTouch.wait(sleepTime);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	/**
	 * 得到两点之间的弧度
	 * @param px1 第一个点的X坐标
	 * @param py1 第一个点的Y坐标
	 * @param px2 第二个点的X坐标
	 * @param py2 第二个点的Y坐标
	 * @return 旋转的弧度
	 */
	public double getRad(float px1, float py1, float px2, float py2)
	{
		// 得到两点X的距离
		float x = px2 - px1;
		// 得到两点Y的距离
		float y = py1 - py2;
		// 算出斜边长
		float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		// 得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
		float cosAngle = x / Hypotenuse;
		// 通过反余弦定理获取到其角度的弧度
		float rad = (float) Math.acos(cosAngle);
		// 当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
		if (py2 < py1)
		{
			rad = -rad;
		}
		return rad;
	}
	
	
	/**
	 *	用于控制游戏子弹的线程
	 */
	private class GameThread extends Thread 
	{
		public void run()
		{
			while (!gameOver)
			{
				try
				{
					doLogic();
					doPaint();
					Thread.sleep(sleepTime);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/**
	 * 实现了SurfaceView回调接口的一个类
	 */
	private class GameCallback implements Callback
	{
		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
			//在SurfaceView视图创建时加载并开始游戏,不然会抛空指针
			initGame();
			startGame();
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
			
		}
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
			gameOver = true;
		}
		
	}
	
}

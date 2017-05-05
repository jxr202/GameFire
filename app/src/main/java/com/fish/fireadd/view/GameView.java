package com.fish.fireadd.view;

import java.util.Random;
import java.util.Vector;

import com.fish.fireadd.activity.MainActivity;
import com.fish.fireadd.activity.R;
import com.fish.fireadd.bean.BackGround;
import com.fish.fireadd.bean.Boss;
import com.fish.fireadd.bean.EnemyBullet;
import com.fish.fireadd.bean.EnemyPlane;
import com.fish.fireadd.bean.MyBullet;
import com.fish.fireadd.bean.MyPlane;
import com.fish.fireadd.bean.Boom;
import com.fish.fireadd.bean.Prize;
import com.fish.fireadd.bean.Rect;
import com.fish.fireadd.bean.Tips;
import com.fish.fireadd.constant.Constant;
import com.fish.fireadd.constant.MediaPlayerUtil;
import com.fish.fireadd.constant.SoundPoolUtil;

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
	
	public SoundPoolUtil soundPool;	//声音播放类
	public MediaPlayerUtil mediaPlayer;	//声音播放类
	public MainActivity activity;
	private SurfaceHolder holder;	//GameView是通过holder来控制View的
	private Paint paint;
	
	private Resources res;
	public Bitmap bmpBackGround;
	//玩家的飞机
	public Bitmap bmpMyPlane;	//正常情况的飞机
	public Bitmap bmpMyPlaneLeft;	//向左飞的飞机
	public Bitmap bmpMyPlaneRight;	//向右习的飞机
	public Bitmap bmpMyPlaneLight;	//无敌时的飞机
	public Bitmap bmpMyPlaneLightLeft;	//无敌时向左飞的飞机
	public Bitmap bmpMyPlaneLightRight;	//无敌时向右飞的飞机
	public Bitmap bmpMyPlaneShield;
	//玩家的子弹
	public Bitmap bmpMyBulletBasic;
	public Bitmap bmpMyBulletS;
	public Bitmap bmpMyBulletSLeft;
	public Bitmap bmpMyBulletSRight;
	public Bitmap bmpMyBulletF;
	public Bitmap bmpMyBulletL;
	public Bitmap bmpMyBulletLLeft;
	public Bitmap bmpMyBulletLRight;
	//玩家飞机爆炸
	public Bitmap[] bmpMyPlaneBoom;
	//玩家罩子爆炸
	public Bitmap[] bmpMyShieldBoom;
	//敌人的飞机
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
	//敌人的子弹
	public Bitmap bmpEnemyBullet1;
	public Bitmap bmpEnemyBullet2;
	public Bitmap bmpEnemyBullet3;
	public Bitmap bmpEnemyBullet4;
	public Bitmap bmpEnemyBullet5;
	public Bitmap bmpEnemyBullet6;
	public Bitmap bmpEnemyBulletBig1;
	public Bitmap bmpEnemyBulletBig2;
	public Bitmap bmpEnemyBulletBig3;
	public Bitmap bmpEnemyBulletBoss1;
	public Bitmap bmpEnemyBulletBoss2;
	public Bitmap bmpEnemyBulletBoss3;
	//敌机的爆炸
	public Bitmap[] bmpEnemyBoom;
	//大型敌机的爆炸
	public Bitmap[] bmpEnemyBoomBig;
	//加强版的大型敌机的爆炸
	public Bitmap[] bmpEnemyBoomBigN;
	//BOSS的图片
	public Bitmap bmpBoss1;
	public Bitmap bmpBoss2;
	public Bitmap bmpBoss3;
	//游戏开始和结束的提示
	public Bitmap bmpTips[];
	//游戏中的奖品
	public Bitmap bmpPrizeLight;
	public Bitmap bmpPrizeP;
	public Bitmap bmpPrizeS;
	public Bitmap bmpPrizeF;
	public Bitmap bmpPrizeL;
	
	public boolean gameOver = false;	//线程开关
	public boolean isBossLive = false;	//创建敌机开关
	
	public BackGround backGround;
	public MyPlane myPlane;
	public Boss boss;
	public Tips tips;
	
	public Vector<MyBullet> bulletVector = new Vector<MyBullet>();
	public Vector<EnemyPlane> enemyPlaneVector = new Vector<EnemyPlane>();
	public Vector<EnemyBullet> enemyBulletVector = new Vector<EnemyBullet>();
	public Vector<Boom> boomVector = new Vector<Boom>();	
	public Vector<Prize> prizeVector = new Vector<Prize>();	
	
//	private int enemyArray[][] = {
//			{4, 7, 4, 2, 1, 2, 1, 1}, {8, 2, 1, 2, 4, 2, 4, 1}, {4, 9, 1, 2, 1, 4, 1, 4},{8, 4}, {4, 7}, {2, 3}, {5, 7}, {3, 3}, {5, 7, 9},
//			{4, 2}, {4, 3}, {1, 5},{4, 4}, {4, 7}, {4, 3}, {4, 7}, {4, 3}, {8, 7, 9},
//			{4, 2}, {4, 3}, {4, 5},{4, 4}, {4, 7}, {8, 3}, {5, 7}, {3, 3}, {5, 7, 9},
//			{1, 4}, {4, 3}, {1, 5},{1, 4}, {1, 7}, {2, 3}, {4, 7}, {3, 3}, {5, 7, 9},
//			{1, 4}, {1, 3}, {1, 5},{1, 4}, {1, 7}, {4, 3}, {5, 7}, {3, 3}, {5, 7, 9}
//	};
	
	public int enemyArrayIndex = 0;
	private int createEnemyTime = 100;
	private int createEnemyCount = 0;
	private Random random;
	
	
	public Random rand = new Random();
	
	public int gameScore = 0;
	public int life = 3;
	public int level = 1;
	
	//触屏点与飞机的距离
	private int dx = 0;
	private int dy = 0;
	
	
	public int enemyArrayData[][][] = {
			{{4, 1, 1}, {4, 1}, {1, 2}, {4}, {4, 5}, {2, 3}, {5, 6}, {3, 3}, {5, 2, 2},
			{4, 2}, {4, 3}, {1, 5},{4, 4}, {4, 6}, {4, 3}, {4, 4}, {4, 3}, {4, 5, 6},
			{7}, {}, {}, {}, {4, 3}, {4, 5}, {4, 4}, {4, 6},  {3, 3}, {5, 2, 4},
			{8}, {}, {}, {}, {1, 4}, {4, 3}, {1, 5}, {1, 4}, {1, 3}, {2, 3}, {4, 5}, 
			{9}, {}, {}, {}, {3, 3}, {5, 2}, {1, 4}, {1, 3}, {1, 5},{1, 4}, {1, 7}, 
			{4, 3}, {5, 7}, {3, 3}, {5, 1}},
			
			{{2}, {3}, {-1}},
			
			{{4}, {5}, {6}, {-1}}
	};
	public int enemyArray[][] = enemyArrayData[0];
	
	
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
		soundPool = SoundPoolUtil.getInstance(activity);
		mediaPlayer = MediaPlayerUtil.getInstance(activity);
		mediaPlayer.playLoop(R.raw.bg_stage1);
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
		//玩家的飞机
		bmpMyPlane = BitmapFactory.decodeResource(res, R.drawable.my_plane);
		bmpMyPlaneLeft = BitmapFactory.decodeResource(res, R.drawable.my_plane_left);
		bmpMyPlaneRight = BitmapFactory.decodeResource(res, R.drawable.my_plane_right);
		bmpMyPlaneLight = BitmapFactory.decodeResource(res, R.drawable.my_plane_light);
		bmpMyPlaneLightLeft = BitmapFactory.decodeResource(res, R.drawable.my_plane_left_light);
		bmpMyPlaneLightRight = BitmapFactory.decodeResource(res, R.drawable.my_plane_right_light);
		bmpMyPlaneShield = BitmapFactory.decodeResource(res, R.drawable.my_plane_shield);
		//玩家的子弹
		bmpMyBulletBasic = BitmapFactory.decodeResource(res, R.drawable.my_bullet_basic);
		bmpMyBulletS = BitmapFactory.decodeResource(res, R.drawable.my_bullet_s);
		bmpMyBulletSLeft = BitmapFactory.decodeResource(res, R.drawable.my_bullet_s_left);
		bmpMyBulletSRight = BitmapFactory.decodeResource(res, R.drawable.my_bullet_s_right);
		bmpMyBulletF = BitmapFactory.decodeResource(res, R.drawable.my_bullet_f);
		bmpMyBulletL = BitmapFactory.decodeResource(res, R.drawable.my_bullet_l);
		bmpMyBulletLLeft = BitmapFactory.decodeResource(res, R.drawable.my_bullet_l_left);
		bmpMyBulletLRight = BitmapFactory.decodeResource(res, R.drawable.my_bullet_l_right);
		//玩家飞机爆炸
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
		//玩家罩子的爆炸
		bmpMyShieldBoom = new Bitmap[10];
		bmpMyShieldBoom[0] = BitmapFactory.decodeResource(res, R.drawable.shield_explode00);
		bmpMyShieldBoom[1] = BitmapFactory.decodeResource(res, R.drawable.shield_explode01);
		bmpMyShieldBoom[2] = BitmapFactory.decodeResource(res, R.drawable.shield_explode02);
		bmpMyShieldBoom[3] = BitmapFactory.decodeResource(res, R.drawable.shield_explode03);
		bmpMyShieldBoom[4] = BitmapFactory.decodeResource(res, R.drawable.shield_explode04);
		bmpMyShieldBoom[5] = BitmapFactory.decodeResource(res, R.drawable.shield_explode05);
		bmpMyShieldBoom[6] = BitmapFactory.decodeResource(res, R.drawable.shield_explode06);
		bmpMyShieldBoom[7] = BitmapFactory.decodeResource(res, R.drawable.shield_explode07);
		bmpMyShieldBoom[8] = BitmapFactory.decodeResource(res, R.drawable.shield_explode08);
		bmpMyShieldBoom[9] = BitmapFactory.decodeResource(res, R.drawable.shield_explode09);
		//敌人的飞机
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
		//敌人的子弹
		bmpEnemyBullet1 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_1);
		bmpEnemyBullet2 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_2);
		bmpEnemyBullet3 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_3);
		bmpEnemyBullet4 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_4);
		bmpEnemyBullet5 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_5);
		bmpEnemyBullet6 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_6);
		bmpEnemyBulletBig1 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_big1);
		bmpEnemyBulletBig2 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_big2);
		bmpEnemyBulletBig3 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_big3);
		bmpEnemyBulletBoss1 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_boss1);
		bmpEnemyBulletBoss2 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_boss2);
		bmpEnemyBulletBoss3 = BitmapFactory.decodeResource(res, R.drawable.enemy_bullet_boss3);
		//敌人飞机的爆炸
		bmpEnemyBoom = new Bitmap[7];
		bmpEnemyBoom[0] = BitmapFactory.decodeResource(res, R.drawable.enemy_explode00);
		bmpEnemyBoom[1] = BitmapFactory.decodeResource(res, R.drawable.enemy_explode01);
		bmpEnemyBoom[2] = BitmapFactory.decodeResource(res, R.drawable.enemy_explode02);
		bmpEnemyBoom[3] = BitmapFactory.decodeResource(res, R.drawable.enemy_explode03);
		bmpEnemyBoom[4] = BitmapFactory.decodeResource(res, R.drawable.enemy_explode04);
		bmpEnemyBoom[5] = BitmapFactory.decodeResource(res, R.drawable.enemy_explode05);
		bmpEnemyBoom[6] = BitmapFactory.decodeResource(res, R.drawable.enemy_explode06);
		bmpEnemyBoomBig = new Bitmap[24];
		bmpEnemyBoomBig[0] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion00);
		bmpEnemyBoomBig[1] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion01);
		bmpEnemyBoomBig[2] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion02);
		bmpEnemyBoomBig[3] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion03);
		bmpEnemyBoomBig[4] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion04);
		bmpEnemyBoomBig[5] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion05);
		bmpEnemyBoomBig[6] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion06);
		bmpEnemyBoomBig[7] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion07);
		bmpEnemyBoomBig[8] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion08);
		bmpEnemyBoomBig[9] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion09);
		bmpEnemyBoomBig[10] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion10);
		bmpEnemyBoomBig[11] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion11);
		bmpEnemyBoomBig[12] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion12);
		bmpEnemyBoomBig[13] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion13);
		bmpEnemyBoomBig[14] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion14);
		bmpEnemyBoomBig[15] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion15);
		bmpEnemyBoomBig[16] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion16);
		bmpEnemyBoomBig[17] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion17);
		bmpEnemyBoomBig[18] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion18);
		bmpEnemyBoomBig[19] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion19);
		bmpEnemyBoomBig[20] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion20);
		bmpEnemyBoomBig[21] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion21);
		bmpEnemyBoomBig[22] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion22);
		bmpEnemyBoomBig[23] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_explosion23);
		//大型敌机的爆炸
		bmpEnemyBoomBigN = new Bitmap[24];
//		bmpEnemyBoomBigN[0] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion00);
//		bmpEnemyBoomBigN[1] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion01);
//		bmpEnemyBoomBigN[2] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion02);
//		bmpEnemyBoomBigN[3] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion03);
//		bmpEnemyBoomBigN[4] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion04);
//		bmpEnemyBoomBigN[5] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion05);
//		bmpEnemyBoomBigN[6] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion06);
//		bmpEnemyBoomBigN[7] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion07);
//		bmpEnemyBoomBigN[8] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion08);
//		bmpEnemyBoomBigN[9] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion09);
//		bmpEnemyBoomBigN[10] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion10);
//		bmpEnemyBoomBigN[11] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion11);
//		bmpEnemyBoomBigN[12] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion12);
//		bmpEnemyBoomBigN[13] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion13);
//		bmpEnemyBoomBigN[14] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion14);
//		bmpEnemyBoomBigN[15] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion15);
//		bmpEnemyBoomBigN[16] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion16);
//		bmpEnemyBoomBigN[17] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion17);
//		bmpEnemyBoomBigN[18] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion18);
//		bmpEnemyBoomBigN[19] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion19);
//		bmpEnemyBoomBigN[20] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion20);
//		bmpEnemyBoomBigN[21] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion21);
//		bmpEnemyBoomBigN[22] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion22);
//		bmpEnemyBoomBigN[23] = BitmapFactory.decodeResource(res, R.drawable.enemy_big_n_explosion23);
		//BOSS的图片
		bmpBoss1 = BitmapFactory.decodeResource(res, R.drawable.boss_1);
		bmpBoss2 = BitmapFactory.decodeResource(res, R.drawable.boss_2);
		bmpBoss3 = BitmapFactory.decodeResource(res, R.drawable.boss_3);
		//游戏提示的图片
		bmpTips = new Bitmap[4];
		bmpTips[0] = BitmapFactory.decodeResource(res, R.drawable.tips_game_start);
		bmpTips[1] = BitmapFactory.decodeResource(res, R.drawable.tips_game_over);
		bmpTips[2] = BitmapFactory.decodeResource(res, R.drawable.tips_game_pass);
		bmpTips[3] = BitmapFactory.decodeResource(res, R.drawable.tips_game_pass_all);
		//游戏奖品的图片
		bmpPrizeLight = BitmapFactory.decodeResource(res, R.drawable.prize_light);
		bmpPrizeP = BitmapFactory.decodeResource(res, R.drawable.prize_p);
		bmpPrizeS = BitmapFactory.decodeResource(res, R.drawable.prize_s);
		bmpPrizeF = BitmapFactory.decodeResource(res, R.drawable.prize_f);
		bmpPrizeL = BitmapFactory.decodeResource(res, R.drawable.prize_l);
		
		
		random = new Random();
		myPlane = new MyPlane(240, 600, this);
		Boom myPlaneBoom = new Boom(240, 50, Boom.TYPE_BOOM_MY_PLANE, this);
		boomVector.add(myPlaneBoom);
		backGround = new BackGround(bmpBackGround, this);
		tips = new Tips(width / 2 - 251, height / 2 - 52, Tips.TIPS_START, this);
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
			
			//Log.i("gameView", "bullet.size = " + bulletVector.size());
			synchronized (bulletVector)
			{
				for(MyBullet bullet : bulletVector)	//画子弹
				{
					bullet.draw(canvas, paint);
				}
			}
			
				
			if (isBossLive)	//画BOSS
			{
				boss.draw(canvas, paint);
				paint.setColor(Color.YELLOW);
				canvas.drawText("Boss血量:" + boss.lifeValue + "/100", 200, 15, paint);
				paint.setColor(Color.RED);
				canvas.drawRect(200, 20, 200 + boss.lifeValue * 2, 35, paint);
			}
			
			//Log.i("gameView", "enemyPlane.size = " + enemyPlaneVector.size());
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
			
			for(Prize prize : prizeVector)	//画奖品
			{
				if(prize.live)
				{
					prize.draw(canvas, paint);
				}
			}
			
			//游戏中的提示,包括游戏开始和游戏结束
			tips.draw(canvas, paint);
			//
			
			//让文字在飞机的上面
			paint.setColor(Color.YELLOW);
			canvas.drawText("当前关数:" + this.level, 1, 15, paint);
			canvas.drawText("游戏得分:" + this.gameScore, 1, 30, paint);
			//canvas.drawText("生命条数:" + this.life, 1, 45, paint);
			
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
		//玩家飞机的逻辑处理
		myPlane.doLogic();
		//提示的逻辑处理
		tips.doLogic();
		//玩家子弹出界的逻辑处理
		this.doRectLogic(bulletVector);
		
		//敌机产生的逻辑处理
		this.createEnemy();
		//敌机出界的逻辑处理
		this.doRectLogic(enemyPlaneVector);
		//敌人子弹出界的逻辑处理
		this.doRectLogic(enemyBulletVector);
		//爆炸的逻辑处理
		this.doRectLogic(boomVector);
		//奖品的逻辑处理
		this.doRectLogic(prizeVector);
		
		//Boss的逻辑处理
		if (isBossLive)
		{
			boss.doLogic();
		}
		
	}
	
	
	/**
	 * 矩阵的逻辑处理,如果矩阵没死,则调用自己的逻辑,如果死了则移除矩阵
	 * @param rectVector 矩阵的集合
	 */
	public void doRectLogic(Vector<? extends Rect> rectVector)
	{
		for(int position = rectVector.size() - 1; position >= 0; position --)
		{
			Rect rect = rectVector.get(position);
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
		if (!isBossLive)
		{
			createEnemyCount ++;
			if (createEnemyCount >= createEnemyTime)
			{
				int enemyTemp[] = enemyArray[enemyArrayIndex];
				for (int i = 0; i < enemyTemp.length; i ++)
				{
					if (enemyTemp[i] > 0 && enemyTemp[i] <= 6)
					{
						int x = random.nextInt(width - 150) + 50;
						EnemyPlane plane = new EnemyPlane(x, -20, enemyTemp[i], this);
						enemyPlaneVector.add(plane);
					}
					else if (enemyTemp[i] > 6)
					{
						//如果是大飞机,则...
						int x = random.nextInt(width - 250) + 50;
						EnemyPlane plane = new EnemyPlane(x, -20, enemyTemp[i], this);
						enemyPlaneVector.add(plane);
					}
					
				}
				
				if (enemyArrayIndex == enemyArray.length - 1)
				{
					//BOSS的类型是10，11，12
					boss = new Boss(width / 2 - 100, 0, this.level + 9, this);
					isBossLive = true;
				}
				else 
				{
					enemyArrayIndex ++;
				}
				
				createEnemyCount = 0;
			}

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
	 *	GAME OVER
	 */
	public void gameOver()
	{
		//gameOver = true;
		Log.i("GameView", "GAME OVER.....");
		//游戏结束将产生结束的提示
		tips = new Tips(width / 2 - 243, -10, Tips.TIPS_END, this);
		
		//activity.hd.sendEmptyMessage(Constant.GAME_OVER);
	}
	
	/**
	 *	GAME PASS
	 */
	public void gamePass()
	{
		Log.i("GameView", "GAME PASS.....");
		//如果没有提示时才提示进入下一关，不然玩家飞机爆掉之后又进入下一关
		if (!tips.live)
		{
			tips = new Tips(width / 2 - 243, -10, Tips.TIPS_PASS, this);
		}
		if (level == 2)
		{
			mediaPlayer.playLoop(R.raw.bg_stage1);
		}
		else if (level == 3)
		{
			mediaPlayer.playLoop(R.raw.bg_stage3);
		}
	}
	
	/**
	 *	GAME PASS ALL
	 */
	public void gamePassAll()
	{
		Log.i("GameView", "GAME OVER ALL.....");
		//进入下一关
		tips = new Tips(width / 2 - 243, -10, Tips.TIPS_PASS_ALL, this);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.step ++;
		if (step >= 7)
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
				if (myPlane.unBeatable)
				{
					myPlane.bmpMyPlane = bmpMyPlaneLight;
				}
				else 
				{
					myPlane.bmpMyPlane = bmpMyPlane;
				}
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

package com.example.m_girl;
//遊戲主畫面配置
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

@SuppressLint({ "WrongCall", "ClickableViewAccessibility" })
public class GameView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {
	MainActivity mainActivity;
	SurfaceHolder holder; 
	Canvas canvas;
	boolean s=false;					//判定移動
	int sx;						//方向球起始X值
	int sy;						//方向球起始Y值
	int endx;					//方向球移動X值
	int endy;					//方向球移動Y值
	MainMenuButton ballbutton;	//方向球按鈕設定
	Paint paint;
	MediaPlayer mMediaPlayer;	//背景音樂播放器類別
	int backGroundY=0;			//起始核心圖Y座標
	int center=0;				//生命用間距
	int dir;					//判定方向
	int status=1;				//判斷當前遊戲狀態 0死亡 1進行
	int i=0;					//核心圖索引

	Bitmap monster1;			//怪物圖片
	Bitmap monster2;
	Bitmap monster3;
	Bitmap boss;

	Bitmap battleback;			//背景
	Bitmap[] battlebacks = new Bitmap[(Constant.SCREEN_HEIGHT/30)];//裝背景分割用途
	Bitmap math[];				//字圖片
	Bitmap ball;				//方向球圖片
	Bitmap down;				//最底下功能用底圖
	Bitmap heart;				//生命圖片
	Bitmap plaid;				//方相框圖片
	Bitmap life;				//生命圖片
	ExplodeThread explodeThread;//爆破圖片執行緒
	MoveThread movethread;		//除了自機的移動執行緒
	KeyThread keythread;		//刷框執行緒
	GameThread gameThread;		//遊戲中的物件出現時間執行緒
	Plane plane=new Plane(Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT-3*Constant.SCREEN_HEIGHT/16, 01, 0, true, 2, this);
	CopyOnWriteArrayList<Bullet> myst =new CopyOnWriteArrayList<Bullet>();			//我方子彈
	CopyOnWriteArrayList<Bullet> mnst =new CopyOnWriteArrayList<Bullet>();			//敵方子彈
	ArrayList<Explode> exp =new ArrayList<Explode>();			//爆炸特效
	ArrayList<HitExplode> hexp =new ArrayList<HitExplode>();	//爆炸特效
	ArrayList<Monster> ms;								//敵機
	ArrayList<Lifeup> lifes;							//存放生命增加用

	int [] exID=new int[]{								//爆炸圖片
			R.drawable.explode1,
			R.drawable.explode2,
			R.drawable.explode3
	};

	int [] sexID=new int[]{								//攻擊爆炸圖片
			R.drawable.explode01,
			R.drawable.explode02,
			R.drawable.explode03
	};
	Bitmap[] explodes =new Bitmap[exID.length];			//封裝爆炸的連續圖
	Bitmap[] sexplodes =new Bitmap[sexID.length];

	public GameView(MainActivity mainActivity) {		//建構子
		super(mainActivity);
		this.mainActivity=mainActivity;
		getHolder().addCallback(this);//登錄回調接口
		//取得第一關的敵人資料
		ms=Map.getFirst();
		lifes=Map.getFirstLife();
		initBitmap();//起始化點陣圖資源
	}
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,-Constant.SCREEN_HEIGHT,Constant.SCREEN_WIDTH,2*Constant.SCREEN_HEIGHT));//只在螢幕範圍內繪制圖片

		canvas.drawColor(Color.WHITE);//界面設定為白色	
		//	canvas.drawBitmap(battleback, 0,0, paint);
		int backGroundY=this.backGroundY;
		int i=this.i;
		//解決i下側問題
		if(backGroundY>0){
			int n=(backGroundY/Constant.pictureHeight)+((backGroundY%Constant.pictureHeight==0)?0:1);//計算i下面有幾張圖
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(battlebacks[(i-j+(Constant.SCREEN_HEIGHT/30))%(Constant.SCREEN_HEIGHT/30)],0,backGroundY+Constant.pictureHeight*j, paint);
			}
		}

		//解決i自己
		canvas.drawBitmap(battlebacks[i],0,backGroundY, paint);

		//解決i上側問題
		if(backGroundY<Constant.SCREEN_HEIGHT-Constant.pictureHeight){
			int k=Constant.SCREEN_HEIGHT-(backGroundY+Constant.pictureHeight);
			int n=(k/Constant.pictureHeight)+((k%Constant.pictureHeight==0)?0:1);//計算i上面有幾張圖
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(battlebacks[(i+j)%(Constant.SCREEN_HEIGHT/30)],0,backGroundY+Constant.pictureHeight*j, paint);
			}
		}   
		plane.draw(canvas, paint);
		try {
			for(Monster mr:ms){
				if(mr.status)
					mr.drawSelf(canvas);
			}
		} 
		catch (Exception e) {}

		try {
			for(Bullet b:myst)
				b.drawSelf(canvas, paint);
		} 
		catch (Exception e) {}
		try {
			for(Bullet b:mnst)
				b.drawSelf(canvas, paint);
		} 
		catch (Exception e) {}
		try {
			for(Explode e:exp){
				e.draw(canvas);
			}
		} catch (Exception e) {}
		try {
			for(HitExplode he:hexp){
				he.draw(canvas);
			}
		} catch (Exception e) {}
		try {
			for(Lifeup l : lifes){
				if(l.status)
					l.drawSelf(canvas);
			}
		} catch (Exception e) {}
		
		//畫出時間
		String timeStr = gameThread.touchtime/10+"";	//轉換成字串
		for(int c=0;c<timeStr.length();c++){		//循環的繪製出時間
			int tempScore=timeStr.charAt(c)-'0';
			canvas.drawBitmap(math[tempScore],14*Constant.SCREEN_WIDTH/16+c*22, 12, paint);
			if(gameThread.touchtime==0){
				timeStr = gameThread.bosstime/10+"";	//轉換成字串
				for(c=0;c<timeStr.length();c++){		//循環的繪製出時間
					tempScore=timeStr.charAt(c)-'0';
					canvas.drawBitmap(math[tempScore],14*Constant.SCREEN_WIDTH/16+c*22, 12, paint);
				}
			}
		}
		//繪製底層功能圖
		canvas.drawBitmap(down, 0,7*Constant.SCREEN_HEIGHT/8, paint);
		//繪製方向框
		canvas.drawBitmap(plaid, 0,7*Constant.SCREEN_HEIGHT/8, paint);
		ballbutton.drawSelf(canvas, paint);
		for(int h=0;h<=plane.planelife;h++){
			if(plane.planelife>5)
				plane.planelife=5;
			if(h>0){
				canvas.drawBitmap(heart, 5*Constant.SCREEN_WIDTH/12+center+((h-1)*(heart.getWidth()+center)),9*Constant.SCREEN_HEIGHT/10, paint);
				if(plane.planelife==0)
					center=0;
				else
					center=Constant.SCREEN_WIDTH/54;
			}
		}	
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {	//建立畫面的時候
		paint=new Paint();//建立畫筆
		paint.setAntiAlias(true);//開啟抗鋸齒
		//	initBitmap();//起始化點陣圖資源
		//背景音樂
		mMediaPlayer=MediaPlayer.create(mainActivity, R.raw.super_gamer_boy_offvocal);//建立背景音樂播放器
		mMediaPlayer.setLooping(true);//循環播放

		keythread=new KeyThread(this);//刷框執行緒
		explodeThread=new ExplodeThread(this);
		gameThread=new GameThread(this);
		movethread=new MoveThread(this);
		ballbutton =new MainMenuButton(mainActivity, ball, ball, Constant.SCREEN_WIDTH/18,18*Constant.SCREEN_HEIGHT/20);
		//開啟全部執行緒	
		startAllThreads();
		//開啟螢幕監聽
		setOnTouchListener(this);
		//開啟背景音樂
		if(mainActivity.backgroundsound)
			mMediaPlayer.start();//開啟音樂	
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {			//摧毀介面的時候
		boolean retry = true;
		stopAllThreads();//停止所有執行緒
		//關閉所有執行緒
		while (retry) {
			try {
				keythread.join();
				explodeThread.join();
				gameThread.join();
				movethread.join();
				retry = false;
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			//不斷地循環，直到其它執行緒結束
		}
		//關閉背景音樂
		if(mMediaPlayer.isPlaying())//若果正在播放
			mMediaPlayer.stop();//停止播放
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	//起始化圖片資源
	public void initBitmap(){
		battleback = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		battleback=PicLoadUtil.scaleToFit(battleback,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		for(int i=0; i<battlebacks.length; i++){	//切小圖片
			battlebacks[i] = Bitmap.createBitmap(battleback,0,Constant.pictureHeight*i, Constant.SCREEN_WIDTH, Constant.pictureHeight);
		}
		battleback = null;	//釋放原本的大圖
		//怪物的圖片
		monster1=BitmapFactory.decodeResource(this.getResources(), R.drawable.monster1);
		monster2=BitmapFactory.decodeResource(this.getResources(), R.drawable.monster2);
		monster3=BitmapFactory.decodeResource(this.getResources(), R.drawable.monster3);
		boss=BitmapFactory.decodeResource(this.getResources(), R.drawable.boss);

		math=new Bitmap[]{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math0),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math1),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math2),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math3),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math4),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math5),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math6),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math7),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math8),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.math9)
		};
		//圖片大小

		plaid=BitmapFactory.decodeResource(this.getResources(), R.drawable.plaid);
		plaid=PicLoadUtil.scaleToFit(plaid,Constant.SCREEN_HEIGHT/8, Constant.SCREEN_HEIGHT/8);
		down=BitmapFactory.decodeResource(this.getResources(), R.drawable.down);
		down=PicLoadUtil.scaleToFit(down,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		ball=BitmapFactory.decodeResource(this.getResources(), R.drawable.ball);
		ball=PicLoadUtil.scaleToFit(ball,Constant.SCREEN_HEIGHT/15, Constant.SCREEN_HEIGHT/15);
		heart=BitmapFactory.decodeResource(this.getResources(),R.drawable.heart);
		heart=PicLoadUtil.scaleToFit(heart,Constant.SCREEN_WIDTH/10, Constant.SCREEN_WIDTH/10);
		life=BitmapFactory.decodeResource(this.getResources(),R.drawable.lifeadd);
		life=PicLoadUtil.scaleToFit(life,Constant.SCREEN_WIDTH/10, Constant.SCREEN_WIDTH/10);

		for(int i=0;i<explodes.length;i++){
			explodes[i]=BitmapFactory.decodeResource(getResources(), exID[i]);
		}
		for(int i=0;i<sexplodes.length;i++){
			sexplodes[i]=BitmapFactory.decodeResource(getResources(), sexID[i]);
		}

		for(Monster mr:ms){
			if(mr.type==1)
				mr.bitmap=monster1;
			else if(mr.type==2)
				mr.bitmap=monster2;
			else if(mr.type==3)
				mr.bitmap=monster3;
			else if(mr.type==5)
				mr.bitmap=boss;
		}
		for(Lifeup l: lifes){
			l.bitmap=life;
		}
	}
	//觸控方向球的方法
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v,MotionEvent event) {
		int startx=(int) event.getX();		//方向球起始X值
		int starty=(int) event.getY();		//方向球起始Y值
		int movex=(int) event.getX();		//方向球移動X值
		int movey=(int) event.getY();		//方向球移動Y值
		try  {   
			switch  (event.getAction()) {  	//觸控事件的處理
			case  MotionEvent.ACTION_DOWN:  //點選屏幕
				picMove(startx, starty);   
				sx=startx;
				sy=starty;
				break ; 
			case  MotionEvent.ACTION_MOVE:  //移動位置
				if(sx<=plaid.getWidth() && sy>=(Constant.SCREEN_HEIGHT-plaid.getHeight())){
					picMove(movex, movey);  
					endx=movex;
					endy=movey;
					s=true;
				}
				else
					s=false;
				break ;   
			case  MotionEvent.ACTION_UP:  	//離開屏幕
				picMove(startx, starty);
				endx=sx;
				endy=sy;
				ballbutton.x=Constant.SCREEN_WIDTH/18;
				ballbutton.y=18*Constant.SCREEN_HEIGHT/20;
				plane.dir=Constant.DIR_STOP;
				s=false;
				break ;  
			}   
		} 
		catch (Exception e) {  
			e.printStackTrace();   
		}   
		return true; 
	}
	//移動方向球的方法
	private void picMove(int x,int y){    
		//防止方向球超過方相框的相關處理 		
		//防止屏幕向右超過屏幕 
		if	(s && endx!=sx && endx >sx+30 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20+15 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20-15){
			ballbutton.x=Constant.SCREEN_WIDTH/18+15;
			plane.dir=Constant.DIR_LEFT;
			if(plane.x>=Constant.SCREEN_WIDTH-Constant.MYSHIP_WIDTH)			//如果自機移動>右邊
				plane.x= Constant.SCREEN_WIDTH-Constant.MYSHIP_WIDTH;	//位置=右邊-自機寬度使其自機右邊對準
			else
				plane.x+=Constant.MYSHIP_SPEED;
		}
		//防止屏幕向左超過屏幕 
		else if(s && endx!=sx && endx<sx-30 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20+15 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20-15){
			ballbutton.x =Constant.SCREEN_WIDTH/18-15;
			plane.dir=Constant.DIR_RIGHT;
			if(plane.x<=0)
				plane.x=0;
			else
				plane.x-=Constant.MYSHIP_SPEED;
		}
		//防止屏幕向上超過屏幕
		else if(s && endy!=sy && endy >= sy+30 && ballbutton.x!=Constant.SCREEN_WIDTH/18+15 && ballbutton.x !=Constant.SCREEN_WIDTH/18-15){
			ballbutton.y=18*Constant.SCREEN_HEIGHT/20+15;
			if(plane.y>=Constant.SCREEN_HEIGHT-Constant.MYSHIP_HEIGHT-Constant.SCREEN_HEIGHT/8)			//如果自機移動>下邊
				plane.y=Constant.SCREEN_HEIGHT-Constant.MYSHIP_HEIGHT-Constant.SCREEN_HEIGHT/8;	//位置=下邊-自機高度使其自機下邊對準
			else
				plane.y+=Constant.MYSHIP_SPEED;
		}
		//防止屏幕向下超過屏幕
		else if(s && endy!=sy && endy <= sy-30 && ballbutton.x!=Constant.SCREEN_WIDTH/18+15 && ballbutton.x !=Constant.SCREEN_WIDTH/18-15){
			ballbutton.y=18*Constant.SCREEN_HEIGHT/20-15;
			if(plane.y<=0)
				plane.y=0;
			else
				plane.y-=Constant.MYSHIP_SPEED;
		}
	}   
	//結束游戲的方法
	public void overGame(){
		stopAllThreads();//停止所有執行緒
		//關閉背景音樂
		if(mMediaPlayer.isPlaying())//若果正在播放
			mMediaPlayer.stop();//停止播放
		mainActivity.sendMessage(2);//傳送訊息，進入游戲結束界面
	}

	//開啟所有執行緒
	public void startAllThreads(){
		keythread.setKeyFlag(true);//刷框執行緒的標志位設為true
		explodeThread.setflag(true);
		movethread.setFlag(true);
		gameThread.setflag(true);

		keythread.start();//開啟刷框執行緒
		explodeThread.start();
		movethread.start();
		gameThread.start();
	}

	//停止所有執行緒
	public void stopAllThreads(){
		keythread.setKeyFlag(false);//關閉刷框執行緒
		explodeThread.setflag(false);
		movethread.setFlag(false);
		gameThread.setflag(false);
	}
	//重新繪制畫面
	public void repaint(){
		Canvas canvas=this.getHolder().lockCanvas();
		try{
			synchronized(canvas){
				onDraw(canvas);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas!=null)
				this.getHolder().unlockCanvasAndPost(canvas);
		}
	}
}


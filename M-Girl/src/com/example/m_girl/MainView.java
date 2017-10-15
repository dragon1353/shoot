package com.example.m_girl;
//遊戲開始畫面配置
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
public class MainView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener{

	MainActivity mainActivity;
	Paint paint;//畫筆

	KeyThread keythread;//主界面的刷框執行緒
	MediaPlayer mMediaPlayer;//背景音樂播放器類別
	MainMenuButton startbutton;//開始按鈕的索引
	MainMenuButton exitbutton;//離開按鈕的索引
	MainMenuButton soundbutton;//音效控制按鈕的索引

	float pointx;//螢幕上觸控到的點
	float pointy;//螢幕上觸控到的點

	Bitmap background;//主界面的背景圖片
	Bitmap startgame;//開始按鈕圖片
	Bitmap exitgame;//離開按鈕圖片
	Bitmap soundoff;//關閉音效的按鈕圖片
	Bitmap soundon;//開啟音效的按鈕圖片

	public MainView(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity=mainActivity;
		getHolder().addCallback(this);//登錄回調接口
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));//只在螢幕範圍內繪制圖片
		canvas.drawColor(Color.WHITE);//界面設定為白色
		//繪制主界面的背景圖片
		canvas.drawBitmap(background, 0,0, paint);
		//繪制開始按鈕
		startbutton.drawSelf(canvas, paint);
		//繪制離開按鈕
		exitbutton.drawSelf(canvas, paint);
		//繪制音效控制按鈕
		soundbutton.drawSelf(canvas, paint);
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN://按下
			if(startbutton.isPointInRect(pointx, pointy))//跳躍到游戲界面
				mainActivity.sendMessage(1);//傳送訊息，進入游戲界面
			if(exitbutton.isPointInRect(pointx, pointy))
				System.exit(0);
			else if(soundbutton.isPointInRect(pointx, pointy)){
				soundbutton.setswitch();//轉換圖片
				//實現其對應功能
				mainActivity.backgroundsound=soundbutton.isOnflag();//為背景音樂開啟標志位給予值
			}
			break;
		case MotionEvent.ACTION_UP://抬起
			
			break;
		}
		return true;
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		paint=new Paint();//建立畫筆
		paint.setAntiAlias(true);//開啟抗鋸齒

		//背景音樂
		mMediaPlayer=MediaPlayer.create(mainActivity, R.raw.background);//建立背景音樂播放器
		mMediaPlayer.setLooping(true);//循環播放

		if(mainActivity.backgroundsound)
			mMediaPlayer.start();//開啟音樂	

		initBitmap();//起始化點陣圖資源
		keythread=new KeyThread();//刷框執行緒建立物件
		//開始按鈕建立物件
		startbutton=new MainMenuButton(mainActivity,startgame,startgame,Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/3);
		//積分按鈕建立物件
		exitbutton=new MainMenuButton(mainActivity,exitgame,exitgame,Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/2);
		//音效按鈕建立物件
		soundbutton=new MainMenuButton(mainActivity,soundon,soundoff,0,7*Constant.SCREEN_HEIGHT/8);

		//開啟螢幕監聽
		setOnTouchListener(this);
		keythread.setKeyFlag(true);//刷框執行緒的標志位設為true
		keythread.start();//開啟刷框執行緒
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		keythread.setKeyFlag(false);//刷框執行緒的標志位設為false

		//關閉背景音樂
		if(mMediaPlayer.isPlaying())//若果正在播放
			mMediaPlayer.stop();//停止播放
	}
	//起始化圖片資源
	public void initBitmap(){
		//主界面的背景圖片
		background=BitmapFactory.decodeResource(this.getResources(), R.drawable.progress);
		background=PicLoadUtil.scaleToFit(background,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		//開始按鈕圖片
		startgame=BitmapFactory.decodeResource(this.getResources(), R.drawable.start);
		startgame=PicLoadUtil.scaleToFit(startgame,Constant.SCREEN_WIDTH/3 ,Constant.SCREEN_HEIGHT/10);
		//積分榜圖片
		exitgame=BitmapFactory.decodeResource(this.getResources(), R.drawable.exitgame);
		exitgame=PicLoadUtil.scaleToFit(exitgame,Constant.SCREEN_WIDTH/3, Constant.SCREEN_HEIGHT/10);
		//開啟音效按鈕
		soundon=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound_on);
		soundon=PicLoadUtil.scaleToFit(soundon,Constant.SCREEN_HEIGHT/8, Constant.SCREEN_HEIGHT/8);
		//關閉音效按鈕
		soundoff=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound_off);
		soundoff=PicLoadUtil.scaleToFit(soundoff,Constant.SCREEN_HEIGHT/8, Constant.SCREEN_HEIGHT/8);
	}

	//刷框執行緒
	private class KeyThread extends Thread{
		private boolean keyFlag=false;
		@Override
		public void run(){
			while(isKeyFlag()){
				try{
					Thread.sleep(500);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				repaint();
			}
		}
		public void setKeyFlag(boolean keyFlag) {
			this.keyFlag = keyFlag;
		}
		public boolean isKeyFlag() {
			return keyFlag;
		}
	}

	//重新繪制畫面
	public void repaint(){
		Canvas canvas=this.getHolder().lockCanvas();
		try{
			synchronized(canvas){
				onDraw(canvas);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas!=null)
				this.getHolder().unlockCanvasAndPost(canvas);
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
}
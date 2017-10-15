package com.example.m_girl;
//失敗介面配置
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



@SuppressLint("ClickableViewAccessibility")
public class FailView extends SurfaceView implements SurfaceHolder.Callback{
	MainActivity mainActivity;
	Paint paint;

	MainMenuButton returngame;//重新開始按鈕的索引
	MainMenuButton exitgame;//離開按鈕的索引

	float pointx;//螢幕上觸控到的點
	float pointy;//螢幕上觸控到的點

	Bitmap mainbackground;//主界面的背景圖片
	Bitmap restart_button;//開始按鈕圖片 
	Bitmap exit_button;//離開游戲按鈕圖片

	public FailView(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity=mainActivity;
		getHolder().addCallback(this);//登錄回調接口

		paint=new Paint();//建立畫筆
		paint.setAntiAlias(true);//開啟抗鋸齒

		initBitmap();//起始化點陣圖資源
		//開始按鈕建立物件
		returngame=new MainMenuButton(mainActivity,restart_button,restart_button,Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/3);
		//離開按鈕建立物件
		exitgame=new MainMenuButton(mainActivity,exit_button,exit_button,Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/2);

	}
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));//只在螢幕範圍內繪制圖片
		canvas.drawColor(Color.WHITE);//界面設定為白色
		//繪制主界面的背景圖片
		canvas.drawBitmap(mainbackground, 0, 0, paint);
		//繪制開始按鈕
		returngame.drawSelf(canvas, paint);
		//繪制離開按鈕
		exitgame.drawSelf(canvas, paint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://按下
			if(returngame.isPointInRect(pointx, pointy))
			{//跳躍到游戲界面
				mainActivity.sendMessage(0);//傳送訊息，進入游戲界面
			}else if(exitgame.isPointInRect(pointx, pointy))//若果按下離開按鈕
			{
				System.exit(0);//離開游戲
			}
			break;
		case MotionEvent.ACTION_UP://抬起

			break;
		}
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {	//建立失敗介面
		repaint();//繪制界面

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	//起始化圖片資源
	public void initBitmap(){
		//主界面的背景圖片
		mainbackground=BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
		mainbackground=PicLoadUtil.scaleToFit(mainbackground,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		//開始按鈕圖片
		restart_button=BitmapFactory.decodeResource(this.getResources(), R.drawable.restart);
		restart_button=PicLoadUtil.scaleToFit(restart_button,Constant.SCREEN_WIDTH/3, Constant.SCREEN_HEIGHT/10);
		//離開按鈕圖片
		exit_button=BitmapFactory.decodeResource(this.getResources(), R.drawable.exitgame);
		exit_button=PicLoadUtil.scaleToFit(exit_button,Constant.SCREEN_WIDTH/3, Constant.SCREEN_HEIGHT/10);
	}
	
	//重新繪制畫面
		@SuppressLint("WrongCall")
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
				if(canvas!=null){
					this.getHolder().unlockCanvasAndPost(canvas);
				}
			}
		}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
}

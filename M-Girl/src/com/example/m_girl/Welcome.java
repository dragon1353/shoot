package com.example.m_girl;
//歡迎畫面配置
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//接回主畫面
public class Welcome extends SurfaceView implements SurfaceHolder.Callback{
	MainActivity mainActivity;

	Bitmap currentLogo;//目前logo圖片參考
	Bitmap[] welcomepic=new Bitmap[1];//logo圖片陣列
	Paint paint;			//改變透明度
	int currentX;
	int currentY;
	int sleepSpan=50;		//延遲50秒
	int alpha = 0;			//透明度
	public Welcome(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity=mainActivity;
		this.getHolder().addCallback(this);//設定生命周期回調接口的實現者
		paint = new Paint();//建立畫筆
		paint.setAntiAlias(true);//開啟抗鋸齒
		//載入圖片
		welcomepic[0]=BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.welcome1);
		welcomepic[0]=PicLoadUtil.scaleToFit(welcomepic[0],Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
	}

	public void onDraw(Canvas canvas){	
		//繪制黑填充矩形清背景
		paint.setColor(Color.WHITE);//設定畫筆彩色
		paint.setAlpha(255);
		canvas.drawRect(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, paint);
		//進行平面貼圖
		if(currentLogo == null)return;
		paint.setAlpha(alpha);	
		canvas.drawBitmap(currentLogo,0 , 0, paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {//建立時被呼叫
		new Thread(){
			@SuppressLint("WrongCall")
			public void run(){
				for(Bitmap bm:welcomepic){
					currentLogo=bm;
					//計算圖片位置
					currentX=Constant.SCREEN_WIDTH/2;
					currentY= Constant.SCREEN_HEIGHT/2;
					for(int i=255;i>-10;i=i-10){//動態變更圖片的透明度值並不斷重繪	
						alpha=i;
						if(alpha<0)
							alpha=0;
						SurfaceHolder myholder=Welcome.this.getHolder();
						Canvas canvas = myholder.lockCanvas();//取得畫布
						try{
							synchronized(myholder){
								onDraw(canvas);//繪制
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							if(canvas != null)
								myholder.unlockCanvasAndPost(canvas);
						}
						try{
							if(i==255)//若是新圖片，多等待一會
								Thread.sleep(1000);
							Thread.sleep(sleepSpan);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				mainActivity.sendMessage(0);//傳送訊息，進入主界面
			}
		}.start();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//銷毀時呼叫
	}
}
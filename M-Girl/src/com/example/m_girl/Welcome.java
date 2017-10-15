package com.example.m_girl;
//�w��e���t�m
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//���^�D�e��
public class Welcome extends SurfaceView implements SurfaceHolder.Callback{
	MainActivity mainActivity;

	Bitmap currentLogo;//�ثelogo�Ϥ��Ѧ�
	Bitmap[] welcomepic=new Bitmap[1];//logo�Ϥ��}�C
	Paint paint;			//���ܳz����
	int currentX;
	int currentY;
	int sleepSpan=50;		//����50��
	int alpha = 0;			//�z����
	public Welcome(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity=mainActivity;
		this.getHolder().addCallback(this);//�]�w�ͩR�P���^�ձ��f����{��
		paint = new Paint();//�إߵe��
		paint.setAntiAlias(true);//�}�ҧܿ���
		//���J�Ϥ�
		welcomepic[0]=BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.welcome1);
		welcomepic[0]=PicLoadUtil.scaleToFit(welcomepic[0],Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
	}

	public void onDraw(Canvas canvas){	
		//ø��¶�R�x�βM�I��
		paint.setColor(Color.WHITE);//�]�w�e���m��
		paint.setAlpha(255);
		canvas.drawRect(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, paint);
		//�i�業���K��
		if(currentLogo == null)return;
		paint.setAlpha(alpha);	
		canvas.drawBitmap(currentLogo,0 , 0, paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {//�إ߮ɳQ�I�s
		new Thread(){
			@SuppressLint("WrongCall")
			public void run(){
				for(Bitmap bm:welcomepic){
					currentLogo=bm;
					//�p��Ϥ���m
					currentX=Constant.SCREEN_WIDTH/2;
					currentY= Constant.SCREEN_HEIGHT/2;
					for(int i=255;i>-10;i=i-10){//�ʺA�ܧ�Ϥ����z���׭Ȩä��_��ø	
						alpha=i;
						if(alpha<0)
							alpha=0;
						SurfaceHolder myholder=Welcome.this.getHolder();
						Canvas canvas = myholder.lockCanvas();//���o�e��
						try{
							synchronized(myholder){
								onDraw(canvas);//ø��
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
							if(i==255)//�Y�O�s�Ϥ��A�h���ݤ@�|
								Thread.sleep(1000);
							Thread.sleep(sleepSpan);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				mainActivity.sendMessage(0);//�ǰe�T���A�i�J�D�ɭ�
			}
		}.start();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//�P���ɩI�s
	}
}
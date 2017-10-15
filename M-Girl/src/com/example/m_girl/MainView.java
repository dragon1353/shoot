package com.example.m_girl;
//�C���}�l�e���t�m
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
	Paint paint;//�e��

	KeyThread keythread;//�D�ɭ�����ذ����
	MediaPlayer mMediaPlayer;//�I�����ּ������O
	MainMenuButton startbutton;//�}�l���s������
	MainMenuButton exitbutton;//���}���s������
	MainMenuButton soundbutton;//���ı�����s������

	float pointx;//�ù��WĲ���쪺�I
	float pointy;//�ù��WĲ���쪺�I

	Bitmap background;//�D�ɭ����I���Ϥ�
	Bitmap startgame;//�}�l���s�Ϥ�
	Bitmap exitgame;//���}���s�Ϥ�
	Bitmap soundoff;//�������Ī����s�Ϥ�
	Bitmap soundon;//�}�ҭ��Ī����s�Ϥ�

	public MainView(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity=mainActivity;
		getHolder().addCallback(this);//�n���^�ձ��f
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));//�u�b�ù��d��ø��Ϥ�
		canvas.drawColor(Color.WHITE);//�ɭ��]�w���զ�
		//ø��D�ɭ����I���Ϥ�
		canvas.drawBitmap(background, 0,0, paint);
		//ø��}�l���s
		startbutton.drawSelf(canvas, paint);
		//ø�����}���s
		exitbutton.drawSelf(canvas, paint);
		//ø��ı�����s
		soundbutton.drawSelf(canvas, paint);
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN://���U
			if(startbutton.isPointInRect(pointx, pointy))//���D������ɭ�
				mainActivity.sendMessage(1);//�ǰe�T���A�i�J�����ɭ�
			if(exitbutton.isPointInRect(pointx, pointy))
				System.exit(0);
			else if(soundbutton.isPointInRect(pointx, pointy)){
				soundbutton.setswitch();//�ഫ�Ϥ�
				//��{������\��
				mainActivity.backgroundsound=soundbutton.isOnflag();//���I�����ֶ}�ҼЧӦ쵹����
			}
			break;
		case MotionEvent.ACTION_UP://��_
			
			break;
		}
		return true;
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		paint=new Paint();//�إߵe��
		paint.setAntiAlias(true);//�}�ҧܿ���

		//�I������
		mMediaPlayer=MediaPlayer.create(mainActivity, R.raw.background);//�إ߭I�����ּ���
		mMediaPlayer.setLooping(true);//�`������

		if(mainActivity.backgroundsound)
			mMediaPlayer.start();//�}�ҭ���	

		initBitmap();//�_�l���I�}�ϸ귽
		keythread=new KeyThread();//��ذ�����إߪ���
		//�}�l���s�إߪ���
		startbutton=new MainMenuButton(mainActivity,startgame,startgame,Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/3);
		//�n�����s�إߪ���
		exitbutton=new MainMenuButton(mainActivity,exitgame,exitgame,Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/2);
		//���ī��s�إߪ���
		soundbutton=new MainMenuButton(mainActivity,soundon,soundoff,0,7*Constant.SCREEN_HEIGHT/8);

		//�}�ҿù���ť
		setOnTouchListener(this);
		keythread.setKeyFlag(true);//��ذ�������ЧӦ�]��true
		keythread.start();//�}�Ҩ�ذ����
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		keythread.setKeyFlag(false);//��ذ�������ЧӦ�]��false

		//�����I������
		if(mMediaPlayer.isPlaying())//�Y�G���b����
			mMediaPlayer.stop();//�����
	}
	//�_�l�ƹϤ��귽
	public void initBitmap(){
		//�D�ɭ����I���Ϥ�
		background=BitmapFactory.decodeResource(this.getResources(), R.drawable.progress);
		background=PicLoadUtil.scaleToFit(background,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		//�}�l���s�Ϥ�
		startgame=BitmapFactory.decodeResource(this.getResources(), R.drawable.start);
		startgame=PicLoadUtil.scaleToFit(startgame,Constant.SCREEN_WIDTH/3 ,Constant.SCREEN_HEIGHT/10);
		//�n���]�Ϥ�
		exitgame=BitmapFactory.decodeResource(this.getResources(), R.drawable.exitgame);
		exitgame=PicLoadUtil.scaleToFit(exitgame,Constant.SCREEN_WIDTH/3, Constant.SCREEN_HEIGHT/10);
		//�}�ҭ��ī��s
		soundon=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound_on);
		soundon=PicLoadUtil.scaleToFit(soundon,Constant.SCREEN_HEIGHT/8, Constant.SCREEN_HEIGHT/8);
		//�������ī��s
		soundoff=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound_off);
		soundoff=PicLoadUtil.scaleToFit(soundoff,Constant.SCREEN_HEIGHT/8, Constant.SCREEN_HEIGHT/8);
	}

	//��ذ����
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

	//���sø��e��
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
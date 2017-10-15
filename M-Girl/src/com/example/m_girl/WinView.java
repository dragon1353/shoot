package com.example.m_girl;
//���Ѥ����t�m
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
public class WinView extends SurfaceView implements SurfaceHolder.Callback{
	MainActivity mainActivity;
	Paint paint;
	MainMenuButton returngame;	//���s�}�l�C�����s������
	MainMenuButton exitgame;//���}���s������

	float pointx;//�ù��WĲ���쪺�I
	float pointy;//�ù��WĲ���쪺�I

	Bitmap victory;			//ø�s�ӧQ�Ϥ�
	Bitmap mainbackground;	//�D�ɭ����I���Ϥ�
	Bitmap restart_button;	//�}�l���s�Ϥ� 
	Bitmap exit_button;		//���}�������s�Ϥ�

	public WinView(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity=mainActivity;
		getHolder().addCallback(this);//�n���^�ձ��f

		paint=new Paint();//�إߵe��
		paint.setAntiAlias(true);//�}�ҧܿ���

		initBitmap();//�_�l���I�}�ϸ귽
		//�D�������s
		returngame=new MainMenuButton(mainActivity,restart_button,restart_button,Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/3);
		//���}���s�إߪ���
		exitgame=new MainMenuButton(mainActivity,exit_button,exit_button,Constant.SCREEN_WIDTH/3,2*Constant.SCREEN_HEIGHT/3);

	}
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));//�u�b�ù��d��ø��Ϥ�
		canvas.drawColor(Color.WHITE);//�ɭ��]�w���զ�
		//ø��D�ɭ����I���Ϥ�
		canvas.drawBitmap(mainbackground, 0, 0, paint);
		//ø�s�ӧQ���Ϥ�
		canvas.drawBitmap(victory, 0, 0, paint);
		//ø�s�}�l���s
		returngame.drawSelf(canvas, paint);
		//ø�����}���s
		exitgame.drawSelf(canvas, paint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://���U
			if(exitgame.isPointInRect(pointx, pointy))//�Y�G���U���}���s
				System.exit(0);//���}����
			if(returngame.isPointInRect(pointx, pointy))
				mainActivity.sendMessage(1);
			break;
		case MotionEvent.ACTION_UP://��_

			break;
		}
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {	//�إߥ��Ѥ���
		repaint();//ø��ɭ�

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	//�_�l�ƹϤ��귽
	public void initBitmap(){
		//�D�ɭ����I���Ϥ�
		victory=BitmapFactory.decodeResource(this.getResources(), R.drawable.victory);
		victory=PicLoadUtil.scaleToFit(victory,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT/5);
		//�ӧQ�Ϥ�
		mainbackground=BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
		mainbackground=PicLoadUtil.scaleToFit(mainbackground,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		//�}�l���s�Ϥ�
		restart_button=BitmapFactory.decodeResource(this.getResources(), R.drawable.restart);
		restart_button=PicLoadUtil.scaleToFit(restart_button,Constant.SCREEN_WIDTH/3, Constant.SCREEN_HEIGHT/10);
		//���}���s�Ϥ�
		exit_button=BitmapFactory.decodeResource(this.getResources(), R.drawable.exitgame);
		exit_button=PicLoadUtil.scaleToFit(exit_button,Constant.SCREEN_WIDTH/3, Constant.SCREEN_HEIGHT/10);
	}

	//���sø��e��
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

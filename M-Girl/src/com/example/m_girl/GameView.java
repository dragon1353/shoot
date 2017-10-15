package com.example.m_girl;
//�C���D�e���t�m
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
	boolean s=false;					//�P�w����
	int sx;						//��V�y�_�lX��
	int sy;						//��V�y�_�lY��
	int endx;					//��V�y����X��
	int endy;					//��V�y����Y��
	MainMenuButton ballbutton;	//��V�y���s�]�w
	Paint paint;
	MediaPlayer mMediaPlayer;	//�I�����ּ������O
	int backGroundY=0;			//�_�l�֤߹�Y�y��
	int center=0;				//�ͩR�ζ��Z
	int dir;					//�P�w��V
	int status=1;				//�P�_��e�C�����A 0���` 1�i��
	int i=0;					//�֤߹ϯ���

	Bitmap monster1;			//�Ǫ��Ϥ�
	Bitmap monster2;
	Bitmap monster3;
	Bitmap boss;

	Bitmap battleback;			//�I��
	Bitmap[] battlebacks = new Bitmap[(Constant.SCREEN_HEIGHT/30)];//�˭I�����Υγ~
	Bitmap math[];				//�r�Ϥ�
	Bitmap ball;				//��V�y�Ϥ�
	Bitmap down;				//�̩��U�\��Ω���
	Bitmap heart;				//�ͩR�Ϥ�
	Bitmap plaid;				//��ۮعϤ�
	Bitmap life;				//�ͩR�Ϥ�
	ExplodeThread explodeThread;//�z�}�Ϥ������
	MoveThread movethread;		//���F�۾������ʰ����
	KeyThread keythread;		//��ذ����
	GameThread gameThread;		//�C����������X�{�ɶ������
	Plane plane=new Plane(Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT-3*Constant.SCREEN_HEIGHT/16, 01, 0, true, 2, this);
	CopyOnWriteArrayList<Bullet> myst =new CopyOnWriteArrayList<Bullet>();			//�ڤ�l�u
	CopyOnWriteArrayList<Bullet> mnst =new CopyOnWriteArrayList<Bullet>();			//�Ĥ�l�u
	ArrayList<Explode> exp =new ArrayList<Explode>();			//�z���S��
	ArrayList<HitExplode> hexp =new ArrayList<HitExplode>();	//�z���S��
	ArrayList<Monster> ms;								//�ľ�
	ArrayList<Lifeup> lifes;							//�s��ͩR�W�[��

	int [] exID=new int[]{								//�z���Ϥ�
			R.drawable.explode1,
			R.drawable.explode2,
			R.drawable.explode3
	};

	int [] sexID=new int[]{								//�����z���Ϥ�
			R.drawable.explode01,
			R.drawable.explode02,
			R.drawable.explode03
	};
	Bitmap[] explodes =new Bitmap[exID.length];			//�ʸ��z�����s���
	Bitmap[] sexplodes =new Bitmap[sexID.length];

	public GameView(MainActivity mainActivity) {		//�غc�l
		super(mainActivity);
		this.mainActivity=mainActivity;
		getHolder().addCallback(this);//�n���^�ձ��f
		//���o�Ĥ@�����ĤH���
		ms=Map.getFirst();
		lifes=Map.getFirstLife();
		initBitmap();//�_�l���I�}�ϸ귽
	}
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,-Constant.SCREEN_HEIGHT,Constant.SCREEN_WIDTH,2*Constant.SCREEN_HEIGHT));//�u�b�ù��d��ø��Ϥ�

		canvas.drawColor(Color.WHITE);//�ɭ��]�w���զ�	
		//	canvas.drawBitmap(battleback, 0,0, paint);
		int backGroundY=this.backGroundY;
		int i=this.i;
		//�ѨMi�U�����D
		if(backGroundY>0){
			int n=(backGroundY/Constant.pictureHeight)+((backGroundY%Constant.pictureHeight==0)?0:1);//�p��i�U�����X�i��
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(battlebacks[(i-j+(Constant.SCREEN_HEIGHT/30))%(Constant.SCREEN_HEIGHT/30)],0,backGroundY+Constant.pictureHeight*j, paint);
			}
		}

		//�ѨMi�ۤv
		canvas.drawBitmap(battlebacks[i],0,backGroundY, paint);

		//�ѨMi�W�����D
		if(backGroundY<Constant.SCREEN_HEIGHT-Constant.pictureHeight){
			int k=Constant.SCREEN_HEIGHT-(backGroundY+Constant.pictureHeight);
			int n=(k/Constant.pictureHeight)+((k%Constant.pictureHeight==0)?0:1);//�p��i�W�����X�i��
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
		
		//�e�X�ɶ�
		String timeStr = gameThread.touchtime/10+"";	//�ഫ���r��
		for(int c=0;c<timeStr.length();c++){		//�`����ø�s�X�ɶ�
			int tempScore=timeStr.charAt(c)-'0';
			canvas.drawBitmap(math[tempScore],14*Constant.SCREEN_WIDTH/16+c*22, 12, paint);
			if(gameThread.touchtime==0){
				timeStr = gameThread.bosstime/10+"";	//�ഫ���r��
				for(c=0;c<timeStr.length();c++){		//�`����ø�s�X�ɶ�
					tempScore=timeStr.charAt(c)-'0';
					canvas.drawBitmap(math[tempScore],14*Constant.SCREEN_WIDTH/16+c*22, 12, paint);
				}
			}
		}
		//ø�s���h�\���
		canvas.drawBitmap(down, 0,7*Constant.SCREEN_HEIGHT/8, paint);
		//ø�s��V��
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
	public void surfaceCreated(SurfaceHolder holder) {	//�إߵe�����ɭ�
		paint=new Paint();//�إߵe��
		paint.setAntiAlias(true);//�}�ҧܿ���
		//	initBitmap();//�_�l���I�}�ϸ귽
		//�I������
		mMediaPlayer=MediaPlayer.create(mainActivity, R.raw.super_gamer_boy_offvocal);//�إ߭I�����ּ���
		mMediaPlayer.setLooping(true);//�`������

		keythread=new KeyThread(this);//��ذ����
		explodeThread=new ExplodeThread(this);
		gameThread=new GameThread(this);
		movethread=new MoveThread(this);
		ballbutton =new MainMenuButton(mainActivity, ball, ball, Constant.SCREEN_WIDTH/18,18*Constant.SCREEN_HEIGHT/20);
		//�}�ҥ��������	
		startAllThreads();
		//�}�ҿù���ť
		setOnTouchListener(this);
		//�}�ҭI������
		if(mainActivity.backgroundsound)
			mMediaPlayer.start();//�}�ҭ���	
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {			//�R���������ɭ�
		boolean retry = true;
		stopAllThreads();//����Ҧ������
		//�����Ҧ������
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
			//���_�a�`���A����䥦���������
		}
		//�����I������
		if(mMediaPlayer.isPlaying())//�Y�G���b����
			mMediaPlayer.stop();//�����
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	//�_�l�ƹϤ��귽
	public void initBitmap(){
		battleback = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		battleback=PicLoadUtil.scaleToFit(battleback,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		for(int i=0; i<battlebacks.length; i++){	//���p�Ϥ�
			battlebacks[i] = Bitmap.createBitmap(battleback,0,Constant.pictureHeight*i, Constant.SCREEN_WIDTH, Constant.pictureHeight);
		}
		battleback = null;	//����쥻���j��
		//�Ǫ����Ϥ�
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
		//�Ϥ��j�p

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
	//Ĳ����V�y����k
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v,MotionEvent event) {
		int startx=(int) event.getX();		//��V�y�_�lX��
		int starty=(int) event.getY();		//��V�y�_�lY��
		int movex=(int) event.getX();		//��V�y����X��
		int movey=(int) event.getY();		//��V�y����Y��
		try  {   
			switch  (event.getAction()) {  	//Ĳ���ƥ󪺳B�z
			case  MotionEvent.ACTION_DOWN:  //�I��̹�
				picMove(startx, starty);   
				sx=startx;
				sy=starty;
				break ; 
			case  MotionEvent.ACTION_MOVE:  //���ʦ�m
				if(sx<=plaid.getWidth() && sy>=(Constant.SCREEN_HEIGHT-plaid.getHeight())){
					picMove(movex, movey);  
					endx=movex;
					endy=movey;
					s=true;
				}
				else
					s=false;
				break ;   
			case  MotionEvent.ACTION_UP:  	//���}�̹�
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
	//���ʤ�V�y����k
	private void picMove(int x,int y){    
		//�����V�y�W�L��ۮت������B�z 		
		//����̹��V�k�W�L�̹� 
		if	(s && endx!=sx && endx >sx+30 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20+15 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20-15){
			ballbutton.x=Constant.SCREEN_WIDTH/18+15;
			plane.dir=Constant.DIR_LEFT;
			if(plane.x>=Constant.SCREEN_WIDTH-Constant.MYSHIP_WIDTH)			//�p�G�۾�����>�k��
				plane.x= Constant.SCREEN_WIDTH-Constant.MYSHIP_WIDTH;	//��m=�k��-�۾��e�רϨ�۾��k����
			else
				plane.x+=Constant.MYSHIP_SPEED;
		}
		//����̹��V���W�L�̹� 
		else if(s && endx!=sx && endx<sx-30 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20+15 && ballbutton.y!=18*Constant.SCREEN_HEIGHT/20-15){
			ballbutton.x =Constant.SCREEN_WIDTH/18-15;
			plane.dir=Constant.DIR_RIGHT;
			if(plane.x<=0)
				plane.x=0;
			else
				plane.x-=Constant.MYSHIP_SPEED;
		}
		//����̹��V�W�W�L�̹�
		else if(s && endy!=sy && endy >= sy+30 && ballbutton.x!=Constant.SCREEN_WIDTH/18+15 && ballbutton.x !=Constant.SCREEN_WIDTH/18-15){
			ballbutton.y=18*Constant.SCREEN_HEIGHT/20+15;
			if(plane.y>=Constant.SCREEN_HEIGHT-Constant.MYSHIP_HEIGHT-Constant.SCREEN_HEIGHT/8)			//�p�G�۾�����>�U��
				plane.y=Constant.SCREEN_HEIGHT-Constant.MYSHIP_HEIGHT-Constant.SCREEN_HEIGHT/8;	//��m=�U��-�۾����רϨ�۾��U����
			else
				plane.y+=Constant.MYSHIP_SPEED;
		}
		//����̹��V�U�W�L�̹�
		else if(s && endy!=sy && endy <= sy-30 && ballbutton.x!=Constant.SCREEN_WIDTH/18+15 && ballbutton.x !=Constant.SCREEN_WIDTH/18-15){
			ballbutton.y=18*Constant.SCREEN_HEIGHT/20-15;
			if(plane.y<=0)
				plane.y=0;
			else
				plane.y-=Constant.MYSHIP_SPEED;
		}
	}   
	//������������k
	public void overGame(){
		stopAllThreads();//����Ҧ������
		//�����I������
		if(mMediaPlayer.isPlaying())//�Y�G���b����
			mMediaPlayer.stop();//�����
		mainActivity.sendMessage(2);//�ǰe�T���A�i�J���������ɭ�
	}

	//�}�ҩҦ������
	public void startAllThreads(){
		keythread.setKeyFlag(true);//��ذ�������ЧӦ�]��true
		explodeThread.setflag(true);
		movethread.setFlag(true);
		gameThread.setflag(true);

		keythread.start();//�}�Ҩ�ذ����
		explodeThread.start();
		movethread.start();
		gameThread.start();
	}

	//����Ҧ������
	public void stopAllThreads(){
		keythread.setKeyFlag(false);//������ذ����
		explodeThread.setflag(false);
		movethread.setFlag(false);
		gameThread.setflag(false);
	}
	//���sø��e��
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


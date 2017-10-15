package com.example.m_girl;
//�۾��t�m

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Plane{
	int x;						//�۾�X�y��
	int y;						//�۾�Y�y��
	int planelife;				//�ͩR
	int type;					//�۾�����
	int dir;					//��V
	int i;						//���}�C��
	Bitmap plane_m;				//�۾��R��
	Bitmap plane_l;				//�۾��V�k����
	Bitmap plane_r;				//�۾��V������
	int shoottype=1;			//�g������
	boolean status=true;		//�۾��O�_�ͦs���A
	GameView gameview;			//�ϥΥD�e��
	public Plane(int x,int y,int type,int dir,boolean status,int planelife,GameView gameview) {
		this.x=x;
		this.y=y;
		this.type=type;
		this.i=1;
		this.dir=dir;
		this.status=status;
		this.planelife=planelife;
		this.gameview=gameview;
		initBitmap();	
	}
	public void initBitmap() {
		if(type == 1){			//�Ĥ@�ئ۾�
			plane_m = BitmapFactory.decodeResource(gameview.getResources(), R.drawable.plane_m);
			plane_l = BitmapFactory.decodeResource(gameview.getResources(), R.drawable.plane_l);
			plane_r = BitmapFactory.decodeResource(gameview.getResources(), R.drawable.plane_r);
		}
		Constant.MYSHIP_WIDTH=plane_m.getWidth();
		Constant.MYSHIP_HEIGHT=plane_m.getHeight();
	}
	public void draw(Canvas canvas,Paint paint) {
		if(dir==Constant.DIR_STOP)
			canvas.drawBitmap(plane_l, x, y, paint);
		else if(dir==Constant.DIR_RIGHT)
			canvas.drawBitmap(plane_r, x, y, paint);
		else if(dir==Constant.DIR_LEFT)
			canvas.drawBitmap(plane_l, x, y, paint);

	}
	public void setDir(int dir){  
		this.dir = dir;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		if(this.x<=0)
			this.x=0;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		if(this.y <= 0)
			this.y = 0;
	}
	public void shoot() {	//�g����k
		if(shoottype==1){
			Bullet b=new Bullet(gameview.plane.x+plane_m.getWidth()/2-5, this.y, 1, Constant.DIR_STOP, gameview, 0);
			gameview.myst.add(b);
		}
	}
	public boolean contain(Bullet b) {	//�Q�Ĥ�l�u�I��
		if(isContain(b.x, b.y, b.bitmap.getWidth(), b.bitmap.getHeight())){
			this.planelife--;
			this.x=Constant.SCREEN_WIDTH/2;
			this.y=Constant.SCREEN_HEIGHT-3*Constant.SCREEN_HEIGHT/16;
			if(this.planelife<0){		//�ͩR�p��0���ɭ�
				gameview.status = 0;
				if(gameview.mMediaPlayer.isPlaying()){
					gameview.mMediaPlayer.stop();
				}
				gameview.mainActivity.handler.sendEmptyMessage(2);	//�o�e�T�����D��
			}
			return true;
		}
		return false;
	}
	public boolean contain(Monster mr){	//�Q�Ĥ�D��I��
		if(isContain(mr.x, mr.y, mr.bitmap.getWidth(), mr.bitmap.getHeight())){
			this.planelife--;
			this.x=Constant.SCREEN_WIDTH/2;
			this.y=Constant.SCREEN_HEIGHT-3*Constant.SCREEN_HEIGHT/16;
			if(this.planelife<0){		//�ͩR�p��0���ɭ�
				gameview.status = 0;
				if(gameview.mMediaPlayer.isPlaying()){
					gameview.mMediaPlayer.stop();
				}
				gameview.mainActivity.handler.sendEmptyMessage(2);	//�o�e�T�����D��
			}
			return true;
		}
		return false;
	}
	public boolean contain(Lifeup l) {	//����W�[�ͩR
		if(isLifeContain(l.x, l.y, l.bitmap.getWidth(), l.bitmap.getHeight())){
			if(this.planelife<5)
				this.planelife++;//�ͩR�[�@
			return true;
		}
		return false;
	}
	private boolean isContain(int ox, int oy, int owidth, int oheight) {	//�P�_�U�ظI������
		int bx=0;		//�kX
		int by=0;		//�UY
		int sx=0;		//��X
		int sy=0;		//�WY
		int width = 0;
		int height = 0;
		boolean xFlag = true;	//�۾�x�O�_�b�e
		boolean yFlag = true;	//�۾�y�O�_�b�e

		if(this.x >= ox){
			bx = this.x;
			sx = ox;
			xFlag = false;
		}
		else{
			bx = ox;
			sx = this.x;
			xFlag = true;
		}

		if(this.y >= oy){
			by = this.y;
			sy = oy;
			yFlag = false;
		}
		else{
			by = oy;
			sy = this.y;
			yFlag = true;
		}

		if(xFlag == true)
			width = this.plane_m.getWidth();
		else 
			width = owidth;

		if(yFlag == true)
			height = this.plane_m.getHeight();
		else
			height = oheight;
		if(bx>=sx && bx<=sx+width-1 && by>=sy && by<=sy+height-1){	//�P�_�O�_���|
			double Dwidth=width-bx+sx;   				//���|�e��  
			double Dheight=height-by+sy; 				//���|����
			if(Dwidth*Dheight/(owidth*oheight)>=0.80){	//���|�W�L80%�P�w�I��
				return true;
			}
		}
		return false;
	}
	private boolean isLifeContain(int ox, int oy, int owidth, int oheight) {	//�P�_�U�ظI������
		int bx=0;		//�kX
		int by=0;		//�UY
		int sx=0;		//��X
		int sy=0;		//�WY
		int width = 0;
		int height = 0;
		boolean xFlag = true;	//�۾�x�O�_�b�e
		boolean yFlag = true;	//�۾�y�O�_�b�e

		if(this.x >= ox){
			bx = this.x;
			sx = ox;
			xFlag = false;
		}
		else{
			bx = ox;
			sx = this.x;
			xFlag = true;
		}

		if(this.y >= oy){
			by = this.y;
			sy = oy;
			yFlag = false;
		}
		else{
			by = oy;
			sy = this.y;
			yFlag = true;
		}

		if(xFlag == true)
			width = this.plane_m.getWidth();
		else 
			width = owidth;

		if(yFlag == true)
			height = this.plane_m.getHeight();
		else
			height = oheight;
		if(bx>=sx && bx<=sx+width-1 && by>=sy && by<=sy+height-1){	//�P�_�O�_���|
			double Dwidth=width-bx+sx;   				//���|�e��  
			double Dheight=height-by+sy; 				//���|����
			if(Dwidth*Dheight/(owidth*oheight)>=0.20){	//���|�W�L20%�P�w�I��
				return true;
			}
		}
		return false;
	}
}

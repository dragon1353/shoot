package com.example.m_girl;
//�l�u�t�m

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	int x;			//�l�u��m
	int y;
	int xx=0;		//�ĤH��۾���X�ȶZ���t
	int yy=0;		//�ĤH��۾���Y�ȶZ���t
	int type;		//�l�u������
	int dir=0;		//��i��V
	int extra;		//�ĤH�S��u��
	Bitmap bitmap;	//�l�u����
	GameView gameview;

	public Bullet(int x,int y,int type,int dir,GameView gameView, int extra) {	//�غc�l
		this.x=x;
		this.y=y;
		this.type=type;
		this.dir=dir;
		this.gameview=gameView;
		this.extra=extra;
		iniBitmap();
	}
	public void iniBitmap() {								//��l�Ƥl�u
		if(type==01)
			bitmap=BitmapFactory.decodeResource(gameview.getResources(), R.drawable.bullet01);
		else if(type==02)
			bitmap=BitmapFactory.decodeResource(gameview.getResources(), R.drawable.bullet02);
		else if(type==03)
			bitmap=BitmapFactory.decodeResource(gameview.getResources(), R.drawable.bullet03);
		else if(type==1)
			bitmap=BitmapFactory.decodeResource(gameview.getResources(), R.drawable.bullet1);
		else if(type==2)
			bitmap=BitmapFactory.decodeResource(gameview.getResources(), R.drawable.bullet2);
		else if(type==3)
			bitmap=BitmapFactory.decodeResource(gameview.getResources(), R.drawable.bullet3);
	}
	public void move() {									//�l�u����
		if(dir==Constant.DIR_STOP)
		this.y-=Constant.SCREEN_WIDTH/10;

	}
	public void monstermove() {
		if(dir==Constant.DIR_DOWN){
			if(this.type==3 || this.extra==4 || this.extra==5)
				this.y+=Constant.SCREEN_WIDTH/16;
			else
				this.y+=Constant.SCREEN_WIDTH/32;
			yy=gameview.plane.y-this.y;
			if(this.y<(2*yy)){
				if((this.extra==1||this.extra==2||this.extra==3||this.extra==4))
					xx=gameview.plane.getX()-this.x;
				else
					xx=0;
			}
			this.x+=xx/15;
		}
	}
	public void drawSelf(Canvas canvas,Paint paint) {		//ø�s
		canvas.drawBitmap(bitmap, x, y,paint);
	}
}

package com.example.m_girl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//�R����������O

public class Explode {
	int x;		//�����z�}��m
	int y;
	Bitmap[] bitmaps;//�z�����Ϥ���
	int pic=0;		 //�Ϥ��հ������e�Ϥ���
	Bitmap bitmap;
	Paint paint;
	public Explode(int x,int y ,GameView gameView) {
		this.x=x;
		this.y=y;
		this.bitmaps=gameView.explodes;
		paint=new Paint();
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap,x,y, paint);
	}
	public boolean nextframe() {	//�e�U�i�ϥΦ��\�N�~�� �_�h�N���^
		if(pic<bitmaps.length){
			bitmap=bitmaps[pic];
			pic++;
			return true;
		}
		return false;	
	}
}

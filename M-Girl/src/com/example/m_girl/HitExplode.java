package com.example.m_girl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class HitExplode {
	int x;		//�����z�}��m
	int y;
	Bitmap[] bitmaps2;//�z�����Ϥ���
	int pic=0;		 //�Ϥ��հ������e�Ϥ���
	Bitmap bitmap;
	Paint paint;
	public HitExplode(int x,int y ,GameView gameView) {
		this.x=x;
		this.y=y;
		this.bitmaps2=gameView.sexplodes;
		paint=new Paint();
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap,x,y, paint);
	}
	public boolean nextframe() {	//�e�U�i�ϥΦ��\�N�~�� �_�h�N���^
		if(pic<bitmaps2.length){
			bitmap=bitmaps2[pic];
			pic++;
			return true;
		}
		return false;	
	}
}

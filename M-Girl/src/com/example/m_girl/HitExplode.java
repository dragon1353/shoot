package com.example.m_girl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class HitExplode {
	int x;		//物體爆破位置
	int y;
	Bitmap[] bitmaps2;//爆炸的圖片組
	int pic=0;		 //圖片組執行緒當前圖片數
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
	public boolean nextframe() {	//畫下張圖用成功就繼續 否則就換回
		if(pic<bitmaps2.length){
			bitmap=bitmaps2[pic];
			pic++;
			return true;
		}
		return false;	
	}
}

package com.example.m_girl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//摧毀物體用類別

public class Explode {
	int x;		//物體爆破位置
	int y;
	Bitmap[] bitmaps;//爆炸的圖片組
	int pic=0;		 //圖片組執行緒當前圖片數
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
	public boolean nextframe() {	//畫下張圖用成功就繼續 否則就換回
		if(pic<bitmaps.length){
			bitmap=bitmaps[pic];
			pic++;
			return true;
		}
		return false;	
	}
}

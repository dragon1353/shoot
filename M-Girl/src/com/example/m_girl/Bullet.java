package com.example.m_girl;
//子彈配置

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	int x;			//子彈位置
	int y;
	int xx=0;		//敵人跟自機的X值距離差
	int yy=0;		//敵人跟自機的Y值距離差
	int type;		//子彈的種類
	int dir=0;		//行進方向
	int extra;		//敵人特殊彈用
	Bitmap bitmap;	//子彈的圖
	GameView gameview;

	public Bullet(int x,int y,int type,int dir,GameView gameView, int extra) {	//建構子
		this.x=x;
		this.y=y;
		this.type=type;
		this.dir=dir;
		this.gameview=gameView;
		this.extra=extra;
		iniBitmap();
	}
	public void iniBitmap() {								//初始化子彈
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
	public void move() {									//子彈移動
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
	public void drawSelf(Canvas canvas,Paint paint) {		//繪製
		canvas.drawBitmap(bitmap, x, y,paint);
	}
}

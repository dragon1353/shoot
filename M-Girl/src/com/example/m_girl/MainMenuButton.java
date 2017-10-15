package com.example.m_girl;
//按鈕配置
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class MainMenuButton {
	MainActivity mainActivity;
	int x;//圖片的左上點x座標
	int y;//圖片的左上點y座標
	int width;//虛擬按鈕的寬
	int height;//虛擬按鈕的高
	Bitmap onBitmap;//按下圖片
	Bitmap offBitmap;//抬起圖片
	boolean isOn=true;//按下狀態為false
	public MainMenuButton(MainActivity mainActivity,Bitmap onBitmap,Bitmap offBitmap,int x,int y){
		this.mainActivity=mainActivity;
		this.isOn=mainActivity.backgroundsound;
		this.onBitmap=onBitmap;
		this.offBitmap=offBitmap;
		this.x=x;
		this.y=y;
		this.width=offBitmap.getWidth();
		this.height=offBitmap.getHeight();
	}
	public void drawSelf(Canvas canvas,Paint paint){//繪制虛擬按鈕
		if(isOn)
			canvas.drawBitmap(onBitmap, x, y, paint);
		else
			canvas.drawBitmap(offBitmap, x, y, paint);
	}
	public void setswitch(){
		isOn=!isOn;
	}
	public boolean isOnflag(){//傳回isOn的狀態
		return isOn;
	}
	//一個點是否在矩形內（內含邊界）
	public boolean isPointInRect(float pointx,float pointy){
		//若果在矩形內，傳回true
		if(pointx>=x&&pointx<=x+width&&pointy>=y&&pointy<=y+height)
			return true;//若果在，傳回true

		return false;//否則傳回false
	}	
}

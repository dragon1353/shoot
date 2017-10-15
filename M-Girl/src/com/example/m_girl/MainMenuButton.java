package com.example.m_girl;
//���s�t�m
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class MainMenuButton {
	MainActivity mainActivity;
	int x;//�Ϥ������W�Ix�y��
	int y;//�Ϥ������W�Iy�y��
	int width;//�������s���e
	int height;//�������s����
	Bitmap onBitmap;//���U�Ϥ�
	Bitmap offBitmap;//��_�Ϥ�
	boolean isOn=true;//���U���A��false
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
	public void drawSelf(Canvas canvas,Paint paint){//ø��������s
		if(isOn)
			canvas.drawBitmap(onBitmap, x, y, paint);
		else
			canvas.drawBitmap(offBitmap, x, y, paint);
	}
	public void setswitch(){
		isOn=!isOn;
	}
	public boolean isOnflag(){//�Ǧ^isOn�����A
		return isOn;
	}
	//�@���I�O�_�b�x�Τ��]���t��ɡ^
	public boolean isPointInRect(float pointx,float pointy){
		//�Y�G�b�x�Τ��A�Ǧ^true
		if(pointx>=x&&pointx<=x+width&&pointy>=y&&pointy<=y+height)
			return true;//�Y�G�b�A�Ǧ^true

		return false;//�_�h�Ǧ^false
	}	
}

package com.example.m_girl;
//掉落生命++配置
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Lifeup {
	Bitmap bitmap;
	int x;			//x起始值
	int y;
	boolean status;	//判定狀態
	int tp;	//判定觸發
	int start;	//起始點
	int target;	//路徑
	int step;	//移動像素
	int [][]path;//移動值
	int xSpan=0;	//移動距離初始化
	int ySpan=0;
	public Lifeup(int start,int target,int step, int [][]path,boolean status,int tp) {
		this.tp=tp;
		this.start=start;
		this.target=target;
		this.step=step;
		this.status=status;
		this.path=path;
		this.x=path[0][start];	
		this.y=path[1][start];		//縱向所以X值固定為怪物身上直線落下
	}
	public void drawSelf(Canvas canvas) {
		canvas.drawBitmap(bitmap, x, y,new Paint());
	}
	public void move() {
	/*	如果path[2]步數用的陣列第一個數相同時 step重計
		 ex:取pathA第二輪start=1 轉換之前  所以start=1之前
		 step(15)=path[2][0](15)
		 step歸0走第二段
		 start=(0+1)%path[1].length(4)→1%4 餘數=1 start=1
		 target=(1+1)%path[1].length(4)→2%4 餘數=2 target=2(下一步目標
		 x=path[0][1](X值用的path[0]第二個數
		 y=path[1][1](Y值用的path[0]第二個數*/


		if(step==path[2][start]){		//路徑結束，轉換下個路徑
			step=0;
			start=(start+1)%(path[1].length);
			target=(target+1)%(path[1].length);
			this.x=path[0][start];
			this.y=path[1][start];
		}

		/* 如果step不等於path[2][start]數值就繼續往下
		  EX:第一輪 start=0 取pathA 所以pathA[2][0]=15步
		  xS移動值=(280-480)/15
		  yS移動值=(110-50)/15	*/		

		else{						//沒有結束就一直往下走
			xSpan=(path[0][target]-path[0][start])/path[2][start];
			ySpan=(path[1][target]-path[1][start])/path[2][start];
			this.x+=xSpan;
			this.y+=ySpan;
			step++;
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x =x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}	
}
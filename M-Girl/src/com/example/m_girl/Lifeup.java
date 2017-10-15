package com.example.m_girl;
//�����ͩR++�t�m
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Lifeup {
	Bitmap bitmap;
	int x;			//x�_�l��
	int y;
	boolean status;	//�P�w���A
	int tp;	//�P�wĲ�o
	int start;	//�_�l�I
	int target;	//���|
	int step;	//���ʹ���
	int [][]path;//���ʭ�
	int xSpan=0;	//���ʶZ����l��
	int ySpan=0;
	public Lifeup(int start,int target,int step, int [][]path,boolean status,int tp) {
		this.tp=tp;
		this.start=start;
		this.target=target;
		this.step=step;
		this.status=status;
		this.path=path;
		this.x=path[0][start];	
		this.y=path[1][start];		//�a�V�ҥHX�ȩT�w���Ǫ����W���u���U
	}
	public void drawSelf(Canvas canvas) {
		canvas.drawBitmap(bitmap, x, y,new Paint());
	}
	public void move() {
	/*	�p�Gpath[2]�B�ƥΪ��}�C�Ĥ@�ӼƬۦP�� step���p
		 ex:��pathA�ĤG��start=1 �ഫ���e  �ҥHstart=1���e
		 step(15)=path[2][0](15)
		 step�k0���ĤG�q
		 start=(0+1)%path[1].length(4)��1%4 �l��=1 start=1
		 target=(1+1)%path[1].length(4)��2%4 �l��=2 target=2(�U�@�B�ؼ�
		 x=path[0][1](X�ȥΪ�path[0]�ĤG�Ӽ�
		 y=path[1][1](Y�ȥΪ�path[0]�ĤG�Ӽ�*/


		if(step==path[2][start]){		//���|�����A�ഫ�U�Ӹ��|
			step=0;
			start=(start+1)%(path[1].length);
			target=(target+1)%(path[1].length);
			this.x=path[0][start];
			this.y=path[1][start];
		}

		/* �p�Gstep������path[2][start]�ƭȴN�~�򩹤U
		  EX:�Ĥ@�� start=0 ��pathA �ҥHpathA[2][0]=15�B
		  xS���ʭ�=(280-480)/15
		  yS���ʭ�=(110-50)/15	*/		

		else{						//�S�������N�@�����U��
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
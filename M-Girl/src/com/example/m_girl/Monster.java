package com.example.m_girl;
//�Ǫ��t�m
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//�Ǫ����O
public class Monster {
	Bitmap bitmap;
	int x;			//�Ǫ��_�l��
	int y;
	boolean status;	//�Ǫ����ͦs���A
	int hp;			//�Ǫ����ͩR��
	int start;		//�Ǫ��X�o���I
	int target;		//�Ǫ���i�ؼ��I
	int step;		//��i�B��
	int touchp;		//�P�wĲ�o�ɶ��I
	int type;		//�Ǫ�����
	int extra;		//�h�ˤl�u
	int xSpan=0;	//���ʶZ����l��
	int ySpan=0;
	int [][]path;	//�Ĥ@��[]0��X 1��Y
	//path[0][?]=X�ȥ�
	//path[1][?]=Y�ȥ�
	//path[2][?]=�B��

	public Monster(int start,int target,int step,int [][]path,boolean status,int touchp,int type,int hp,int extra) {
		this.start=start;
		this.target=target;
		this.step=step;
		this.path=path;
		this.status=status;
		this.touchp=touchp;
		this.type=type;
		this.hp=hp;
		this.x=path[0][start];
		this.y=path[1][start];
		this.extra=extra;
	} 
	public void drawSelf(Canvas canvas) {
		canvas.drawBitmap(bitmap,x,y,new Paint());
	}
	public void move() {
		/*�p�Gpath[2]�B�ƥΪ��}�C�Ĥ@�ӼƬۦP�� step���p
		 ex:��pathA�ĤG��start=1 �ഫ���e  �ҥHstart=1���e
		 step(15)=path[2][0](15)
		 step�k0���ĤG�q
		 start=(0+1)%path[1].length(4)��1%4 �l��=1 start=1
		 target=(1+1)%path[1].length(4)��2%4 �l��=2 target=2(�U�@�B�ؼ�
		 x=path[0][1](X�ȥΪ�path[0]�ĤG�Ӽ�
		 y=path[1][1](Y�ȥΪ�path[0]�ĤG�Ӽ�
		 */

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
		  yS���ʭ�=(110-50)/15			*/

		else{						//�S�������N�@�����U��
			xSpan=(path[0][target]-path[0][start])/path[2][start];
			ySpan=(path[1][target]-path[1][start])/path[2][start];
			this.x+=xSpan;
			this.y+=ySpan;
			step++;
		}
	}
	public void shoot(GameView gameView) {
		if(type==1){
			Bullet b=new Bullet(x, y+bitmap.getHeight(), 2, Constant.DIR_DOWN, gameView, 0);
			gameView.mnst.add(b);
		}
		else if(type==2){
			Bullet b1=new Bullet(x, y+bitmap.getHeight()+5, 3, Constant.DIR_DOWN, gameView, 0);
			Bullet b2=new Bullet(x, y+bitmap.getHeight()-5, 3, Constant.DIR_DOWN, gameView, 0);
			gameView.mnst.add(b1);
			gameView.mnst.add(b2);
		}
		if(extra==1){
			Bullet b1=new Bullet(x+20, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 1);
			Bullet b2=new Bullet(x-20, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 1);
			gameView.mnst.add(b1);
			gameView.mnst.add(b2);
		}
		else if(extra==2){
			Bullet b1=new Bullet(x+20, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 2);
			Bullet b2=new Bullet(x-20, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 2);
			gameView.mnst.add(b1);
			gameView.mnst.add(b2);
		}
		else if(extra==3){
			Bullet b1=new Bullet(x+20, y+bitmap.getHeight(), 01, Constant.DIR_DOWN, gameView, 3);
			Bullet b2=new Bullet(x+40, y+bitmap.getHeight(), 02, Constant.DIR_DOWN, gameView, 3);
			Bullet b3=new Bullet(x-20, y+bitmap.getHeight(), 01, Constant.DIR_DOWN, gameView, 3);
			Bullet b4=new Bullet(x-40, y+bitmap.getHeight(), 02, Constant.DIR_DOWN, gameView, 3);
			gameView.mnst.add(b1);
			gameView.mnst.add(b2);
			gameView.mnst.add(b3);
			gameView.mnst.add(b4);
		}
		else if(extra==4){
			Bullet b1=new Bullet(x, y+bitmap.getHeight()+20, 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b2=new Bullet(x, y+bitmap.getHeight()-20, 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b3=new Bullet(x+20, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b4=new Bullet(x-20, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 3);
			gameView.mnst.add(b1);
			gameView.mnst.add(b2);
			gameView.mnst.add(b3);
			gameView.mnst.add(b4);
		}
		else if(extra==5){
			Bullet b1=new Bullet(x, y+bitmap.getHeight()-50, 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b2=new Bullet(x-25, y+bitmap.getHeight()+50, 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b3=new Bullet(x+50, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b4=new Bullet(x-50, y+bitmap.getHeight(), 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b5=new Bullet(x+25, y+bitmap.getHeight()+50, 3, Constant.DIR_DOWN, gameView, 3);
			Bullet b6=new Bullet(x+10, y+bitmap.getHeight(), 2, Constant.DIR_DOWN, gameView, 3);
			Bullet b7=new Bullet(x-10, y+bitmap.getHeight(), 2, Constant.DIR_DOWN, gameView, 3);
			Bullet b8=new Bullet(x+15, y+bitmap.getHeight()+15, 2, Constant.DIR_DOWN, gameView, 3);
			Bullet b9=new Bullet(x-15, y+bitmap.getHeight()+15, 2, Constant.DIR_DOWN, gameView, 3);
			Bullet b10=new Bullet(x, y+bitmap.getHeight()+10, 2, Constant.DIR_DOWN, gameView, 3);
			gameView.mnst.add(b1);
			gameView.mnst.add(b2);
			gameView.mnst.add(b3);
			gameView.mnst.add(b4);
			gameView.mnst.add(b5);
			gameView.mnst.add(b6);
			gameView.mnst.add(b7);
			gameView.mnst.add(b8);
			gameView.mnst.add(b9);
			gameView.mnst.add(b10);
		}
	}
	public boolean contain(Bullet b,GameView gameview){	//�Q�ڤ�l�u�I��
		if(isContain(b.x, b.y, b.bitmap.getWidth(), b.bitmap.getHeight())){
			this.hp--;
			if(this.hp<0){		//�ͩR�p��0���ɭ�
				this.status=false;		
				if(this.type==5){ 						//BOSS���Ѯ�
					gameview.status=3;					//�ӧQ
					if(gameview.mMediaPlayer.isPlaying())
						gameview.mMediaPlayer.stop();	//�����
					gameview.mainActivity.handler.sendEmptyMessage(3);	//�o�e�T�����D��
				}
			}
			return true;
		}
		return false;
	}

	private boolean isContain(int ox, int oy, int owidth, int oheight) {	//�P�_�U�ظI������
		int bx=0;		//�kX
		int by=0;		//�UY
		int sx=0;		//��X
		int sy=0;		//�WY
		int width = 0;
		int height = 0;
		boolean xFlag = true;	//�۾�x�O�_�b�e
		boolean yFlag = true;	//�۾�y�O�_�b�e

		if(this.x >= ox){
			bx = this.x;
			sx = ox;
			xFlag = false;
		}
		else{
			bx = ox;
			sx = this.x;
			xFlag = true;
		}

		if(this.y >= oy){
			by = this.y;
			sy = oy;
			yFlag = false;
		}
		else{
			by = oy;
			sy = this.y;
			yFlag = true;
		}

		if(xFlag == true)
			width = this.bitmap.getWidth();
		else 
			width = owidth;

		if(yFlag == true)
			height = this.bitmap.getHeight();
		else
			height = oheight;
		if(bx>=sx && bx<=sx+width-1 && by>=sy && by<=sy+height-1){	//�P�_�O�_���|
			double Dwidth=width-bx+sx;   				//���|�e��  
			double Dheight=height-by+sy; 				//���|����
			if(Dwidth*Dheight/(owidth*oheight)>=0.30){	//���|�W�L30%�P�w�I��
				return true;
			}
		}
		return false;
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

package com.example.m_girl;
//怪物配置
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//怪物類別
public class Monster {
	Bitmap bitmap;
	int x;			//怪物起始值
	int y;
	boolean status;	//怪物的生存狀態
	int hp;			//怪物的生命值
	int start;		//怪物出發的點
	int target;		//怪物行進目標點
	int step;		//行進步數
	int touchp;		//判定觸發時間點
	int type;		//怪物種類
	int extra;		//多樣子彈
	int xSpan=0;	//移動距離初始化
	int ySpan=0;
	int [][]path;	//第一個[]0為X 1為Y
	//path[0][?]=X值用
	//path[1][?]=Y值用
	//path[2][?]=步數

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
		/*如果path[2]步數用的陣列第一個數相同時 step重計
		 ex:取pathA第二輪start=1 轉換之前  所以start=1之前
		 step(15)=path[2][0](15)
		 step歸0走第二段
		 start=(0+1)%path[1].length(4)→1%4 餘數=1 start=1
		 target=(1+1)%path[1].length(4)→2%4 餘數=2 target=2(下一步目標
		 x=path[0][1](X值用的path[0]第二個數
		 y=path[1][1](Y值用的path[0]第二個數
		 */

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
		  yS移動值=(110-50)/15			*/

		else{						//沒有結束就一直往下走
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
	public boolean contain(Bullet b,GameView gameview){	//被我方子彈碰撞
		if(isContain(b.x, b.y, b.bitmap.getWidth(), b.bitmap.getHeight())){
			this.hp--;
			if(this.hp<0){		//生命小於0的時候
				this.status=false;		
				if(this.type==5){ 						//BOSS擊敗時
					gameview.status=3;					//勝利
					if(gameview.mMediaPlayer.isPlaying())
						gameview.mMediaPlayer.stop();	//停止音樂
					gameview.mainActivity.handler.sendEmptyMessage(3);	//發送訊息給主體
				}
			}
			return true;
		}
		return false;
	}

	private boolean isContain(int ox, int oy, int owidth, int oheight) {	//判斷各種碰撞條件
		int bx=0;		//右X
		int by=0;		//下Y
		int sx=0;		//左X
		int sy=0;		//上Y
		int width = 0;
		int height = 0;
		boolean xFlag = true;	//自機x是否在前
		boolean yFlag = true;	//自機y是否在前

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
		if(bx>=sx && bx<=sx+width-1 && by>=sy && by<=sy+height-1){	//判斷是否重疊
			double Dwidth=width-bx+sx;   				//重疊寬度  
			double Dheight=height-by+sy; 				//重疊高度
			if(Dwidth*Dheight/(owidth*oheight)>=0.30){	//重疊超過30%判定碰撞
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

package com.example.m_girl;
//自機配置

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Plane{
	int x;						//自機X座標
	int y;						//自機Y座標
	int planelife;				//生命
	int type;					//自機種類
	int dir;					//方向
	int i;						//取陣列圖
	Bitmap plane_m;				//自機靜止
	Bitmap plane_l;				//自機向右的圖
	Bitmap plane_r;				//自機向左的圖
	int shoottype=1;			//射擊種類
	boolean status=true;		//自機是否生存狀態
	GameView gameview;			//使用主畫面
	public Plane(int x,int y,int type,int dir,boolean status,int planelife,GameView gameview) {
		this.x=x;
		this.y=y;
		this.type=type;
		this.i=1;
		this.dir=dir;
		this.status=status;
		this.planelife=planelife;
		this.gameview=gameview;
		initBitmap();	
	}
	public void initBitmap() {
		if(type == 1){			//第一種自機
			plane_m = BitmapFactory.decodeResource(gameview.getResources(), R.drawable.plane_m);
			plane_l = BitmapFactory.decodeResource(gameview.getResources(), R.drawable.plane_l);
			plane_r = BitmapFactory.decodeResource(gameview.getResources(), R.drawable.plane_r);
		}
		Constant.MYSHIP_WIDTH=plane_m.getWidth();
		Constant.MYSHIP_HEIGHT=plane_m.getHeight();
	}
	public void draw(Canvas canvas,Paint paint) {
		if(dir==Constant.DIR_STOP)
			canvas.drawBitmap(plane_l, x, y, paint);
		else if(dir==Constant.DIR_RIGHT)
			canvas.drawBitmap(plane_r, x, y, paint);
		else if(dir==Constant.DIR_LEFT)
			canvas.drawBitmap(plane_l, x, y, paint);

	}
	public void setDir(int dir){  
		this.dir = dir;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		if(this.x<=0)
			this.x=0;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		if(this.y <= 0)
			this.y = 0;
	}
	public void shoot() {	//射擊方法
		if(shoottype==1){
			Bullet b=new Bullet(gameview.plane.x+plane_m.getWidth()/2-5, this.y, 1, Constant.DIR_STOP, gameview, 0);
			gameview.myst.add(b);
		}
	}
	public boolean contain(Bullet b) {	//被敵方子彈碰撞
		if(isContain(b.x, b.y, b.bitmap.getWidth(), b.bitmap.getHeight())){
			this.planelife--;
			this.x=Constant.SCREEN_WIDTH/2;
			this.y=Constant.SCREEN_HEIGHT-3*Constant.SCREEN_HEIGHT/16;
			if(this.planelife<0){		//生命小於0的時候
				gameview.status = 0;
				if(gameview.mMediaPlayer.isPlaying()){
					gameview.mMediaPlayer.stop();
				}
				gameview.mainActivity.handler.sendEmptyMessage(2);	//發送訊息給主體
			}
			return true;
		}
		return false;
	}
	public boolean contain(Monster mr){	//被敵方主體碰撞
		if(isContain(mr.x, mr.y, mr.bitmap.getWidth(), mr.bitmap.getHeight())){
			this.planelife--;
			this.x=Constant.SCREEN_WIDTH/2;
			this.y=Constant.SCREEN_HEIGHT-3*Constant.SCREEN_HEIGHT/16;
			if(this.planelife<0){		//生命小於0的時候
				gameview.status = 0;
				if(gameview.mMediaPlayer.isPlaying()){
					gameview.mMediaPlayer.stop();
				}
				gameview.mainActivity.handler.sendEmptyMessage(2);	//發送訊息給主體
			}
			return true;
		}
		return false;
	}
	public boolean contain(Lifeup l) {	//撞到增加生命
		if(isLifeContain(l.x, l.y, l.bitmap.getWidth(), l.bitmap.getHeight())){
			if(this.planelife<5)
				this.planelife++;//生命加一
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
			width = this.plane_m.getWidth();
		else 
			width = owidth;

		if(yFlag == true)
			height = this.plane_m.getHeight();
		else
			height = oheight;
		if(bx>=sx && bx<=sx+width-1 && by>=sy && by<=sy+height-1){	//判斷是否重疊
			double Dwidth=width-bx+sx;   				//重疊寬度  
			double Dheight=height-by+sy; 				//重疊高度
			if(Dwidth*Dheight/(owidth*oheight)>=0.80){	//重疊超過80%判定碰撞
				return true;
			}
		}
		return false;
	}
	private boolean isLifeContain(int ox, int oy, int owidth, int oheight) {	//判斷各種碰撞條件
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
			width = this.plane_m.getWidth();
		else 
			width = owidth;

		if(yFlag == true)
			height = this.plane_m.getHeight();
		else
			height = oheight;
		if(bx>=sx && bx<=sx+width-1 && by>=sy && by<=sy+height-1){	//判斷是否重疊
			double Dwidth=width-bx+sx;   				//重疊寬度  
			double Dheight=height-by+sy; 				//重疊高度
			if(Dwidth*Dheight/(owidth*oheight)>=0.20){	//重疊超過20%判定碰撞
				return true;
			}
		}
		return false;
	}
}

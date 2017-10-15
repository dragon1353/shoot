package com.example.m_girl;
//地圖物件執行緒配置
import java.util.ArrayList;

public class MoveThread extends Thread{
	private int span=50; //延遲50毫秒
	private boolean moveflag=false;//執行緒開關
	GameView gameView;
	GameThread gameThread;
	ArrayList<Bullet> delb=new ArrayList<Bullet>();
	ArrayList<Lifeup> dell = new ArrayList<Lifeup>();
	ArrayList<Monster> delmr=new ArrayList<Monster>();
	int countBM=0;		//我方子彈移動計數
	int countBMN=2;		//每2次重新循環
	int countEM=0;		//敵方子彈射出計數
	public int countEMN=75;	//每75次重新循環
	int countMM=0;		//敵機移動計數
	int countMMN=3;		//每3次重新循環
	int countEMM=0;		//敵方子彈移動計數
	int countEMMN=2;	//每2次重新循環

	public MoveThread(GameView gameView) {	//建構子
		this.gameView=gameView;
	}
	public void setFlag(boolean moveflag) {
		this.moveflag=moveflag;
	}
	public boolean isFlag() {
		return moveflag;
	}
	public void run() {
		while(isFlag()){
			if(countBM==0){		//我方子彈移動
				synchronized (gameView.myst) {
					for(Bullet b :gameView.myst){
						b.move();
						if(b.x<0 || b.x>Constant.SCREEN_WIDTH || b.y<0)
							delb.add(b);
						else{		//在螢幕內
							for(Monster mr : gameView.ms){
								if(mr.status){
									if(mr.contain(b,gameView)){//判定打中
										if(mr.hp<=0){
											Explode e = new Explode(mr.x, mr.y, gameView);
											gameView.exp.add(e);
											delb.add(b);
										}
										else{
											HitExplode he = new HitExplode(mr.x,mr.y, gameView);
											gameView.hexp.add(he);
											delb.add(b);
										}
									}
								}
							}
						}
					}
				gameView.myst.removeAll(delb);
				delb.clear();
				}
			}
			if(countEM==0){		//敵方子彈
				for(Monster mr:gameView.ms){
					if(mr.status){
						if(mr.extra!=3 ||mr.extra!=4){
							countEMN=75;
							mr.shoot(gameView);
						}
						else{
							countEMN=10;
							mr.shoot(gameView);
						}
					}
				}
			}
			if(countEMM==0){	//敵方子彈移動
				for(Bullet b:gameView.mnst){
					b.monstermove();
					if(b.x<0 || b.x>Constant.SCREEN_WIDTH || b.y<0 || b.y>7*Constant.SCREEN_HEIGHT/8)
						delb.add(b);
					else{		//在螢幕內
						if(gameView.plane.contain(b)){//判定打中
							Explode e = new Explode(b.x,b.y, gameView);
							gameView.exp.add(e);
							delb.add(b);
						}
					}
				} 
				gameView.mnst.removeAll(delb);
				delb.clear();
			}
			if(countMM==0){		//怪物
				for(Monster mr:gameView.ms){
					if(mr.status){
						mr.move();
						if(mr.getX()<0|| mr.getX()>Constant.SCREEN_WIDTH ||mr.getY()>7*Constant.SCREEN_HEIGHT/8)
							delmr.add(mr);
						else{
							if(gameView.plane.contain(mr)){
								Explode e = new Explode(mr.x, mr.y, gameView);
								gameView.exp.add(e);
								mr.hp--;
								if(mr.hp <=0)
									delmr.add(mr);
							}
						}
					}
				}
				gameView.ms.removeAll(delmr);
				delmr.clear();

				for(Lifeup l:gameView.lifes){
					if(l.status){
						l.move();
						if(l.x<0|| l.x>Constant.SCREEN_WIDTH ||l.y>7*Constant.SCREEN_HEIGHT/8)
							dell.add(l);
						else{
							if(gameView.plane.contain(l)){
								dell.add(l);
							}
						}
					}
				}
						gameView.lifes.removeAll(dell);
						dell.clear();
			}

			countBM=(countBM+1)%countBMN;
			countEM=(countEM+1)%countEMN;
			countMM=(countMM+1)%countMMN;
			countEMM=(countEMM+1)%countEMMN;
			try {
				Thread.sleep(span);	//延遲秒數
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

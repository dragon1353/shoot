package com.example.m_girl;
//�a�Ϫ��������t�m
import java.util.ArrayList;

public class MoveThread extends Thread{
	private int span=50; //����50�@��
	private boolean moveflag=false;//������}��
	GameView gameView;
	GameThread gameThread;
	ArrayList<Bullet> delb=new ArrayList<Bullet>();
	ArrayList<Lifeup> dell = new ArrayList<Lifeup>();
	ArrayList<Monster> delmr=new ArrayList<Monster>();
	int countBM=0;		//�ڤ�l�u���ʭp��
	int countBMN=2;		//�C2�����s�`��
	int countEM=0;		//�Ĥ�l�u�g�X�p��
	public int countEMN=75;	//�C75�����s�`��
	int countMM=0;		//�ľ����ʭp��
	int countMMN=3;		//�C3�����s�`��
	int countEMM=0;		//�Ĥ�l�u���ʭp��
	int countEMMN=2;	//�C2�����s�`��

	public MoveThread(GameView gameView) {	//�غc�l
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
			if(countBM==0){		//�ڤ�l�u����
				synchronized (gameView.myst) {
					for(Bullet b :gameView.myst){
						b.move();
						if(b.x<0 || b.x>Constant.SCREEN_WIDTH || b.y<0)
							delb.add(b);
						else{		//�b�ù���
							for(Monster mr : gameView.ms){
								if(mr.status){
									if(mr.contain(b,gameView)){//�P�w����
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
			if(countEM==0){		//�Ĥ�l�u
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
			if(countEMM==0){	//�Ĥ�l�u����
				for(Bullet b:gameView.mnst){
					b.monstermove();
					if(b.x<0 || b.x>Constant.SCREEN_WIDTH || b.y<0 || b.y>7*Constant.SCREEN_HEIGHT/8)
						delb.add(b);
					else{		//�b�ù���
						if(gameView.plane.contain(b)){//�P�w����
							Explode e = new Explode(b.x,b.y, gameView);
							gameView.exp.add(e);
							delb.add(b);
						}
					}
				} 
				gameView.mnst.removeAll(delb);
				delb.clear();
			}
			if(countMM==0){		//�Ǫ�
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
				Thread.sleep(span);	//������
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

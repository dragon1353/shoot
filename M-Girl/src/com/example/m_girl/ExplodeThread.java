package com.example.m_girl;

import java.util.ArrayList;

public class ExplodeThread extends Thread{
	boolean flag=false;		//執行緒開關
	int span=100;			//延遲時間
	GameView	gameView;
	ArrayList<Explode>	delexp=new ArrayList<Explode>();	//存放刪除用爆炸圖
	ArrayList<HitExplode>	delhexp=new ArrayList<HitExplode>();
	public ExplodeThread(GameView gameView) {
		this.gameView=gameView;
	}
	public void setflag(boolean flag) {					
		this.flag=flag;
	}
	public boolean isFlag() {
		return flag;
	}
	public void run() {
		while (isFlag()) {
			try {
				for(Explode e:gameView.exp){
					if(e.nextframe()){}
					else
						delexp.add(e);
				}
				gameView.exp.removeAll(delexp);
				delexp.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				for(HitExplode he:gameView.hexp){
					if(he.nextframe()){}
					else
						delhexp.add(he);
				}
				gameView.hexp.removeAll(delhexp);
				delhexp.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(span);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

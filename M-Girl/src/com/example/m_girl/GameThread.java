package com.example.m_girl;

import android.media.MediaPlayer;

//怪物、道具掉落配置
public class GameThread extends Thread{
	int sleepspan=100;					//延遲1000毫秒
	int span=Constant.SCREEN_HEIGHT/60;	//圖片一次移動1/60
	boolean flag=false;					//執行緒啟動
	GameView gameView;
	int touchtime=1200;					//開始時間
	int bosstime=600;					//boss時間倒數
	public GameThread(GameView gameView) {
		this.gameView=gameView;	
	}
	public void setflag(boolean flag) {
		this.flag=flag;
	}
	public boolean gameflag() {
		return flag;
	}
	public void run() {
		while(gameflag()){
			if(gameView.status==1){			//遊戲進行中
				 gameView.backGroundY -= span;
	                if(gameView.backGroundY >Constant.pictureHeight){
	                    gameView.i = (gameView.i+1)%(Constant.SCREEN_HEIGHT/30);
	                    gameView.backGroundY+=Constant.pictureHeight;
	                }
				touchtime--;
				try {
					for(Monster mr:gameView.ms){
						if(mr.touchp==touchtime)
							mr.status=true;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				try {
					for(Lifeup l:gameView.lifes){	//時間出現生命
						if(l.tp==touchtime)
							l.status=true;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				if(touchtime==0){			//BOSS戰開始
					gameView.mMediaPlayer=MediaPlayer.create(gameView.mainActivity, R.raw.boss_bgm);//轉換背景音樂播放器
					bosstime--;
				}
				if(touchtime==0){			//BOSS戰時間到
					this.flag=false;
				}
				try {
					Thread.sleep(sleepspan);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

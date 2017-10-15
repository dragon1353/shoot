package com.example.m_girl;
//執行緒配置
public class KeyThread extends Thread {
	GameView gameView;
	int myt=1;					//子彈連發時間判定
	int mt;
	int delaymyt=3;			//延遲
	private boolean keyFlag=false;

	public KeyThread(GameView gameView){
		this.gameView=gameView;
	}
	@Override
	public void run(){
		while(isKeyFlag()){
			try{
				Thread.sleep(20);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			gameView.repaint();
			if(mt==0 && gameView.plane.status)
				gameView.plane.shoot();
		}
		if(myt>3)
			myt=0;
		else{
		myt++;
		mt=myt%delaymyt;
		}
	}

	public void setKeyFlag(boolean keyFlag) {
		this.keyFlag = keyFlag;
	}
	public boolean isKeyFlag() {
		return keyFlag;
	}

}

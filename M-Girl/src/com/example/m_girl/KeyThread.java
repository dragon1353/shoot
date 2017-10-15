package com.example.m_girl;
//������t�m
public class KeyThread extends Thread {
	GameView gameView;
	int myt=1;					//�l�u�s�o�ɶ��P�w
	int mt;
	int delaymyt=3;			//����
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

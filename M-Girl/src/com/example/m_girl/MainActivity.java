package com.example.m_girl;
//�D�n�{���Ұʰt�m
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.annotation.SuppressLint;

enum WhichView {Welcome_View,Game_View,Main_View,Fail_View,Win_View}
@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	GameView gameView;					//�C������
	Welcome welcome;					//�w�虜��
	FailView failView;					//���`����
	MainView mainView;					//�D��椶��
	WhichView whichView;				//�C�|����
	WinView winView;					//�ӧQ����
	boolean backgroundsound=true;	//BGM�w�]�}��
	Handler handler=new Handler(){	//�B�z�C�Ӥ������ǭ�
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				goToMainView();//�i�J��椶��
				break;
			case 1:
				goToGameView();//�i�J�C������
				break;
			case 2:
				goToFailView();//�i�J���������ɭ�
				break;
			case 3:
				goToWinView();//�i�J�����ӧQ�ɭ�
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//�h�����D
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//�h�����Y
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//�j���
		//���o�ѪR��
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//���`�����O�����ù����M�e������
		Constant.SCREEN_WIDTH=dm.widthPixels;
		Constant.SCREEN_HEIGHT=dm.heightPixels;
		Constant.MYSHIP_SPEED=Constant.SCREEN_WIDTH/70;
		GotoWelcome();//�i�J"�w��ɭ�"
	}
	public boolean onKeyDown(int keyCode,KeyEvent e) {
		if(keyCode==4){
			switch (whichView) {
			case Game_View:		//�C���������^�D����
			case Fail_View:		//���Ѹ��^�D����
			case Win_View:		//�ӧQ���^�D����
				goToMainView();
				break;
			case Welcome_View:	//�w�虜�� ���}�C��
			case Main_View:		//�D����  ���}�C��
				System.exit(0);
				break;
			}
			return true;
		}
		return false;
	}
	//handler�ǰe�T������k
	public void sendMessage(int what)
	{
		Message msg = handler.obtainMessage(what);
		handler.sendMessage(msg);
	} 
	public void GotoWelcome() {
		if(welcome==null)
			welcome=new Welcome(this);
		setContentView(welcome);
		whichView=WhichView.Welcome_View;
	}
	//�i�J�C������
	public void goToGameView() {
		if(gameView==null)
			gameView=new GameView(this);
		if(mainView!=null)
			mainView=null;
		setContentView(gameView);
		gameView.requestFocus();//���o�J�I
		gameView.setFocusableInTouchMode(true);//�]���iĲ��
		whichView=WhichView.Game_View;
	}
	//�i�J����D�ɭ�
	public void goToMainView(){
		if(mainView==null)
			mainView=new MainView(this);
		setContentView(mainView);
		mainView.requestFocus();//���o�J�I
		mainView.setFocusableInTouchMode(true);//�]���iĲ��
		whichView=WhichView.Main_View;
	}
	//�i�J���������ɭ�
	public void goToFailView() {
		if(failView==null)
			failView=new FailView(this);
		if(gameView!=null){
			gameView.keythread.setKeyFlag(false);//������ذ����
			gameView.movethread.setFlag(false);
			gameView.gameThread.setflag(false);
			gameView = null;
		}		
		setContentView(failView);
		failView.requestFocus();//���o�J�I
		failView.setFocusableInTouchMode(true);//�]���iĲ��
		whichView=WhichView.Fail_View;
	}
	public void goToWinView() {
		if(winView==null)
			winView=new WinView(this);
		if(gameView!=null){
			gameView.keythread.setKeyFlag(false);//������ذ����
			gameView.movethread.setFlag(false);
			gameView.gameThread.setflag(false);
			gameView = null;
		}		
		setContentView(winView);
		winView.requestFocus();//���o�J�I
		winView.setFocusableInTouchMode(true);//�]���iĲ��
		whichView=WhichView.Win_View;
	}
	@Override 
	public void onResume()
	{
		super.onResume();
	}
	@Override 
	public void onPause()
	{
		super.onPause();
	}
}

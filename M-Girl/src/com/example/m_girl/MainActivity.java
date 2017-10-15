package com.example.m_girl;
//主要程式啟動配置
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
	GameView gameView;					//遊戲介面
	Welcome welcome;					//歡迎介面
	FailView failView;					//死亡介面
	MainView mainView;					//主選單介面
	WhichView whichView;				//列舉介面
	WinView winView;					//勝利介面
	boolean backgroundsound=true;	//BGM預設開啟
	Handler handler=new Handler(){	//處理每個介面的傳值
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				goToMainView();//進入選單介面
				break;
			case 1:
				goToGameView();//進入遊戲介面
				break;
			case 2:
				goToFailView();//進入游戲結束界面
				break;
			case 3:
				goToWinView();//進入游戲勝利界面
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉標題
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉標頭
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//強制直屏
		//取得解析度
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//給常數類別中的螢幕高和寬給予值
		Constant.SCREEN_WIDTH=dm.widthPixels;
		Constant.SCREEN_HEIGHT=dm.heightPixels;
		Constant.MYSHIP_SPEED=Constant.SCREEN_WIDTH/70;
		GotoWelcome();//進入"歡迎界面"
	}
	public boolean onKeyDown(int keyCode,KeyEvent e) {
		if(keyCode==4){
			switch (whichView) {
			case Game_View:		//遊戲介面跳回主介面
			case Fail_View:		//失敗跳回主介面
			case Win_View:		//勝利跳回主介面
				goToMainView();
				break;
			case Welcome_View:	//歡迎介面 離開遊戲
			case Main_View:		//主介面  離開遊戲
				System.exit(0);
				break;
			}
			return true;
		}
		return false;
	}
	//handler傳送訊息的方法
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
	//進入遊戲介面
	public void goToGameView() {
		if(gameView==null)
			gameView=new GameView(this);
		if(mainView!=null)
			mainView=null;
		setContentView(gameView);
		gameView.requestFocus();//取得焦點
		gameView.setFocusableInTouchMode(true);//設為可觸控
		whichView=WhichView.Game_View;
	}
	//進入選取主界面
	public void goToMainView(){
		if(mainView==null)
			mainView=new MainView(this);
		setContentView(mainView);
		mainView.requestFocus();//取得焦點
		mainView.setFocusableInTouchMode(true);//設為可觸控
		whichView=WhichView.Main_View;
	}
	//進入游戲結束界面
	public void goToFailView() {
		if(failView==null)
			failView=new FailView(this);
		if(gameView!=null){
			gameView.keythread.setKeyFlag(false);//關閉刷框執行緒
			gameView.movethread.setFlag(false);
			gameView.gameThread.setflag(false);
			gameView = null;
		}		
		setContentView(failView);
		failView.requestFocus();//取得焦點
		failView.setFocusableInTouchMode(true);//設為可觸控
		whichView=WhichView.Fail_View;
	}
	public void goToWinView() {
		if(winView==null)
			winView=new WinView(this);
		if(gameView!=null){
			gameView.keythread.setKeyFlag(false);//關閉刷框執行緒
			gameView.movethread.setFlag(false);
			gameView.gameThread.setflag(false);
			gameView = null;
		}		
		setContentView(winView);
		winView.requestFocus();//取得焦點
		winView.setFocusableInTouchMode(true);//設為可觸控
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

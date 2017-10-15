package com.example.m_girl;
//常數配置
public class Constant {
	//介面用向量
    
	public static int SCREEN_WIDTH;					//螢幕的寬 540
	public static int SCREEN_HEIGHT;				//螢幕的高	960
	
	public static int MOSNTER_WIDTH;				//怪物的寬
	public static int MOSNTER_HEIGHT;				//怪物的高
	
	public static int MYSHIP_WIDTH;					//自機的寬
	public static int MYSHIP_HEIGHT;				//自機的高
	
	public static int BOSS_WIDTH;					//魔王的寬
	public static int BOSS_HEIGHT;					//魔王的高
	
	public static int backcount=60;					//地圖段數
	
	public static int TRANSPARENT_WINDOW_HEIGHT;	//半透明視窗的高
	public static int TRANSPARENT_WINDOW_WIDTH;		//半透明視窗的寬
	
	public static int BAR_Y;						//得分條的Y座標
	public static int MOSNTER_SPEED;				//怪物移動速度
	public static int BOSS_SPEED;					//魔王移動速度
	public static int MYSHIP_SPEED;					//自機移動速度
	public static int MAP_SPEED=SCREEN_WIDTH/30;	//地圖速度
	public static int pictureWidth=540;
	public static int pictureHeight=30;
	public static int top=10;
	
	//遊戲數值用向量
		//0靜止,1上,2右上,3右,4右下,5下,6左下,7左,8左上
		public static final int DIR_STOP = 0;
	    public static final int DIR_UP = 1;
	    public static final int DIR_RIGHT_UP = 2;
	    public static final int DIR_RIGHT = 3;
	    public static final int DIR_RIGHT_DOWN = 4;
	    public static final int DIR_DOWN = 5;	//怪物用居多
	    public static final int DIR_LEFT_DOWN = 6;
	    public static final int DIR_LEFT = 7;
	    public static final int DIR_LEFT_UP = 8;
	    
	public static void changeRadio()
	{
		TRANSPARENT_WINDOW_HEIGHT=SCREEN_WIDTH/4;//半透明視窗的高
		TRANSPARENT_WINDOW_WIDTH=SCREEN_HEIGHT/4;//半透明視窗的寬
	}
}

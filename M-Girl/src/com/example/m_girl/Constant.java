package com.example.m_girl;
//�`�ưt�m
public class Constant {
	//�����ΦV�q
    
	public static int SCREEN_WIDTH;					//�ù����e 540
	public static int SCREEN_HEIGHT;				//�ù�����	960
	
	public static int MOSNTER_WIDTH;				//�Ǫ����e
	public static int MOSNTER_HEIGHT;				//�Ǫ�����
	
	public static int MYSHIP_WIDTH;					//�۾����e
	public static int MYSHIP_HEIGHT;				//�۾�����
	
	public static int BOSS_WIDTH;					//�]�����e
	public static int BOSS_HEIGHT;					//�]������
	
	public static int backcount=60;					//�a�Ϭq��
	
	public static int TRANSPARENT_WINDOW_HEIGHT;	//�b�z����������
	public static int TRANSPARENT_WINDOW_WIDTH;		//�b�z���������e
	
	public static int BAR_Y;						//�o������Y�y��
	public static int MOSNTER_SPEED;				//�Ǫ����ʳt��
	public static int BOSS_SPEED;					//�]�����ʳt��
	public static int MYSHIP_SPEED;					//�۾����ʳt��
	public static int MAP_SPEED=SCREEN_WIDTH/30;	//�a�ϳt��
	public static int pictureWidth=540;
	public static int pictureHeight=30;
	public static int top=10;
	
	//�C���ƭȥΦV�q
		//0�R��,1�W,2�k�W,3�k,4�k�U,5�U,6���U,7��,8���W
		public static final int DIR_STOP = 0;
	    public static final int DIR_UP = 1;
	    public static final int DIR_RIGHT_UP = 2;
	    public static final int DIR_RIGHT = 3;
	    public static final int DIR_RIGHT_DOWN = 4;
	    public static final int DIR_DOWN = 5;	//�Ǫ��Ω~�h
	    public static final int DIR_LEFT_DOWN = 6;
	    public static final int DIR_LEFT = 7;
	    public static final int DIR_LEFT_UP = 8;
	    
	public static void changeRadio()
	{
		TRANSPARENT_WINDOW_HEIGHT=SCREEN_WIDTH/4;//�b�z����������
		TRANSPARENT_WINDOW_WIDTH=SCREEN_HEIGHT/4;//�b�z���������e
	}
}

package com.example.m_girl;

import java.util.ArrayList;

//地圖、怪物配置
public class Map {
	//int xSpan=(path[0][target]-path[0][start])/path[2][start];
	//例如    xSpan=(280-480)/15
	//int ySpan=(path[1][target]-path[1][start])/path[2][start];
	//例如    ySpan=(110-50)/15
	public static int [][] life= {
		{Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2},
		{0,Constant.SCREEN_HEIGHT/3,2*Constant.SCREEN_HEIGHT/3,Constant.SCREEN_HEIGHT},
		{10,10,10,10}
	};
	public static int [][] pathA= {
		{Constant.SCREEN_WIDTH/2,6*Constant.SCREEN_WIDTH/7,0,Constant.SCREEN_WIDTH/2},
		{0,2*Constant.SCREEN_HEIGHT/7,3*Constant.SCREEN_HEIGHT/4,10*Constant.SCREEN_HEIGHT/9},
		{15,15,15,15}
	};
	public static int [][] pathB= {
		{Constant.SCREEN_WIDTH/2,0,6*Constant.SCREEN_WIDTH/7,Constant.SCREEN_WIDTH/2},
		{0,2*Constant.SCREEN_HEIGHT/7,3*Constant.SCREEN_HEIGHT/4,10*Constant.SCREEN_HEIGHT/9},
		{15,15,15,15}
	};
	public static int [][] pathC= {
		{Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2},
		{0,2*Constant.SCREEN_HEIGHT/7,3*Constant.SCREEN_HEIGHT/7,10*Constant.SCREEN_HEIGHT/9},
		{30,30,30,30}
	};
	public static int [][] pathD= {
		{20,20,20,20},
		{0,2*Constant.SCREEN_HEIGHT/7,3*Constant.SCREEN_HEIGHT/7,10*Constant.SCREEN_HEIGHT/9},
		{60,60,60,60}
	};
	public static int [][] pathE= {
		{Constant.SCREEN_WIDTH-30,Constant.SCREEN_WIDTH-30,Constant.SCREEN_WIDTH-30,Constant.SCREEN_WIDTH-30},
		{0,2*Constant.SCREEN_HEIGHT/7,3*Constant.SCREEN_HEIGHT/7,10*Constant.SCREEN_HEIGHT/9},
		{60,60,60,60}
	};
	public static int [][] pathF= {
		{ Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2+50,Constant.SCREEN_WIDTH/2-50,Constant.SCREEN_WIDTH/2+60,Constant.SCREEN_WIDTH/2-50},
		{0,Constant.SCREEN_HEIGHT/3,Constant.SCREEN_HEIGHT/3+20,Constant.SCREEN_HEIGHT/3-30,10*Constant.SCREEN_HEIGHT/9},
		{150,150,150,150,150}
	};
	public static int [][] pathG= {
		{Constant.SCREEN_WIDTH/3,Constant.SCREEN_WIDTH/3,2*Constant.SCREEN_WIDTH/9,4*Constant.SCREEN_WIDTH/9,2*Constant.SCREEN_WIDTH/9,4*Constant.SCREEN_WIDTH/9,Constant.SCREEN_WIDTH/3,Constant.SCREEN_WIDTH/3},
		{0,Constant.SCREEN_HEIGHT/10,Constant.SCREEN_HEIGHT/5,Constant.SCREEN_HEIGHT/10,Constant.SCREEN_HEIGHT/10,Constant.SCREEN_HEIGHT/5,Constant.SCREEN_HEIGHT/10,10*Constant.SCREEN_HEIGHT/9},
		{30,30,30,30,30,30,30,30}
	};
	public static int [][] pathH= {
		{2*Constant.SCREEN_WIDTH/3,2*Constant.SCREEN_WIDTH/3,4*Constant.SCREEN_WIDTH/9,6*Constant.SCREEN_WIDTH/9,4*Constant.SCREEN_WIDTH/9,6*Constant.SCREEN_WIDTH/9,2*Constant.SCREEN_WIDTH/3,2*Constant.SCREEN_WIDTH/3},
		{0,Constant.SCREEN_HEIGHT/10,Constant.SCREEN_HEIGHT/5,Constant.SCREEN_HEIGHT/10,Constant.SCREEN_HEIGHT/10,Constant.SCREEN_HEIGHT/5,Constant.SCREEN_HEIGHT/10,10*Constant.SCREEN_HEIGHT/9},
		{30,30,30,30,30,30,30,30}
	};
	public static int [][] pathI= {
		{ Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2+50,Constant.SCREEN_WIDTH/2-50,Constant.SCREEN_WIDTH/2+60,Constant.SCREEN_WIDTH/2-50},
		{0,Constant.SCREEN_HEIGHT/3,Constant.SCREEN_HEIGHT/3+20,Constant.SCREEN_HEIGHT/3-30,10*Constant.SCREEN_HEIGHT/9},
		{150,150,150,150,150}
	};
	public static int [][] pathJ= {
		{ Constant.SCREEN_WIDTH/2,Constant.SCREEN_WIDTH/2+100,Constant.SCREEN_WIDTH/2-100,Constant.SCREEN_WIDTH/2+100,Constant.SCREEN_WIDTH/2-100},
		{0,Constant.SCREEN_HEIGHT/3,Constant.SCREEN_HEIGHT/3+200,Constant.SCREEN_HEIGHT/3-300,10*Constant.SCREEN_HEIGHT/9},
		{120,120,120,120,120}
	};
		public static ArrayList<Lifeup> getFirstLife(){ //為第一關添加愛心
			ArrayList<Lifeup> lifes =  new  ArrayList<Lifeup>();
			lifes.add(new Lifeup(0, 1, 0, life, false, 1080));
			lifes.add(new Lifeup(0, 1, 0, life, false, 900));
			lifes.add(new Lifeup(0, 1, 0, life, false, 600));
			lifes.add(new Lifeup(0, 1, 0, life, false, 350));
			return lifes;
		}

	//出發點、目標點、當前段上的第幾步、路徑數組、狀態、觸發時間、類型、生命
	public static ArrayList<Monster> getFirst(){
		ArrayList<Monster> ms = new ArrayList<Monster>();
		ms.add(new Monster(0, 1, 0, pathA, false, 1150, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 1150, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 1140, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 1140, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 1130, 2,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 1130, 2,  2, 0));
		ms.add(new Monster(0, 1, 0, pathC, false, 1080, 3, 10, 3));
		ms.add(new Monster(0, 1, 0, pathA, false, 1060, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 1050, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 1040, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 1030, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 1020, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 1010, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 1000, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 1000, 1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 980,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 980,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 960,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 960,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 940, 01,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 940, 01,  2, 0));
		ms.add(new Monster(0, 1, 0, pathD, false, 900, 02,  2, 1));
		ms.add(new Monster(0, 1, 0, pathE, false, 900, 02,  2, 2));
		ms.add(new Monster(0, 1, 0, pathD, false, 880, 03,  2, 1));
		ms.add(new Monster(0, 1, 0, pathE, false, 880, 03,  2, 2));
		ms.add(new Monster(0, 1, 0, pathD, false, 860, 02,  2, 1));
		ms.add(new Monster(0, 1, 0, pathE, false, 860, 02,  2, 2));
		ms.add(new Monster(0, 1, 0, pathD, false, 840, 01,  2, 1));
		ms.add(new Monster(0, 1, 0, pathE, false, 840, 01,  2, 2));
		ms.add(new Monster(0, 1, 0, pathA, false, 720,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 720,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathA, false, 660,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathB, false, 660,  1,  2, 0));
		ms.add(new Monster(0, 1, 0, pathF, false, 600,  1, 10, 0));
		ms.add(new Monster(0, 1, 0, pathG, false, 500,  1,  5, 3));
		ms.add(new Monster(0, 1, 0, pathH, false, 500,  1,  5, 3));
		ms.add(new Monster(0, 1, 0, pathG, false, 400,  1,  5, 3));
		ms.add(new Monster(0, 1, 0, pathH, false, 400,  1,  5, 3));
		ms.add(new Monster(0, 1, 0, pathI, false, 300, 03, 50, 4));
		ms.add(new Monster(0, 1, 0, pathJ, false, 0  , 5,100, 5));		//boss
		return ms;
	}
}

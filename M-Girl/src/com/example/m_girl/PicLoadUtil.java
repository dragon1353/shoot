package com.example.m_girl;
//圖片大小配置
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PicLoadUtil {
   //縮放圖片的方法
   public static Bitmap scaleToFit(Bitmap bm,float targetWidth,float targetHeight){//縮放圖片的方法
   	float width = bm.getWidth(); //圖片寬度
   	float height = bm.getHeight();//圖片高度	
   	Matrix m1 = new Matrix(); 
   	m1.postScale(targetWidth/width, targetHeight/height);//這裡指的是目的區域（不是目的圖片）   	
   	Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, (int)width, (int)height, m1, true);//宣告點陣圖        	
   	return bmResult;
   }
}
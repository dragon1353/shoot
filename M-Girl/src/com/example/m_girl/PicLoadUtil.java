package com.example.m_girl;
//�Ϥ��j�p�t�m
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PicLoadUtil {
   //�Y��Ϥ�����k
   public static Bitmap scaleToFit(Bitmap bm,float targetWidth,float targetHeight){//�Y��Ϥ�����k
   	float width = bm.getWidth(); //�Ϥ��e��
   	float height = bm.getHeight();//�Ϥ�����	
   	Matrix m1 = new Matrix(); 
   	m1.postScale(targetWidth/width, targetHeight/height);//�o�̫����O�ت��ϰ�]���O�ت��Ϥ��^   	
   	Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, (int)width, (int)height, m1, true);//�ŧi�I�}��        	
   	return bmResult;
   }
}
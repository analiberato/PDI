package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Limiariza {


	//LIMIARIZAÇÃO
	public static BufferedImage Limiarizacao(BufferedImage img, int scale){
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4]; //vetor para armazenar cada pixel r[0] g[0]1 b[2]

		//usa uma matriz pra ler a img
	      for (int i = 1; i < img.getWidth()-1; i++){
	         for (int j = 1; j < img.getHeight()-1; j++) {
	        	 raster.getPixel(i, j, pixels);
	        	 //o r é maior que o limear, se sim o r passa a ser 1, se menor o r passa para 0
	        	 if(pixels[0] > scale){
	        		 pixels[0] = 255; //255 pq é preto
	        	 } else {
	        		 pixels[0] = 0;
				}
	        	 if(pixels[1] > scale){
	        		 pixels[1] = 255;
	        	 } else {
	        		 pixels[1] = 0;
				}
	        	 if(pixels[2] > scale){
	        		 pixels[2] = 255;
	        	 } else {
	        		 pixels[2] = 0;
				}
	        	raster.setPixel(i, j, pixels);
	         }
	      }
	    try{
	    	newImg.setData(raster);
	    }catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}

}

package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Negativa {


	//NEVATIVAR IMAGEM
	public static BufferedImage Negativo(BufferedImage img){
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
	      for (int i = 1; i < img.getWidth()-1; i++){
	         for (int j = 1; j < img.getHeight()-1; j++) {
	        	 raster.getPixel(i, j, pixels);
	        	 pixels[0] = (int) 255 - pixels[0];
		         pixels[1] = (int) 255 - pixels[1];
		         pixels[2] = (int) 255 - pixels[2];
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

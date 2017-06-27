package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class EscalasDeCinza {
	
	//ESCALA DE CINZA COM MÉDIA SIMPLES E COM MÉDIA PONDERADA
	public static BufferedImage Cinza(BufferedImage img, int nvR, int nvG, int nvB){
		WritableRaster raster = img.getRaster();
		//processamento de img sempre em rgb type_int_rgb
		BufferedImage newImg = new BufferedImage(img.getWidth(), 
				img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
	      for (int i = 1; i < img.getWidth()-1; i++){
	         for (int j = 1; j < img.getHeight()-1; j++) {
	        	//faz uma media e adciona um tom de cinza em cada camada r g b
	        	 raster.getPixel(i, j, pixels);
	        	 double media = (pixels[0]+pixels[1]+pixels[2])/3;
	        	 if(nvR!=0 && nvG!=0 && nvB!=0)
	        		 media = ((pixels[0]*nvR)+(pixels[1]*nvG)+(pixels[2]*nvB))/100;
	        	 pixels[0] = (int)media;
		         pixels[1] = (int)media;
		         pixels[2] = (int)media;
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
		
	//CRIA IMAGEM COM LISTRAS COM TOM DE CINZA
	public static BufferedImage CinzaZebrado(BufferedImage img, int faixas){
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int qtpixelsfaixas = img.getWidth()/faixas; 
		int pixels[] = new int[4];
		int qtpixelschanged = 0;
		faixas = 0;
		for (int i = 1; i < img.getWidth()-1; i++){
			if(qtpixelschanged >= qtpixelsfaixas){
				qtpixelschanged = 0;
		    	faixas ++;
		   	}
			for (int j = 1; j < img.getHeight()-1; j++) {
				if(faixas == 0 || faixas%2 == 0){
					raster.getPixel(i, j, pixels);
					double media = (pixels[0]+pixels[1]+pixels[2])/3;
		        		 pixels[0] = (int)media;
		        		 pixels[1] = (int)media;
		        		 pixels[2] = (int)media;
		        		 raster.setPixel(i, j, pixels);
		   	 	}
			}  qtpixelschanged ++;
		}
	    try{
		   	newImg.setData(raster);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	
	
	public static int calculaEscalaCinza(int tipo,int[] pixels, int propR, int propG,int propB) {
		int resultado = 0;
		switch (tipo) {
		// CALCULA ESCALA SIMPLES
		case 1:
			resultado = (int) ((pixels[0]+pixels[1]+pixels[2])/3);
			break;
		//CALCULA ESCALA PONDERADA
		case 2:
			resultado = (int) (((pixels[0]*propR)+(pixels[1]*propG)+(pixels[2]*propB))/100);
			break;
		default:
			break;
		}
		return resultado;
	}
	


}

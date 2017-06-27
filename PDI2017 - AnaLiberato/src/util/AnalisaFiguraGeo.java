package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class AnalisaFiguraGeo {
	
	// VERIFICA SE UM QUADRADO ESTÁ PREENCHIDO
		public static String analiseQuadrado(BufferedImage image) {
			WritableRaster raster = image.getRaster();
			int pixels[] = new int[4];
			int xInicio = 0,yInicio = 0;
			int xFim = 0,yFim = 0;
			boolean achouInicio = false;
			for (int i = 1; i < image.getHeight()-1; i++) {
				for (int j = 1; j <image.getWidth()-1; j++) {
					raster.getPixel(j,i,pixels);
						if (pixels[0] == 0) {
							if (!achouInicio) {
								achouInicio = true;
								xInicio = i;
								yInicio = j;
								xFim = i;
								yFim = j;
							} else {
								xFim = Math.max(xFim,i);
								yFim = Math.max(yFim,j);
							}
						}
				}
			}
			raster.getPixel((int) ((xFim-xInicio)/2), (int) ((yFim-yInicio)/2), pixels);
			if (pixels[0] == 0) {
				return "QUADRADO PREENCHIDO";
			} else {
				return "QUADRADO VAZIO";
			}
		}
		
		public static String analiseRetangulo(BufferedImage image) {
			WritableRaster raster = image.getRaster();
			int pixels[] = new int[4];
			int xInicio = 0,yInicio = 0;
			int xFim = 0,yFim = 0;
			boolean achouInicio = false;
			boolean achouFim = false;
			boolean retanguloAberto = false;
			ArrayList<Integer> pixelPintado = new ArrayList<>();
			
			//acha o ponto x, y de inicio

			for (int i = 1; i < image.getWidth()-1; i++){
				for (int j = 1; j < image.getHeight()-1; j++) {
					raster.getPixel(i,j,pixels);
						if (pixels[0] == 0) {
							if (!achouInicio) {
								achouInicio = true;
								xInicio = i;
								yInicio = j;
								System.out.println(xInicio + "" + yInicio);
							} else {
								//xFim = Math.max(xFim,i);
								//yFim = Math.max(yFim,j);
							}
							
						}
				}
			}
			//acha o x, y final


			for (int i = 1; i < image.getWidth()-1; i++){
				for (int j = 1; j < image.getHeight()-1; j++) {
					raster.getPixel(i,j,pixels);
						if (pixels[0] == 0) {
							if (!achouFim) {
								achouFim = true;
								xFim = i;
								yFim = j;
								System.out.println(xFim + "" + yFim);
							} else {
								//xFim = Math.max(xFim,i);
								//yFim = Math.max(yFim,j);
							}
							
						}
				}
			}
			
			
			for (int i = xInicio; i < xFim; i++){
				raster.getPixel(i, yInicio, pixels);
				pixelPintado.add(pixels[0]);
				raster.getPixel(i, yFim, pixels);
				pixelPintado.add(pixels[0]);
			}
			
			for (Integer pixel : pixelPintado) {
				if(pixel.equals(255)){
					retanguloAberto = true;
					
				}
			}
			
			//verifica linhas
			/*for (int x = xInicio; x < xFim; x++) {
				raster2.getPixel(x,yInicio,pixels);
				if(pixels[0] == 255){
					retanguloAberto = true;
					System.out.println("aqui");
				}
			}
			for (int x = xInicio; x < xFim; x++) {
				raster2.getPixel(yFim,x,pixels);
				System.out.println(yFim + " " + x);
				if(pixels[0] == 255){
					retanguloAberto = true;
					System.out.println("aqui");
				}
			}
			for (int y = yInicio; y < yFim; y++) {
				raster2.getPixel(y,xFim,pixels);
				if(pixels[0] == 255){
					retanguloAberto = true;
					System.out.println("aqui");
				}
			}*/

			//raster.getPixel((int) ((xFim-xInicio)/2), (int) ((yFim-yInicio)/2), pixels);
			//if (pixels[0] == 0) {
			if (retanguloAberto) {
				return "RETANGULO ABERTO";
			} else {
				return "RETANGULO FECHADO";
			}
		}

}

package util;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Rotaciona {

	//ROTACIONA A IMAGEM EM 90º E EM 360º
		public static BufferedImage Rotacionar90e360(BufferedImage image, int grau) {
				WritableRaster raster = image.getRaster();
				BufferedImage newImg = new BufferedImage( image.getHeight(), image.getWidth(),BufferedImage.TYPE_INT_RGB);
				WritableRaster raster2 = newImg.getRaster();
				int pixels[] = new int[4];
				if(grau == 90){
					for (int i = 1; i < image.getWidth()-1; i++) {
						for (int j = 1; j <image.getHeight()-1; j++) {
							raster.getPixel((i+1),image.getHeight()-(1+j),pixels);
							raster2.setPixel(j,i, pixels);
						}
					}
				}else if(grau == 360){
					for (int i = 1; i < image.getWidth()-1; i++) {
						for (int j = 1; j <image.getHeight()-1; j++) {
							raster.getPixel(image.getWidth()-(i+1),(1+j),pixels);
							raster2.setPixel(j,i, pixels);
						}
					}
				}
				try {
					newImg.setData(raster2);
				} catch (Exception e) {
					System.out.println("Erro ao rotacionar imagem");
					e.printStackTrace();
				}
				return newImg;
			}
			
		//ROTACIONA A IMAGEM EM 180º
		public static BufferedImage Rotacionar180(BufferedImage image) {
			WritableRaster raster = image.getRaster();
			BufferedImage newImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			WritableRaster raster2 = newImg.getRaster();
			int pixels[] = new int[4];
			for (int i = 1; i < image.getWidth()-1; i++) {
				for (int j = 1; j <image.getHeight()-1; j++) {
					raster.getPixel(image.getWidth()-(i+1),image.getHeight()-(1+j),pixels);
					raster2.setPixel(i, j, pixels);
				}
			}
			try {
				newImg.setData(raster2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return newImg;
		}
		
		//PROVA
		//Questão 1 - dividir em 4 quadrantes
		public static BufferedImage dividirQuadrantes(BufferedImage img) {
			BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			int qtpixelsWidth = img.getWidth()/2; 
			int qtpixelsHeight = img.getHeight()/2; 

			newImg = filtroInversaoComDesenho2(img, 0, qtpixelsWidth, 0, qtpixelsHeight, qtpixelsWidth, img.getWidth()-1, qtpixelsHeight, img.getHeight()-1);
			
			return newImg;
			
		}
		
		// FUNÇÕES PARA INVERSAO DE UMA PARTE DA IMAGEM
		public static BufferedImage filtroInversaoComDesenho(BufferedImage image, Point inicio, Point fim) {
			int xMaior = Math.max(inicio.x, fim.x);
			int xMenor = Math.min(inicio.x, fim.x);
			int yMaior = Math.max(inicio.y, fim.y);
			int yMenor = Math.min(inicio.y, fim.y);
			BufferedImage newImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			WritableRaster raster = image.getRaster();
			WritableRaster raster2 = newImg.getRaster();
			int pixels[] = new int[4];
			for (int i = 1; i < image.getWidth()-1; i++) {
				for (int j = 1; j <image.getHeight()-1; j++) {
					if (i >= xMenor && i <= xMaior && j >= yMenor && j <= yMaior) {
						raster.getPixel(xMaior-(Math.max(xMenor,i)-Math.min(xMenor,i)),yMaior-(Math.max(yMenor,j)-Math.min(yMenor,j)),pixels);
					} else {
						raster.getPixel(i,j,pixels);
					}
					raster2.setPixel(i, j, pixels);
				}
			}
			try {
				newImg.setData(raster2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return newImg;
		}
		

		public static BufferedImage filtroInversaoComDesenho2(BufferedImage image, int x1, int x2, int y1, int y2, int segundox1, int segundox2, int segundoy1, int segundoy2) {
			//quadrante 1
			int xMaior = Math.max(x1, x2);
			int xMenor = Math.min(x1, x2);
			int yMaior = Math.max(y1, y2);
			int yMenor = Math.min(y1, y2);
			//quadrante 2
			int xMaior2 = Math.max(segundox1, segundox2);
			int xMenor2 = Math.min(segundox1, segundox2);
			int yMaior2 = Math.max(segundoy1, segundoy2);
			int yMenor2 = Math.min(segundoy1, segundoy2);
			
			BufferedImage newImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			WritableRaster raster = image.getRaster();
			WritableRaster raster2 = newImg.getRaster();
			int pixels[] = new int[4];
			for (int i = 1; i < image.getWidth()-1; i++) {
				for (int j = 1; j <image.getHeight()-1; j++) {
					if (i >= xMenor && i <= xMaior && j >= yMenor && j <= yMaior) {
						raster.getPixel(xMaior-(Math.max(xMenor,i)-Math.min(xMenor,i)),yMaior-(Math.max(yMenor,j)-Math.min(yMenor,j)),pixels);
					}else if(i >= xMenor2 && i <= xMaior2 && j >= yMenor2 && j <= yMaior2){
						raster.getPixel(xMaior2-(Math.max(xMenor2,i)-Math.min(xMenor2,i)),yMaior2-(Math.max(yMenor2,j)-Math.min(yMenor2,j)),pixels);
					}else {
						raster.getPixel(i,j,pixels);
					}
					raster2.setPixel(i, j, pixels);
				}
			}
			try {
				newImg.setData(raster2);
			} catch (Exception e) {
				System.out.println("Erro ao inverter imagem");
				e.printStackTrace();
			}
			return newImg;
		}
}

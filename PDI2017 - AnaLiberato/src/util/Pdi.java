package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


/**
 * FUNÇÕES PARA PROCESSAMENTO DIGITAL DE IMAGEM
 * @author ANA LIBERATO
 */

public class Pdi {
	
    //ADIÇÃO E SUBTRAÇÃO DE DUAS IMAGENS 
	public static BufferedImage adicaoSubtracao(BufferedImage img1, BufferedImage img2, boolean soma,int txImg1, int txImg2) {
		WritableRaster raster1 = img1.getRaster();
		WritableRaster raster2 = img2.getRaster();
		int Xmaior = Math.max(img1.getWidth(),img2.getWidth());
		int Ymaior = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage newImg = new BufferedImage(Xmaior, Ymaior, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster3 = newImg.getRaster();
		int pixels1[] = new int[4];
		int pixels2[] = new int[4];
		int c1 = 0,c2 = 0;
		for (int i = 1; i < Xmaior-1; i++) {
			for (int j = 1; j < Ymaior-1; j++) {
				
				if (i < img1.getWidth() && j < img1.getHeight()) {
						raster1.getPixel(i,j,pixels1);
				} else {
					pixels1[0] = 0;
					pixels1[1] = 0;
					pixels1[0] = 0;
				}
				
				if (i < img2.getWidth() && j < img2.getHeight()) {
					raster2.getPixel(i,j,pixels2);
				} else {
					pixels1[0] = 0;
					pixels1[1] = 0;
					pixels1[0] = 0;
				}
								
				
				c1 = (int) (pixels1[0]*((double) txImg1/100));
				c2 = (int) (pixels2[0]*((double) txImg2/100));
				pixels1[0] = (soma) ? c1+c2 : pixels1[0]-pixels2[0];
				pixels1[0] = (pixels1[0] < 0) ? (pixels1[0]*(-1)) : pixels1[0];
				
				c1 = (int) (pixels1[1]*((double) txImg1/100));
				c2 = (int) (pixels2[1]*((double) txImg2/100));
				pixels1[1] = (soma) ? c1+c2 : pixels1[1]-pixels2[1];
				pixels1[1] = (pixels1[1] < 0) ? (pixels1[1]*(-1)) : pixels1[1];
				
				c1 = (int) (pixels1[2]*((double) txImg1/100));
				c2 = (int) (pixels2[2]*((double) txImg2/100));
				pixels1[2] = (soma) ? c1+c2 : pixels1[2]-pixels2[2];
				pixels1[2] = (pixels1[2] < 0) ? (pixels1[2]*(-1)) : pixels1[2];
								
				raster3.setPixel(i,j,pixels1);
			}
		}
		try {
			newImg.setData(raster3);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return newImg;
		}

	//SELECIONA UMA PARTE DA IMAGEM, DEMARCA OS PONTOS X E Y
	public static BufferedImage demarcarImagem(BufferedImage img, Point inicio, Point fim) {
		Graphics graf = img.getGraphics();
		int inicioX = inicio.x;
		int inicioY = inicio.y;
		int tamanhoX = fim.x - inicio.x;
		int tamanhoY = fim.y - inicio.y;

		if (fim.x < inicioX) {
			inicioX = fim.x;
			tamanhoX = inicio.x - fim.x;
		}

		if (fim.y < inicioY) {
			inicioY = fim.y;
			tamanhoY = inicio.y - fim.y;
		}
		graf.setColor(Color.BLACK);
		graf.drawRect(inicioX, inicioY, tamanhoX, tamanhoY);

		return img;
	}
	
	
	
	
		
	
	

	
}

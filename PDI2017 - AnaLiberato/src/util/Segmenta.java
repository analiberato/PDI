package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Segmenta {

	//SEGMENTAÇÃO
		public static BufferedImage Segmentacao(BufferedImage img){
			WritableRaster raster = img.getRaster();
			BufferedImage newImg = new BufferedImage(img.getWidth(),
					img.getHeight(), BufferedImage.TYPE_INT_RGB);
			WritableRaster raster2 = newImg.getRaster();

			int pixel[] = new int[4]; 
			int histogramaRGB[][] = new int[3][256];
			int histogramaRGBacumulado[][] = new int[3][256];
			for (int i = 1; i < img.getWidth() - 1; i++) { // largura
				for (int j = 1; j < img.getHeight() - 1; j++) { // altura
					raster.getPixel(i, j, pixel);
					histogramaRGB[0][pixel[0]]++;
					histogramaRGB[1][pixel[1]]++;
					histogramaRGB[2][pixel[2]]++;
				}
			}		
			// pega acumulado
			int r=0,g=0,b=0;
			for (int i = 0; i < 255; i++) {
				r = r + histogramaRGB[0][i];
				g = g + histogramaRGB[1][i];
				b = b + histogramaRGB[2][i];
				histogramaRGBacumulado[0][i] = r;
				histogramaRGBacumulado[1][i] = g;
				histogramaRGBacumulado[2][i] = b;
				//System.out.println(histogramaRGBacumulado[0][pixel[0]]);
			}
			// cores de cada setor (partição,,, partes,,, etc)
			int[] setor = new int[3];
			for (int cor = 0; cor <= 2; cor++) { 
				int[] ordem = histogramaRGBacumulado[cor];
				Arrays.sort(ordem); //Coloca em ordem
				setor[cor] = ordem[0]; //pega o valor do meio
			}
			//vizinhaca quadrada
			int pixels[][] = new int[10][4];
			for (int i = 1; i < img.getWidth() - 1; i++) { // largura
				for (int j = 1; j < img.getHeight() - 1; j++) { // altura

					// linha de cima da matriz
					raster.getPixel(i - 1, j - 1, pixels[1]);
					raster.getPixel(i, j - 1, pixels[2]);
					raster.getPixel(i + 1, j - 1, pixels[3]);

					// linha do meio da matriz
					raster.getPixel(i - 1, j, pixels[4]);
					raster.getPixel(i, j, pixels[5]);
					raster.getPixel(i + 1, j, pixels[6]);

					// linha de baixo da matriz
					raster.getPixel(i - 1, j + 1, pixels[7]);
					raster.getPixel(i, j + 1, pixels[8]);
					raster.getPixel(i + 1, j + 1, pixels[9]);

					//media	
					for (int cor = 0; cor <= 2; cor++) {
							pixels[5][cor] = (pixels[1][cor] + pixels[2][cor]
									+ pixels[3][cor] + pixels[4][cor]
									+ pixels[6][cor] + pixels[7][cor]
									+ pixels[8][cor] + pixels[9][cor]) / 8;
					}
					
					// calcula diferenças
					int DifR = (setor[0] - pixels[5][0]);
					int DifG = (setor[1] - pixels[5][1]);
					int DifB = (setor[2] - pixels[5][2]);
					if(DifR <= DifG && DifR <= DifB){
						pixels[5][0] = setor[0];
						//pixels[5][1] =  0;
						//pixels[5][2] =  0;
					}
					if(DifG <= DifR && DifG <= DifB){
						//pixels[5][0] =  0;
						pixels[5][1] =  setor[1];
						//pixels[5][2] =  0;
					}
					if(DifB <= DifG && DifB <= DifR){
						//pixels[5][0] =  0;
						//pixels[5][1] =  0;
						pixels[5][2] =   setor[2];
					}
					raster2.setPixel(i, j, pixels[5]);
				}
			}
			try {
				newImg.setData(raster2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return newImg;
		}
		
	// REALIZA A SEGMENTAÇÃO POR GRUPOS
		public static BufferedImage segmentacaoGrupos(BufferedImage image, int nGrupos) {
			int[][] matrizCores = new int[5][5];
			matrizCores[0][0] = 255;
			matrizCores[0][1] = 0;
			matrizCores[0][2] = 0;
			matrizCores[1][0] = 255;
			matrizCores[1][1] = 255;
			matrizCores[1][2] = 0;
			matrizCores[2][0] = 0;
			matrizCores[2][1] = 0;
			matrizCores[2][2] = 0;
			matrizCores[3][0] = 255;
			matrizCores[3][1] = 0;
			matrizCores[3][2] = 255;
			WritableRaster raster = image.getRaster();
			BufferedImage newImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			WritableRaster raster2 = newImg.getRaster();
			int pixels[] = new int[4];
			int menor = 255, maior = 0;
			for (int i = 1; i < image.getWidth()-1; i++) {
				for (int j = 1; j <image.getHeight()-1; j++) {
					raster.getPixel(i,j,pixels);
					int media = EscalasDeCinza.calculaEscalaCinza(1, pixels, 0,0,0);
					if (media < menor) { 
						menor = media; 
					} else if (media > maior) {
						maior = media;
					}
				}
			}
			int mediaGrupo = Math.round((maior-menor) / nGrupos)+1;
			System.out.println("MAIOR -> "+maior+" - MENOR -> "+menor+" - MEDIA -> "+mediaGrupo);
			for (int j = 1; j <image.getHeight()-1; j++) {
				for (int i = 1; i < image.getWidth()-1; i++) {
					raster.getPixel(i,j,pixels);
					int mediaPixel = EscalasDeCinza.calculaEscalaCinza(1, pixels, 0,0,0);
					int grupo = Math.floorDiv(mediaPixel,mediaGrupo);
					if (grupo < 3) {
						//System.out.println("TROCANDO GRUPO ["+grupo+"] ["+pixels[0]+","+pixels[1]+","+pixels[2]+"] PARA ESCALA -> ["+matrizCores[grupo][0]+","+matrizCores[grupo][1]+","+matrizCores[grupo][2]+"]");
						pixels[0] = matrizCores[grupo][0];
						pixels[1] = matrizCores[grupo][1];
						pixels[2] = matrizCores[grupo][2];
					} else {
						//System.err.println("ERRO: grupo inválido -> ("+grupo+") - MEDIA -> ("+mediaGrupo+") - PIXELS -> ["+pixels[0]+","+pixels[1]+","+pixels[2]+"] ESCALA -> ("+mediaPixel+")");
					}
					raster2.setPixel(i,j,pixels);
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

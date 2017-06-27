package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class Vizinhanca {

	//VIZINHANÇA QUADRADO
	public static BufferedImage VizinhancaEstrela(BufferedImage img, String MediaOuMediana){
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster2 = newImg.getRaster();

		int pixels[][] = new int[10][4]; //vetor para armazenar cada pixel r[0] g[1] b[2]
		for (int i = 1; i < img.getWidth()-1; i++){//altura
			for (int j = 1; j < img.getHeight()-1; j++) { //largura

				//linha de cima
				raster.getPixel(i-1, j-1, pixels[1]);
				raster.getPixel(i+1, j-1, pixels[3]);

				//linha do meio
				raster.getPixel(i, j, pixels[5]);

				//linha de baixo
				raster.getPixel(i-1, j+1, pixels[7]);
				raster.getPixel(i+1, j+1, pixels[9]);

				if (MediaOuMediana.equals("Média Aritmética")) {
					for (int cor = 0; cor <= 2; cor++) {
						pixels[5][cor] = (pixels[1][cor] + pixels[3][cor] + pixels[7][cor] + pixels[9][cor]) / 4;
					}
				}else{
					if(MediaOuMediana.equals("Mediana")){
						for (int cor = 0; cor <= 2 ; cor++) {
							int[] ordem = {pixels[1][cor], pixels[3][cor], pixels[7][cor], pixels[9][cor]} ;
							Arrays.sort(ordem);
							pixels[5][cor] =  ordem[2];
						}
					}
				}
				raster2.setPixel(i, j, pixels[5]);
			}
		}
		try{
			newImg.setData(raster2);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	//OK
	public static BufferedImage VizinhancaCruz(BufferedImage img, String MediaOuMediana){
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster2 = newImg.getRaster();

		int pixels[][] = new int[10][4];
		for (int i = 1; i < img.getWidth()-1; i++){ //largura
			for (int j = 1; j < img.getHeight()-1; j++) { //altura

				//linha de cima da matriz
				raster.getPixel(i  , j-1, pixels[2]);

				//linha do meio da matriz
				raster.getPixel(i-1, j, pixels[4]);
				raster.getPixel(i, j, pixels[5]);
				raster.getPixel(i+1, j, pixels[6]);

				//linha de baixo da matriz
				raster.getPixel(i  , j+1, pixels[8]);

				if (MediaOuMediana.equals("Média Aritmética")) {
					for (int cor = 0; cor <= 2 ; cor++) {
						pixels[5][cor] = (pixels[2][cor] +
								pixels[4][cor] + pixels[6][cor] + pixels[8][cor]) / 4;
					}}else {
						if (MediaOuMediana.equals("Mediana")) {
							for (int cor = 0; cor <= 2 ; cor++) {
								int[] ordem = { pixels[2][cor],
										pixels[4][cor], pixels[6][cor], pixels[8][cor]} ;
								Arrays.sort(ordem);
								pixels[5][cor] =  ordem[2];
							}
						}
					}
				raster2.setPixel(i, j, pixels[5]);
			}
		}
		try{
			newImg.setData(raster2);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	//OK
	public static BufferedImage VizinhancaQuadrado(BufferedImage img, String MediaOuMediana){
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster2 = newImg.getRaster();

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

				if (MediaOuMediana.equals("Média Aritmética")) {
					for (int cor = 0; cor <= 2; cor++) {
						pixels[5][cor] = (pixels[1][cor] + pixels[2][cor]
								+ pixels[3][cor] + pixels[4][cor]
										+ pixels[6][cor] + pixels[7][cor]
												+ pixels[8][cor] + pixels[9][cor]) / 8;
					}
				} else if (MediaOuMediana.equals("Mediana")) {
					for (int cor = 0; cor <= 2; cor++) {
						int[] ordem = { pixels[1][cor], pixels[2][cor],
								pixels[3][cor], pixels[4][cor], pixels[6][cor],
								pixels[7][cor], pixels[8][cor], pixels[9][cor] };
						Arrays.sort(ordem); //Coloca em ordem
						pixels[5][cor] = ordem[4]; //pega o valor do meio
					}
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

}

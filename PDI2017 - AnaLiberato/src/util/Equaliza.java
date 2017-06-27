package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;

public class Equaliza {

	//EQUALIZAÇÃO  PROVA QUESTÃO 2 - Equaliza imagem na diagonal
	public static BufferedImage equalizar(BufferedImage img, boolean diagonal) {
		Map<Integer, Integer> valoresR = new HashMap<Integer, Integer>();
		Map<Integer, Integer> valoresG = new HashMap<Integer, Integer>();
		Map<Integer, Integer> valoresB = new HashMap<Integer, Integer>();
		WritableRaster raster = img.getRaster();
		WritableRaster raster2 = img.getRaster();
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j <img.getHeight()-1; j++) {
				raster.getPixel(i,j,pixels);
				if (valoresR.get(pixels[0]) == null) {
					valoresR.put(pixels[0], 1);
				} else {
					valoresR.put(pixels[0], valoresR.get(pixels[0])+1);
					}
						if (valoresG.get(pixels[1]) == null) {
							valoresG.put(pixels[1], 1);
						} else {
							valoresG.put(pixels[1], valoresG.get(pixels[1])+1);
						}
						if (valoresB.get(pixels[2]) == null) {
							valoresB.put(pixels[2], 1);
						} else {
							valoresB.put(pixels[2], valoresB.get(pixels[2])+1);
						}
					}
				}
				Map<Integer, Integer> histAcumuladoR = new HashMap<Integer, Integer>();
				Map<Integer, Integer> histAcumuladoG = new HashMap<Integer, Integer>();
				Map<Integer, Integer> histAcumuladoB = new HashMap<Integer, Integer>();
				int soma = 0;
				for (int i = 0; i < 256; i++) {
					soma += (valoresR.get(i) == null) ? 0 : valoresR.get(i);
					histAcumuladoR.put(i, soma);
				}
				soma = 0;
				for (int i = 0; i < 256; i++) {
					soma += (valoresG.get(i) == null) ? 0 : valoresG.get(i);
					histAcumuladoG.put(i, soma);
				}
				soma = 0;
				for (int i = 0; i < 256; i++) {
					soma += (valoresB.get(i) == null) ? 0 : valoresB.get(i);
					histAcumuladoB.put(i, soma);
				}
				BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
				int tamanhoTotal = (int) (img.getWidth()-1)*(img.getHeight()-1);
				double mediaCalculo = (255/(double)tamanhoTotal);

				for (int i = 1; i < img.getWidth()-1; i++) {
					for (int j = 1; j <img.getHeight()-1; j++) {
						//pegar as diagonais
						if(j <= i && diagonal == true){
						raster.getPixel(i,j,pixels);
						pixels[0] = (int) Math.round(mediaCalculo*histAcumuladoR.get(pixels[0]));
						pixels[1] = (int) Math.round(mediaCalculo*histAcumuladoG.get(pixels[1]));
						pixels[2] = (int) Math.round(mediaCalculo*histAcumuladoB.get(pixels[2]));
						}else{
							raster2.getPixel(i,j,pixels);
						}
						raster.setPixel(i,j,pixels);
					}
				}
				try {
					newImg.setData(raster);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return newImg;
			}



}

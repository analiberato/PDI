package util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Histograma {

	//MOSTRA HISTOGRAMA CAMILA
	public static BufferedImage MostrarHistograma(BufferedImage img){
		WritableRaster raster = img.getRaster();
		DefaultCategoryDataset dsR = new DefaultCategoryDataset();
		DefaultCategoryDataset dsG = new DefaultCategoryDataset();
		DefaultCategoryDataset dsB = new DefaultCategoryDataset();

		int[] pixels = new int[4];
		int[][] histogramaRGB = new int[3][256];
		for (int i = 1; i < img.getWidth() - 1; i++) { // largura
			for (int j = 1; j < img.getHeight() - 1; j++) { // altura
				raster.getPixel(i, j, pixels);

				histogramaRGB[0][pixels[0]]++;
				histogramaRGB[1][pixels[1]]++;
				histogramaRGB[2][pixels[2]]++;
			}
		}
		for (int i = 0; i < 255; i++) {
			if (histogramaRGB[0][i] >= 0) {
				dsR.addValue(histogramaRGB[0][i], "", i+"");
			}
		}
		JFreeChart grafico = ChartFactory.createBarChart("Histograma Vermelho", "", "", dsR);
		try {
			OutputStream arquivo = new FileOutputStream("imgs/graficoR.jpg");
			ChartUtilities.writeChartAsJPEG(arquivo, grafico, 300, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i1 = 0; i1 < 255; i1++) {
			if (histogramaRGB[1][i1] >= 0) {
				dsG.addValue(histogramaRGB[1][i1], "", i1+"");
			}
		}
		grafico = ChartFactory.createBarChart("Histograma Verde", "", "", dsG);
		try {
			OutputStream arquivo = new FileOutputStream("imgs/graficoG.jpg");
			ChartUtilities.writeChartAsJPEG(arquivo, grafico, 300, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i2 = 0; i2 < 255; i2++) {
			if (histogramaRGB[2][i2] >= 0) {
				dsB.addValue(histogramaRGB[2][i2], "", i2+"");
			}
		}
		grafico = ChartFactory.createBarChart("Histograma Azul", "", "", dsB);
		try {
			OutputStream arquivo = new FileOutputStream("imgs/graficoB.jpg");
			ChartUtilities.writeChartAsJPEG(arquivo, grafico, 300, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}


}

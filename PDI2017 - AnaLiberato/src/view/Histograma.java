package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;

public class Histograma extends Shell {
	private CLabel lblHistogramaG;
	private CLabel lblHistogramaR;

	private Image image;
	private CLabel lblHistogramaB;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Histograma shell = new Histograma(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Histograma(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		lblHistogramaR = new CLabel(this, SWT.NONE);
		lblHistogramaR.setBounds(21, 26, 61, 21);
		lblHistogramaR.setText("");
		
		lblHistogramaG = new CLabel(this, SWT.NONE);
		lblHistogramaG.setBounds(21, 177, 61, 21);
		lblHistogramaG.setText("");
		
		lblHistogramaB = new CLabel(this, SWT.NONE);
		lblHistogramaB.setBounds(21, 316, 61, 21);
		lblHistogramaB.setText("");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Histograma - Ana Espindola");
		setSize(360, 500);
		carregaHistogramas();

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void carregaHistogramas(){
		image = new Image(null, "imgs/graficoR.jpg");
		carregaImagem(lblHistogramaR,image);
		image = new Image(null, "imgs/graficoG.jpg");
		carregaImagem(lblHistogramaG,image);
		image = new Image(null, "imgs/graficoB.jpg");
		carregaImagem(lblHistogramaB,image);
		
	}
	
	private void carregaImagem(CLabel label, Image img){
		label.setBackground(img);
		label.setBounds(
				label.getBounds().x, 
				label.getBounds().y, 
				img.getImageData().width,  
				img.getImageData().height
				);
	}
}

package view;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import util.AnalisaFiguraGeo;
import util.Equaliza;
import util.EscalasDeCinza;
import util.Limiariza;
import util.Morfologia;
import util.Negativa;
import util.Pdi;
import util.Rotaciona;
import util.Segmenta;
import util.Vizinhanca;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Spinner;

public class PDI extends Shell {
	private Label lblR;
	private Label lblG;
	private Label lblB;
	private CLabel clImg1;
	private ScrolledComposite scrolledComposite_1;
	private ScrolledComposite scrolledComposite_2;
	private Button btnNewButton;
	private CLabel clImg2;
	private CLabel clImg3;

	private FileDialog fileDialog;
	private Image image1, image2, image3;
	private String pathImg1, pathImg2;
	private ScrolledComposite scrolledComposite;
	private Text txtR;
	private Text txtG;
	private Text txtB;
	private Scale scalelimiarizacao;
	private Button btnCruz;
	private Button btnQuadrado;
	private Button btnEstrela;
	private Button btnMedia;
	private Button btnMediana;
	private Button btnAritmetica;
	private Button btnPonderada;
	private CTabItem tbtmLimiarizao;
	private CTabItem tbtmRuido;
	private CTabItem tbtmDilatao;
	private CTabItem tbtmHistograma;
	private Button btnDilatar;
	private Button btnAbreImagem;
	private Button btnEqualizar;

	private Point pontoInicio = null;
	private Point pontoFim  = null;
	private boolean imgDemarcada = false;
	private CTabItem tbtmBordas;
	private Composite composite_2;
	private Button btnCanny;
	private Button btnSobel;
	private Button btnLimiarizaoOpencv;
	private Button btnLaplacianoGaussiano;
	private Button btnEqualizarOpenCv;
	private CTabItem tbtmGradiente;
	private Composite composite_4;
	private Button btnLimiarizaoInvertidaOpencv;
	private Button btnGradienteHoughOpencv;
	private CTabItem tbtmDetecoDeClulas;
	private Composite composite_5;
	private Label labelExecucao;
	private Button btnGaussianoOpencv;
	private Button btnCannyGradiente;
	private Button btnSim;
	private Button btnNo;
	private Text txtzebra;
	private Button button;
	private CTabItem tbtmNegativaERotaciona;
	private Composite composite_6;
	private Button btnNegativa;
	private Button btnRotaciona;
	private Button btnRotacionaSelecionada;
	private CCombo comboGrau;
	private CTabItem tbtmAdioESubtrao;
	private Composite composite_7;
	private Spinner txt_img1;
	private Spinner txt_img2;
	private Button button_1;
	private Button button_2;
	private Label label_1;
	private Label label_2;
	private CTabItem tbtmAnalisaFigura;
	private Composite composite_8;
	private Button button_3;
	private Button button_4;
	private Button btnDetectarQuadrado;
	private Label labelresultado;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			PDI shell = new PDI(display);
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
	public PDI(Display display) {
		super(display, SWT.SHELL_TRIM);

		lblR = new Label(this, SWT.NONE);
		lblR.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblR.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblR.setBounds(10, 10, 122, 28);
		lblR.setText("R:");

		lblG = new Label(this, SWT.NONE);
		lblG.setText("G:");
		lblG.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblG.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblG.setBounds(10, 44, 122, 28);

		lblB = new Label(this, SWT.NONE);
		lblB.setText("B:");
		lblB.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblB.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblB.setBounds(10, 79, 147, 28);

		CTabFolder tabFolder_1 = new CTabFolder(this, SWT.BORDER);
		tabFolder_1.setBounds(186, 10, 981, 125);
		tabFolder_1.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmNewItem = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmNewItem.setText("Tons de Cinza");

		Composite composite_3 = new Composite(tabFolder_1, SWT.NONE);
		tbtmNewItem.setControl(composite_3);

		btnAritmetica = new Button(composite_3, SWT.NONE);
		btnAritmetica.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					BufferedImage original = ImageIO.read(new File(pathImg1));
					BufferedImage manipulada = EscalasDeCinza.Cinza(original, 0, 0, 0);
					ImageIO.write(manipulada, "jpg", new File("imgs/_cinzaSimples.jpg"));
					image3 = new Image(null, "imgs/_cinzaSimples.jpg");
					carregaImagem(clImg3, image3);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		});
		btnAritmetica.setText("M\u00E9dia Aritm\u00E9tica");
		btnAritmetica.setBounds(10, 10, 171, 25);

		txtR = new Text(composite_3, SWT.BORDER);
		txtR.setBounds(10, 53, 26, 21);

		txtG = new Text(composite_3, SWT.BORDER);
		txtG.setBounds(42, 53, 26, 21);

		txtB = new Text(composite_3, SWT.BORDER);
		txtB.setBounds(74, 53, 26, 21);

		btnPonderada = new Button(composite_3, SWT.NONE);
		btnPonderada.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					int r = Integer.parseInt(txtR.getText());
					int g = Integer.parseInt(txtG.getText());
					int b = Integer.parseInt(txtB.getText());
					BufferedImage original = ImageIO.read(new File(pathImg1));
					BufferedImage manipulada = EscalasDeCinza.Cinza(original, r, g, b);
					ImageIO.write(manipulada, "jpg", new File("imgs/_cinzaPonderado.jpg"));
					image3 = new Image(null, "imgs/_cinzaPonderado.jpg");
					carregaImagem(clImg3, image3);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		});
		btnPonderada.setText("Ponderada");
		btnPonderada.setBounds(106, 51, 75, 25);

		txtzebra = new Text(composite_3, SWT.BORDER);
		txtzebra.setBounds(394, 22, 76, 21);

		button = new Button(composite_3, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int faixas = Integer.parseInt(txtzebra.getText());
				try {
					BufferedImage original = ImageIO.read(new File(pathImg1));
					BufferedImage manipulada = EscalasDeCinza.CinzaZebrado(original,faixas);
					ImageIO.write(manipulada, "jpg", new File("imgs/_cinzaZebrado.jpg"));
					image3 = new Image(null, "imgs/_cinzaZebrado.jpg");
					carregaImagem(clImg3,image3);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setText("Zebrar");
		button.setBounds(395, 49, 75, 25);

		tbtmNegativaERotaciona = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmNegativaERotaciona.setText("Negativa e rotaciona");

		composite_6 = new Composite(tabFolder_1, SWT.NONE);
		tbtmNegativaERotaciona.setControl(composite_6);

		btnNegativa = new Button(composite_6, SWT.NONE);
		btnNegativa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage original = ImageIO.read(new File(pathImg1));
					BufferedImage manipulada = Negativa.Negativo(original);
					ImageIO.write(manipulada, "jpg", new File("imgs/_negativa.jpg"));
					image3 = new Image(null, "imgs/_negativa.jpg");
					carregaImagem(clImg3,image3);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnNegativa.setText("Negativar");
		btnNegativa.setBounds(118, 10, 75, 25);

		btnRotaciona = new Button(composite_6, SWT.NONE);
		btnRotaciona.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int grau = Integer.parseInt(comboGrau.getItem(comboGrau.getSelectionIndex()));
				try {
					BufferedImage original = ImageIO.read(new File(pathImg1));
					BufferedImage manipulada = null;
					if(grau == 90 || grau == 360)
						manipulada = Rotaciona.Rotacionar90e360(original,grau);
					else
						manipulada = Rotaciona.Rotacionar180(original);
					ImageIO.write(manipulada, "jpg", new File("imgs/_rotacionado.jpg"));
					image3 = new Image(null, "imgs/_rotacionado.jpg");
					carregaImagem(clImg3,image3);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnRotaciona.setText("Rotacionar");
		btnRotaciona.setBounds(118, 53, 75, 25);

		btnRotacionaSelecionada = new Button(composite_6, SWT.NONE);
		btnRotacionaSelecionada.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (imgDemarcada == true && pontoInicio.x > 0 && pontoInicio.y > 0 && pontoFim.x > 0 && pontoFim.y > 0) {
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Rotaciona.filtroInversaoComDesenho(original, pontoInicio, pontoFim);
						ImageIO.write(manipulada, "jpg", new File("imgs/_areaRotacionada.jpg"));
						image3 = new Image(null, "imgs/_areaRotacionada.jpg");
						carregaImagem(clImg3,image3);
						imgDemarcada = false;
					} catch (IOException e1) {
						mensagem("Erro iniciar função de rotação de area selecionada");
						e1.printStackTrace();
					}
				}else{
					mensagem("Demarque a area a ser rotacionada!");
				}


			}
		});
		btnRotacionaSelecionada.setText("Rotacionar area selecionada");
		btnRotacionaSelecionada.setBounds(222, 53, 208, 25);

		comboGrau = new CCombo(composite_6, SWT.BORDER);
		comboGrau.setItems(new String[] {"90", "180", "360"});
		comboGrau.setBounds(10, 57, 91, 21);


		tbtmLimiarizao = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmLimiarizao.setText("Limiariza\u00E7\u00E3o");

		Composite compositeLimiarizacao = new Composite(tabFolder_1, SWT.NONE);
		tbtmLimiarizao.setControl(compositeLimiarizacao);

		scalelimiarizacao = new Scale(compositeLimiarizacao, SWT.NONE);
		scalelimiarizacao.setMaximum(255);
		scalelimiarizacao.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					System.out.println(scalelimiarizacao.getSelection());
					BufferedImage original = ImageIO.read(new File(pathImg1));
					BufferedImage manipulada = Limiariza.Limiarizacao(original,scalelimiarizacao.getSelection());
					ImageIO.write(manipulada, "jpg", new File("imgs/_limiarizacao.jpg"));
					image3 = new Image(null, "imgs/_limiarizacao.jpg");
					carregaImagem(clImg3,image3);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		scalelimiarizacao.setBounds(25, 28, 397, 42);

		btnLimiarizaoOpencv = new Button(compositeLimiarizacao, SWT.NONE);
		btnLimiarizaoOpencv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat destination = Imgcodecs.imread(pathImg1);
		        Imgproc.threshold(destination,destination,127,255,Imgproc.THRESH_BINARY);
				Imgcodecs.imwrite("imgs/_limiariazacaoOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_limiariazacaoOpenCV.jpg");
				carregaImagem(clImg3,image3);


			}
		});
		btnLimiarizaoOpencv.setBounds(510, 10, 181, 25);
		btnLimiarizaoOpencv.setText("Limiariza\u00E7\u00E3o OpenCV");

		btnLimiarizaoInvertidaOpencv = new Button(compositeLimiarizacao, SWT.NONE);
		btnLimiarizaoInvertidaOpencv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat destination = Imgcodecs.imread(pathImg1);
		        Imgproc.threshold(destination,destination,127,255,Imgproc.THRESH_BINARY_INV);
				Imgcodecs.imwrite("imgs/_limiariazacaoInvertOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_limiariazacaoInvertOpenCV.jpg");
				carregaImagem(clImg3,image3);

			}
		});
		btnLimiarizaoInvertidaOpencv.setText("Limiariza\u00E7\u00E3o Invertida OpenCV");
		btnLimiarizaoInvertidaOpencv.setBounds(510, 41, 181, 25);

		tbtmAdioESubtrao = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmAdioESubtrao.setText("Adi\u00E7\u00E3o e Subtra\u00E7\u00E3o");

		composite_7 = new Composite(tabFolder_1, SWT.NONE);
		tbtmAdioESubtrao.setControl(composite_7);

		txt_img1 = new Spinner(composite_7, SWT.BORDER);
		txt_img1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				txt_img2.setSelection(100-txt_img1.getSelection());
			}
		});
		txt_img1.setBounds(55, 10, 47, 22);

		txt_img2 = new Spinner(composite_7, SWT.BORDER);
		txt_img2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				txt_img1.setSelection(100-txt_img2.getSelection());
			}
		});
		txt_img2.setBounds(164, 12, 47, 22);

		button_1 = new Button(composite_7, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null && pathImg2 != null) {
					try {
						BufferedImage original1 = ImageIO.read(new File(pathImg1));
						BufferedImage original2 = ImageIO.read(new File(pathImg2));
						BufferedImage manipulada = Pdi.adicaoSubtracao(original1, original2, true, Integer.parseInt(txt_img1.getText()), Integer.parseInt(txt_img2.getText()));
						ImageIO.write(manipulada, "jpg", new File("imgs/_cruz.jpg"));
						image3 = new Image(null, "imgs/_cruz.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						mensagem("Erro iniciar função de adicao");
						e1.printStackTrace();
					}
				} else {
					mensagem("ERRO: SELECIONE DUAS IMAGENS!");
				}

			}
		});
		button_1.setText("Adicionar");
		button_1.setBounds(235, 10, 75, 25);

		button_2 = new Button(composite_7, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null && pathImg2 != null) {
				try {
					BufferedImage original1 = ImageIO.read(new File(pathImg1));
					BufferedImage original2 = ImageIO.read(new File(pathImg2));
					BufferedImage manipulada = Pdi.adicaoSubtracao(original1, original2, false, Integer.parseInt(txt_img1.getText()), Integer.parseInt(txt_img2.getText()));
					ImageIO.write(manipulada, "jpg", new File("imgs/_cruz.jpg"));
					image3 = new Image(null, "imgs/_cruz.jpg");
					carregaImagem(clImg3,image3);
				} catch (IOException e1) {
					mensagem("Erro iniciar função de adicao");
					e1.printStackTrace();
				}
			} else {
				mensagem("ERRO: SELECIONE DUAS IMAGENS!");
			}

			}
		});
		button_2.setText("Subtrair");
		button_2.setBounds(340, 10, 75, 25);

		label_1 = new Label(composite_7, SWT.NONE);
		label_1.setText("IMG2");
		label_1.setBounds(126, 17, 32, 15);

		label_2 = new Label(composite_7, SWT.NONE);
		label_2.setText("IMG1");
		label_2.setBounds(17, 17, 32, 15);

		tbtmRuido = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmRuido.setText("Ruido");

		Composite compositeVizinhanca = new Composite(tabFolder_1, SWT.NONE);
		tbtmRuido.setControl(compositeVizinhanca);

		Group grpCalculo = new Group(compositeVizinhanca, SWT.NONE);
		grpCalculo.setText("Calculo");
		grpCalculo.setBounds(414, 8, 246, 82);

		btnMediana = new Button(grpCalculo, SWT.RADIO);
		btnMediana.setBounds(135, 37, 90, 16);
		btnMediana.setText("Mediana");

		btnMedia = new Button(grpCalculo, SWT.RADIO);
		btnMedia.setBounds(10, 37, 90, 16);
		btnMedia.setText("Media");

		Group grpVizinhana = new Group(compositeVizinhanca, SWT.NONE);
		grpVizinhana.setText("Vizinhan\u00E7a");
		grpVizinhana.setBounds(10, 8, 386, 82);

		btnEstrela = new Button(grpVizinhana, SWT.RADIO);
		btnEstrela.setBounds(10, 38, 90, 16);
		btnEstrela.setText("Estrela");

		btnCruz = new Button(grpVizinhana, SWT.RADIO);
		btnCruz.setBounds(230, 38, 90, 16);
		btnCruz.setText("Cruz");

		btnQuadrado = new Button(grpVizinhana, SWT.RADIO);
		btnQuadrado.setBounds(106, 38, 90, 16);
		btnQuadrado.setText("Quadrado");

		Button btnOk = new Button(compositeVizinhanca, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String calculo = null;
				if(btnMedia.getSelection()){ calculo = "Média Aritmética";}
				if(btnMediana.getSelection()){ calculo = "Mediana";}
				if(btnQuadrado.getSelection()){
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Vizinhanca.VizinhancaQuadrado(original,calculo);
						ImageIO.write(manipulada, "jpg", new File("imgs/_quadrado.jpg"));
						image3 = new Image(null, "imgs/_quadrado.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else if(btnEstrela.getSelection()){
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Vizinhanca.VizinhancaEstrela(original,calculo);
						ImageIO.write(manipulada, "jpg", new File("imgs/_estrela.jpg"));
						image3 = new Image(null, "imgs/_estrela.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else if(btnCruz.getSelection()){
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Vizinhanca.VizinhancaCruz(original,calculo);
						ImageIO.write(manipulada, "jpg", new File("imgs/_cruz.jpg"));
						image3 = new Image(null, "imgs/_cruz.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnOk.setBounds(683, 37, 75, 25);
		btnOk.setText("OK");

		tbtmHistograma = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmHistograma.setText("Histograma");

		Composite compositeHistograma = new Composite(tabFolder_1, SWT.NONE);
		tbtmHistograma.setControl(compositeHistograma);

		btnEqualizarOpenCv = new Button(compositeHistograma, SWT.NONE);
		btnEqualizarOpenCv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				Mat gray = Imgcodecs.imread(pathImg1, 0);
				Imgproc.equalizeHist(gray, gray);
				Imgcodecs.imwrite("imgs/_equalizaOpenCV.jpg", gray);
				image3 = new Image(null, "imgs/_equalizaOpenCV.jpg");
				carregaImagem(clImg3,image3);
			}
		});
		btnEqualizarOpenCv.setBounds(174, 41, 119, 25);
		btnEqualizarOpenCv.setText("Equalizar Open CV");

		Group grpDiagonal = new Group(compositeHistograma, SWT.NONE);
		grpDiagonal.setText("Diagonal");
		grpDiagonal.setBounds(10, 10, 119, 82);

				btnSim = new Button(grpDiagonal, SWT.RADIO);
				btnSim.setBounds(10, 25, 53, 16);
				btnSim.setText("Sim");

				btnNo = new Button(grpDiagonal, SWT.RADIO);
				btnNo.setBounds(69, 25, 43, 16);
				btnNo.setText("N\u00E3o");

				btnEqualizar = new Button(grpDiagonal, SWT.NONE);
				btnEqualizar.setBounds(20, 47, 75, 25);
				btnEqualizar.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						boolean diagonal = false;
						if(btnSim.getSelection())
							diagonal = true;
						if (pathImg1 != null) {
							try {
								BufferedImage original = ImageIO.read(new File(pathImg1));
								BufferedImage manipulada = Equaliza.equalizar(original, diagonal);
								ImageIO.write(manipulada, "jpg", new File("imgs/_equalizacao.jpg"));
								image3 = new Image(null, "imgs/_equalizacao.jpg");
								carregaImagem(clImg3,image3);
								//carrega histograma na tela
								util.Histograma.MostrarHistograma(original);
								Histograma.main(null);
							} catch (IOException e1) {
								mensagem("Erro ao iniciar equalizacao");
								e1.printStackTrace();
							}
						}

					}
				});
				btnEqualizar.setText("Equalizar");



		tbtmDilatao = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmDilatao.setText("Dilata\u00E7\u00E3o / Eros\u00E3o");

		Composite composite = new Composite(tabFolder_1, SWT.NONE);
		tbtmDilatao.setControl(composite);

		btnDilatar = new Button(composite, SWT.NONE);
		btnDilatar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null) {
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Morfologia.funcaoDilatar(original);
						ImageIO.write(manipulada, "jpg", new File("imgs/_dilatacao.jpg"));
						image3 = new Image(null, "imgs/_dilatacao.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						mensagem("Erro ao iniciar dilatação");
						e1.printStackTrace();
					}
				}
			}
		});
		btnDilatar.setBounds(40, 38, 75, 25);
		btnDilatar.setText("Dilatar");

		Button btnEroso = new Button(composite, SWT.NONE);
		btnEroso.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null) {
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Morfologia.erodir(original);
						ImageIO.write(manipulada, "jpg", new File("imgs/_erocao.jpg"));
						image3 = new Image(null, "imgs/_erocao.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						mensagem("Erro ao iniciar eroção");
						e1.printStackTrace();
					}
				}
			}
		});
		btnEroso.setBounds(151, 38, 75, 25);
		btnEroso.setText("Eros\u00E3o");

		CTabItem tbtmSegmentao = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmSegmentao.setText("Segmenta\u00E7\u00E3o");

		Composite composite_1 = new Composite(tabFolder_1, SWT.NONE);
		tbtmSegmentao.setControl(composite_1);

		Button btnSegmentar = new Button(composite_1, SWT.NONE);
		btnSegmentar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null) {
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Segmenta.Segmentacao(original);
						ImageIO.write(manipulada, "jpg", new File("imgs/_segmentacao.jpg"));
						image3 = new Image(null, "imgs/_segmentacao.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						mensagem("Erro ao iniciar segmentação");
						e1.printStackTrace();
					}
				}
			}
		});
		btnSegmentar.setBounds(51, 35, 75, 25);
		btnSegmentar.setText("Segmentar");

		Button btnSegmentaoComOpencv = new Button(composite_1, SWT.NONE);
		btnSegmentaoComOpencv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					Mat gray = Imgcodecs.imread(pathImg1, 0);
					Imgproc.threshold(gray,gray,127,200,Imgproc.THRESH_BINARY);
					Imgcodecs.imwrite("imgs/_segmentacaoOpenCV.jpg", gray);
					image3 = new Image(null, "imgs/_segmentacaoOpenCV.jpg");
					carregaImagem(clImg3,image3);


					carregaImagem(clImg3,image3);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		btnSegmentaoComOpencv.setBounds(178, 35, 180, 25);
		btnSegmentaoComOpencv.setText("Segmenta\u00E7\u00E3o com OpenCV");

		tbtmBordas = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmBordas.setText("Bordas");

		composite_2 = new Composite(tabFolder_1, SWT.NONE);
		tbtmBordas.setControl(composite_2);

		btnCanny = new Button(composite_2, SWT.NONE);
		btnCanny.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {

					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					Mat color = Imgcodecs.imread(pathImg1, 3);

					Mat gray = new Mat();
					Mat draw = new Mat();
					Mat wide = new Mat();

					Imgproc.cvtColor(color, gray, Imgproc.COLOR_BGR2GRAY);
					Imgproc.Canny(gray, wide, 50, 150, 3, false);
					wide.convertTo(draw, CvType.CV_8U);
					Imgcodecs.imwrite("imgs/_cannyOpenCV.jpg", draw);
					image3 = new Image(null, "imgs/_cannyOpenCV.jpg");
					carregaImagem(clImg3,image3);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCanny.setBounds(68, 37, 75, 25);
		btnCanny.setText("Canny");

		btnSobel = new Button(composite_2, SWT.NONE);
		btnSobel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					Mat gray = Imgcodecs.imread(pathImg1, 0);
					Imgproc.Sobel(gray, gray, gray.depth(), 2, 2);
					Imgcodecs.imwrite("imgs/_sobelOpenCV.jpg", gray);
					image3 = new Image(null, "imgs/_sobelOpenCV.jpg");
					carregaImagem(clImg3,image3);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSobel.setBounds(198, 37, 75, 25);
		btnSobel.setText("Sobel");

		Button btnLaplaciano = new Button(composite_2, SWT.NONE);
		btnLaplaciano.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				Mat gray = Imgcodecs.imread(pathImg1, 0);
				Imgproc.Laplacian(gray, gray, gray.depth());
				Imgcodecs.imwrite("imgs/_laplacianoOpenCV.jpg", gray);
				image3 = new Image(null, "imgs/_laplacianoOpenCV.jpg");
				carregaImagem(clImg3,image3);
			}
		});
		btnLaplaciano.setBounds(337, 37, 75, 25);
		btnLaplaciano.setText("Laplaciano");

		btnLaplacianoGaussiano = new Button(composite_2, SWT.NONE);
		btnLaplacianoGaussiano.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				Mat gray = Imgcodecs.imread(pathImg1, 0);
				Imgproc.Laplacian(gray, gray, gray.depth(), 3, 3, 3, Imgproc.CV_GAUSSIAN);
				Imgcodecs.imwrite("imgs/_laplacianoGassianoOpenCV.jpg", gray);
				image3 = new Image(null, "imgs/_laplacianoGassianoOpenCV.jpg");
				carregaImagem(clImg3,image3);
			}
		});
		btnLaplacianoGaussiano.setBounds(483, 37, 147, 25);
		btnLaplacianoGaussiano.setText("Laplaciano Gaussiano");

		tbtmAnalisaFigura = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmAnalisaFigura.setText("Analisa Figura");

		composite_8 = new Composite(tabFolder_1, SWT.NONE);
		tbtmAnalisaFigura.setControl(composite_8);

		button_3 = new Button(composite_8, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null) {
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						String resultado = AnalisaFiguraGeo.analiseRetangulo(original);
						labelresultado.setText(resultado);
					} catch (IOException e1) {
						mensagem("Não foi possível identificar o retangulo");
						e1.printStackTrace();
					}
				}
			}
		});
		button_3.setText("Detectar retangulo");
		button_3.setBounds(10, 43, 166, 25);

		button_4 = new Button(composite_8, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null) {
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						BufferedImage manipulada = Rotaciona.dividirQuadrantes(original);
						ImageIO.write(manipulada, "jpg", new File("imgs/_quadrantes.jpg"));
						image3 = new Image(null, "imgs/_quadrantes.jpg");
						carregaImagem(clImg3,image3);
					} catch (IOException e1) {
						mensagem("Não foi possível identificar o quadrado");
						e1.printStackTrace();
					}
				}

			}
		});
		button_4.setText("Rotacionar Quadrantes");
		button_4.setBounds(276, 10, 152, 25);

		btnDetectarQuadrado = new Button(composite_8, SWT.NONE);
		btnDetectarQuadrado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathImg1 != null) {
					try {
						BufferedImage original = ImageIO.read(new File(pathImg1));
						String resultado = AnalisaFiguraGeo.analiseQuadrado(original);
						labelresultado.setText(resultado);
					} catch (IOException e1) {
						mensagem("Não foi possível identificar o quadrado");
						e1.printStackTrace();
					}
				}

			}
		});
		btnDetectarQuadrado.setText("Detectar quadrado");
		btnDetectarQuadrado.setBounds(10, 10, 166, 25);

		labelresultado = new Label(composite_8, SWT.NONE);
		labelresultado.setBounds(553, 10, 245, 58);

		tbtmGradiente = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmGradiente.setText("Gradiente");

		composite_4 = new Composite(tabFolder_1, SWT.NONE);
		tbtmGradiente.setControl(composite_4);

		Button btnGradienteComOpencv = new Button(composite_4, SWT.NONE);
		btnGradienteComOpencv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat destination = Imgcodecs.imread(pathImg1);
		        Imgproc.threshold(destination,destination,127,255,Imgproc.MORPH_GRADIENT);
				Imgcodecs.imwrite("imgs/_gradientemorphOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_gradientemorphOpenCV.jpg");
				carregaImagem(clImg3,image3);
			}
		});
		btnGradienteComOpencv.setBounds(50, 31, 177, 25);
		btnGradienteComOpencv.setText("Gradiente OpenCV");

		btnGradienteHoughOpencv = new Button(composite_4, SWT.NONE);
		btnGradienteHoughOpencv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat destination = Imgcodecs.imread(pathImg1);
		        Imgproc.threshold(destination,destination,127,255,Imgproc.CV_HOUGH_GRADIENT );
				Imgcodecs.imwrite("imgs/_gradienteHoughOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_gradienteHoughOpenCV.jpg");
				carregaImagem(clImg3,image3);
			}
		});
		btnGradienteHoughOpencv.setText("Gradiente Hough OpenCV");
		btnGradienteHoughOpencv.setBounds(253, 31, 177, 25);

		btnGaussianoOpencv = new Button(composite_4, SWT.NONE);
		btnGaussianoOpencv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat destination = Imgcodecs.imread(pathImg1);
				Imgproc.threshold(destination,destination,127,255,Imgproc.CV_GAUSSIAN);
				//Imgproc.GaussianBlur(destination, destination, new Size(45,45),1,1); EMBASSA A IMAGEM
				Imgcodecs.imwrite("imgs/_gaussianoOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_gaussianoOpenCV.jpg");
				carregaImagem(clImg3,image3);
			}
		});
		btnGaussianoOpencv.setBounds(453, 31, 143, 25);
		btnGaussianoOpencv.setText("Gaussiano OpenCV");

		btnCannyGradiente = new Button(composite_4, SWT.NONE);
		btnCannyGradiente.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat destination = Imgcodecs.imread(pathImg1);
				Imgproc.threshold(destination,destination,127,255,Imgproc.CV_CANNY_L2_GRADIENT);
				//Imgproc.GaussianBlur(destination, destination, new Size(45,45),1,1); EMBASSA A IMAGEM
				Imgcodecs.imwrite("imgs/_cannyGradienteOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_cannyGradienteOpenCV.jpg");
				carregaImagem(clImg3,image3);
			}
		});
		btnCannyGradiente.setBounds(628, 31, 174, 25);
		btnCannyGradiente.setText("Canny Gradiente OpenCV");

		tbtmDetecoDeClulas = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmDetecoDeClulas.setText("Detec\u00E7\u00E3o de c\u00E9lulas");

		composite_5 = new Composite(tabFolder_1, SWT.NONE);
		tbtmDetecoDeClulas.setControl(composite_5);

		labelExecucao = new Label(composite_5, SWT.NONE);
		labelExecucao.setBounds(455, 10, 238, 80);

		Button btnDetectarClulasNa = new Button(composite_5, SWT.NONE);
		btnDetectarClulasNa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Segmentação inicial,
				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat destination = Imgcodecs.imread(pathImg1);
		        Imgproc.threshold(destination,destination,127,255,Imgproc.THRESH_BINARY);
				Imgcodecs.imwrite("imgs/_limiariazacaoOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_limiariazacaoOpenCV.jpg");
				carregaImagem(clImg3,image3);
				labelExecucao.setText("Limiarização executada...");
				System.out.println("Limiarização executada...");
				//Preenchimento filtragem
				//Distância de Chamfer
				//Separação de WaterShed
				Mat markers = new Mat(destination.size(),CvType.CV_32SC1, new Scalar(0));
				Imgproc.watershed(destination, markers);
				Imgcodecs.imwrite("imgs/_watershedOpenCV.jpg", destination);
		        image3 = new Image(null, "imgs/_watershedOpenCV.jpg");
				carregaImagem(clImg3,image3);
				labelExecucao.setText("WaterShed executado...");

				//Extração de células não proliferantes
				//Extração de células proliferantes

			}
		});
		btnDetectarClulasNa.setBounds(73, 36, 186, 25);
		btnDetectarClulasNa.setText("Detectar c\u00E9lulas na imagem");

		btnAbreImagem = new Button(this, SWT.NONE);
		btnAbreImagem.setBounds(10, 165, 112, 25);
		btnAbreImagem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pathImg1 = fileDialog.open();
				abreImagem(1);
			}
		});
		btnAbreImagem.setText("Abre Imagem 1");

		btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setBounds(352, 165, 112, 25);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pathImg2 = fileDialog.open();
				abreImagem(2);
			}
		});
		btnNewButton.setText("Abre Imagem 2");

		scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 196, 336, 427);
		clImg1 = new CLabel(scrolledComposite, SWT.NONE);
		clImg1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				pontoInicio = new Point(e.x, e.y);
				imgDemarcada = true;
			}

			@Override
			public void mouseUp(MouseEvent e) {
				pontoFim = new Point(e.x, e.y);

				try {
					BufferedImage img = Pdi.demarcarImagem(ImageIO.read(new File(pathImg1)), pontoInicio, pontoFim);
					ImageIO.write(img, "jpg", new File("imgs/_imagemDemarcada.jpg"));
					image3 = new Image(null, "imgs/_imagemDemarcada.jpg");
					carregaImagem(clImg1, image3);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		clImg1.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent mouse) {
				encontraPixel(mouse.x, mouse.y, image1);
			}
		});
		clImg1.setBounds(0, 0, 61, 21);
		clImg1.setText("");
		scrolledComposite.setContent(clImg1);

		scrolledComposite_1 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBounds(352, 196, 394, 427);
		clImg2 = new CLabel(scrolledComposite_1, SWT.NONE);
		clImg2.setBounds(0, 0, 61, 21);
		clImg2.setText("");
		scrolledComposite_1.setContent(clImg2);

		scrolledComposite_2 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_2.setBounds(752, 196, 415, 427);
		clImg3 = new CLabel(scrolledComposite_2, SWT.NONE);
		clImg3.setBounds(0, 0, 61, 21);
		clImg3.setText("");
		scrolledComposite_2.setContent(clImg3);

		Label lblResultado = new Label(this, SWT.NONE);
		lblResultado.setBounds(752, 175, 55, 15);
		lblResultado.setText("Resultado:");
		createContents();
		criaFileDialog();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Processamento Digital de Imagens - Ana Espindola E Camila Martins");
		setSize(1193, 672);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void abreImagem(int indice){
		if(indice==1){
			if(pathImg1!=null && !pathImg1.equals("")){
				image1 = new Image(null, pathImg1);
				carregaImagem(clImg1, image1);
			}
		}else{
			if(pathImg2!=null && !pathImg2.equals("")){
				image2 = new Image(null, pathImg2);
				carregaImagem(clImg2, image2);
			}
		}
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

	private void criaFileDialog(){
		 fileDialog = new FileDialog(this, SWT.OPEN);
		 fileDialog.setText("Abrir");
		 fileDialog.setFilterPath("imgs");
	     String[] filterExt = {"*.*","*.png", "*.bmp", "*.jpg" };
	     fileDialog.setFilterExtensions(filterExt);
	}

	private void encontraPixel(int x, int y, Image image){
		if(image != null){
			ImageData imageData=image.getImageData();
			PaletteData palette = imageData.palette;
			if(palette != null && x > -1 && y > -1){
				int pixel = imageData.getPixel(x,y);
				RGB rgb = palette.getRGB(pixel);
				lblR.setText("R: "+rgb.red);
				lblG.setText("G: "+rgb.green);
				lblB.setText("B: "+rgb.blue);
			}
		}
	}

	private void mensagem(String texto){
		MessageBox mb = new MessageBox(getShell(),
				SWT.ICON_WARNING);
		mb.setMessage(texto);
		mb.setText("Aviso");
		mb.open();
	}
}

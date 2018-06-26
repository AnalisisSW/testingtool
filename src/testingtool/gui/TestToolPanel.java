package testingtool.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import testingtool.app.CodeAnalyzer;

public class TestToolPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private JButton btnFolderSelection;
	private JComboBox<String> cbFileSelection;
	private JComboBox<String> cbClassSelection;
	private JComboBox<String> cbMethodSelection;	
	private JTextArea taViewer;
	private Map<String, RandomAccessFile> fileMap;
	private CodeAnalyzer codeAnalyzer;
	private JLabel lblCodeLines;
	private JLabel lblCommentLines;
	private JLabel lblCommentPercentage;
	private JLabel lblCyclomaticComplexity;
	private JLabel lblFanIn;
	private JLabel lblFanOut;
	private JLabel lblHalsteadLength;
	private JLabel lblHalsteadVolume;


	public TestToolPanel() {
		
		setLayout(new GridBagLayout());
		
		initializeComponents();
		
		codeAnalyzer = new CodeAnalyzer();

		initializeActions();

	}

	private void initializeComponents() {
		
		final Dimension IN_COMP_SIZE = new Dimension(300, 30);
		final Dimension OUT_TEXT_COMP_SIZE = new Dimension(250, 30);
		final Dimension OUT_DATA_COMP_SIZE = new Dimension(150, 30);
		final Dimension TEXT_AREA_SIZE = new Dimension(500, 600);
		
		GridBagConstraints c = new GridBagConstraints();

		/** COMPONENTES PARA SELECCION DE CARPETA **/

		// Label
		JLabel lblFolderSelection = new JLabel("Carpeta de códigos fuente:");
		lblFolderSelection.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblFolderSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.left = 10; 
		c.insets.right = 5;
		c.insets.top = 10;
		c.insets.bottom = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblFolderSelection, c);

		// Button
		btnFolderSelection = new JButton("Abrir carpeta...");
		btnFolderSelection.setFont(new java.awt.Font("Calibri", Font.BOLD, 14));
		btnFolderSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 0;
		c.insets.bottom = 10;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(btnFolderSelection, c);


		/** COMPONENTES PARA SELECCION DE ARCHIVO **/

		// Label
		JLabel lblFileSelection = new JLabel("Archivo de código fuente:");
		lblFileSelection.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblFileSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 5;
		c.insets.bottom = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblFileSelection, c);

		// Combo Box
		cbFileSelection = new JComboBox<String>();
		cbFileSelection.setFont(new Font("Calibri", Font.PLAIN, 18));
		cbFileSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 5;
		c.insets.bottom = 10;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(cbFileSelection, c);


		/** COMPONENTES PARA SELECCION DE CLASE **/

		// Label
		JLabel lblClassSelection = new JLabel("Clase:");
		lblClassSelection.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblClassSelection.setHorizontalAlignment(SwingConstants.LEFT);
		lblClassSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 5;
		c.insets.bottom = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblClassSelection, c);

		// Combo Box
		cbClassSelection = new JComboBox<String>();
		cbClassSelection.setFont(new Font("Calibri", Font.PLAIN, 18));
		cbClassSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 5;
		c.insets.bottom = 10;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(cbClassSelection, c);
		
		
		/** COMPONENTES PARA SELECCION DE METODO **/

		// Label
		JLabel lblMethodSelection = new JLabel("Método:");
		lblMethodSelection.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblMethodSelection.setHorizontalAlignment(SwingConstants.LEFT);
		lblMethodSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 5;
		c.insets.bottom = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblMethodSelection, c);

		// Combo Box
		cbMethodSelection = new JComboBox<String>();
		cbMethodSelection.setFont(new Font("Calibri", Font.PLAIN, 18));
		cbMethodSelection.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 5;
		c.insets.bottom = 10;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(cbMethodSelection, c);
		
		
		/** COMPONENTES PARA MOSTRAR RESULTADOS **/
		
		JLabel title = new JLabel("RESULTADOS DEL ANÁLISIS");
		title.setFont(new java.awt.Font("Calibri", Font.BOLD, 26));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(IN_COMP_SIZE);
		c.insets.top = 30;
		c.insets.bottom = 20;
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(title, c);
		
		// DATO 1
		
		JLabel lblDat1 = new JLabel("Líneas de código totales:");
		lblDat1.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat1.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.insets.top = 0;
		c.insets.bottom = 5;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat1, c);
		
		lblCodeLines = new JLabel();
		lblCodeLines.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblCodeLines.setHorizontalAlignment(SwingConstants.CENTER);
		lblCodeLines.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 9;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblCodeLines, c);
		
		// DATO 2
		
		JLabel lblDat2 = new JLabel("Líneas de código comentadas:");
		lblDat2.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat2.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.gridx = 0;
		c.gridy = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat2, c);
		
		lblCommentLines = new JLabel();
		lblCommentLines.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblCommentLines.setHorizontalAlignment(SwingConstants.CENTER);
		lblCommentLines.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblCommentLines, c);
		
		// DATO 3
		
		JLabel lblDat3 = new JLabel("Porcentaje de comentarios:");
		lblDat3.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat3.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.gridx = 0;
		c.gridy = 11;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat3, c);
		
		lblCommentPercentage = new JLabel();
		lblCommentPercentage.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblCommentPercentage.setHorizontalAlignment(SwingConstants.CENTER);
		lblCommentPercentage.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 11;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblCommentPercentage, c);
		
		// DATO 4
		
		JLabel lblDat4 = new JLabel("Complejidad Ciclomática:");
		lblDat4.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat4.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.gridx = 0;
		c.gridy = 12;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat4, c);
		
		lblCyclomaticComplexity = new JLabel();
		lblCyclomaticComplexity.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblCyclomaticComplexity.setHorizontalAlignment(SwingConstants.CENTER);
		lblCyclomaticComplexity.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 12;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblCyclomaticComplexity, c);
		
		// DATO 5
		
		JLabel lblDat5 = new JLabel("FAN-IN:");
		lblDat5.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat5.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.gridx = 0;
		c.gridy = 13;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat5, c);
		
		lblFanIn = new JLabel();
		lblFanIn.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanIn.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 13;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblFanIn, c);
		
		// DATO 6
		
		JLabel lblDat6 = new JLabel("FAN-OUT:");
		lblDat6.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat6.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.gridx = 0;
		c.gridy = 14;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat6, c);
		
		lblFanOut = new JLabel();
		lblFanOut.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanOut.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 14;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblFanOut, c);
		
		// DATO 7
		
		JLabel lblDat7 = new JLabel("HALSTEAD - Longitud:");
		lblDat7.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat7.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.gridx = 0;
		c.gridy = 15;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat7, c);
		
		lblHalsteadLength = new JLabel();
		lblHalsteadLength.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblHalsteadLength.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadLength.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 15;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblHalsteadLength, c);
		
		// DATO 8
		
		JLabel lblDat8 = new JLabel("HALSTEAD - Volumen:");
		lblDat8.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		lblDat8.setPreferredSize(OUT_TEXT_COMP_SIZE);
		c.gridx = 0;
		c.gridy = 16;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblDat8, c);
		
		lblHalsteadVolume = new JLabel();
		lblHalsteadVolume.setFont(new java.awt.Font("Courier New", Font.BOLD, 20));
		lblHalsteadVolume.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadVolume.setPreferredSize(OUT_DATA_COMP_SIZE);
		c.gridx = 1;
		c.gridy = 16;
		c.insets.bottom = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblHalsteadVolume, c);
				
		
		/** VISUALIZADOR **/
		
		JLabel title2 = new JLabel("Código:");
		title2.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));
		title2.setHorizontalAlignment(SwingConstants.LEFT);
		title2.setPreferredSize(IN_COMP_SIZE);
		c.insets.left = 5; 
		c.insets.right = 10;
		c.insets.top = 10;
		c.insets.bottom = 5;
		c.gridx = 3;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(title2, c);
		
		taViewer = new JTextArea();
		taViewer.setFont(new Font("Consolas", Font.PLAIN, 12));
		taViewer.setEditable(false);
		JScrollPane spViewer = new JScrollPane(taViewer, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spViewer.setPreferredSize(TEXT_AREA_SIZE);
		c.insets.top = 0;
		c.insets.bottom = 10;
		c.gridx = 3;
		c.gridy = 1;
		c.gridheight = 16;
		c.fill = GridBagConstraints.VERTICAL;
		add(spViewer, c);
		
		
	}


	private void initializeActions() {
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Java (*.java)", "java");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setFileFilter(filter);	
		fileChooser.setDialogTitle("Abrir Carpeta...");		
		
		btnFolderSelection.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {				

				File directory = null;
				String fileName = null;
				int javaFiles = 0;
				
				fileMap = new LinkedHashMap<String, RandomAccessFile>();

				int option = fileChooser.showOpenDialog(null);

				if (option == JFileChooser.APPROVE_OPTION) {

					directory = fileChooser.getSelectedFile();
						
						// si por error el usuario elige un archivo, se toma la carpeta donde se encuentra ese archivo
						if (directory.isDirectory() == false)						
							directory = directory.getParentFile();
						
						cbFileSelection.removeAllItems();						
						cbClassSelection.removeAllItems();
						cbMethodSelection.removeAllItems();
						clearResults();
					
						for (File file : directory.listFiles()) {
							
							fileName = file.getName();
							
							if (fileName.contains(".java")) {
								
								javaFiles++;
							
								try {
									
									fileMap.put( fileName, new RandomAccessFile(file, "r") );
									
								} catch (FileNotFoundException e) {
									
									JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE, null);
									System.exit(3);
									
								}
								
								if (javaFiles == 1) {
									
									// dejo la primera posicion del Combo Box con una leyenda para obligar a que el usuario elija
									// lo hago solo cuando la variable esta en uno porque es necesario que como minimo haya un
									// archivo .java para poder habilitar el combo box
									cbFileSelection.addItem("Elija un archivo...");
									
								}
								
								// agrego los nombres en el Combo Box
								cbFileSelection.addItem(fileName);								
								
							}
							
						} // END for (File file : directory.listFiles())
						
						if (javaFiles == 0) {
							
							JOptionPane.showMessageDialog(null, "Debe seleccionar una carpeta que contenga archivos con extensión \".java\".", 
									"Aviso", JOptionPane.WARNING_MESSAGE, null);
														
						} 
							
	
				} // END if (option == JFileChooser.APPROVE_OPTION)
				
			}

		});
		
		cbFileSelection.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent event) {
				
				if (event.getStateChange() == ItemEvent.SELECTED) {					
					
					// inicializo combo box
					cbClassSelection.removeAllItems();
					cbMethodSelection.removeAllItems();
					clearResults();
								
					if (cbFileSelection.getSelectedIndex() != 0) {
						
						// seteo combo box					
						String selectedFileName = (String)cbFileSelection.getSelectedItem();
						
						RandomAccessFile selectedFile = fileMap.get(selectedFileName);
						
						codeAnalyzer.parseFile(selectedFile);
						
						cbClassSelection.addItem("Elija una clase...");
																
						Set<String> keySet = codeAnalyzer.getClassMap().keySet();	
						
						for (String className : keySet)
							cbClassSelection.addItem(className);				
					
					}
					
				}
				
			}
			
		});
		
		cbClassSelection.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {
												
				// si se selecciona la primer opción, se borran todas las demas cosas				
				if (event.getStateChange() == ItemEvent.SELECTED) {
					
					cbMethodSelection.removeAllItems();
					clearResults();					
									
					if (cbClassSelection.getSelectedIndex() != 0) {
						
						// seteo combo box
						String selectedClassName = (String)cbClassSelection.getSelectedItem();
						
						taViewer.setText(codeAnalyzer.getClassMap().get(selectedClassName));
						
						codeAnalyzer.parseClass(selectedClassName);
						
						cbMethodSelection.addItem("Elija un metodo...");				
					
						Set<String> keySet = codeAnalyzer.getMethodMap().keySet();	
					
						for (String methodName : keySet)						
							cbMethodSelection.addItem(methodName);
						
					}
					
				}
				
			}

		});
		
		
		cbMethodSelection.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {
				
				// si se selecciona la primer opción, se borran todas las demas cosas				
				if (event.getStateChange() == ItemEvent.SELECTED && cbMethodSelection.getSelectedIndex() != 0) {	
						
					clearResults();
				
					String selectedMethodName = (String)cbMethodSelection.getSelectedItem();
					
					DecimalFormat df = new DecimalFormat("0.00");
					
					taViewer.setText(codeAnalyzer.getMethodMap().get(selectedMethodName));
					
					codeAnalyzer.analyzeMethod(selectedMethodName);
					
					lblCodeLines.setText(String.valueOf(codeAnalyzer.getCodeLines()));
					
					lblCommentLines.setText(String.valueOf(codeAnalyzer.getCommentLines()));
										
					double percentage = codeAnalyzer.getCommentPercentage();
					Color percentageColor = getCommentPercentageColor((int)percentage);
					lblCommentPercentage.setForeground(percentageColor);					
					lblCommentPercentage.setText(df.format(percentage) + " %");
					
					int cyclomaticComplexity = codeAnalyzer.getCyclomaticComplexity();
					Color cyclomaticComplexityColor = getCyclomaticComplexityColor(cyclomaticComplexity);
					lblCyclomaticComplexity.setForeground(cyclomaticComplexityColor);
					lblCyclomaticComplexity.setText(String.valueOf(cyclomaticComplexity));
					
					int fanIn = codeAnalyzer.getFanIn();
					Color fanInColor = getFanInOutColor(fanIn);
					lblFanIn.setForeground(fanInColor);
					lblFanIn.setText(String.valueOf(fanIn));
					
					int fanOut = codeAnalyzer.getFanOut();
					Color fanOutColor = getFanInOutColor(fanOut);
					lblFanOut.setForeground(fanOutColor);
					lblFanOut.setText(String.valueOf(fanOut));
					
					lblHalsteadLength.setText(String.valueOf(codeAnalyzer.getHalsteadLength()));
					
					lblHalsteadVolume.setText(df.format(codeAnalyzer.getHalsteadVolume()));
											
				}
				
			}

		});
		
	}
	
	private Color getCommentPercentageColor(int p) {
		
		if (p == 0) 
			return Color.RED;
		else if (p > 0 && p <= 5)
			return new Color(234, 120, 7); // ORANGE
		else if (p > 5 && p < 8)
			return new Color(214, 189, 0); // YELLOW
		else if (p >= 8 && p <= 12)
			return new Color(103, 173, 19); // GREEN
		else if (p > 12 && p <= 15)
			return new Color(214, 189, 0); // YELLOW
		else if (p > 15 && p <= 20)
			return new Color(234, 120, 7); // ORANGE
		else return Color.RED;

	}
	
	private Color getCyclomaticComplexityColor(int c) {
		
		if (c <= 10)
			return new Color(103, 173, 19); // GREEN
		else if (c > 10 && c <= 20)
			return new Color(214, 189, 0); // YELLOW
		else if (c > 20 && c <= 30)
			return new Color(234, 120, 7); // ORANGE
		else
			return Color.RED;
		
	}
	
	private Color getFanInOutColor(int f) {
		
		if (f <= 5)
			return new Color(103, 173, 19); // GREEN
		else if (f > 5 && f <= 10)
			return new Color(214, 189, 0); // YELLOW
		else if (f > 10 && f <= 15)
			return new Color(234, 120, 7); // ORANGE
		else
			return Color.RED;
	}
	
	private void clearResults() {
		
		taViewer.setText("");
		lblCodeLines.setText("");
		lblCommentLines.setText("");
		lblCommentPercentage.setText("");
		lblCyclomaticComplexity.setText("");
		lblFanIn.setText("");
		lblFanOut.setText("");
		lblHalsteadLength.setText("");
		lblHalsteadVolume.setText("");
				
	}	

}


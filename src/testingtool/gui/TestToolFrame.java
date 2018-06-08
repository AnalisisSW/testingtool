package testingtool.gui;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class TestToolFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TestToolPanel panel;
	private Image iconImage;
	
	public TestToolFrame(String title)
	{
		super();		
		
		try {			
			
			iconImage = ImageIO.read(TestToolFrame.class.getResource("/images/icon.png"));
			
			panel = new TestToolPanel();
			
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);				

			setResizable(false);
			
			setTitle(title);
			
			setIconImage(iconImage);

			initializeMenuBar();		

			addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {

					int response = JOptionPane.showOptionDialog(null, "¿Seguro deseas salir de la aplicación?", "¡Atención!", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE, null, null, null);

					if (response == JOptionPane.OK_OPTION)
						System.exit(0);
				}			

			});		

			
			add(panel);
			
			
		} catch (IOException ioe) {
			
			JOptionPane.showMessageDialog(null, "No se encontro archivo \"" + ioe.getMessage() + "\".", "ERROR", JOptionPane.ERROR_MESSAGE, null);
			System.exit(1);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error al inicializar ventana de aplicación.", "ERROR", JOptionPane.ERROR_MESSAGE, null);
			e.printStackTrace();
			System.exit(1);
			
		}

	}


	private void initializeMenuBar() {
		
		InputStream helpFileStream = TestToolFrame.class.getResourceAsStream("/other/help.txt");
		InputStream aboutFileStream = TestToolFrame.class.getResourceAsStream("/other/about.txt");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// "ARCHIVO"

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		ExitMenuItem miSalir = new ExitMenuItem();

		mnArchivo.add(miSalir);
		

		// "AYUDA"

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		HelpMenuItem miAyuda = new HelpMenuItem("Ayuda...", helpFileStream, iconImage);
		AboutMenuItem miAcercaDe = new AboutMenuItem("Acerca de...", aboutFileStream, iconImage);		

		mnAyuda.add(miAyuda);
		mnAyuda.add(miAcercaDe);

	}

}




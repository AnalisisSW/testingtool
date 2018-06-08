package testingtool.gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class AboutMenuItem extends JMenuItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDialog aboutWindow;

	public AboutMenuItem(String title, InputStream fileStream, Image iconImage) {		
		
		super(title);
		
		try {
			
			aboutWindow = new JDialog();
			
			String contentString = null;
		    BufferedReader br = new BufferedReader( new InputStreamReader( fileStream ) );
		    StringBuilder stringBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
		    String line = null;
		    
		    while ( (line = br.readLine()) != null ) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }
		    
		    contentString = stringBuilder.toString();
			
			contentString = contentString.replace("<", "&lt;");
			contentString = contentString.replace(" ", "&nbsp;");
			contentString = contentString.replace("\r\n", "<br>");
			contentString = "<html>" + contentString;
			contentString = contentString.concat("</html>");
							  
			JLabel contentLabel = new JLabel(contentString);
			contentLabel.setFont(new Font("Courier New", Font.BOLD, 14));
						
			aboutWindow.add(contentLabel);
			aboutWindow.setTitle(title);
			aboutWindow.setIconImage(iconImage);			
			aboutWindow.setResizable(false);
			aboutWindow.setModal(true);
			aboutWindow.pack();				
			aboutWindow.setLocationRelativeTo(null);
			
			addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
										
						aboutWindow.setVisible(true);
				}

			});
			
		} catch (IOException ioe) {
			
			JOptionPane.showMessageDialog(null, "No se encontro archivo \"" + ioe.getMessage() + "\".", "ERROR", JOptionPane.ERROR_MESSAGE, null);
			System.exit(2);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error al inicializar menú de aplicación.", "ERROR", JOptionPane.ERROR_MESSAGE, null);
			e.printStackTrace();
			System.exit(2);
			
		}
		
		
	}

}

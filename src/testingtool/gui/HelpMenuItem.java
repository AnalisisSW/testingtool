package testingtool.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class HelpMenuItem extends JMenuItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame helpWindow;

	public HelpMenuItem(String title, InputStream fileStream, Image iconImage) {
		
		super(title);
		
	try {
			helpWindow = new JFrame();
			
			String contentString = null;
		    BufferedReader br = new BufferedReader( new InputStreamReader( fileStream, "UTF8" ) );
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
			contentLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
			
			JScrollPane scrollPanel = new JScrollPane(contentLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
						
			helpWindow.add(scrollPanel);
			helpWindow.setTitle(title);
			helpWindow.setIconImage(iconImage);			
			helpWindow.setResizable(false);
			helpWindow.setPreferredSize(new Dimension(720, 700));
			helpWindow.pack();			
			helpWindow.setLocationRelativeTo(null);
			
			addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
										
					helpWindow.setVisible(true);
					
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

package testingtool.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ExitMenuItem extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExitMenuItem()  {
		
		super("Salir");
		
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int response = JOptionPane.showOptionDialog(null, "¿Seguro deseas salir de la aplicación?", "¡Atención!", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null, null, null);

				if (response == JOptionPane.OK_OPTION)
					System.exit(0);

			}

		});
				
	}

}

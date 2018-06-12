package testingtool.app;

import javax.swing.SwingUtilities;

import testingtool.gui.TestToolFrame;

public class Run {

	public static void main(String[] args) {
		
        try {
        	
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            	
                if ("Windows".equals(info.getName())) {
                	
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                    
                }
            }
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        	
            ex.printStackTrace();
            System.exit(1);
            
        }        
		
		TestToolFrame frame = new TestToolFrame("Testing Tool");
		
		frame.pack();
		frame.setLocationRelativeTo(null);
				
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	frame.setVisible(true);
            }
        });

	}

}

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;


public class DiskPannel {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiskPannel window = new DiskPannel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DiskPannel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.setEditable(false);
		frame.getContentPane().add(textField, BorderLayout.NORTH);
		textField.setColumns(4);
	}

}

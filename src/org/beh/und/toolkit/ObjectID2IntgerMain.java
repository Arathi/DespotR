package org.beh.und.toolkit;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import org.beh.und.Util;

public class ObjectID2IntgerMain {

	private JFrame frame;
	private JTextField textField_Integer;
	private JTextField textField_ID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ObjectID2IntgerMain window = new ObjectID2IntgerMain();
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
	public ObjectID2IntgerMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 423, 144);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField_Integer = new JTextField();
		textField_Integer.setBounds(110, 41, 99, 21);
		frame.getContentPane().add(textField_Integer);
		textField_Integer.setColumns(10);
		
		textField_ID = new JTextField();
		textField_ID.setColumns(10);
		textField_ID.setBounds(284, 41, 54, 21);
		frame.getContentPane().add(textField_ID);
		
		JButton btnConvertInt2ID = new JButton("=>");
		btnConvertInt2ID.setBounds(220, 25, 54, 23);
		frame.getContentPane().add(btnConvertInt2ID);
		
		JButton btnConvertID2Int = new JButton("<=");
		btnConvertID2Int.setBounds(220, 58, 54, 23);
		frame.getContentPane().add(btnConvertID2Int);
		
		JLabel lblObjectid = new JLabel("ObjectIDInteger");
		lblObjectid.setBounds(10, 44, 90, 15);
		frame.getContentPane().add(lblObjectid);
		
		JLabel lblObjectid_1 = new JLabel("ObjectID");
		lblObjectid_1.setBounds(348, 44, 54, 15);
		frame.getContentPane().add(lblObjectid_1);

		btnConvertInt2ID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String objectIntegerString = textField_Integer.getText();
				if (objectIntegerString.equals("")) return;
				try{
					int objectInteger = Integer.parseInt(objectIntegerString);
					String objectID = Util.int2id(objectInteger);
					textField_ID.setText(objectID);
				}
				catch (NumberFormatException nfe){
					nfe.printStackTrace();
				}
			}
		});
		btnConvertID2Int.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String objectIDString = textField_ID.getText();
				if (objectIDString.equals("")) return;
				if (objectIDString.length()==4){
					objectIDString="["+objectIDString+"]";
					textField_ID.setText(objectIDString);
				}
				int objectInteger = Util.id2int(objectIDString);
				textField_Integer.setText(objectInteger+"");
			}
		});
	}
}

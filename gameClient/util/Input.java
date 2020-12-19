package gameClient.util;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Input extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField_ID;
	private JTextField textField_lvl;
	private Long id;
	private Integer level;


	/**
	 * Create the frame.
	 */
	public Input() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JPanel p = new JPanel();
		JLabel l = new JLabel("Level: ");
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setFont(new Font("Tahoma", Font.PLAIN, 23));
		p.add(l);
		contentPane.add(p, BorderLayout.CENTER);
		
		textField_lvl = new JTextField();
		textField_lvl.setFont(new Font("Tahoma", Font.PLAIN, 23));
		p.add(textField_lvl);
		textField_lvl.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("ID: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel.add(lblNewLabel);
		
		textField_ID = new JTextField();
		textField_ID.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel.add(textField_ID);
		textField_ID.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnNewButton.addActionListener(this);
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		try
		{
			this.id = Long.parseLong(textField_ID.getText(), 10);
			this.level = Integer.parseInt(textField_lvl.getText());
			
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Wrong Input");
		}
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}
}

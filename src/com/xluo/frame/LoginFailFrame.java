package com.xluo.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.xluo.util.Constant;
import com.xluo.util.ImageUtil;

public class LoginFailFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel contentPane;

	public LoginFailFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(ImageUtil.loadImage(Constant.ICON_404));
		setBounds(100, 100, 450, 300);
		contentPane = new JLabel();
		contentPane.setIcon(ImageUtil.loadImageIcon(Constant.BACKGROUND_404));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("login fail");
		title.setForeground(Color.RED);
		title.setFont(new Font("宋体", Font.PLAIN, 34));
		title.setBounds(142, 10, 177, 59);
		contentPane.add(title);
		
		JLabel lblUserIsNot = new JLabel("user is not exist...");
		lblUserIsNot.setForeground(Color.GREEN);
		lblUserIsNot.setBounds(172, 79, 126, 15);
		contentPane.add(lblUserIsNot);
		
		JButton btnLoginAgain = new JButton("Login Again");
		btnLoginAgain.setBounds(223, 229, 104, 23);
		btnLoginAgain.addActionListener(new LoginFailFrameBtnListener());
		contentPane.add(btnLoginAgain);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new LoginFailFrameBtnListener());
		btnExit.setBounds(337, 229, 87, 23);
		contentPane.add(btnExit);
	}
	
	private class LoginFailFrameBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if("Login Again".equals(e.getActionCommand())){
				new LoginFrame().setVisible(true);
				dispose();
			}else if("Exit".equals(e.getActionCommand())){
				System.exit(NORMAL);
			}
		}
		
	}

}

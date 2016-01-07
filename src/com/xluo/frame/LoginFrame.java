package com.xluo.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.xluo.dao.UserDao;
import com.xluo.po.User;
import com.xluo.util.Constant;
import com.xluo.util.ImageUtil;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel contentPane;
	private JTextField passwordText;
	private JTextField emailText;

	private UserDao userDao = new UserDao();

	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(ImageUtil.loadImage(Constant.LOGO));

		setBounds(100, 100, 450, 300);
		contentPane = new JLabel();
		contentPane.setIcon(ImageUtil.loadImageIcon(Constant.DOOR));
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.WHITE);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		passwordText = new JPasswordField();
		passwordText.setBounds(93, 119, 122, 21);
		contentPane.add(passwordText);
		passwordText.setColumns(10);

		emailText = new JTextField();
		emailText.setBounds(93, 88, 122, 21);
		contentPane.add(emailText);
		emailText.setColumns(10);

		JLabel lblName = new JLabel("Email");
		lblName.setBounds(49, 91, 34, 15);
		contentPane.add(lblName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(29, 122, 54, 15);
		contentPane.add(lblPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new LoginFrameBtnListener());
		btnLogin.setBounds(30, 166, 82, 23);
		contentPane.add(btnLogin);

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(133, 166, 82, 23);
		btnReset.addActionListener(new LoginFrameBtnListener());
		contentPane.add(btnReset);

		JLabel lblNewLabel = new JLabel("school library");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 32));
		lblNewLabel.setBounds(109, 10, 233, 38);
		contentPane.add(lblNewLabel);
	}

	private class LoginFrameBtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("Login".equals(e.getActionCommand())) {
				String email = emailText.getText();
				String password = passwordText.getText();
				try {
					 User user = userDao.selectOne(
					 new String[]{"email", "password"},
					 new Object[]{email, password},
					 new String[]{"username", "id"});
					if (user == null) {
						new LoginFailFrame().setVisible(true);
					} else {
						new IndexFrame(user).setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					new LoginFailFrame().setVisible(true);
				}
				dispose();
			} else if ("Reset".equals(e.getActionCommand())) {
				passwordText.setText("");
				emailText.setText("");
			}
		}

	}
	
}

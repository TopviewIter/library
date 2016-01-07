package com.xluo.frame.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xluo.util.ImageUtil;
import com.xluo.util.SwingUtil;

public class UserInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private InfoTemplatePanel schoolInfoPanel;
	private InfoTemplatePanel meInfoPanel;
	private JLabel lblLeftlabel;
	private JLabel lblRightlabel;
	
	public UserInfoPanel() {

		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(0, 0, 731, 369);
		
		schoolInfoPanel = new InfoTemplatePanel("school-info", 
				new String[]{"school: ", "departemnt: ", "class: ", "no: "});
		add(schoolInfoPanel);
		
	    meInfoPanel = new InfoTemplatePanel("me-info", 
				new String[]{"name: ", "oldPassword: ", "password: ", "email: "});
		add(meInfoPanel);		
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.setBounds(60, 272, 100, 23);
		btnCancel.addActionListener(new ButtonListener());
		meInfoPanel.add(btnCancel);
		
		JButton btnSubmit = new JButton("submit");
		btnSubmit.setBounds(265, 272, 100, 23);
		btnSubmit.addActionListener(new ButtonListener());
		meInfoPanel.add(btnSubmit);
		meInfoPanel.setVisible(false);
		
		lblLeftlabel = new JLabel("leftLabel");
		lblLeftlabel.setIcon(ImageUtil.loadImageIcon("/userInfo_left.png"));
		lblLeftlabel.setBounds(56, 117, 54, 130);
		lblLeftlabel.setVisible(false);
		lblLeftlabel.addMouseListener(new LabelListener());
		add(lblLeftlabel);
		
		lblRightlabel = new JLabel("rightLabel");
		lblRightlabel.setIcon(ImageUtil.loadImageIcon("/userInfo_right.png"));
		lblRightlabel.setBounds(620, 122, 54, 120);
		lblRightlabel.addMouseListener(new LabelListener());
		add(lblRightlabel);
		
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String label = e.getActionCommand();
			if("cancel".equals(label)){
				SwingUtil.clearText(schoolInfoPanel.getTexts());
				SwingUtil.clearText(meInfoPanel.getTexts());
			}else if("submit".equals(label)){
				
			}
		}
		
	}
	
	private class LabelListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			String label = ((JLabel)e.getSource()).getText();
			if("leftLabel".equals(label)){
				schoolInfoPanel.setVisible(true);
				meInfoPanel.setVisible(false);
				lblLeftlabel.setVisible(false);
				lblRightlabel.setVisible(true);
			}else if("rightLabel".equals(label)){
				schoolInfoPanel.setVisible(false);
				meInfoPanel.setVisible(true);
				lblLeftlabel.setVisible(true);
				lblRightlabel.setVisible(false);
			}
		}
		
	}
}

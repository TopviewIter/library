package com.xluo.frame.panel;

import javax.swing.JPanel;

import com.xluo.util.ImageUtil;

import javax.swing.JLabel;
import java.awt.Color;

public class AuthorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public AuthorPanel() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(0, 0, 731, 364);
		
		JLabel lblSrc = new JLabel("");
		lblSrc.setBounds((731 - 280) / 2, 5, 280, 280);
		add(lblSrc);
		lblSrc.setIcon(ImageUtil.loadImageIcon("/git.png"));
		
		JLabel lblName = new JLabel("AuthorName: xluo");
		lblName.setBounds(232, 286, 96, 15);
		add(lblName);
		
		JLabel lblQQ = new JLabel("Author  QQ: 1040706472");
		lblQQ.setBounds(232, 307, 132, 15);
		add(lblQQ);
		
		JLabel lblBlog = new JLabel("AuthorBlog: http://www.cnblogs.com/D-Key/");
		lblBlog.setBounds(232, 328, 248, 15);
		add(lblBlog);

	}
}

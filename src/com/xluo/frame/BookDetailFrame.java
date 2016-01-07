package com.xluo.frame;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.xluo.po.Book;

public class BookDetailFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public BookDetailFrame(Book book) {
		
		JPanel detailPanel = new JPanel();
		getContentPane().add(detailPanel, BorderLayout.CENTER);
		detailPanel.setLayout(null);
		
		JLabel lblBookDetail = new JLabel("Book - Detail");
		lblBookDetail.setFont(new Font("宋体", Font.PLAIN, 30));
		lblBookDetail.setBounds(119, 10, 195, 44);
		detailPanel.add(lblBookDetail);
		
		JLabel lblBookname = new JLabel("BookName: " + book.getBookname());
		lblBookname.setBounds(57, 60, 315, 22);
		detailPanel.add(lblBookname);
		
		JLabel lblAuthor = new JLabel("Author: " + book.getAuthor());
		lblAuthor.setBounds(57, 92, 315, 15);
		detailPanel.add(lblAuthor);
		
		JLabel lblType = new JLabel("Type:" + book.getType());
		lblType.setBounds(57, 117, 315, 15);
		detailPanel.add(lblType);
		
		JLabel lblPublishtime = new JLabel("PublishTime: " + book.getPublishTime());
		lblPublishtime.setBounds(57, 142, 315, 15);
		detailPanel.add(lblPublishtime);
		
		JLabel lblSimpleinfo = new JLabel("SimpleInfo:");
		lblSimpleinfo.setBounds(57, 170, 109, 15);
		detailPanel.add(lblSimpleinfo);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(57, 195, 315, 57);
		textPane.setText(book.getSimpleInfo());
		textPane.setEditable(false);
		detailPanel.add(textPane);
		
		setResizable(false);
		setBounds(100, 100, 450, 300);
	}

}

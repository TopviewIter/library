package com.xluo.frame.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.xluo.dao.BookDao;
import com.xluo.dao.UserDao;
import com.xluo.dto.UserBookDto;
import com.xluo.po.User;

public class AllUserPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private UserDao userDao = new UserDao();
	private BookDao bookDao = new BookDao();

	public AllUserPanel() throws SQLException {
		setBounds(10, 25, 710, 360);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 710, 360);
		add(scrollPane);
		
		
		List<User> users = userDao.selectListOrder(null, null, null, new String[]{"username"}, false);
		List<UserBookDto> userBookDtos = new ArrayList<UserBookDto>();
		if(users != null && users.size() != 0){
			for(User u : users){
				userBookDtos.addAll(UserBookDto.changeToDto(u, bookDao.selectRentBook(u)));
			}
		}
		String[] names = { "username", "bookname", "isreturn"};
		
		Object[][] bookInfo = UserBookDto.changeToArray(userBookDtos, names);
		JTable table = new JTable(bookInfo, names);
		table.setPreferredScrollableViewportSize(new Dimension(550, 500));
		table.setEnabled(false);
		scrollPane.setSize(731, 360);
		scrollPane.setLocation(0, 0);
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		
	}
	
}

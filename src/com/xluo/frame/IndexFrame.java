package com.xluo.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.xluo.dao.BookDao;
import com.xluo.po.Book;
import com.xluo.po.User;
import com.xluo.util.Constant;
import com.xluo.util.ImageUtil;
import java.awt.SystemColor;

public class IndexFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel contentPane;
	private JPanel tablePanel;
	private JPanel bookTypePanel;
	private JPanel bookDetailPanel;
	private JScrollPane bookDetailScrollPane;

	private BookDao bookDao = new BookDao();

	public IndexFrame(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 452);
		contentPane = new JLabel();
		 contentPane.setIcon(ImageUtil.loadImageIcon(Constant.LIBRARY));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 731, 21);
		contentPane.add(menuBar);

		JMenu mnLibrary = new JMenu("library");
		mnLibrary.setIcon(ImageUtil.loadImageIcon(Constant.ICON_LIBRARY));
		menuBar.add(mnLibrary);

		JMenuItem mntmNew = new JMenuItem("newest book");
		mntmNew.addActionListener(new IndexFrameItemListener());
		mnLibrary.add(mntmNew);

		JMenuItem mntmHot = new JMenuItem("hot book");
		mntmHot.addActionListener(new IndexFrameItemListener());
		mnLibrary.add(mntmHot);

		JMenuItem mntmOther = new JMenuItem("other...");
		mntmOther.addActionListener(new IndexFrameItemListener());
		mnLibrary.add(mntmOther);

		JMenu mnInfo = new JMenu("myInfo");
		mnInfo.setIcon(ImageUtil.loadImageIcon(Constant.ICON_ME));
		menuBar.add(mnInfo);

		JMenuItem mntmScaned = new JMenuItem("scaned");
		mnInfo.add(mntmScaned);

		JMenuItem mntmRented = new JMenuItem("rented");
		mnInfo.add(mntmRented);

		JMenu mnSetting = new JMenu("setting");
		mnSetting.setIcon(ImageUtil.loadImageIcon(Constant.SETTING));
		menuBar.add(mnSetting);

		JMenuItem mntmSwapUser = new JMenuItem("swap user");
		mnSetting.add(mntmSwapUser);

		JMenuItem mntmExit = new JMenuItem("exit");
		mnSetting.add(mntmExit);

		JMenu mnAbout = new JMenu("about");
		mnAbout.setIcon(ImageUtil.loadImageIcon(Constant.ABOUT));
		menuBar.add(mnAbout);

		JMenuItem mntmAuthor = new JMenuItem("author");
		mnAbout.add(mntmAuthor);

		JMenuItem mntmVersion = new JMenuItem("version");
		mnAbout.add(mntmVersion);

		tablePanel = new JPanel();
		tablePanel.setBounds(10, 25, 710, 350);
		contentPane.add(tablePanel);
		tablePanel.setLayout(null);
		tablePanel.setOpaque(false);

		bookTypePanel = new JPanel();
		bookTypePanel.setBounds(0, 21, 151, 354);
		contentPane.add(bookTypePanel);
		bookTypePanel.setOpaque(false);
		bookTypePanel.setLayout(new BorderLayout(0, 0));

		bookDetailScrollPane = new JScrollPane();
		bookDetailScrollPane.setVisible(false);
		bookDetailScrollPane.setBounds(168, 21, 552, 354);
		contentPane.add(bookDetailScrollPane);
		
		JPanel footPanel = new JPanel();
		footPanel.setBackground(SystemColor.activeCaption);
		footPanel.setBounds(0, 389, 731, 35);
		contentPane.add(footPanel);
		footPanel.setLayout(null);
		@SuppressWarnings("deprecation")
		JLabel lblWelcome = new JLabel("welcome-" + user.getUsername() + " | login time:<" + new Date().toLocaleString() + ">");
		lblWelcome.setBounds(10, 10, 344, 16);
		footPanel.add(lblWelcome);
		lblWelcome.setFont(new Font("宋体", Font.PLAIN, 14));
		lblWelcome.setForeground(Color.RED);
		
		setResizable(false);

	}

	private void createBookDetailBox(List<Book> book) {
		bookDetailScrollPane.setVisible(true);
		bookDetailPanel = new JPanel();
		bookDetailPanel.setLayout(null);
//		bookDetailPanel.setPreferredSize(new Dimension(552, (book.size() / 3 + 1) * 177));
		bookDetailPanel.removeAll();
		for (int i = 0, j = -1; i < book.size(); i++) {
			JPanel bookBoxPanel = new JPanel();
			if (i % 3 == 0)
				j++;
			bookBoxPanel.setBounds((i % 3) * 183, j * 177, 183, 177);
			bookDetailPanel.add(bookBoxPanel);
			bookBoxPanel.setLayout(null);

			JLabel lblBookImage = new JLabel("bookImage");
			lblBookImage.setBounds(10, 5, 163, 136);
			lblBookImage.setIcon(ImageUtil.loadImageIcon(Constant.LIBRARY));
			bookBoxPanel.add(lblBookImage);

			JLabel lblBookName = new JLabel("bookname");
			lblBookName.setBounds(49, 145, 90, 15);
			bookBoxPanel.add(lblBookName);
		}
		bookDetailScrollPane.add(bookDetailPanel);
		bookDetailPanel.setBounds(0, 0, 552, 1000);
		contentPane.add(bookDetailScrollPane);
	}

	private class IndexFrameListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if ("computer".equals(((JList<?>) e.getSource()).getSelectedValue())) {
				createBookDetailBox(getBookData("computer"));
			} else if ("database".equals(((JList<?>) e.getSource()).getSelectedValue())) {
				createBookDetailBox(getBookData("database"));
			}
		}

		private List<Book> getBookData(String type) {
			try {
				return bookDao.selectList(new String[] { "type" }, new Object[] { type },
						new String[] { "img", "bookname" });
			} catch (SQLException e1) {
				e1.printStackTrace();
				return null;
			}
		}

	}

	private class IndexFrameItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("newest book".equals(e.getActionCommand())) {
				CreateNewBookTable();
			} else if ("hot book".equals(e.getActionCommand())) {
				createHotBookTable();
			} else if ("other...".equals(e.getActionCommand())) {
				createBookTypeList();
			}
		}

		private void createBookTypeList() {
			bookTypePanel.removeAll();
			bookTypePanel.setVisible(true);
			bookDetailScrollPane.setVisible(false);
			tablePanel.setVisible(false);
			bookTypePanel.setBackground(Color.WHITE);
			bookTypePanel.setOpaque(true);
			JList<String> bookTypeList = new JList<String>();
			bookTypeList.addListSelectionListener(new IndexFrameListListener());
			bookTypeList.setFont(new Font("宋体", Font.PLAIN, 14));
			bookTypeList.setModel(new AbstractListModel<String>() {
				private static final long serialVersionUID = 1L;
				String[] values = new String[] { "computer", "database" };

				public int getSize() {
					return values.length;
				}

				public String getElementAt(int index) {
					return values[index];
				}
			});
			bookTypeList.setBounds(0, 0, 151, 354);
			bookTypePanel.add(bookTypeList, BorderLayout.CENTER);
			contentPane.add(bookTypePanel);
		}

		private void createHotBookTable() {
			tablePanel.removeAll();
			tablePanel.setVisible(true);
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			String[] Names = { "bookname", "simpleInfo", "author", "acount" };
			Object[][] playerInfo = { { "oracle", "good book", "xiaoming", 1 }, { "web", "good book", "xiaoming", 2 } };
			JTable table = new JTable(playerInfo, Names);
			table.setPreferredScrollableViewportSize(new Dimension(550, 30));
			table.setEnabled(false);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setSize(731, 364);
			scrollPane.setLocation(0, 20);
			table.setBackground(Color.WHITE);
			tablePanel.add(scrollPane);
			contentPane.add(tablePanel);
		}

		private void CreateNewBookTable() {
			tablePanel.setVisible(true);
			tablePanel.removeAll();
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			String[] Names = { "bookname", "simpleInfo", "author", "acount" };
			Object[][] playerInfo = { { "javase", "good book", "xiaoming", 1 },
					{ "mysql", "good book", "xiaoming", 2 } };
			JTable table = new JTable(playerInfo, Names);
			table.setPreferredScrollableViewportSize(new Dimension(550, 30));
			table.setEnabled(false);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setSize(731, 364);
			scrollPane.setLocation(0, 20);
			table.setOpaque(false);
			table.setBackground(Color.WHITE);
			tablePanel.add(scrollPane);
			contentPane.add(tablePanel);
		}

	}
}

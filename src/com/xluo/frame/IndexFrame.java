package com.xluo.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
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
import javax.swing.event.MenuEvent;

import com.xluo.adapter.MenuListenerAdapter;
import com.xluo.dao.BookDao;
import com.xluo.frame.panel.AuthorPanel;
import com.xluo.po.Book;
import com.xluo.po.User;
import com.xluo.service.BookService;
import com.xluo.util.Constant;
import com.xluo.util.ImageUtil;

public class IndexFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel tablePanel;
	private JPanel bookTypePanel;
	private JPanel bookDetailPanel;
	private JScrollPane bookDetailScrollPane;
	private JPanel authorPanel;

	private BookDao bookDao = new BookDao();
	private BookService bookService = new BookService();

	public IndexFrame(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 452);
		contentPane = new JPanel();
//		contentPane.setIcon(ImageUtil.loadImageIcon(Constant.LIBRARY));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 731, 21);
		contentPane.add(menuBar);

		JMenu mnLibrary = new JMenu("library");
		mnLibrary.setIcon(ImageUtil.loadImageIcon(Constant.ICON_LIBRARY));
		mnLibrary.addSeparator();
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
		mnInfo.addSeparator();
		menuBar.add(mnInfo);

		JMenuItem mntmScaned = new JMenuItem("scaned");
		mnInfo.add(mntmScaned);

		JMenuItem mntmRented = new JMenuItem("rented");
		mnInfo.add(mntmRented);

		JMenu mnSetting = new JMenu("setting");
		mnSetting.setIcon(ImageUtil.loadImageIcon(Constant.SETTING));
		mnSetting.addMenuListener(new IndexFrameJItemListener());
		mnSetting.addSeparator();
		menuBar.add(mnSetting);

		JMenuItem mntmSwapUser = new JMenuItem("swap user");
		mntmSwapUser.addActionListener(new IndexFrameItemListener());
		mnSetting.add(mntmSwapUser);

		JMenuItem mntmExit = new JMenuItem("exit");
		mntmExit.addActionListener(new IndexFrameItemListener());
		mnSetting.add(mntmExit);

		JMenu mnAbout = new JMenu("about");
		mnAbout.setIcon(ImageUtil.loadImageIcon(Constant.ABOUT));
		mnAbout.addMenuListener(new IndexFrameJItemListener());
		mnAbout.addSeparator();
		menuBar.add(mnAbout);

		JMenuItem mntmAuthor = new JMenuItem("author");
		mntmAuthor.addActionListener(new IndexFrameItemListener());
		mnAbout.add(mntmAuthor);

		JMenuItem mntmVersion = new JMenuItem("version");
		mnAbout.add(mntmVersion);

		tablePanel = new JPanel();
		tablePanel.setBounds(10, 25, 710, 360);
		tablePanel.setVisible(false);
		contentPane.add(tablePanel);
		tablePanel.setLayout(null);
		tablePanel.setOpaque(false);

		bookTypePanel = new JPanel();
		bookTypePanel.setBounds(0, 21, 151, 364);
		contentPane.add(bookTypePanel);
		bookTypePanel.setOpaque(false);
		bookTypePanel.setLayout(new BorderLayout(0, 0));

		bookDetailScrollPane = new JScrollPane();
		bookDetailScrollPane.setVisible(false);
		bookDetailScrollPane.setBounds(168, 21, 552, 364);
		contentPane.add(bookDetailScrollPane);

		JPanel footPanel = new JPanel();
		footPanel.setBackground(SystemColor.activeCaption);
		footPanel.setBounds(0, 389, 731, 35);
		contentPane.add(footPanel);
		footPanel.setLayout(null);
		@SuppressWarnings("deprecation")
		JLabel lblWelcome = new JLabel(
				"welcome-" + user.getUsername() + " | login time:<" + new Date().toLocaleString() + ">");
		lblWelcome.setBounds(10, 10, 344, 16);
		footPanel.add(lblWelcome);
		lblWelcome.setFont(new Font("宋体", Font.PLAIN, 14));
		lblWelcome.setForeground(Color.RED);
		
		authorPanel = new JPanel();
		authorPanel.setBounds(0, 21, 731, 364);
		authorPanel.setVisible(false);
		contentPane.add(authorPanel);
		authorPanel.setLayout(new BorderLayout());

		setResizable(false);

	}

	private class IndexFrameJItemListener extends MenuListenerAdapter {

		@Override
		public void menuSelected(MenuEvent e) {
			bookDetailScrollPane.setVisible(false);
		}

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

	private void createBookDetailBox(List<Book> book) {
		bookDetailScrollPane.setVisible(true);
		bookDetailPanel = new JPanel();
		bookDetailPanel.setLayout(null);
		bookDetailPanel.setPreferredSize(new Dimension(552, (book.size() / 3 + 1) * 177));
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
		bookDetailScrollPane.setViewportView(bookDetailPanel);
		bookDetailPanel.setBounds(0, 0, 552, (book.size() / 3 + 1) * 177);
		contentPane.add(bookDetailScrollPane);
	}

	private class IndexFrameItemListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String label = e.getActionCommand();
			if ("newest book".equals(label)) {
				try {
					CreateNewBookTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if ("hot book".equals(label)) {
				createHotBookTable();
			} else if ("other...".equals(label)) {
				createBookTypeList();
			} else if ("author".equals(label)){
				authorPanel.setVisible(true);
				bookDetailScrollPane.setVisible(false);
				bookTypePanel.setVisible(false);
				tablePanel.setVisible(false);
				authorPanel.add(new AuthorPanel(), BorderLayout.CENTER);
			} else if("swap user".equals(label)){
				new LoginFrame().setVisible(true);
				dispose();
			}else if ("exit".equals(label)){
				System.exit(NORMAL);
			}
		}

		private void createBookTypeList() {
			bookTypePanel.removeAll();
			bookTypePanel.setVisible(true);
			bookDetailScrollPane.setVisible(false);
			authorPanel.setVisible(false);
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
			authorPanel.setVisible(false);
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

		private void CreateNewBookTable() throws SQLException {
			tablePanel.setVisible(true);
			tablePanel.removeAll();
			authorPanel.setVisible(false);
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			String[] Names = { "bookname", "simpleInfo", "author", "account" };
			List<Book> books = bookDao.selectListOrder(null, null, Names, new String[]{"publishTime"}, true);
			Object[][] results = bookService.changeToArray(books, Names);
			JTable table = new JTable(results, Names);
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

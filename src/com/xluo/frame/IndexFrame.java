package com.xluo.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
import com.xluo.dao.PermissionDao;
import com.xluo.frame.panel.AllUserPanel;
import com.xluo.frame.panel.AuthorPanel;
import com.xluo.frame.panel.RentBookPanel;
import com.xluo.frame.panel.UserInfoPanel;
import com.xluo.frame.render.ListCellRender;
import com.xluo.po.Book;
import com.xluo.po.Permission;
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
	private JPanel userInfoPanel;
	private JPanel allUserPanel;
	private JPanel rentPanel;

	private RentBookPanel rentOrReturnBookPanel;

	private BookDao bookDao = new BookDao();
	private BookService bookService = new BookService();

	private PermissionDao permissionDao = new PermissionDao();
	private User user;

	public IndexFrame(User user) throws SQLException {
		this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 452);
		setIconImage(ImageUtil.loadImage(Constant.LOGO));
		contentPane = new JPanel();
		// contentPane.setIcon(ImageUtil.loadImageIcon(Constant.LIBRARY));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 731, 21);
		contentPane.add(menuBar);
		createMenu(user, menuBar);

		tablePanel = new JPanel();
		tablePanel.setBounds(10, 25, 710, 360);
		tablePanel.setVisible(false);
		contentPane.add(tablePanel);
		tablePanel.setLayout(null);
		tablePanel.setOpaque(false);

		bookTypePanel = new JPanel();
		bookTypePanel.setBounds(0, 21, 163, 369);
		contentPane.add(bookTypePanel);
		bookTypePanel.setOpaque(false);
		bookTypePanel.setLayout(new BorderLayout(0, 0));

		bookDetailScrollPane = new JScrollPane();
		bookDetailScrollPane.setVisible(false);
		bookDetailScrollPane.setBounds(168, 21, 560, 369);
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

		allUserPanel = new JPanel();
		allUserPanel.setBounds(0, 21, 731, 364);
		allUserPanel.setVisible(false);
		contentPane.add(allUserPanel);
		allUserPanel.setLayout(new BorderLayout());

		userInfoPanel = new JPanel();
		userInfoPanel.setBounds(0, 21, 731, 369);
		userInfoPanel.setVisible(false);
		contentPane.add(userInfoPanel);
		userInfoPanel.setLayout(new BorderLayout());

		rentPanel = new JPanel();
		rentPanel.setBounds(0, 21, 731, 369);
		rentPanel.setVisible(false);
		contentPane.add(rentPanel);
		rentPanel.setLayout(new BorderLayout());

		setResizable(false);

	}

	private void createMenu(User user, JMenuBar menuBar) throws SQLException {
		List<Permission> permissions = permissionDao.getLoginPermission(user);
		if (permissions != null) {
			for (Permission permission : permissions) {
				JMenu mn = new JMenu(permission.getPermissionName());
				mn.setIcon(ImageUtil.loadImageIcon(permission.getIcon()));
				mn.addSeparator();
				menuBar.add(mn);

				List<Permission> childPermissions = permissionDao.getChildPermission(permission.getId());
				if (childPermissions != null) {
					for (Permission cp : childPermissions) {
						JMenuItem mntm = new JMenuItem(cp.getPermissionName());
						mntm.addActionListener(new IndexFrameItemListener());
						mntm.setIcon(ImageUtil.loadImageIcon(cp.getIcon()));
						mn.add(mntm);
					}
				}
			}
		}
	}

	private class IndexFrameListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			String label = ((JList<?>) e.getSource()).getSelectedValue().toString();
			createBookDetailBox(getBookData(label));
		}

		private List<Book> getBookData(String type) {
			try {
				return bookDao.selectList(new String[] { "type" }, new Object[] { type },
						new String[] { "img", "bookname", "id" });
			} catch (SQLException e1) {
				e1.printStackTrace();
				return null;
			}
		}

	}

	private void createBookDetailBox(List<Book> books) {
		bookDetailScrollPane.setVisible(true);
		bookDetailPanel = new JPanel();
		bookDetailPanel.setLayout(null);
		bookDetailPanel.setPreferredSize(new Dimension(552, (books.size() / 3 + 1) * 177));
		bookDetailPanel.removeAll();
		for (int i = 0, j = -1; i < books.size(); i++) {
			JPanel bookBoxPanel = new JPanel();
			if (i % 3 == 0)
				j++;
			bookBoxPanel.setBounds((i % 3) * 183, j * 177, 183, 177);
			bookDetailPanel.add(bookBoxPanel);
			bookBoxPanel.addMouseListener(new BookBoxListener());
			bookBoxPanel.setLayout(null);

			JLabel lblBookImage = new JLabel("bookImage");
			lblBookImage.setBounds(10, 5, 163, 136);
			lblBookImage.setIcon(ImageUtil.loadImageIcon(Constant.LIBRARY));
			bookBoxPanel.add(lblBookImage);

			JLabel lblBookName = new JLabel(books.get(i).getBookname());
			lblBookName.setBounds(49, 145, 90, 15);
			bookBoxPanel.add(lblBookName);

			JLabel lblBookId = new JLabel(books.get(i).getId());
			lblBookId.setBounds(49, 145, 90, 15);
			lblBookId.setVisible(false);
			bookBoxPanel.add(lblBookId);
		}
		bookDetailScrollPane.setViewportView(bookDetailPanel);
		bookDetailPanel.setBounds(0, 0, 552, (books.size() / 3 + 1) * 177);
		contentPane.add(bookDetailScrollPane);
	}

	private class BookBoxListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			JPanel bookBox = (JPanel) e.getSource();
			JLabel bookIdLabel = (JLabel) bookBox.getComponent(2);
			try {
				Book book = bookDao.selectOne(new String[] { "id" }, new String[] { bookIdLabel.getText() }, null);
				new BookDetailFrame(book).setVisible(true);
				bookDao.insertUserScanBook(user.getId(), book.getId());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	private class IndexFrameItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String label = e.getActionCommand();
				if ("newest book".equals(label)) {
					String[] Names = { "bookname", "simpleInfo", "author", "account" };
					List<Book> books = bookDao.selectListOrder(null, null, Names, new String[] { "publishTime" }, true);
					CreateNewBookTable(books);
				} else if ("hot book".equals(label)) {
					createHotBookTable();
				} else if ("other...".equals(label)) {
					createBookTypeList();
				} else if ("author".equals(label)) {
					createAuthorPanel();
				} else if ("swap user".equals(label)) {
					new LoginFrame().setVisible(true);
					dispose();
				} else if ("exit".equals(label)) {
					System.exit(NORMAL);
				} else if ("scaned".equals(label)) {
					List<Book> books = bookDao.selectScanBook(user);
					CreateNewBookTable(books);
				} else if ("rented".equals(label)) {
					List<Book> books = bookDao.selectRentBook(user);
					CreateNewBookTable(books);
				} else if ("update-info".equals(label)) {
					createUserInfoPanel();
				} else if ("all user".equals(label)) {
					createAllUserPanel();
				} else if ("insert-rent".equals(label)) {
					if (rentOrReturnBookPanel == null) {
						createRentOrReturnPanel("rent", new String[] { "cancel", "rent" });
					} else {
						rentPanel.setVisible(true);
						rentPanel.setOpaque(false);
						authorPanel.setVisible(false);
						allUserPanel.setVisible(false);
						bookDetailScrollPane.setVisible(false);
						bookTypePanel.setVisible(false);
						tablePanel.setVisible(false);
						userInfoPanel.setVisible(false);
						rentOrReturnBookPanel.getLblRent().setText("rent");
						rentOrReturnBookPanel.getBtnSubmit().setText("rent");
					}
				} else if ("insert-return".equals(label)) {
					if (rentOrReturnBookPanel == null) {
						createRentOrReturnPanel("return", new String[] { "cancel", "return" });
					} else {
						rentPanel.setVisible(true);
						rentPanel.setOpaque(false);
						authorPanel.setVisible(false);
						allUserPanel.setVisible(false);
						bookDetailScrollPane.setVisible(false);
						bookTypePanel.setVisible(false);
						tablePanel.setVisible(false);
						userInfoPanel.setVisible(false);
						rentOrReturnBookPanel.getLblRent().setText("return");
						rentOrReturnBookPanel.getBtnSubmit().setText("return");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private void createAuthorPanel() {
			authorPanel.setVisible(true);
			rentPanel.setVisible(false);
			allUserPanel.setVisible(false);
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			tablePanel.setVisible(false);
			userInfoPanel.setVisible(false);
			authorPanel.add(new AuthorPanel(), BorderLayout.CENTER);
		}

		private void createRentOrReturnPanel(String title, String[] buttonTexts) throws SQLException {
			rentPanel.setVisible(true);
			rentPanel.setOpaque(false);
			authorPanel.setVisible(false);
			allUserPanel.setVisible(false);
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			tablePanel.setVisible(false);
			userInfoPanel.setVisible(false);
			rentOrReturnBookPanel = new RentBookPanel(title, buttonTexts);
			rentPanel.add(rentOrReturnBookPanel, BorderLayout.CENTER);
		}

		private void createUserInfoPanel() {
			userInfoPanel.setVisible(true);
			rentPanel.setVisible(false);
			allUserPanel.setVisible(false);
			authorPanel.setVisible(false);
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			tablePanel.setVisible(false);
			userInfoPanel.add(new UserInfoPanel(), BorderLayout.CENTER);
		}

		private void createBookTypeList() throws SQLException {
			bookTypePanel.removeAll();
			bookTypePanel.setVisible(true);
			rentPanel.setVisible(false);
			allUserPanel.setVisible(false);
			userInfoPanel.setVisible(false);
			bookDetailScrollPane.setVisible(false);
			authorPanel.setVisible(false);
			tablePanel.setVisible(false);
			bookTypePanel.setBackground(Color.WHITE);
			bookTypePanel.setOpaque(true);
			JList<String> bookTypeList = new JList<String>();
			bookTypeList.setCellRenderer(new ListCellRender());
			bookTypeList.addListSelectionListener(new IndexFrameListListener());
			bookTypeList.setFont(new Font("宋体", Font.PLAIN, 14));
			List<Book> books = bookDao.selectListOrder(null, null, new String[] { "type" }, new String[] { "type" },
					false);
			bookTypeList.setListData(bookService.changeTypeInVector(books));
			bookTypeList.setBounds(5, 0, 163, 364);
			bookTypePanel.add(bookTypeList, BorderLayout.CENTER);
			contentPane.add(bookTypePanel);
		}

		private void createHotBookTable() {
			tablePanel.removeAll();
			tablePanel.setVisible(true);
			rentPanel.setVisible(false);
			allUserPanel.setVisible(false);
			authorPanel.setVisible(false);
			userInfoPanel.setVisible(false);
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			String[] Names = { "bookname", "simpleInfo", "author", "account" };
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

		private void CreateNewBookTable(List<Book> books) throws SQLException {
			tablePanel.setVisible(true);
			tablePanel.removeAll();
			allUserPanel.setVisible(false);
			rentPanel.setVisible(false);
			authorPanel.setVisible(false);
			userInfoPanel.setVisible(false);
			bookDetailScrollPane.setVisible(false);
			bookTypePanel.setVisible(false);
			String[] Names = { "bookname", "simpleInfo", "author", "account" };
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

	public void createAllUserPanel() throws SQLException {
		allUserPanel.setVisible(true);
		allUserPanel.removeAll();
		rentPanel.setVisible(false);
		authorPanel.setVisible(false);
		bookDetailScrollPane.setVisible(false);
		bookTypePanel.setVisible(false);
		tablePanel.setVisible(false);
		userInfoPanel.setVisible(false);
		allUserPanel.add(new AllUserPanel(), BorderLayout.CENTER);
	}
}

package com.xluo.frame.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.xluo.dao.BookDao;
import com.xluo.dao.UserDao;
import com.xluo.frame.label.LineLabel;
import com.xluo.po.Book;
import com.xluo.po.User;
import com.xluo.service.BookService;
import com.xluo.service.UserService;

public class RentBookPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblLineLabel;
	private LineLabel lineLabel;
	private JLabel lblRent;
	private JButton btnCancel;
	private JButton btnSubmit;
	private JComboBox<String> bookComboBox;
	private JComboBox<String> userComboBox;
	private UserDao userDao = new UserDao();
	private UserService userService = new UserService();

	private BookDao bookDao = new BookDao();
	private BookService bookService = new BookService();

	private Map<String, String> userResult;
	private Map<String, String> bookResult;

	public RentBookPanel(String title, String[] buttonTexts) throws SQLException {
		setBounds(10, 25, 710, 369);
		setOpaque(false);
		setLayout(null);

		JPanel rentPanel = new JPanel();
		rentPanel.setBackground(Color.WHITE);
		rentPanel.setBounds(123, 0, 464, 369);
		add(rentPanel);
		rentPanel.setLayout(null);

		lblRent = new JLabel(title);
		lblRent.setFont(new Font("宋体", Font.PLAIN, 30));
		lblRent.setBounds(162, 10, 140, 35);
		rentPanel.add(lblRent);

		lblLineLabel = new LineLabel("lineLabel");
		lblLineLabel.setBounds(0, 66, 464, 15);
		rentPanel.add(lblLineLabel);

		lineLabel = new LineLabel("lineLabel");
		lineLabel.setBounds(0, 315, 464, 15);
		rentPanel.add(lineLabel);

		JLabel lblUser = new JLabel("user: ");
		lblUser.setFont(new Font("宋体", Font.PLAIN, 20));
		lblUser.setBounds(43, 91, 97, 24);
		rentPanel.add(lblUser);

		JLabel lblBook = new JLabel("book: ");
		lblBook.setFont(new Font("宋体", Font.PLAIN, 20));
		lblBook.setBounds(43, 125, 97, 24);
		rentPanel.add(lblBook);

		userComboBox = new JComboBox<String>();
		List<User> users = userDao.selectListOrder(null, null, new String[] { "username", "id", "email" },
				new String[] { "username" }, false);
		userResult = userService.changeToMap(users);
		userComboBox.setModel(new DefaultComboBoxModel<String>(userResult.keySet().toArray(new String[] {})));
		userComboBox.setBounds(150, 95, 140, 21);
		rentPanel.add(userComboBox);

		bookComboBox = new JComboBox<String>();
		List<Book> books = bookDao.selectListOrder(null, null, new String[] { "bookname", "id", "author" },
				new String[] { "bookname" }, false);
		bookResult = bookService.changeToMap(books);
		bookComboBox.setModel(new DefaultComboBoxModel<String>(bookResult.keySet().toArray(new String[] {})));
		bookComboBox.setBounds(150, 129, 140, 21);
		rentPanel.add(bookComboBox);

		btnCancel = new JButton(buttonTexts[0]);
		btnCancel.setBounds(150, 262, 93, 23);
		rentPanel.add(btnCancel);
		btnCancel.addActionListener(new ButtonListener());

		btnSubmit = new JButton(buttonTexts[1]);
		btnSubmit.setBounds(293, 262, 93, 23);
		rentPanel.add(btnSubmit);
		btnSubmit.addActionListener(new ButtonListener());

	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String label = e.getActionCommand();
			if ("cancel".equals(label)) {
				resetComboBox();
			} else if ("rent".equals(label)) {
				try {
					String userId = userResult.get(userComboBox.getSelectedItem());
					String bookId = bookResult.get(bookComboBox.getSelectedItem());
					if (userId == null || bookId == null) {
						JOptionPane.showMessageDialog(null, "please select one target...", "sweet-info",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (bookDao.isRent(userId, bookId)) {
							JOptionPane.showMessageDialog(null, "he/she had ever rent this book...", "sweet-info",
									JOptionPane.WARNING_MESSAGE);
							resetComboBox();
							return;
						}
						if (bookDao.insertUserBook(userId, bookId)) {
							JOptionPane.showMessageDialog(null, "operate success...", "sweet-info",
									JOptionPane.WARNING_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "operate fail...", "sweet-info",
									JOptionPane.WARNING_MESSAGE);
						}
						resetComboBox();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if ("return".equals(label)) {
				try {
					String userId = userResult.get(userComboBox.getSelectedItem());
					String bookId = bookResult.get(bookComboBox.getSelectedItem());
					if (userId == null || bookId == null) {
						JOptionPane.showMessageDialog(null, "please select one target...", "sweet-info",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (!bookDao.isRent(userId, bookId)) {
							JOptionPane.showMessageDialog(null, "he/she had never rent this book...", "sweet-info",
									JOptionPane.WARNING_MESSAGE);
							resetComboBox();
							return;
						}
						if (bookDao.updateUserBook(userId, bookId)) {
							JOptionPane.showMessageDialog(null, "operate success...", "sweet-info",
									JOptionPane.WARNING_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "operate fail...", "sweet-info",
									JOptionPane.WARNING_MESSAGE);
						}
						resetComboBox();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		}

	}

	private void resetComboBox() {
		userComboBox.setSelectedIndex(0);
		bookComboBox.setSelectedIndex(0);
	}

	public JLabel getLblRent() {
		return lblRent;
	}

	public void setLblRent(JLabel lblRent) {
		this.lblRent = lblRent;
	}

	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	public void setBtnSubmit(JButton btnSubmit) {
		this.btnSubmit = btnSubmit;
	}
	
}

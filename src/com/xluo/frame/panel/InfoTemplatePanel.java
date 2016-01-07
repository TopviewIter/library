package com.xluo.frame.panel;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.xluo.frame.label.LineLabel;

public class InfoTemplatePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JLabel lblTitle;
	private JLabel[] labels;
	private JTextField[] texts;
	private JLabel lblLineLabel;
	private LineLabel lineLabel;

	public InfoTemplatePanel(String title, String[] labelTexts) {
		
		setBounds(153, 0, 425, 369);
		setLayout(null);
		
		lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("宋体", Font.PLAIN, 30));
		lblTitle.setBounds(130, 29, 165, 31);
		add(lblTitle);
		
		labels = new JLabel[labelTexts.length];
		texts = new JTextField[labelTexts.length];
		for(int i = 0; i < labels.length; i++){
			labels[i] = new JLabel(labelTexts[i]);
			labels[i].setBounds(63, 101 + 41 * i, 120, 15);
			
			texts[i] = new JTextField();
			texts[i].setBounds(193, 101 + 41 * i, 173, 21);
			texts[i].setColumns(10);
			
			add(labels[i]);
			add(texts[i]);
		}
		
		lblLineLabel = new LineLabel("lineLabel");
		lblLineLabel.setBounds(0, 66, 425, 15);
		add(lblLineLabel);
		
		lineLabel = new LineLabel("lineLabel");
		lineLabel.setBounds(0, 315, 425, 15);
		add(lineLabel);
		
	}

	public JLabel[] getLabels() {
		return labels;
	}

	public void setLabels(JLabel[] labels) {
		this.labels = labels;
	}

	public JTextField[] getTexts() {
		return texts;
	}

	public void setTexts(JTextField[] texts) {
		this.texts = texts;
	}
	
}
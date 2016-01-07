package com.xluo.frame.label;

import java.awt.Graphics;

import javax.swing.JLabel;

public class LineLabel extends JLabel{

	private static final long serialVersionUID = 1L;

	public LineLabel(String text){
		super(text);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawLine(0, 0, this.getWidth(), 0);
	}
	
}

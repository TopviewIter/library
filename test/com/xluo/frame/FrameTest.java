package com.xluo.frame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameTest {  
  
    public static void main(String[] args) {  
        JFrame frame = new JFrame();  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setUndecorated(true);  
        frame.setBounds(500, 500, 300, 300);  
  
        JPanel pane = new JPanel() {  
			private static final long serialVersionUID = 1L;

			@Override  
            public void paint(Graphics g) {  
                super.paint(g);  
                g.setColor(Color.red);  
                g.fill3DRect(10, 10, 100, 100, true);  
            }  
        };  
  
        frame.setContentPane(pane);  
  
        frame.setVisible(true);  
    }  
}  

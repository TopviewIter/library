package com.xluo.util;

import javax.swing.JTextField;

public class SwingUtil {

	public static void clearText(JTextField... texts){
		for(int i = 0; i < texts.length; i++){
			texts[i].setText("");
		}
	}
	
}

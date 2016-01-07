package com.xluo.frame.panel;

import javax.swing.JFrame;

import org.junit.Test;

public class InfoTemplatePanelTest {

	@Test
	public void testInfoTemplatePanel() throws InterruptedException {
		
		JFrame frame = new JFrame();
		frame.add(new InfoTemplatePanel("school-info", 
				new String[]{"school: ", "departemnt: ", "class: ", "no: "}));
		frame.setVisible(true);
		Thread.sleep(50000);
	}

}

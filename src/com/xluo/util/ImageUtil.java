package com.xluo.util;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageUtil {

	public static Image loadImage(String path){
		return loadImageIcon(path).getImage();
	}
	
	public static ImageIcon loadImageIcon(String path){
		return new ImageIcon(ImageUtil.class.getResource(path));
	}
	
}

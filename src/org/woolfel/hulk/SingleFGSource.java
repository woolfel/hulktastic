package org.woolfel.hulk;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SingleFGSource implements ForegroundSource {

	private String filename;
	
	public SingleFGSource(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}
	
	public BufferedImage getBufferedImage() {
		try {
			return ImageIO.read(new java.io.File(filename));
		} catch (IOException e) {
			return null;
		}
	}

}

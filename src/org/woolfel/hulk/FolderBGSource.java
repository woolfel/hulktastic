package org.woolfel.hulk;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class FolderBGSource implements BackgroundSource {

	private String folder = null;
	private List<File> files = new ArrayList<File>();
	private Random random = new Random();
	
	public FolderBGSource(String name) {
		this.folder = name;
		this.scanFolder();
	}

	protected void scanFolder() {
		File dir = new File(this.folder);
		File[] list = dir.listFiles();
		for (File f: list) {
			if (f.isFile() && f.getName().toLowerCase().endsWith(".jpg")) {
				this.files.add(f);
			}
		}
	}
	
	public int getCount() {
		return this.files.size();
	}

	public BufferedImage getBufferedImage() {
		File f = this.files.get(this.random.nextInt(this.files.size()));
		try {
			return ImageIO.read(f);
		} catch (IOException e) {
			return null;
		}
	}

}

package org.woolfel.hulk;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class BatchTest {

	@Test
	public void test() {
		String outputdir = "./data/robots/";
		File dir = new File(outputdir);
		dir.mkdirs();
		ForegroundSource fg = new SingleFGSource("./data/foreground/robot_right.png");
		BackgroundSource bg = new FolderBGSource("./data/background");
		RandomComposite composite = new RandomComposite();
		composite.setFinalDimensions(320, 240);
		composite.setMargins(10, 10, 10, 10);
		for (int i=0; i < 500; i++) {
			BufferedImage newimg = composite.composite(fg, bg);
			String filename = outputdir + "composite" + i + ".jpg";
			try {
				ImageIO.write(newimg, "jpg", new File(filename));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				fail();
			} catch (IOException e) {
				e.printStackTrace();
				fail();
			}
		}
		assertTrue(true);
	}

}

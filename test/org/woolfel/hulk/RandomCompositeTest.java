package org.woolfel.hulk;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class RandomCompositeTest {

	@Test
	public void test() {
		String output = "./data/result/newimage.jpg";
		ForegroundSource fg = new SingleFGSource("./data/foreground/robot_front.png");
		BackgroundSource bg = new FolderBGSource("./data/background");
		RandomComposite composite = new RandomComposite();
		composite.setFinalDimensions(320, 240);
		composite.setMargins(10, 10, 10, 10);
		BufferedImage newimg = composite.composite(fg, bg);
		try {
			ImageIO.write(newimg, "jpg", new FileOutputStream(output));
			assertTrue(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

}

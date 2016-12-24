package org.woolfel.hulk;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Test;

public class ScaleTest {

	@Test
	public void test() {
		ScaleForegroundComposite composite = new ScaleForegroundComposite(0.65);
		composite.setFinalDimensions(320, 240);
		composite.setMargins(5, 5, 5, 5);
		SingleFGSource source = new SingleFGSource("./data/foreground/robot_front.png");
		FolderBGSource bground = new FolderBGSource("./data/background");
		
		String output = "./data/result/newimage.jpg";
		BufferedImage newimg = composite.composite(source, bground);
		try {
			ImageIO.write(newimg, "jpg", new File(output));
			assertTrue(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

}

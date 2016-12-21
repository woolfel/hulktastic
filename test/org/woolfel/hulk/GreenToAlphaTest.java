package org.woolfel.hulk;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GreenToAlphaTest {

	public GreenToAlphaTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String filename = "./data/foreground/abcd-greenscreen.png";
		String alphaname = "./data/foreground/abcd-alpha.png";
		try {
			BufferedImage image = ImageIO.read(new File(filename));
			int width = 320;
			int height = 240;
			int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
			Color alpha = new Color(255,255,255,0);
			System.out.println("alpha is: " + alpha.getRGB());
			for (int i=0; i < pixels.length; i++) {
				Color color = new Color(pixels[i]);				
				
				if (color.getGreen() >= 200 && color.getRed() <=15 && color.getBlue() <= 15) {
					pixels[i] = alpha.getRGB();
				}
			}
			image.setRGB(0, 0, width, height, pixels, 0, width);

			ImageIO.write(image, "png", new File(alphaname));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

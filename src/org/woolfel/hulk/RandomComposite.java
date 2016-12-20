package org.woolfel.hulk;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class RandomComposite implements Composite {

	private int imageWidth = 0;
	private int imageHeight = 0;
	private int topMargin = 0;
	private int bottomMargin = 0;
	private int leftMargin = 0;
	private int rightMargin = 0;
	private Random random = new Random();
	private boolean crop = false;
	
	public RandomComposite() {
	}

	public void setFinalDimensions(int width, int height) {
		this.crop = true;
		this.imageWidth = width;
		this.imageHeight = height;
	}

	public void setMargins(int top, int bottom, int left, int right) {
		this.topMargin = top;
		this.bottomMargin = bottom;
		this.leftMargin = left;
		this.rightMargin = right;
	}
	
	protected boolean backgroundLarger(int width, int height) {
		return width > this.imageWidth || height > this.imageHeight;
	}

	public BufferedImage composite(ForegroundSource foreground, BackgroundSource background) {
		BufferedImage fg = foreground.getBufferedImage();
		BufferedImage bg = background.getBufferedImage();
		int x = this.calculateStartX(bg.getWidth() - (fg.getWidth() + this.rightMargin + this.leftMargin));
		int y = this.calculateStartY(bg.getHeight() - (fg.getHeight() + this.bottomMargin + this.topMargin));
		
		System.out.println("fg start x/y: " + x + ", " + y);
		bg = this.drawForeground(bg, fg, x, y);
		if (this.crop && backgroundLarger(bg.getWidth(),bg.getHeight())) {
			// crop the image using the center of the foreground
			int fgXcenter = x + (fg.getWidth()/2);
			int fgYcenter = y + (fg.getHeight()/2);
			// the starting x,y
			int cropX = x - random.nextInt(this.imageWidth/3);
			int cropY = y - random.nextInt(this.imageHeight/3);
			System.out.println("crop X/Y: " + cropX + ", " + cropY);
			BufferedImage cropped = new BufferedImage(this.imageWidth, this.imageHeight, bg.getType());
			int[] croppedPixels = bg.getRGB(cropX, cropY, this.imageWidth, this.imageHeight, null, 0, this.imageWidth);
			cropped.setRGB(0, 0, this.imageWidth, this.imageHeight, croppedPixels, 0, this.imageWidth);
			return cropped;
		}
		return bg;
	}

	protected BufferedImage drawForeground(BufferedImage background, BufferedImage foreground,
			int startx, int starty) {
		BufferedImage fg = foreground;
		BufferedImage bg = background;
		int width = fg.getWidth();
		int height = fg.getHeight();
		int x = startx;
		int y = starty;
		
	    int[] bgPixels = bg.getRGB(x, y, width, height, null, 0, width);
	    int[] fgPixels = fg.getRGB(0, 0, width, height, null, 0, width);

	    for (int i = 0; i < fgPixels.length; i++)
	    {
	        int alpha = (fgPixels[i] >> 24) & 0xFF;
	        if (alpha == 255) {
		        bgPixels[i] = fgPixels[i];
	        }
	    }

	    bg.setRGB(x, y, width, height, bgPixels, 0, width);
		return bg;
	}
	
	protected int calculateStartX(int width) {
		return this.leftMargin + random.nextInt(width);
	}
	
	protected int calculateStartY(int height) {
		return this.topMargin + random.nextInt(height);
	}
}

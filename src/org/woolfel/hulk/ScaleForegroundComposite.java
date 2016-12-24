package org.woolfel.hulk;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ScaleForegroundComposite implements Composite {

	private int imageWidth = 0;
	private int imageHeight = 0;
	private int topMargin = 0;
	private int bottomMargin = 0;
	private int leftMargin = 0;
	private int rightMargin = 0;
	private Random random = new Random();
	private boolean crop = false;
	private double scale = 1.0;
	
	public ScaleForegroundComposite() {
	}
	
	/**
	 * Create an instance with a defined amount of scaling of the
	 * foreground image. If 1.0 is passed, the image will remain
	 * the original size. If 0.80 is passed, the width and height
	 * will be multiplied by 0.80 to get the new size.
	 * @param value
	 */
	public ScaleForegroundComposite(double value) {
		this.scale = value;
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
		// scale the foreground image
		fg = this.scaleForeground(fg, fg.getType());
		BufferedImage bg = background.getBufferedImage();
		int x = this.calculateStartX(bg.getWidth() - (fg.getWidth() + this.rightMargin + this.leftMargin));
		int y = this.calculateStartY(bg.getHeight() - (fg.getHeight() + this.bottomMargin + this.topMargin));
		
		System.out.println("fg start x/y: " + x + ", " + y);
		bg = this.drawForeground(bg, fg, x, y);
		if (this.crop && backgroundLarger(bg.getWidth(),bg.getHeight())) {
			// the starting x,y
			int cropX = x - random.nextInt(this.imageWidth/3);
			int cropY = y - random.nextInt(this.imageHeight/3);
			if (cropX < 0) {
				cropX = 0;
			}
			if ((cropX + this.imageWidth) > bg.getWidth()) {
				cropX = bg.getWidth() - this.imageWidth;
			}
			if (cropY < 0) {
				cropY = 0;
			}
			if ((cropY + this.imageHeight) > bg.getHeight()) {
				cropY = bg.getHeight() - this.imageHeight;
			}
			System.out.println("crop X/Y: " + cropX + ", " + cropY);
			BufferedImage cropped = new BufferedImage(this.imageWidth, this.imageHeight, bg.getType());
			int[] croppedPixels = bg.getRGB(cropX, cropY, this.imageWidth, this.imageHeight, null, 0, this.imageWidth);
			cropped.setRGB(0, 0, this.imageWidth, this.imageHeight, croppedPixels, 0, this.imageWidth);
			return cropped;
		}
		return bg;
	}

	/**
	 * Method uses BufferedImage.getRGB() method to get a flattened
	 * array int[] of all the pixels. The method checks the alpha
	 * value of each pixel to determine if the pixel should be drawn
	 * over the background. A value of 255 means the pixel is
	 * transparent and shouldn't be drawn. A value less than 255
	 * means it should be drawn.
	 * @param background
	 * @param foreground
	 * @param startx
	 * @param starty
	 * @return
	 */
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
	
	/**
	 * Dumb simple way of calculating a random starting point for
	 * the foreground. The X is the upper left corner of where it
	 * should start drawing
	 * @param width
	 * @return
	 */
	protected int calculateStartX(int width) {
		return this.leftMargin + random.nextInt(width);
	}
	
	/**
	 * Dumb simple way of calculating a random starting point for
	 * the foreground. The Y is the upper left corner of where it
	 * should start drawing
	 * @param height
	 * @return
	 */
	protected int calculateStartY(int height) {
		return this.topMargin + random.nextInt(height);
	}
	
	protected BufferedImage scaleForeground(BufferedImage original, int type) {
		int newWidth = (int)(original.getWidth() * this.scale);
		int newHeight = (int)(original.getHeight() * this.scale);
		Image img = original.getScaledInstance(newWidth, newHeight, type);
		BufferedImage smaller = new BufferedImage(newWidth, newHeight, type);
		smaller.getGraphics().drawImage(img, 0, 0, null);
		return smaller;
	}
}

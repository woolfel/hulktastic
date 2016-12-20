package org.woolfel.hulk;

import java.awt.image.BufferedImage;

public interface BackgroundSource {
	/**
	 * the number of background images in the given file or folder. If the source is a folder
	 * containing multiple images, count is the number of images found.
	 * @return
	 */
	public int getCount();
	/**
	 * Returns the data of an image as BufferedImage. If there's multiple background images,
	 * the method will randomly pick one.
	 * @return
	 */
	public BufferedImage getBufferedImage();
}

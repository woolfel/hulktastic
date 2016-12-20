package org.woolfel.hulk;

import java.awt.image.BufferedImage;

public interface Composite {
	public void setFinalDimensions(int width, int height);
	public void setMargins(int top, int bottom, int left, int right);
	public BufferedImage composite(ForegroundSource foreground, BackgroundSource background);
}

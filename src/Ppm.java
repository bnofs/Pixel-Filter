import java.awt.Color;

/**
 * 
 * @author bnofs
 *
 */
public class Ppm {

	private Color[][] data;

	/**
	 * Consists of a 2D array of color values that can be written to ppm file
	 * 
	 * @param data
	 */
	public Ppm(Color[][] data) {
		this.data = data;
	}

	/**
	 * Returns a reference to the data
	 * 
	 * @return Color[][]
	 */
	public Color[][] getData() {
		return data;
	}

	/**
	 * Divides each of the red, green, and blue components in half
	 * 
	 * @return Ppm
	 */
	public Ppm darken() {
		Color[][] newData = new Color[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int redVal = data[i][j].getRed();
				int greenVal = data[i][j].getGreen();
				int blueVal = data[i][j].getBlue();
				newData[i][j] = new Color(redVal / 2, greenVal / 2, blueVal / 2);
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Doubles each component, up to a max of 255. So, 123 would become 246, but
	 * 200 would become 255.
	 * 
	 * @return Ppm
	 */
	public Ppm lighten() {
		Color[][] newData = new Color[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int redVal = data[i][j].getRed() * 2;
				if (redVal > 255)
					redVal = 255;
				int greenVal = data[i][j].getGreen() * 2;
				if (greenVal > 255)
					greenVal = 255;
				int blueVal = data[i][j].getBlue() * 2;
				if (blueVal > 255)
					blueVal = 255;

				newData[i][j] = new Color(redVal, greenVal, blueVal);
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Sets each component to be the average of the red, green, and blue
	 * components.
	 * 
	 * @return Ppm
	 */
	public Ppm greyscale() {
		Color[][] newData = new Color[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int redVal = data[i][j].getRed();
				int greenVal = data[i][j].getGreen();
				int blueVal = data[i][j].getBlue();
				int average = (redVal + greenVal + blueVal) / 3;

				newData[i][j] = new Color(average, average, average);
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Negates each component, so 0 becomes 255, 1 becomes 254, and so on
	 * 
	 * @return Ppm
	 */
	public Ppm invert() {
		Color[][] newData = new Color[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int redVal = 255 - data[i][j].getRed();
				int greenVal = 255 - data[i][j].getGreen();
				int blueVal = 255 - data[i][j].getBlue();

				newData[i][j] = new Color(redVal, greenVal, blueVal);
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Flips the image left to right
	 * 
	 * @return Ppm
	 */
	public Ppm flipHorizontal() {
		int height = data.length;
		int width = data[0].length;
		Color[][] newData = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newData[i][width - 1 - j] = data[i][j];
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Flips the image top to bottom
	 * 
	 * @return Ppm
	 */
	public Ppm flipVertical() {
		int height = data.length;
		int width = data[0].length;
		Color[][] newData = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newData[height - 1 - i][j] = data[i][j];
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Adjusts the image by adding and/or subtracting random values to each
	 * component
	 * 
	 * @return Ppm
	 */
	public Ppm addNoise() {
		Color[][] newData = new Color[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int redVal = data[i][j].getRed() + (int) (Math.random() * 75);
				if (redVal > 255)
					redVal = 255;
				int greenVal = data[i][j].getGreen()
						+ (int) (Math.random() * 75);
				if (greenVal > 255)
					greenVal = 255;
				int blueVal = data[i][j].getBlue() + (int) (Math.random() * 75);
				if (blueVal > 255)
					blueVal = 255;
				newData[i][j] = new Color(redVal, greenVal, blueVal);
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Rounds each component so the Ppm will consist of a less number of colors
	 * 
	 * @return Ppm
	 */
	public Ppm posterize() {
		Color[][] newData = new Color[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int redVal = data[i][j].getRed() / 50 * 50;
				int greenVal = data[i][j].getGreen() / 50 * 50;
				int blueVal = data[i][j].getBlue() / 50 * 50;
				newData[i][j] = new Color(redVal, greenVal, blueVal);
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Creates a new image where each pixel's components is the average of
	 * itself and all of its neighboring pixels. Note that a pixel in the middle
	 * of the image has 8 neighbors. Pixels on the edge or in the corners have
	 * fewer neighbors
	 * 
	 * @return Ppm
	 */
	public Ppm soften() {
		int height = data.length-1;
		int width = data[0].length-1;
		Color[][] newData = new Color[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int count = 1;
				int redVal = (data[i][j].getRed());
				int greenVal = data[i][j].getGreen();
				int blueVal = data[i][j].getBlue();
				if (i < height) {
					redVal += data[i + 1][j].getRed();
					greenVal += data[i + 1][j].getGreen();
					blueVal += data[i + 1][j].getBlue();
					count++;
				}
				if (j < width) {
					redVal += data[i][j + 1].getRed();
					greenVal += data[i][j + 1].getGreen();
					blueVal += data[i][j + 1].getBlue();
					count++;
				}
				if (i > 0) {
					redVal += data[i - 1][j].getRed();
					greenVal += data[i - 1][j].getGreen();
					blueVal += data[i - 1][j].getBlue();
					count++;
				}
				if (j > 0) {
					redVal += data[i][j - 1].getRed();
					greenVal += data[i][j - 1].getGreen();
					blueVal += data[i][j - 1].getBlue();
					count++;
				}
				if (i < height && j < width) {
					redVal += data[i + 1][j + 1].getRed();
					greenVal += data[i + 1][j + 1].getGreen();
					blueVal += data[i + 1][j + 1].getBlue();
					count++;
				}
				if (i > 0 && j > 0) {
					redVal += data[i - 1][j - 1].getRed();
					greenVal += data[i - 1][j - 1].getGreen();
					blueVal += data[i - 1][j - 1].getBlue();
					count++;
				}
				if (i < height && j > 0) {
					redVal += data[i + 1][j - 1].getRed();
					greenVal += data[i + 1][j - 1].getGreen();
					blueVal += data[i + 1][j - 1].getBlue();
					count++;
				}
				if (i > 0 && j < width) {
					redVal += data[i - 1][j + 1].getRed();
					greenVal += data[i - 1][j + 1].getGreen();
					blueVal += data[i - 1][j + 1].getBlue();
					count++;
				}
				redVal /= count;
				greenVal /= count;
				blueVal /= count;
				newData[i][j] = new Color(redVal, greenVal, blueVal);
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Returns a copy of the original image, but with a frame wrapped around it
	 * with the given width and color. So, if you have a 500 by 400 image, and
	 * you wrap it with a frame whose width is 100, the new image will measure
	 * 700 by 600
	 * 
	 * @param frameWidth
	 * @param Color
	 * @return Ppm
	 */
	public Ppm addFrame(int width, Color c) {
		int newHeight = data.length + (width * 2);
		int newWidth = data[0].length + (width * 2);
		Color[][] newData = new Color[newHeight + 1][newWidth + 1];

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				if (j >= width && j < newWidth - width
						&& i >= width && i < newHeight - width)
					newData[i][j] = data[i - width][j - width];
				else
					newData[i][j] = c;
			}
		}
		return new Ppm(newData);
	}

	/**
	 * Flips the red and blue components for each color
	 * @return Ppm
	 */
	public Ppm surprise() {

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int redVal = data[i][j].getRed();
				int greenVal = data[i][j].getGreen();
				int blueVal = data[i][j].getBlue();
				data[i][j] = new Color(blueVal, greenVal, redVal);
			}
		}
		return new Ppm(data);
	}

}

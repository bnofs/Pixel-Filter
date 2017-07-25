import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * All filters work. Surprise filter simply swaps red and blue values for each
 * color
 * 
 * @author bnofs
 *
 */
public class PpmDriver {

	public static void main(String[] args) throws FileNotFoundException {

		Color[][] data;

		// Gets input from console
		Scanner in = new Scanner(System.in);
		System.out.println("What is the name of the PPM file?");
		String inFile = in.next();
		in.close();

		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new File(inFile));
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Program terminated.");
			System.exit(0);
		}

		// Finds length of file and initializes 2D array
		int width = 0;
		int height = 0;
		while (width == 0) {
			try {
				width = fileScanner.nextInt();
			} catch (InputMismatchException e) {
				fileScanner.nextLine();
			}
		}

		height = fileScanner.nextInt();
		data = new Color[height][width];
		fileScanner.nextInt();

		// Assigns color values to 2D array
		try {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int red = fileScanner.nextInt();
					int green = fileScanner.nextInt();
					int blue = fileScanner.nextInt();
					data[i][j] = new Color(red, green, blue);
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("Error reading file. Program terminated.");
		}

		fileScanner.close();

		// Create ppm with data
		Ppm ppm = new Ppm(data);

		// Writes 11 files for each filter
		String fileName = inFile.substring(0, inFile.length() - 4);
		writeOutFile(ppm.darken(), height, width, fileName + "_darken.ppm");
		writeOutFile(ppm.lighten(), height, width, fileName + "_lighten.ppm");
		writeOutFile(ppm.greyscale(), height, width, fileName
				+ "_greyscale.ppm");
		writeOutFile(ppm.invert(), height, width, fileName + "_invert.ppm");
		writeOutFile(ppm.flipHorizontal(), height, width, fileName
				+ "_flipHorizontal.ppm");
		writeOutFile(ppm.flipVertical(), height, width, fileName
				+ "_flipVertical.ppm");
		writeOutFile(ppm.addNoise(), height, width, fileName + "_addNoise.ppm");
		writeOutFile(ppm.posterize(), height, width, fileName
				+ "_posterize.ppm");
		writeOutFile(ppm.soften(), height, width, fileName + "_soften.ppm");
		writeOutFile(ppm.addFrame(100, Color.BLACK), height + 200, width + 200,
				fileName + "_addFrame.ppm");
		writeOutFile(ppm.surprise(), height, width, fileName + "_surprise.ppm");
		
		System.out.println("11 new files successfully written to folder.");
	}

	// Writes a file given a Ppm, height, width, and name
	public static void writeOutFile(Ppm ppm, int height, int width, String name)
			throws FileNotFoundException {
		PrintWriter PW = new PrintWriter(new File(name));
		Color[][] data = ppm.getData();
		PW.println("P3");
		PW.println(width + " " + height);
		PW.println("255");
		PW.println("# " + name);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				PW.print(data[i][j].getRed() + "\t");
				PW.print(data[i][j].getGreen() + "\t");
				PW.println(data[i][j].getBlue());
			}
		}
		PW.close();
		return;
	}

}

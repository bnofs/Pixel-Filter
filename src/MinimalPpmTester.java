import java.awt.Color;

public class MinimalPpmTester {

	public static void main(String[] args) {

		Color[][] colors = new Color[3][4];
		for (int i = 0; i < colors.length; i++)
			for (int j = 0; j < colors[0].length; j++)
				colors[i][j] = Color.BLACK;

		Ppm ppm = new Ppm(colors);
		colors = ppm.getData();

		Ppm result;

		result = ppm.darken();
		result = ppm.lighten();
		result = ppm.greyscale();
		result = ppm.flipVertical();
		result = ppm.flipHorizontal();
		result = ppm.posterize();
		result = ppm.soften();
		result = ppm.addFrame(0, Color.BLUE);
		result = ppm.addNoise();
		result = ppm.surprise();
		result = ppm.invert();

		System.out.println("DONE");
		
	}

}

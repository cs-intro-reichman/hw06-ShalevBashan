// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		Color[][] tinypic = read("cake.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// imageOut = flippedHorizontally(tinypic);
		// imageOut = flippedVertically(tinypic);
		// imageOut = grayScaled(tinypic);
		imageOut = scaled(tinypic, 400, 600);
		System.out.println();
		print(imageOut);
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}


	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) { 
			for (int j = 0; j < numCols; j++) {
				image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt());
			}
		}
		return image;
	}

	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	private static void print(Color[][] image) {
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				print(image[i][j]);
			}
		System.out.println ();
		}
	}
	
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] flipped = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				flipped[i][j] = image[i][image[i].length - j - 1];
			}
		}
		return flipped;
	}
	
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] flipped = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				flipped[i][j] = image[image.length - 1 - i][j];
			}
		}
		return flipped;
	}
	
	public static Color luminance(Color pixel) {
		int lumValue = (int)(0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		Color lum = new Color(lumValue, lumValue, lumValue);
		return lum;
	}
	

	public static Color[][] grayScaled(Color[][] image) {
		Color[][] gScale = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				gScale[i][j] = luminance(image[i][j]);
			}
		}
		return gScale;
	}	

	public static Color[][] scaled(Color[][] image, int width, int height) {
		int cH = image.length;
		int cW = image[0].length;
		Color[][] scaledImage = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				scaledImage[i][j] = image[i*cH/height][j*cW/width];
			}
		}
		return scaledImage;
	}
	
	public static Color blend(Color c1, Color c2, double alpha) {
		double red = alpha*c1.getRed() + (1-alpha)*c2.getRed();
		double green = alpha*c1.getGreen() + (1-alpha)*c2.getGreen();
		double blue = alpha*c1.getBlue() + (1-alpha)*c2.getBlue();
		Color blended = new Color((int) red, (int) green, (int) blue);
		return blended;
	}
	
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blended = new Color[image1.length][image1[0].length];
		for (int i = 0; i < blended.length; i++) {
			for (int j = 0; j < blended[i].length; j++) {
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blended;
	}


	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] scaledTarget = new Color[source.length][source[0].length];
		scaledTarget = scaled(target, source[0].length, source.length);
		double alpha = 0;
		for (int i = 0; i < n; i++){
			alpha = (n - i)/n;
			display(blend(source, scaledTarget, alpha));
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}


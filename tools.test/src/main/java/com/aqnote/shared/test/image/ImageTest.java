
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created by madding.lip on 4/20/17.
 */
public class ImageTest {

	public static void main(String[] args) throws IOException {
		ImageTest test = new ImageTest();
		test.testCreateImage();
	}

	public void testCreateImage() throws IOException {
		BufferedImage img1 = createImage("./1.jpg");
		BufferedImage img2 = createImage("./2.jpg");
		BufferedImage dst = mergeImage(img1, img2, false, 5);

		try {
			File outputfile = new File("dst.jpg");
			ImageIO.write(dst, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage img3 = createImage("./3.jpg");
		BufferedImage dst2 = mergeImage(dst, img3, true, 5);

		try {
			File outputfile = new File("dst2.jpg");
			ImageIO.write(dst2, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage createImage(String imageFile) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
			System.out.println("load Image error, " + e.getMessage());
			e.printStackTrace();
			;
		}
		return img;
	}

	public static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2, boolean isHorizontal, int padding)
			throws IOException {
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		int[] imageArrayOne = new int[w1 * h1];
		imageArrayOne = img1.getRGB(0, 0, w1, h1, imageArrayOne, 0, w1);

		int w2 = img2.getWidth();
		int h2 = img2.getHeight();
		int[] imageArrayTwo = new int[w2 * h2];
		imageArrayTwo = img2.getRGB(0, 0, w2, h2, imageArrayTwo, 0, w2);

		int width, height;
		BufferedImage destImage = null;
		if (isHorizontal) {
			width = w1 + padding + w2;
			height = h1 > h2 ? h1 : h2;
			destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			destImage.setRGB(0, 0, w1, h1, imageArrayOne, 0, w1);
			destImage.setRGB(w1 + padding, 0, w2, h2, imageArrayTwo, 0, w2);
		} else {
			width = w1 > w2 ? w1 : w2;
			height = h1 + padding + h2;
			destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			destImage.setRGB(0, 0, w1, h1, imageArrayOne, 0, w1);
			destImage.setRGB(0, h1 + padding, w2, h2, imageArrayTwo, 0, w2);
		}
		return destImage;
	}
}

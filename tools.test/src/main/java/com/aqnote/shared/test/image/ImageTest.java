package com.aqnote.shared.test.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aqnote.shared.test.TestMain;

/**
 * Created by madding on 4/20/17.
 */
public class ImageTest {

	public static void main(String[] args) throws IOException {
		ImageTest test = new ImageTest();
		test.testCreateImage();
	}

	public void testCreateImage() throws IOException {
		//		// size = 640 * 320 * 4B = 800kB
		//		cteateImage(640, 320);

		// size = 4096 * 4096 * 4B = 64MB
		cteateImage(4096, 4096);
	}

	private static void cteateImage(int width, int height) throws IOException {
//		String fullFilename = "/Users/madding/Downloads/fixed_size.png";
//		BufferedImage image = ImageUtil.cteateFixedSizeImage(width, height);
//
//		File f = new File(fullFilename);
//		ImageIO.write(image, "png", f);

	}
}

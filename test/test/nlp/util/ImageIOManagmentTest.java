package test.nlp.util;

import org.junit.Test;

import ims.nlp.util.ImageIOManagment;

public class ImageIOManagmentTest {

	@Test
	public void testTransImageToByte() {
		String imagePath = "D:/project_superhy/test1.jpeg";
		// String imagePath2 = "D:/project_superhy/test2.jpg";

		byte[] bytes = ImageIOManagment.transImageToByte(imagePath);
		// byte[] bytes2 = ImageIOManagment.transImageToByte(imagePath2);

		System.out.println(bytes);
		for (byte b : bytes) {
			System.out.println(b);
		}
		// System.out.println(bytes2.toString());
	}
}

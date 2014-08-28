package ims.nlp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 将附件图片转化为二进制流
 * 
 * @author superhy
 * 
 */
public class ImageIOManagment {

	/**
	 * 将图片转化为二进制流返回，传入图片在磁盘上的路径
	 * 
	 * @param imagePath
	 * @return
	 */
	public static byte[] transImageToByte(String imagePath) {

		String imageSuffix = imagePath
				.substring(imagePath.lastIndexOf('.') + 1);

		try {
			ImageIcon image = new ImageIcon(imagePath);
			BufferedImage bi = new BufferedImage(image.getImage()
					.getWidth(null), image.getImage().getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ImageIO.write(bi, imageSuffix, bos);

			byte[] bytes = bos.toByteArray();

			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("图片转化未成功，请检查图片格式");

			return null;
		}

	}
}

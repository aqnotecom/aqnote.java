package com.aqnote.shared.image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** Created by madding on 4/20/17. */
public class ImageUtil {

  public static BufferedImage cteateFixedSizeImage(int width, int height) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    // 1p = 4B
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int alpha = genRandomNum(256); //alpha
        int red = genRandomNum(256); //red
        int green = genRandomNum(256); //green
        int blue = genRandomNum(256); //blue
        int pixel = (alpha << 24) | (red << 16) | (green << 8) | blue; //pixel
        image.setRGB(x, y, pixel);
      }
    }
    return image;
  }

  public static Image getFileImage(File file) {
    Image image = null;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }

  /**
   * 构建一个预定义图像类型的BufferedImage
   *
   * @param file 图片文件的路径
   * @param scale 缩放值
   */
  public static BufferedImage getFileImageAndScale(File file, float scale) {

    Image image = BMPImageUtil.load(file); // 得到Image对象
    BufferedImage buffImg = null;
    try {
      buffImg = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 宽跟高
    int width = (int) (image.getWidth(null));
    int height = (int) (image.getHeight(null));
    // 构建一个预定义图像类型的BufferedImage
    buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // 创建Graphics2D对象，用在BufferedImage对象上绘图
    Graphics2D g2d = buffImg.createGraphics();
    // 设置图形上下文的当前颜色为白色
    g2d.setColor(Color.white);
    // 填充指定的矩形区域
    g2d.fillRect(0, 0, width, height);
    g2d.drawImage(image, 0, 0, width, height, null);
    if (scale != 1.0f) {
      // 缩放图片
      BufferedImage filteredBufImage =
          new BufferedImage(
              (int) (width * scale), (int) (height * scale), BufferedImage.TYPE_INT_RGB); // 过滤后的图像
      AffineTransform transform = new AffineTransform(); // 仿射变换对象
      transform.setToScale(scale, scale); // 设置仿射变换的比例因子
      AffineTransformOp imageOp = new AffineTransformOp(transform, null); // 创建仿射变换操作对象
      imageOp.filter(buffImg, filteredBufImage); // 过滤图像，目标图像在filteredBufImage
      buffImg = filteredBufImage;
    }
    return buffImg;
  }

  private static int genRandomNum(int maxValue) {
    return (int) (Math.random() * maxValue);
  }

  public static String getFileExtension(String filename) {
    if (filename == null) return null;
    return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
  }
}

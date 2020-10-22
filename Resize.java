/**
 * Resize.java
 *
 * resizes image to specified dimesions
 * 1.0 version
 *
 * @author Anthony Chatfield
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;

public class Resize {

    /**
     * resize - returns resize image
     * @param img BufferedImage class, image to resize
     * @param width
     * @param height
     */
    public static BufferedImage resize(BufferedImage img, int width, int height) {
      // scales image to correct size
      Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      // creates new BufferedImage variable with scaled Image
      BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      // Graphics2D g2d = resized.createGraphics();
      // g2d.drawImage(tmp, 0, 0, null);
      // g2d.dispose();
      return resized;
    }
}

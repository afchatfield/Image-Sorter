/**
 * LoadImage.java
 *
 * loads image as BuffereImage type
 * 3.0 version
 *
 * @author Anthony Chatfield
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.Toolkit;

/**
 * This class loads an Image from an external file
 */
public class LoadImage {

    //instance variable image
    public BufferedImage img;

    /**
     * constructor - loads image file
     * @param fileName file name string
     */
    public LoadImage(String fileName) {
      //try is necessary when using ImageIO to avoid IOException
       try {
           //load image by creating a file from the directory and reading it as an image
           img = ImageIO.read(new File("images/"+fileName));
       } catch (IOException e) {
       }
    }

    /**
     * constructor - loads image file
     * @param fileName the current state of the puzzle
     * @param width width to resize to
     * @param height height to resize to
     */
    public LoadImage(String fileName, int width, int height) {
       try {
           img = ImageIO.read(new File("images/"+fileName));
           img = Resize.resize(img, width, height);
       } catch (IOException e) {
       }
    }

    /**
     * constructor - loads image file
     * @param fileName
     * @param percent percent to resize image by - float
     */
    public LoadImage(String fileName, double percent) {
       try {
           img = ImageIO.read(new File("images/"+fileName));
           //calculate width and height by percentage
           int newW = (int) (img.getWidth() * percent);
           int newH = (int) (img.getHeight() * percent);
           img = Resize.resize(img, newW, newH);
       } catch (IOException e) {
       }
    }

    /**
     * accessor - returns image instance variable
     */
    public BufferedImage getImage() {
      return img;
    }

}

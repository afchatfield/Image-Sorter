/**
 * DrawImages.java
 *
 * takes in mutiple images and draws them in sorted order (by colour) on a screen
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
import java.util.*;
import java.lang.*;

public class DrawImages extends JPanel {

  //initialize instance variables
  private BufferedImage[] images; //array of images of BufferedImage type
  private int imageWidth = 100, imageHeight = 100;
  private int[][] coords; //2D array for image coordinates
  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //finds dimensions of users screen
  private int[] screenDimensions; //dimension of screen created for graphics
  private int amount; //amount of images
  private String[] files; //names of files to load
  private DimensionCalc calc;

  /**
   * constructor - sets up instance variables and calls super class
   * version with amount parameter to control amount of images drawn if wanted
   * @param amount amount of files to draw
   */
  public DrawImages(int amount){
    super();
    this.files = ImageColour.getSortedFiles();
    this.amount = amount;
    this.calc = new DimensionCalc(this.amount);
    this.imageWidth = getPictureSize();
    this.imageHeight = getPictureSize();
    this.screenDimensions = setScreenDimension();
    this.images = loadImages(amount);
    this.coords = setCoords();
  }

  /**
   * constructor - sets up instance variables and calls super class
   * different from other constructor as it initializes amount based on
   * amount of files in the "images" directory
   */
  public DrawImages(){
    super();
    this.files = ImageColour.getSortedFiles();
    this.amount = this.files.length;
    this.calc = new DimensionCalc(this.amount);
    this.imageWidth = getPictureSize();
    this.imageHeight = getPictureSize();
    this.screenDimensions = setScreenDimension();
    this.images = loadImages(this.amount);
    this.coords = setCoords();
  }

  public int getPictureSize(){
    int size;
    size = (int)screenSize.getWidth()/this.calc.x;
    if ((size*this.calc.y)>(screenSize.getHeight()-100)) {
      size = (int)(screenSize.getHeight()-100)/this.calc.y;
    }
    return size;
  }
  /**
   * paintComponent - function that draws to canvas
   * @param g Graphics typer to identify where to draw
   */
  public void paintComponent(Graphics g) {
    //calls super class function to draw to screen
    super.paintComponent(g);
    //for loop to draw each image
    for (int i = 0; i<this.amount; i++) {
      g.drawImage(this.images[i], this.coords[i][0], this.coords[i][1], this);
    }
  }

  /**
   * loadImages - loads each image from directory and resizes them
   * @param size amount of files
   */
  public BufferedImage[] loadImages(int size) {
    BufferedImage[] images = new BufferedImage[size];
    //call instance variable files to avoid having to recall same method
    String[] files = this.files;
    for (int i = 0; i<size; i++) {
      String fileName = files[i];
      //load image using LoadImage class with resize parameters
      images[i] = new LoadImage(fileName, this.imageHeight, this.imageWidth).getImage();
    }
    return images;
  }

  /**
   * setScreenDimension - finds best dimensions to fit the screen and each image
   * returns an int array of the dimensions of the screen to be drawn
   */
  public int[] setScreenDimension() {
    int[] dimensions = new int[2];
    dimensions[0] = this.imageWidth * this.calc.x;
    dimensions[1] = this.imageWidth * this.calc.y;
    return dimensions;
  }

  /**
   * setCoords - create 2D array of image coords
   */
  public int[][] setCoords() {
    //initialise array of length this.amount from constructor
    int[][] coords = new int[this.amount][2];
    int x = 0;
    int y = 0;
    for (int i=0; i<this.amount; i++) {
      //if index (i) is 0, do nothing as coords should start at 0,0
      if (i == 0 ) {}
      // otherwise if next picture starting location is wider than screen
      else if (this.imageWidth+x >= this.screenDimensions[0]) {
        // go down a row
        x = 0;
        y += this.imageHeight;
      }
      else {
        x += this.imageWidth;
      }
      coords[i] = new int[]{x,y};
    }
    return coords;
  }

  /**
   * filesThatWorked - tests amount of files that were drawn
   * as some sometimes do not work properly
   */
  public static void filesThatWorked(DrawImages object) {
    System.out.print(object.amount);
    System.out.print(" out of ");
    System.out.print(ImageColour.getFiles().length);
    System.out.println(" worked.");
  }

  public static void main(String[] args) {
    DrawImages panel = new DrawImages();
    //initialise frame for graphics
    JFrame f = new JFrame("Album Covers");
    // System.out.println(panel.imageWidth);
    // System.out.println(panel.imageHeight);

    //event monitor for exit
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //set screen dimensions
    f.setSize(panel.screenDimensions[0],panel.screenDimensions[1]);

    //draw images onto pane(screen)
    Container pane = f.getContentPane();
    pane.add(panel);

    f.setVisible(true);

    filesThatWorked(panel);
  }

}

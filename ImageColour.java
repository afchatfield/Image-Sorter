/**
 * ImageColour.java
 *
 * find average colour of an image
 * 1.0 version
 *
 * @author Anthony Chatfield
 */

import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.Color;
import java.util.HashMap;
import java.lang.*;

public class ImageColour {

  /**
   * averageColour - returns average colour of an image
   * can be focused to a subsection of an image
   * @param bi image
   * @param x0 starting pixel to scan on x plane
   * @param y0 starting pixel to scan on y plane
   * @param w width of pixels to scan
   * @param h height of pixels to scan
   * returns a Color of the mean colour in the image
   */
  public static Color averageColour(BufferedImage img, int x0, int y0, int w,
        int h) {
    // identify end points of section to scan
    int x1 = x0 + w;
    int y1 = y0 + h;
    // variables for sum of each rgb value for average calculation
    long sumr = 0, sumg = 0, sumb = 0;
    //loop though rows of pixels
    for (int x = x0; x < x1; x++) {
        // for each row of pixels, loop through each column
        for (int y = y0; y < y1; y++) {
            // find rgb value of current pixel
            Color pixel = new Color(img.getRGB(x, y));
            // add rgb values to running total
            sumr += pixel.getRed();
            sumg += pixel.getGreen();
            sumb += pixel.getBlue();
        }
    }
    //calculate average and return average colour
    int num = w * h;
    return new Color((int)sumr / num, (int)sumg / num, (int)sumb / num);
  }

  public static LinkedHashMap<Color,Integer> findPictureColours(BufferedImage img){
    HashMap<Color, Integer> colours = new HashMap<Color,Integer>();
    int totalPixels = 0;
    for (int x = 0; x< img.getWidth(); x++){
      for (int y = 0; y< img.getHeight(); y++){
        Color pixel = new Color(img.getRGB(x,y));
        // String pixelColour = ColourUtils.getColourNameFromColour(pixel);
        if (colours.containsKey(pixel)){
          // System.out.println(pixel);
          int num = colours.get(pixel);
          colours.replace(pixel,num+1);
        } else {
          colours.put(pixel,1);
        }
        totalPixels+=1;
      }
    }
    for (Map.Entry<Color,Integer> colourAmount: colours.entrySet()){
      double num = colourAmount.getValue();
      double total =totalPixels;
      double dec= num/total;
      dec *= 100;
      colours.replace(colourAmount.getKey(), (int)dec);
    }
    return sortByValue(colours);
  }

  public static float[] returnMainColourHSB(BufferedImage img) {
    LinkedHashMap<Color, Integer> fileColours = findPictureColours(img);
    Color colour = null;
    for (Map.Entry<Color, Integer> entry : fileColours.entrySet()){
      colour = entry.getKey();
    }
    float[] hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(),
    colour.getBlue(), null);
    return hsb;
  }

  public static LinkedHashMap<String,float[]> getPicturesByHSB() {
    File[] files = getFiles();
    LinkedHashMap<String,float[]> fileHSB = new LinkedHashMap<String, float[]>();
    for (File file: files) {
      String name = file.getName();
      BufferedImage img = new LoadImage(name).getImage();
      // test file has image
      if (img == null) continue;
      System.out.print(name +": ");
      float[] hsb = returnMainColourHSB(img);
      System.out.println(Arrays.toString(hsb));
      fileHSB.put(name, hsb);
    }
    return fileHSB;
  }

  public static int compareHSB(float[] c1, float[] c2) {
    if (c1[0] < c2[0])
        return -1;
    if (c1[0] > c2[0])
        return 1;
    if (c1[1] < c2[1])
        return -1;
    if (c1[1] > c2[1])
        return 1;
    if (c1[2] < c2[2])
        return -1;
    if (c1[2] > c2[2])
        return 1;
    return 0;
  }

  public static String[] sortPictures(LinkedHashMap<String, float[]> hm) {
    // Create a list from elements of HashMap
    List<Map.Entry<String, float[]> > list =
           new LinkedList<Map.Entry<String, float[]> >(hm.entrySet());

    // Sort the list by integer value
    // Collections.sort(list, new Comparator<Map.Entry<String, float[]> >() {
    //     // compare each entry (value) to other entries
    //     public int compare(Map.Entry<String, float[]> o1,
    //                        Map.Entry<String, float[]> o2)
    //     {
    //         return compareHSB(o1.getValue(), o2.getValue());
    //     }
    // });

    Collections.sort(list, (o1, o2) -> { return compareHSB(o1.getValue(), o2.getValue()); });
    // o1.getValue()[0].compareTo(o2.getValue()[0])

    // put data from sorted list to array
    String[] temp = new String[hm.size()];
    int pointer = 0;
    for (Map.Entry<String, float[]> entry : list) {
      temp[pointer] = entry.getKey();
      pointer++;
    }
    return temp;
  }

  /**
   * getFiles - returns folder directory of files
   */
  public static File[] getFiles() {
    //load directory of images
    File dir = new File("images/");
    //create array of the files
    File[] files = dir.listFiles();
    return files;
  }

  /**
   * averageColours - finds average colour of each image, returns a hashmap of
   * file name and rgb integer value
   */
  public static HashMap<String,Integer> averageColours(){
    // access files
    File[] files = getFiles();
    HashMap<String, Integer> avgColours = new HashMap<String,Integer>();
    // iterate over files
    for (File file : files) {
      // load file as image
      BufferedImage img = new LoadImage(file.getName()).getImage();
      // test file has image
      if (img == null) break;
      // get average colour of image by calling averageColour
      Color avgColour = averageColour(img, 0, 0, img.getWidth(), img.getHeight());
      avgColours.put(file.getName(), avgColour.getRGB());
    }
    // convert color keySet to rgb
    // HashMap<String, Integer> sortedColours = new LinkedHashMap<String, Integer>();
    //     for (Map.Entry<String, Color> aa : avgColours.entrySet()) {
    //         sortedColours.put(aa.getKey(), aa.getValue().getRGB());
    //     }
    return avgColours;
  }

  /**
   * sortByValue - sorts a hashmap with integer values
   * @param hm unsorted hashmap to sort, has string keys and integer values
   */
   public static LinkedHashMap<Color, Integer> sortByValue(HashMap<Color, Integer> hm) {

      // Create a list from elements of HashMap
      List<Map.Entry<Color, Integer> > list =
             new LinkedList<Map.Entry<Color, Integer> >(hm.entrySet());

      // Sort the list by integer value
      Collections.sort(list, new Comparator<Map.Entry<Color, Integer> >() {
          // compare each entry (value) to other entries
          public int compare(Map.Entry<Color, Integer> o1,
                             Map.Entry<Color, Integer> o2)
          {
              return (o1.getValue()).compareTo(o2.getValue());
          }
      });

      // put data from sorted list to hashmap
      LinkedHashMap<Color, Integer> temp = new LinkedHashMap<Color, Integer>();
      for (Map.Entry<Color, Integer> aa : list) {
          temp.put(aa.getKey(), aa.getValue());
      }
      return temp;
    }

  /**
   * getSortedFiles - returns String list of files sorted by color rgb value
   */
  public static String[] getSortedFiles(){
    // create hashmap of average colours for each image
    LinkedHashMap<String,float[]> colouredFiles = getPicturesByHSB();
    // sort hashmap and convert to string
    String[] fileNames = sortPictures(colouredFiles);
    System.out.println(Arrays.toString(fileNames));
    return fileNames;
  }

  // public static String[] getSortedFilesByMainColour();


  public static void main(String[] args) {
    // LinkedHashMap<String, float[]> fileColours = getPicturesByHSB();
    // for (String file: fileColours.keySet()){
    //   System.out.print(file +": ");
    //   float[] colour = fileColours.get(file);
    //   System.out.println(Arrays.toString(colour));
    // }
    // File[] files = getFiles();
    // for (File file: files) {
    //   String name = file.getName();
    //   BufferedImage img = new LoadImage(name).getImage();
    //   // test file has image
    //   if (img == null) break;
    //   System.out.print(name + ": ");
    //   System.out.println(Arrays.toString(returnMainColourHSB(img)));
    // }
    // BufferedImage img = new LoadImage("Arctic_Monkeys_-_AM.jpg").getImage();
    // HashMap<Color, Integer> fileColours = findPictureColours(img);
    // // for (Color Colour: fileColours.keySet()){
    // //   float[] hsb = Color.RGBtoHSB(Colour.getRed(), Colour.getGreen(),
    // //   Colour.getBlue(), null);
    // //   System.out.println(Arrays.toString(hsb) + ": " + fileColours.get(Colour));
    // // }
    // System.out.println(Arrays.toString(returnMainColourHSB(img)));
    // float[] hsb = Color.RGBtoHSB(71, 71, 71, null);
    // System.out.println(Arrays.toString(hsb));
    // for (File file: getFiles()) {
    //   System.out.println(file.getName());
    // }
    // for (String file: getSortedFiles()) {
    //   System.out.println(file);
    // }
    // LinkedHashMap<String, float[]> fileColours = getPicturesByHSB();
    // String[] sortedFiles = sortPictures(fileColours);
    // System.out.println(Arrays.toString(sortedFiles));
  }
}

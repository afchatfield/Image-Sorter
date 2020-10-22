import java.awt.Color;
import java.util.ArrayList;

public class ColourUtils {

  private static ArrayList<ColourName> initColourList() {
    ArrayList<ColourName> colourList = new ArrayList<ColourName>();
    colourList.add(new ColourName("Black",0,0,0));
    colourList.add(new ColourName("Red",255,0,0));
    colourList.add(new ColourName("Pink",255,192,203));
    colourList.add(new ColourName("Purple",128,0,128));
    colourList.add(new ColourName("Blue",0,0,255));
    colourList.add(new ColourName("Light Blue",173, 216, 230));
    colourList.add(new ColourName("Navy Blue",0, 0, 139));
    colourList.add(new ColourName("Green",0,255,0));
    colourList.add(new ColourName("Yellow",255,255,0));
    colourList.add(new ColourName("Orange",255,127,0));
    // colourList.add(new ColourName("Beige",245,245,220));
    // colourList.add(new ColourName("Brown",150,75,0));
    colourList.add(new ColourName("Grey",128,128,128));
    // colourList.add(new ColourName("Linen",250,240,230));
    // colourList.add(new ColourName("Tan",210,180,140));
    colourList.add(new ColourName("White",255,255,255));
    return colourList;
  }

  public static String getColourNameFromRgb(int r, int g, int b) {
    ArrayList<ColourName> colourList = initColourList();
    ColourName closestMatch = null;
    int minMSE = Integer.MAX_VALUE;
    int mse;
    for (ColourName c : colourList) {
        //convert to hsb as easier to use for colour identification
        mse = c.getDifference(r, g, b);
        if (mse < minMSE) {
            minMSE = mse;
            closestMatch = c;
        }
    }
    if (closestMatch != null) {
        return closestMatch.getName();
    } else {
        return "No matched color name.";
    }
  }

  public static String getColourNameFromColour(Color colour) {
    return getColourNameFromRgb(colour.getRed(), colour.getGreen(),
                colour.getBlue());
  }

}

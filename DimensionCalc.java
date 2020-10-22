/**
 * DrawImages.java
 *
 * takes in mutiple images and draws them in sorted order (by colour) on a screen
 * 3.0 version
 *
 * @author Anthony Chatfield
 */

import java.util.*;
import java.lang.*;

public class DimensionCalc {

  private int amount;
  public int x, y;

  public DimensionCalc(int amount) {
    this.amount = amount;
    int[] size = findWH(amount);
    this.x = size[0];
    this.y = size[1];
  }

  private int[] findWH(int num){
    if (num==1) return new int[]{1,1};
    int gap = num - 1;
    int amount = num;
    List<int[]> factorPairs = new ArrayList<int[]>();
    int repeats = 0;
    int index;
    do {
      factorPairs.clear();
      for (int i=1; i<=(int)amount/2; i++) {
        if (amount%i==0) {
          int[] pair = new int[]{i, (amount/i)};
          Arrays.sort(pair);
          factorPairs.add(pair);
        }
      }
      index = 0;
      for (int[] pair: factorPairs) {
        int temp = Math.abs(pair[0]-pair[1]);
        if (temp<gap) {
          gap = temp;
          index = factorPairs.indexOf(pair);
        }
      }
      amount -= 1;
    } while (gap>2);
    int[] pair = factorPairs.get(index);
    if ((pair[0]*pair[1])!=num){
      pair[1]+=1;
    }
    return pair;
  }

  public static void main(String[] args) {
    DimensionCalc obj = new DimensionCalc(16);
    for (int i = 1; i<51; i++){
      System.out.print(i+": ");
      System.out.println(Arrays.toString(obj.findWH(i)));
    }
  }

}

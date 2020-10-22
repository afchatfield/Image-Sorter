import java.lang.*;
public class Prime {
  public static boolean isPrime(int num) {
    if (num==0) return false;
    for (int i = 1; i<=Math.floor(num/2); i++) {
      if (num%(i*i)==0) return true;
      System.out.println(i);
      }
    return false; //TODO
  }
}

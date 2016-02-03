package gwtxg2d.client.helper;

public class Arrays {

  public static byte[] copyOf(byte[] org, int length) {
    byte[] ret = new byte[length];
    for (int i = 0; i < java.lang.Math.min(length, org.length); i++)
      ret[i] = org[i];
    return ret;
  }

  public static int[] copyOf(int[] org, int length) {
    int[] ret = new int[length];
    for (int i = 0; i < java.lang.Math.min(length, org.length); i++)
      ret[i] = org[i];
    return ret;
  }

  public static float[] copyOf(float[] org, int length) {
    float[] ret = new float[length];
    for (int i = 0; i < java.lang.Math.min(length, org.length); i++)
      ret[i] = org[i];
    return ret;
  }

  public static double[] copyOf(double[] org, int length) {
    double[] ret = new double[length];
    for (int i = 0; i < java.lang.Math.min(length, org.length); i++)
      ret[i] = org[i];
    return ret;
  }

  public static void sort(double[] res, int i, int num) {
    java.util.Arrays.sort(res, i, num);
  }

}

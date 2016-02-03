package gwtxg2d.client.helper;

public class Cloner {
  public static float[] clone(float[] obj) {
    float[] ret = new float[obj.length];
    for (int i = 0; i < obj.length; i++)
      ret[i] = obj[i];
    return ret;
  }
}

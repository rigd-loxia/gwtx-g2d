package gwtxg2d.client.helper;

public class Math {

  public static double ulp(double d) {
    // XXX correct for our default cases?
    if (d < 0)
      return -d;
    return 0;
  }

  public static double IEEEremainder(double x, double y) { 
    double regularMod = x % y;
    if (java.lang.Double.isNaN(regularMod)) { 
        return java.lang.Double.NaN;
    }
    if (regularMod == 0) {
        if (x<0.0) { 
            return -0.0;
        } 
    } 
    double alternativeResult;
    alternativeResult = regularMod - (java.lang.Math.abs(y) * java.lang.Math.signum(x)); 
    if (java.lang.Math.abs(alternativeResult) == java.lang.Math.abs(regularMod)) {
        double divisionResult = x/y;
        double roundedResult = java.lang.Math.round(divisionResult);
        if (java.lang.Math.abs(roundedResult) > java.lang.Math.abs(divisionResult)) { 
            return alternativeResult;
        } 
        else { 
            return regularMod;
        } 
    }
    if (java.lang.Math.abs(alternativeResult) < java.lang.Math.abs(regularMod)) {
        return alternativeResult;
    } 
    else {
        return regularMod; 
    } 
  }
 }
  
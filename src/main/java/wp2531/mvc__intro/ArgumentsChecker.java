/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wp2531.mvc__intro;

import java.util.stream.DoubleStream;

/**
 * Checks arguments type 
 * and values correctness
 * 
 * @author comeillfoo
 */
public class ArgumentsChecker {
  
  private double[] ACCEPTABLE_VALUES = new double[0]; // array of acceptable values
  private double MIN_ACCEPTABLE_VALUE = 0; // minimum acceptable value in the interval
  private double MAX_ACCEPTABLE_VALUE = 0; // maximum acceptable value in the interval
  private final double EPSILON; // calculation fillability
  
  /**
   * Sets array a set 
   * of acceptable values
   * 
   * @param values array of acceptable values
   */
  public void setAcceptable(double... values) {
    ACCEPTABLE_VALUES = values;
  }
  
  /**
   * Sets the maximum and
   * the minimum in the interval
   * 
   * @param min minumum value
   * @param max maximum value
   */
  public void setInterval(double min, double max) {
    MIN_ACCEPTABLE_VALUE = min;
    MAX_ACCEPTABLE_VALUE = max;
  }
  
  /**
   * Returns instance with preset
   * all fields
   * 
   * @param error the fillability
   * @param min the minimum one
   * @param max the maximum one
   * @param values array of acceptable values
   */
  public ArgumentsChecker(double error, double min, double max, double... values) {
    this(error, min, max);
    setAcceptable(values);
  }
  
  /**
   * Returns instance with preset
   * maximum and minimum values
   * 
   * @param error the fillability
   * @param min the minimum one
   * @param max the maximum one
   */
  public ArgumentsChecker(double error, double min, double max) {
    this(error);
    setInterval(min, max);
  }
  
  /**
   * Returns instance with preset
   * just the fillability 
   * 
   * @param error the fillability
   */
  public ArgumentsChecker(double error) {
    EPSILON = error;
  }
  
  /**
   * Checks if transferred parameter
   * casts to double and not a null.
   * Otherwise returning error message.
   * 
   * @param arg value of parameter
   * @param argName name of parameter
   * @return error message if it occused
   */
  public static String checkRealParameter(String arg, String argName) { 
    try { Double.parseDouble(arg); 
    } catch (NullPointerException npe) { return "NPE: не указан параметр " + argName + ";<br>";
    } catch (NumberFormatException nfe) { return "NFE: не удалось привести параметр " + argName + " к числу;<br>"; }
    return "";
  }
  
  /**
   * Checks if the interval 
   * contains value implicitly
   * 
   * @param value
   * @return sign of inclusion
   */
  public boolean isInInterval(double value) {
    return value - EPSILON > MIN_ACCEPTABLE_VALUE && value + EPSILON < MAX_ACCEPTABLE_VALUE;
  }
  
  /**
   * Checks if the set of values
   * contains value
   * @param value
   * @return sign of inclusion
   */
  public boolean isAcceptable(Double value) {
    return DoubleStream.of(ACCEPTABLE_VALUES).anyMatch(value::equals);
  }
}

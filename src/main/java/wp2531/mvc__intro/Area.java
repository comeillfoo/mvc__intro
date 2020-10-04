/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wp2531.mvc__intro;

/**
 *
 * @author comeillfoo
 */
public class Area {
  private final double radius;
  
  public Area(double radius) { this.radius = radius; }
  
  public boolean isHit(double x, double y, double error) {
    return isHit1Qdrt(x, y, error) || isHit2Qdrt(x, y, error) || isHit3Qdrt(x, y, error) || isHit4Qdrt(x, y, error);
  }
  
  private boolean isHit1Qdrt(double x, double y, double error) { return ((x - error >= 0) && (y + error >= 0) && (radius / 2 - x - error <= y + error)); }
  
  private boolean isHit2Qdrt(double x, double y, double error) { return ((x + error <= 0) && (y - error >= 0) && (x - error >= -radius) && (y + error <= radius / 2)); }
  
  private boolean isHit3Qdrt(double x, double y, double error) { return (x + error <= 0 && y + error <= 0 && x * x + y * y + 2 * error * (y + x) <= radius / 2); }
  
  private boolean isHit4Qdrt(double x, double y, double error) { return false; }
}

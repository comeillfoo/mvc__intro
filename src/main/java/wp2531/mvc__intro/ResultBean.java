/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wp2531.mvc__intro;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author comeillfoo
 */
public class ResultBean implements Serializable {

  @Override
  public String toString() {
    return "ResultBean{" + "date=" + date + ", time=" + time + ", x=" + x + ", y=" + y + ", r=" + r + ", hit=" + hit + '}';
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 47 * hash + Objects.hashCode(this.date);
    hash = 47 * hash + Objects.hashCode(this.time);
    hash = 47 * hash + Objects.hashCode(this.x);
    hash = 47 * hash + Objects.hashCode(this.y);
    hash = 47 * hash + Objects.hashCode(this.r);
    hash = 47 * hash + (this.hit ? 1 : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ResultBean other = (ResultBean) obj;
    if (this.hit != other.hit) {
      return false;
    }
    if (!Objects.equals(this.date, other.date)) {
      return false;
    }
    if (!Objects.equals(this.x, other.x)) {
      return false;
    }
    if (!Objects.equals(this.y, other.y)) {
      return false;
    }
    if (!Objects.equals(this.r, other.r)) {
      return false;
    }
    if (!Objects.equals(this.time, other.time)) {
      return false;
    }
    return true;
  }
  
  private String date;
  
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  private Long time;
  
  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  private String x;
  
  public String getX() {
    return x;
  }

  public void setX(String x) {
    this.x = x;
  }
  
  private String y;

  public String getY() {
    return y;
  }

  public void setY(String y) {
    this.y = y;
  }
  
  private String r;
  
  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }
  
  private boolean hit;
  
  public boolean isHit() {
    return hit;
  }

  public void setHit(boolean hit) {
    this.hit = hit;
  }
  
}

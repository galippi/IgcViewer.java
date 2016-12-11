/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc;

/**
 *
 * @author liptakok
 */
public class IGC_Coordinate {
  double coor;
  public IGC_Coordinate(double val)
  {
    coor = val;
  }
  public IGC_Coordinate neg()
  {
    return new IGC_Coordinate(-coor);
  }
  public IGC_Coordinate min(IGC_Coordinate val)
  {
    if (val.coor < coor)
      return val;
    return this;
  }
  public IGC_Coordinate max(IGC_Coordinate val)
  {
    if (val.coor > coor)
      return val;
    return this;
  }
  public double abs()
  {
    return Math.abs(coor);
  }
  public double val()
  {
    return coor;
  }
}


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
public class IGC_point {

  public IGC_Coordinate lat, lon;
  public IGC_Altitude Altitude;
  public IGC_Time t;
  public IGC_point()
  {
  }
  public IGC_point(IGC_Coordinate lat, IGC_Coordinate lon, IGC_Altitude Altitude, IGC_Time t)
  {
    this.lat = lat;
    this.lon = lon;
    this.Altitude = Altitude;
    this.t = t;
  }
  public double getDir(IGC_point pt)
  {
    double delta_lon = pt.lon.coor - lon.coor;
    double delta_lat = pt.lat.coor - lat.coor;
    if (Math.abs(delta_lat) < 1e-9)
    {
      if (Math.abs(delta_lon) < 1e-9)
      {
        return 0.0; /* direction is not calculable -> return an invalid value */
      }else
      {
        if (delta_lon < 0)
          return Math.PI * 1.5; /* 270 grad */
        else
          return Math.PI * 0.5; /* 90 grad */
      }
    }
    double div_result = delta_lon/delta_lat;
    double result = Math.atan(div_result);
    if (result >= 0)
    {
      if (delta_lat >= 0)
      { /* 1st quarter - result is ok */
      }else
      { /* 3rd quarter -> shift 180 deg */
        result += Math.PI;
      }
    }else
    {
      if (delta_lat < 0)
      { /* 2nd quarter */
        result = Math.PI + result;
      }else
      { /* 4th quarter */
        result = Math.PI * 2 + result;
      }
    }
    return result;
  }
}

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
public class GeoPoint
{
  public GeoPoint(double lon, double lat)
  {
    this.lon = lon;
    this.lat = lat;
  }
  public GeoPoint(IGC_point pt)
  {
    lon = pt.lon.val() * Math.PI / 180;
    lat = pt.lat.val() * Math.PI / 180;
  }
  public double getDistance(GeoPoint pt)
  {
    double dLng = this.lon - pt.lon;
    double dLat = this.lat - pt.lat;
    double sin_dlat_2 = java.lang.Math.sin(dLat/2);
    double sin_dlng_2 = java.lang.Math.sin(dLng/2);
    double a = sin_dlat_2 * sin_dlat_2 +
               java.lang.Math.cos(this.lat) * java.lang.Math.cos(pt.lat) *
               sin_dlng_2 * sin_dlng_2;
    double dist = earthRadius * 2 * java.lang.Math.atan2(java.lang.Math.sqrt(a), java.lang.Math.sqrt(1-a));
    return dist;
  }
  public double getAngle(GeoPoint pt)
  {
    double delta_lon, delta_lat;
    delta_lon = pt.lon - this.lon;
    delta_lat = pt.lat - this.lat;
    if (java.lang.Math.abs(delta_lat) < 1e-9)
    {
      if (java.lang.Math.abs(delta_lon) < 1e-9)
      {
        return -1; //GeoCoor::GeoCoor_NA;
      }else
      {
        if (delta_lon < 0)
          return java.lang.Math.PI + PI_2; /* 270 grad */
        else
          return PI_2; /* 90 grad */
      }
    }
    double div_result = delta_lon/delta_lat;
    double result = java.lang.Math.atan(div_result);
    if (result >= 0)
    {
      if (delta_lat >= 0)
      { /* 1st quarter - result is ok */
      }else
      { /* 3rd quarter -> shift 180 deg */
        result += java.lang.Math.PI;
      }
    }else
    {
      if (delta_lat < 0)
      { /* 2nd quarter */
        result = java.lang.Math.PI + result;
      }else
      { /* 4th quarter */
        result = TWOPI + result;
      }
    }
    return result;
  }
  public double lon, lat;
  static final double earthRadius = 3958.75 * 1609.00;
  static final double PI_2 = java.lang.Math.PI / 2;
  static final double TWOPI = java.lang.Math.PI * 2;
}

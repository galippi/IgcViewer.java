/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import utils.dbg;

/**
 *
 * @author liptakok
 */
/* public*/ class GeoPoint
{
  public double lon, lat;
}

/* public*/ class GeoPoint2D
{
  public double lon, lat;
}
        
public class GeoUtil
{
  public GeoUtil(GeoUtil gu)
  {
    Set(gu.lon_min, gu.lon_max, gu.lat_min, gu.lat_max, gu.w, gu.h);
  }

  public GeoUtil()
  {
    Set(19.2, 19.4, 47.2, 47.4, 1024, 768);
  }
  GeoUtil(double _lon_min, double _lon_max, double _lat_min, double _lat_max, int width, int height)
  {
    Set(_lon_min, _lon_max, _lat_min, _lat_max, width, height);
  }

  void Set(double _lon_min, double _lon_max, double _lat_min, double _lat_max, int width, int height)
  {
    x_offs = 0;
    y_offs = 0;
    redraw_forced = true;
    double zoom_x = width / (_lon_max - _lon_min);
    double zoom_y = height / (_lat_max - _lat_min);
    if (zoom_x < zoom_y)
    {
      zoom = zoom_x;
      lon_min = _lon_min;
      lon_max = _lon_max;
      double lat_delta = ((height / zoom) - (_lat_max - _lat_min)) / 2;
      lat_min = _lat_min - lat_delta;
      lat_max = _lat_max + lat_delta;
    }
    else
    {
      zoom = zoom_y;
      lat_min = _lat_min;
      lat_max = _lat_max;
      double lon_delta = ((width / zoom) - (_lon_max - _lon_min)) / 2;
      lon_min = _lon_min - lon_delta;
      lon_max = _lon_max + lon_delta;
    }
    w = (int)(zoom * (lon_max - lon_min) + 0.5);
    h = (int)(zoom * (lat_max - lat_min) + 0.5);
    dbg.println(11, dbg.d_format("GeoUtil::Set() lon_min=%lf lat_min=%lf lon_max=%lf lat_max=%lf zoom=%lf w=%d h=%d", lon_min, lat_min, lon_max, lat_max, zoom, w, h));
    redraw = true;
  }
  void Zoom(int x, int y, double zoom_new)
  {
    Double lon = new Double(0), lat = new Double(0);
    GetPos(x, y, lon, lat);
    dbg.println(11, dbg.d_format("Zoom x=%d y=%d lon=%lf lat=%lf", x, y, lon, lat));
    lon_min = lon - (x / zoom_new);
    lat_max = lat + (y / zoom_new);
    zoom = zoom_new;
    lon_max = lon_min + w / zoom;
    lat_min = lat_max - h / zoom;
    redraw_forced = true;
    dbg.println(11, dbg.d_format("GeoUtil::Zoom lon_min=%lf lat_min=%lf lon_max=%lf lat_max=%lf zoom=%lf w=%d h=%d", lon_min, lat_min, lon_max, lat_max, zoom, w, h));
  }
  void Move(int dx, int dy)
  {
    Move(dx, dy, false);
  }
  void Move(int dx, int dy, boolean forced)
  {
    dbg.println(11, dbg.d_format("GeoUtil::Move dx=%d dy=%d forced=%s", dx, dy, forced ? "true" : "false"));
    if (forced)
    {
      GetPos(-x_offs, -y_offs, lon_min, lat_max);
      lon_max = lon_min + w / zoom;
      lat_min = lat_max - h / zoom;
      dbg.println(11, dbg.d_format("lon_min=%lf lat_min=%lf lon_max=%lf lat_max=%lf zoom=%lf w=%d h=%d", lon_min, lat_min, lon_max, lat_max, zoom, w, h));
      x_offs = dx;
      y_offs = dy;
      redraw_forced = true;
    }else
    {
      x_offs += dx;
      y_offs += dy;
      redraw_forced = false;
    }
  }
  void GetPos(int x, int y, Double lon, Double lat)
  {
    lon = lon_min + (x / zoom);
    lat = lat_max - (y / zoom);
  };
  void GetPos(double lon, double lat, Integer x, Integer y)
  {
    x = (int)((lon - lon_min) * zoom + 0.5);
    y = (int)((lat_max - lat) * zoom + 0.5);
  };
  void GetPosOffs(double lon, double lat, Integer x, Integer y)
  {
    GetPos(lon, lat, x, y);
    x += x_offs; y += y_offs;
  };
  void GetPosOffs(GeoPoint pt, Integer x, Integer y)
  {
    GetPosOffs(pt.lon, pt.lat, x, y);
  }
  void GetPosOffs(GeoPoint2D pt, Integer x, Integer y)
  {
    GetPosOffs(pt.lon, pt.lat, x, y);
  }
  double lon_min, lon_max, lat_min, lat_max, zoom;
  int w, h;
  int x_offs, y_offs;
  boolean redraw_forced;
  boolean redraw;
}

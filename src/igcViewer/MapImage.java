/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import utils.threadImage;
import igc.GeoUtil;
import java.awt.Color;
import java.io.IOException;
import utils.Sprintf;
import utils.dbg;
import xcm.xcm;
import xcm.Shp;
import xcm.Shape;

/**
 *
 * @author liptakok
 */
public class MapImage extends threadImage
{
  public MapImage(java.awt.Component parent, GeoUtil gu)
  {
    super(parent);
    this.gu = new GeoUtil(gu);
  }
  @Override
    protected void Drawing()
  { /* drawing function */
    if (Xcm == null)
    {
      String filename = "Hungary.xcm";
      try
      {
        Xcm = new xcm(filename);
      }catch (IOException e)
      {
        dbg.dprintf(1, "Error: unable to open xcm file \"%s\"!", filename);
        Xcm = null;
      }
    }
    java.awt.Graphics2D g = img.createGraphics();
    if (Xcm != null)
    { // the map is successfully loaded
      g.setBackground(new Color(0, true));
      g.clearRect(0, 0, img.getWidth(), img.getHeight());
      g.setColor(Color.BLACK);
      if (dbg.get(9)) g.drawString("Xcm drawing - MapImage - size="+Xcm.ShapeFiles.size()+"!", 40, 40);
      for (int i = 0; i < Xcm.ShapeFiles.size(); i++)
      {
        Shp shp = Xcm.get(i);
        if (dbg.get() >= 9)
        {
          g.drawString("Shape " + i + ": " + shp.toStringBounds() + " zoom=" + shp.zoom, 50, 50 + i * 12);
        }
        if (shp.zoom > (10000 / gu.zoom))
        {
          if (isShpVisible(shp))
          {
            g.setColor(shp.color);
            for (int j = 0; j < shp.size(); j++)
            {
              shapeDraw(g, shp.get(j), gu);
            }
            g.setColor(Color.BLACK);
            for (int j = 0; j < shp.size(); j++)
            {
                Shape shape = shp.get(j);
              if (shape.name != null)
              {
                if (shape.type == Shape.SHP_POINT)
                {
                    int x = gu.getPosXOffs(shape.Points[0].x);
                    int y = gu.getPosYOffs(shape.Points[0].y);
                    g.drawString(shape.name, x, y);
                }else
                {
                    double lon = (shape.getLonMax() - shape.getLonMin()) / 2;
                    double lat = (shape.getLatMax() - shape.getLatMin()) / 2;
                    int x, y;
                    x = gu.getPosXOffs(lon);
                    y = gu.getPosYOffs(lat);
                    g.drawString(shape.name, x, y);
                }
              }
            }
          }
        }
      }
      // drawing raster net, if it's possible
      g.setColor(Color.BLACK);
      double dLon = gu.lon_max - gu.lon_min;
      double lonStep = getRaster(dLon);
      if (lonStep > 1e-6)
      {
          double lon = (int)(gu.lon_min / lonStep) * lonStep + (0.2/60);
          while(lon < gu.lon_max)
          {
            int x = gu.getPosXOffs(lon);
            g.drawLine(x, 0, x, gu.getH());
            g.drawString(Sprintf.sprintf("%4d:%02d", (int)lon, (int)(lon*60) % 60), x-20, 15);
            lon += lonStep;
          }
      }
      double dLat = gu.lat_max - gu.lat_min;
      double latStep = getRaster(dLat);
      if (latStep > 1e-6)
      {
          double lat = (int)(gu.lat_min / latStep) * latStep + (0.2/60);
          while(lat < gu.lat_max)
          {
            int y = gu.getPosYOffs(lat);
            g.drawLine(0, y, gu.getW(), y);
            g.drawString(Sprintf.sprintf("%4d:%02d", (int)lat, (int)(lat*60) % 60), 0, y);
            lat += latStep;
        }
      }
    }else
    { // the map could not be loaded
      g.setColor(Color.red);
      g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
      g.setColor(Color.green);
      g.drawString("MapImage - error loading map!", 40, 40);
    }
    g.dispose();
  }
  double getRaster(double delta)
  {
      final double raster[] = {0.0, 1.0/60, 2.5/60, 5.0/60, 10.0/60, 15.0/60, 20.0/60, 1.0, 2.0, 2.5, 5.0};
      double deltaHalf = Math.abs(delta / 2);
      if (deltaHalf < raster[0])
          return 0.0; // to small area - do not draw raster lines
      for(int i = 1; i < raster.length; i++)
      {
          if (deltaHalf < raster[i])
              return raster[i - 1];
      }
      return 0.0; // too large area
  }
  void shapeDraw(java.awt.Graphics2D g, Shape shape, GeoUtil gu)
  {
    //dc.SetBrush(brush);
    if (isShapeVisible(shape))
    {
      if (shape.type == shape.SHP_POINT)
      {
        int x = gu.getPosXOffs(shape.Points[0].x);
        int y = gu.getPosYOffs(shape.Points[0].y);
        g.fillOval(x, y, 5, 5);
      }else
      {
        for (int j = 0; j < shape.nParts; j++)
        {
          int LastPoint;
          if (j == shape.PartsPos.length - 1)
          {
            LastPoint = shape.Points.length - 1;
          }else
          {
            LastPoint = shape.PartsPos[j + 1] - 1;
          }
          int num = LastPoint - shape.PartsPos[j] + 1;
          int[] xPoints = new int[num + 1];
          int[] yPoints = new int[num + 1];
          int i = 0;
          for (int PointIdx = shape.PartsPos[j]; PointIdx <= LastPoint; i++, PointIdx++)
          {
            xPoints[i] = gu.getPosXOffs(shape.Points[PointIdx].x);
            yPoints[i] = gu.getPosYOffs(shape.Points[PointIdx].y);
          }
          if (shape.type == shape.SHP_POLYGON)
          { /* the polygon type should be closed and filled */
            xPoints[num] = xPoints[0];
            yPoints[num] = yPoints[0];
            if ((xPoints[num - 1] != xPoints[0]) ||
                (yPoints[num - 1] != yPoints[0]))
            {
              num++;
            }
            g.fillPolygon(xPoints, yPoints, num);
          }else
          { /* shape.SHP_ARC */
            g.drawPolyline(xPoints, yPoints, num);
          }
        }
      }
    }
//    if (Shapes[idx]->Parts.size() != 1)
//    {
//      DEBUG_MSG(9, wxString("Shapes[") << idx << "]=");
//      for (size_t j = 0; j < Shapes[idx]->Parts.size(); j++)
//      {
//        DEBUG_MSG(9, wxString("  ") << Shapes[idx]->Parts[j]);
//      }
//    }
  }
  boolean isShpVisible(Shp shp)
  {
    if ((shp.getLonMin() < gu.lon_max) && (shp.getLatMin() < gu.lat_max) &&
        (shp.getLonMax() > gu.lon_min) && (shp.getLatMax() > gu.lat_min) &&
        //(shp.Points.size() > 0) && (Parts.size() > 0)
        true
       )
    {
      return true;
    }else
    {
      return false;
    }
  }
  boolean isShapeVisible(Shape shape)
  {
    if ((shape.getLonMin() < gu.lon_max) && (shape.getLatMin() < gu.lat_max) &&
        (shape.getLonMax() > gu.lon_min) && (shape.getLatMax() > gu.lat_min) &&
        (shape.Points.length > 0) && 
        ((shape.type == shape.SHP_POINT) || (shape.PartsPos.length > 0))
       )
    {
      return true;
    }else
    {
      return false;
    }
  }
  public void setGeoUtil(GeoUtil gu)
  {
    if (!this.gu.isEqual(gu))
    {
      this.gu = new GeoUtil(gu);
      setImage(this.gu.getW(), this.gu.getH());
      repaint();
    }
  }
  GeoUtil gu;
  xcm Xcm;
}

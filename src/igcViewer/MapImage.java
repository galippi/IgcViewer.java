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
import utils.dbg;
import xcm.xcm;

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
      g.setColor(Color.green);
      g.drawString("Xcm drawing - MapImage - size="+Xcm.ShapeFiles.size()+"!", 40, 40);
    }else
    { // the map could not be loaded
      g.setColor(Color.red);
      g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
      g.setColor(Color.green);
      g.drawString("MapImage - error loading map!", 40, 40);
    }
    g.dispose();
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

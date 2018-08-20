/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import igc.GeoUtil;
import java.awt.Color;
import utils.threadImage;

/**
 *
 * @author liptakok
 */
public class AirspaceImage extends threadImage
{
  public AirspaceImage(java.awt.Component parent, GeoUtil gu)
  {
    super(parent);
    this.gu = new GeoUtil(gu);
    airSpaces = new xcm.AirSpaces();
  }
  @Override
    protected void Drawing()
  { /* drawing function */
    java.awt.Graphics2D g = img.createGraphics();
    Color baseColor = Color.gray;
    g.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 127));
    g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
    g.setColor(Color.cyan);
    g.drawString("AirspaceImage!", 40, 100);
    airSpaces.draw(g);
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
  xcm.AirSpaces airSpaces;
}

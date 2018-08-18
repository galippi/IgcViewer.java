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
  }
  @Override
    protected void Drawing()
  { /* drawing function */
    java.awt.Graphics2D g = img.createGraphics();    
    g.setColor(Color.gray);
    g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
    g.setColor(Color.cyan);
    g.drawString("AirspaceImage!", 40, 40);
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
}

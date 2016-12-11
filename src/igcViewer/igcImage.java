/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.Color;
import utils.threadImage;
import igc.GeoUtil;

/**
 *
 * @author liptakok
 */
public class igcImage extends threadImage
{
  public igcImage(java.awt.Component parent, IgcFiles igcFiles)
  {
    super(parent);
    this.igcFiles = igcFiles;
    gu = new GeoUtil();
    ctr = 0;
  }
  IgcFiles igcFiles;
  @Override
    protected void Drawing()
  {
    java.awt.Graphics2D g = img.createGraphics();
    g.setColor(Color.red);
    g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
    g.setColor(Color.white);
    g.fillRect(0, 0, 200, 100);
    g.setColor(Color.green);
    g.drawString("igcImage.drawString count=" + igcFiles.size(), 0, 10);
    g.drawString("igcImage.drawString ctr=" + ctr, 0, 40);ctr++;
    g.drawString("igcImage.drawString gu=" + gu.toString(), 0, 70);
    g.dispose();
  }
  int ctr;
  public void setGeoUtil(GeoUtil gu)
  {
    if (!this.gu.isEqual(gu))
    {
      this.gu = new GeoUtil(gu);
      repaint();
    }
  }
  GeoUtil gu;
}

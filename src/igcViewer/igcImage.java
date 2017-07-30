/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.Color;

import utils.threadImage;
import igc.GeoUtil;
import igc.igc;
import igc.IgcFiles;

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
    g.setBackground(new Color(0, true));
    g.clearRect(0, 0, img.getWidth(), img.getHeight());
    //g.setColor(new Color(0, true));
    //g.fillRect(0, 0, img.getWidth(), img.getHeight());
    if (igcFiles.size() > 0)
    {
      //g.setBackground(new Color(Color.TRANSLUCENT));
      //g.clearRect(0, 0, img.getWidth(), img.getHeight());
      //g.setColor(new Color(Color.TRANSLUCENT));
      g.setColor(new Color(0, true));
      g.fillRect(0, 0, img.getWidth(), img.getHeight());
      for (int i = 0; i < igcFiles.size(); i++)
      {
        igc igc = igcFiles.get(i);
        if (igc.size() > 1)
        {
          g.setColor(igc.color);
          int x0 = gu.getPosX(igc.get(0).lon.val());
          int y0 = gu.getPosY(igc.get(0).lat.val());
          for (int j = 1; j < igc.size(); j++)
          {
            int x1 = gu.getPosX(igc.get(j).lon.val());
            int y1 = gu.getPosY(igc.get(j).lat.val());
            g.drawLine(x0, y0, x1, y1);
            x0 = x1;
            y0 = y1;
          }
        }
      }
    }else
    {
      //g.setColor(Color.red);
      //g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
      //g.setColor(Color.white);
      //g.fillRect(0, 0, 200, 100);
    }
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

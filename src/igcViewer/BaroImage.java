/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import igc.igc;
import java.awt.Color;
import utils.threadImage;

import igc.IgcFiles;

/**
 *
 * @author liptak
 */
public class BaroImage extends threadImage
{
  public BaroImage(java.awt.Component parent, IgcFiles igcFiles)
  {
    super(parent);
    this.igcFiles = igcFiles;
  }
  IgcFiles igcFiles;
  @Override
    protected void Drawing()
  {
    java.awt.Graphics2D g = img.createGraphics();
    g.setBackground(Color.WHITE);
    g.clearRect(0, 0, img.getWidth(), img.getHeight());
    int dT = igcFiles.t_max - igcFiles.t_min;
    int dH = igcFiles.alt_max - igcFiles.alt_min;
    if ((igcFiles.size() != 0) && (dT != 0) && (dH != 0))
    { /* baro(s) shall be painted */
      double AltitudeScale = (img.getHeight() - 1) / (double)(dH);
      double TimeScale = (img.getWidth() -1) / (double)(dT);
      for (int i = 0; i < igcFiles.size(); i++)
      {
        igc igc = igcFiles.get(i);
        if (igc.size() > 1)
        {
          g.setColor(igc.color);
          int x0 = (int)(((int)igc.get(0).t.t - igcFiles.t_min) * TimeScale + 0.5);
          int y0 = img.getHeight() - (int)((igc.get(0).Altitude.h - igcFiles.alt_min) * AltitudeScale);
          for (int t = 1; t < igc.size(); t++)
          {
            int x1 = (int)(((int)igc.get(t).t.t - igcFiles.t_min) * TimeScale + 0.5);
            int y1 = img.getHeight() - (int)((igc.get(t).Altitude.h - igcFiles.alt_min) * AltitudeScale);
            g.drawLine(x0, y0, x1, y1);
            x0 = x1;
            y0 = y1;
          }
        }
      }
    }else
    {
      g.setColor(Color.RED);
      g.fillOval(50, 50, 70, 30);
    }
    g.setColor(Color.BLACK);
    g.drawString("BaroImage.Drawing", 0, 20);
    g.dispose();
  }
}

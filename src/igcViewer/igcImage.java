/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.Color;
import utils.threadImage;

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
    g.fillRect(40,30,200, 100);
    g.setColor(Color.green);
    g.drawString("igcImage.drawString ctr=" + ctr, 40, 40);ctr++;
    g.dispose();
  }
  int ctr;
}

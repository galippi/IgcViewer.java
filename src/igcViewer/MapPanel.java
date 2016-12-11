/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author liptakok
 */
public class MapPanel extends javax.swing.JPanel
{
    IgcFiles igcFiles;
    public MapPanel(IgcFiles igcFiles)
    {
      this.igcFiles = igcFiles;
      ctr = 0;
      img = new java.awt.image.BufferedImage(100, 50,
                 java.awt.image.BufferedImage.TYPE_INT_ARGB);
      java.awt.Graphics2D g2 = img.createGraphics();
      g2.setColor(Color.red);
      g2.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
      g2.dispose();
      igc = new igcImage(this, null);
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
          public void run() {
            //repaint();
            igc.repaint();
          }
      }, 5000);
    }
    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        g.drawString("ctr=" + ctr + " size=" + igcFiles.size(), 20, 20);
        ctr++;
        g.drawRect(200, 200, 200, 200);
        //java.awt.Graphics gc = java.awt.GraphicsConfiguration.createCompatibleImage(600, 400);
        //java.awt.Image img = new java.awt.Image();
        g.drawImage(img, 100, 100, null);
        java.awt.image.BufferedImage img = igc.getImage();
        if (img != null)
          g.drawImage(img, 200, 200, null);
        if (ctr % 10 == 0)
        {
          igc.setImage(getWidth(), getHeight());
          igc.repaint();
        }
    }
    public void Repaint()
    {
      igc.repaint();
    }
    int ctr;
    java.awt.image.BufferedImage img;
    igcImage igc;
}

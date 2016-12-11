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
      super();
      this.igcFiles = igcFiles;
      ctr = 0;
      igc = new igcImage(this, this.igcFiles);
      if (false)
      {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
            public void run() {
              //repaint();
              igc.repaint();
            }
        }, 5000);
      }
    }
    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        g.drawString("mapPanel ctr=" + ctr + " size=" + igcFiles.size(), 5, 10);
        ctr++;
        {
          if (igc.setImage(getWidth(), getHeight()))
            igc.repaint();
        }
        if (igc.isReady())
          g.drawImage(igc.getImage(), 0, 30, null);
    }
    public void Repaint()
    {
      igc.repaint();
    }
    int ctr;
    igcImage igc;
}

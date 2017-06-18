/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import igc.GeoUtil;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import utils.dbg;

/**
 *
 * @author liptakok
 */
public class MapPanel extends javax.swing.JPanel
{
    IgcFiles igcFiles;
    int igcFileCnt = 0;
    MapImage map;
    public MapPanel(IgcFiles igcFiles)
    {
      super();
      this.igcFiles = igcFiles;
      gu = new GeoUtil();
      ctr = 0;
      igc = new igcImage(this, this.igcFiles);
      map = new MapImage(this, gu);
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
      //Register for mouse-wheel events on the text area.
      addMouseWheelListener(new MouseWheelListener() {
        public void mouseWheelMoved(MouseWheelEvent e) {
          mouseWheelMovedHandler(e);
        }
      });
    }
    public void mouseWheelMovedHandler(MouseWheelEvent e) {
          dbg.println(9, "mouseWheelMovedHandler="+e.getWheelRotation() + " x=" + e.getX() + " y=" + e.getY());
          double zoom_new = (e.getWheelRotation() < 0) ? (gu.zoom * zoom_factor) : (gu.zoom / zoom_factor);
          gu.Zoom(e.getX(), e.getY(), zoom_new);
          repaint();
    }
    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getWidth(), getHeight());
        ctr++;
        gu.setSize(getWidth(), getHeight());
        map.setGeoUtil(gu);
        if (map.isReady())
        {
          g.drawImage(map.getImage(), 0, 0, null);
        }
        igc.setGeoUtil(gu);
        if (igc.setImage(getWidth(), getHeight()))
          igc.repaint();
        if (igc.isReady())
        {
          //g.drawImage(igc.getImage(), 0, 0, null);
        }
        g.setColor(Color.BLACK);
        g.drawString("mapPanel ctr=" + ctr + " size=" + igcFiles.size() + " zoom=" + gu.zoom, 5, 10);
    }
    public void Repaint()
    {
      if ((igcFileCnt != igcFiles.size()) && (igcFiles.size() != 0))
      {
        gu.Set(igcFiles.lon_min, igcFiles.lon_max, igcFiles.lat_min, igcFiles.lat_max, getWidth(), getHeight());
        igcFileCnt = igcFiles.size();
      }
      igc.repaint();
    }
    int ctr;
    igcImage igc;
    GeoUtil gu;
    double zoom_factor = 1.3;
}

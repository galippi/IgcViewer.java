/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import igc.GeoUtil;
import igc.IgcCursor;
import igc.IgcFiles;
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
    IgcCursor igcCursor;
    int igcFileCnt = 0;
    MapImage map;
    public MapPanel(IgcCursor igcCursor)
    {
      super();
      this.igcFiles = igcCursor.igcFiles;
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
      //Register for mouse-wheel events on the map area.
      addMouseWheelListener(new MouseWheelListener() {
        public void mouseWheelMoved(MouseWheelEvent e) {
          mouseWheelMovedHandler(e);
        }
      });
      //Register for mouse-wheel events on the map area.
      addMouseMotionListener(new MouseMotionListener() {
        public void mouseMoved(MouseEvent e) {
          mouseHandler(e);
        }
        public void mouseDragged(MouseEvent e) {
          mouseHandler(e);
        }
      });      //Register for mouse events on the map area.
      addMouseListener(new MouseListener() {
        public void mouseMoved(MouseEvent e) {
          mouseHandler(e);
        }
        public void mouseClicked(MouseEvent e) {
          mouseHandler(e);
        }
        public void mousePressed(MouseEvent e) {
          mouseHandler(e);
        }
        public void mouseReleased(MouseEvent e) {
          mouseHandler(e);
        }
        public void mouseEntered(MouseEvent e) {
          mouseHandler(e);
        }
        public void mouseExited(MouseEvent e) {
          mouseHandler(e);
        }
      });
    }
    public void mouseWheelMovedHandler(MouseWheelEvent e) {
          dbg.println(9, "mouseWheelMovedHandler="+e.getWheelRotation() + " x=" + e.getX() + " y=" + e.getY());
          double zoom_new = (e.getWheelRotation() < 0) ? (gu.zoom * zoom_factor) : (gu.zoom / zoom_factor);
          gu.Zoom(e.getX(), e.getY(), zoom_new);
          repaint();
    }
    public void mouseHandler(MouseEvent e) {
      dbg.println(19, "mouseHandler "+e.toString()+" x=" + e.getX() + " y=" + e.getY() + " button=" + e.getButton());
      if (e.getID() == e.MOUSE_PRESSED)
      {
        if ((!m_capture) && (e.getButton() == e.BUTTON1))
        {
          m_capture = true;
          //CaptureMouse();
          x_start = e.getX();
          y_start = e.getY();
        }
      }else
      if ((e.getID() == e.MOUSE_MOVED) || (e.getID() == e.MOUSE_DRAGGED))
      {
        if (m_capture)
        {
          int dx, dy;
          dx = e.getX() - x_start;
          dy = e.getY() - y_start;
          if ((dx != 0) || (dy != 0))
          {
            //x_pos += dx;
            //y_pos += dy;
            x_start = e.getX();
            y_start = e.getY();
            gu.Move(dx, dy);
            //AutoPos = false;
            repaint();
          }
          dbg.dprintf(9, "IGCMapCanvas::OnMouse(EVT_MOTION x_pos=%d y_pos=%d)\n", gu.x_offs, gu.y_offs);
        }
      }else
      if (e.getID() == e.MOUSE_RELEASED)
      {
        if (m_capture)
        {
          //ReleaseMouse();
          m_capture = false;
          if ((gu.x_offs != 0) || (gu.y_offs != 0))
          {
            gu.Move(0, 0, true);
            //x_pos = 0;
            //y_pos = 0;
            repaint();
          }
        }
      }else
      { // not used event
      }
    }
    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        dbg.println(9, "MapPanel - paintComponent");
        dbg.dprintf(21, "MapPanel - paintComponent(%d, %d)\n", gu.x_offs, gu.y_offs);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        ctr++;
        gu.setSize(getWidth(), getHeight());
        map.setGeoUtil(gu);
        if (map.isReady())
        {
          dbg.dprintf(21, "MapPanel - paintComponent(%d, %d)\n", gu.x_offs, gu.y_offs);
          g.drawImage(map.getImage(), gu.x_offs, gu.y_offs, null);
        }
        igc.setGeoUtil(gu);
        if (igc.setImage(getWidth(), getHeight()))
          igc.repaint();
        if (igc.isReady())
        {
          g.drawImage(igc.getImage(), gu.x_offs, gu.y_offs, null);
        }
        g.setColor(Color.BLACK);
        g.drawString("mapPanel ctr=" + ctr + " size=" + igcFiles.size() + " zoom=" + gu.zoom, 5, 10);
        g.drawString("gu.x_offs=" + gu.x_offs + " gu.y_offs=" + gu.y_offs, 5, 20);
    }
    public void Repaint()
    {
      gu.setSize(getWidth(), getHeight());
      if ((igcFileCnt != igcFiles.size()) && (igcFiles.size() != 0))
      {
        gu.Set(igcFiles.lon_min, igcFiles.lon_max, igcFiles.lat_min, igcFiles.lat_max, getWidth(), getHeight());
        igcFileCnt = igcFiles.size();
      }
      igc.repaint();
    }
    public GeoUtil getGeoUtil()
    {
      return gu;
    }
    int ctr;
    igcImage igc;
    GeoUtil gu;
    double zoom_factor = 1.3;
    boolean m_capture = false;
    int x_start, y_start;
    //int x_pos = 0, y_pos = 0;
}

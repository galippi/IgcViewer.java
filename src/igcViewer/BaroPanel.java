/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Color;

import igc.IgcCursor;
import igc.IgcFiles;

import utils.dbg;

/**
 *
 * @author liptak
 */
public class BaroPanel extends javax.swing.JPanel
{
  public BaroPanel(IgcCursor igcCursor)
  {
    super();
    set(igcCursor.igcFiles);
    baroImage = new BaroImage(this, igcCursor.igcFiles);

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
  void set(IgcFiles igcFiles)
  {
    igcCursor.set(igcFiles);
    if (igcCursor.igcFiles.size() == 0)
    {
      timeCursorX = -1;
    }
  }
  public void mouseWheelMovedHandler(MouseWheelEvent e) {
    dbg.println(9, "BaroPanel.mouseWheelMovedHandler="+e.getWheelRotation() + " x=" + e.getX() + " y=" + e.getY());
    if (igcFiles.size() == 0)
      return; // nothing to do, if no file is loaded
    repaint();
  }
  public void mouseHandler(MouseEvent e) {
    if ((e.getID() != e.MOUSE_MOVED) || (e.getButton() != 0))
      dbg.println(9, "BaroPanel.mouseHandler "+e.toString()+" x=" + e.getX() + " y=" + e.getY() + " button=" + e.getButton());
    if (igcFiles.size() == 0)
      return; // nothing to do, if no file is loaded
    if ((e.getID() == e.MOUSE_PRESSED) && (e.getButton() == e.BUTTON1))
    {
      m_capture = true;
      updateCursor(e.getX());
    }else
    if (((e.getID() == e.MOUSE_MOVED) || (e.getID() == e.MOUSE_DRAGGED)) && m_capture)
    {
      updateCursor(e.getX());
    }else
    {
      m_capture = false;
    }
  }
  void updateCursor(int x)
  {
    timeCursorX = x;
    timeCursor.t = igcFiles.t_min + (int)((((igcFiles.t_max - igcFiles.t_min) * (double)timeCursorX) / getWidth()) + 0.5);
    dbg.dprintf(9, "BaroPanel - updateCursor: t=%d tmin=%d tmax=%d\n", (int)timeCursor.t, igcFiles.t_min, igcFiles.t_max);
    drawCursor();
    updateUIAll();
  }
  void drawCursor()
  {
    repaint();
  }
  void updateUIAll()
  {
    
  }
  @Override
  public void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    dbg.println(9, "BaroPanel - paintComponent");
    boolean repaintNeeded = false;
    if (igcFileCnt != igcFiles.size())
    { /* baro shall be repainted */
      set(igcFiles);
      igcFileCnt = igcFiles.size();
      repaintNeeded = true;
    }
    if (baroImage.setImage(getWidth(), getHeight()))
    {
      repaintNeeded = true;
    }
    if (repaintNeeded)
      baroImage.repaint();
    if (baroImage.isReady())
    {
      g.drawImage(baroImage.getImage(), 0, 0, null);
      if ((timeCursorX >= 0) && (timeCursorX < getWidth()))
      {
        g.setColor(Color.red);
        g.drawLine(timeCursorX, 0, timeCursorX, getHeight() - 1);
      }
    }
  }
  IgcCursor igcCursor;
  int igcFileCnt = 0;
  BaroImage baroImage;
  int timeCursorX = -1;
  boolean m_capture = false;
}

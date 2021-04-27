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
import java.awt.FontMetrics;

import igc.IgcCursor;
import igc.IgcFiles;

import utils.dbg;

/**
 *
 * @author liptak
 */
class RepainterBaroPanel extends igc.Repainter {
  BaroPanel parent;
  RepainterBaroPanel(BaroPanel parent) {
    this.parent = parent;
  }
  @Override
    public void repaint(boolean forced)
  {
    parent.repaint(forced);
  }
}

public class BaroPanel extends javax.swing.JPanel
{
  RepainterBaroPanel repainter;
  public BaroPanel(IgcCursor igcCursor)
  {
    super();
    cursorMain = new BaroCursor();
    cursorAux = new BaroCursor();
    repainter = new RepainterBaroPanel(this);
    set(igcCursor);
    igcCursor.set(cursorMain, cursorAux);
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
      long lastClickTime = -1;
      public void mouseClicked(MouseEvent e) {
        long currTime = System.nanoTime();
        long deltaTimeMs = (currTime - lastClickTime) / 1000000;
        if ((lastClickTime != -1) && (deltaTimeMs < 1000))
          mouseDoubleClickHandler(e);
        else
          mouseHandler(e);
        lastClickTime = currTime;
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
  void set(IgcCursor igcCursor)
  {
    this.igcCursor = igcCursor;
    igcCursor.set(repainter);
  }
  void set_(IgcFiles igcFiles)
  {
    igcCursor.set(igcFiles);
    if (igcCursor.igcFiles.size() == 0)
    { // disable cursor painting
      cursorMain.timeCursorX = -1;
      cursorAux.timeCursorX = -1;
    }
  }
  public void mouseWheelMovedHandler(MouseWheelEvent e) {
    dbg.println(9, "BaroPanel.mouseWheelMovedHandler="+e.getWheelRotation() + " x=" + e.getX() + " y=" + e.getY());
    if (igcCursor.size() == 0)
      return; // nothing to do, if no file is loaded
    repaint();
  }
  BaroCursor cursorLast;
  public void mouseDoubleClickHandler(MouseEvent e)
  {
    dbg.println(9, "BaroPanel.mouseDoubleClickHandler x=" + e.getX() + " y=" + e.getY() + " button=" + e.getButton() + " e=" + e.toString());
    cursorAux.invalidate();
    drawCursor();
  }
  public void mouseHandler(MouseEvent e) {
    if ((e.getID() != e.MOUSE_MOVED) || (e.getButton() != 0))
      dbg.println(9, "BaroPanel.mouseHandler "+e.toString()+" x=" + e.getX() + " y=" + e.getY() + " button=" + e.getButton());
    if (igcCursor.size() == 0)
    {
      cursorLast = null;
      return; // nothing to do, if no file is loaded
    }

    if (e.getID() == e.MOUSE_PRESSED)
    {
      if (cursorLast != null)
      {
        cursorLast.m_capture = false;
        cursorLast = null;
      }
      if (e.getButton() == e.BUTTON1)
      {
        cursorLast = cursorMain;
      }else if (e.getButton() == e.BUTTON3)
      {
        cursorLast = cursorAux;
      }else
      {
        cursorLast.m_capture = false;
        cursorLast = null;
        return;
      }
      cursorLast.m_capture = true;
      updateCursor(cursorLast, e.getX());
    }else
    if (((e.getID() == e.MOUSE_MOVED) || (e.getID() == e.MOUSE_DRAGGED)) && (cursorLast != null) && (cursorLast.m_capture))
    {
      updateCursor(cursorLast, e.getX());
    }else
    {
      if (cursorLast != null)
        cursorLast.m_capture = false;
      cursorLast = null;
    }
  }
  void updateCursor(BaroCursor cursor, int x)
  {
    cursor.timeCursorX = x;
    cursor.timeCursor = igcCursor.igcFiles.t_min + (int)((((igcCursor.igcFiles.t_max - igcCursor.igcFiles.t_min) * (double)cursor.timeCursorX) / getWidth()) + 0.5);
    dbg.dprintf(9, "BaroPanel - updateCursor: t=%d tmin=%d tmax=%d\n", cursor.timeCursor, igcCursor.igcFiles.t_min, igcCursor.igcFiles.t_max);
    drawCursor();
    igcCursor.repaint(false);
    updateUIAll();
  }
  void drawCursor()
  {
    repaint();
  }
  void updateUIAll()
  {
    
  }
  public void repaint(boolean forced)
  {
    if (forced)
    {
      baroImage.repaint();
    }
    repaint();
  }
  @Override
  public void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    dbg.println(9, "BaroPanel - paintComponent");
    boolean repaintNeeded = false;
    if (igcFileCnt != igcCursor.size())
    { /* baro shall be repainted */
      igcFileCnt = igcCursor.size();
      if (igcFileCnt == 0)
      { // disable cursor painting
        cursorMain.timeCursorX = -1;
        cursorAux.timeCursorX = -1;
      }
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
      if ((cursorMain.timeCursorX >= 0) && (cursorMain.timeCursorX < getWidth()))
      {
        g.setColor(Color.red);
        g.drawLine(cursorMain.timeCursorX, 0, cursorMain.timeCursorX, getHeight() - 1);
        // xxx
        for (int i=0; i < igcCursor.size(); i++)
        {
            igc.igc igcFile = igcCursor.get(i);
            int ptIdx = igcFile.getIdx(igcCursor.getTime());
            int h = igcFile.getAltitude(ptIdx);
            String val = "" + h + " m";
            g.setColor(igcFile.color);
            FontMetrics metrics = g.getFontMetrics();
            int fontHgt = metrics.getHeight();
            int textWidth = metrics.stringWidth(val);
            //g.setBackground(Color.WHITE);
            int x = cursorMain.timeCursorX - (textWidth / 2);
            if (x < 0)
                x = 0;
            else if (x > (getWidth() - textWidth))
                x= (getWidth() - textWidth);
            int y = baroImage.getY(h) + (fontHgt / 2);
            if (y > (getHeight() - 10))
                y = getHeight() - 10;
            g.clearRect(x, y - fontHgt, textWidth, fontHgt);
            g.drawRect(x, y - fontHgt, textWidth + 1, fontHgt);
            g.drawString(val, x + 1, y - 1);
        }
      }
      if ((cursorAux.timeCursorX >= 0) && (cursorAux.timeCursorX < getWidth()))
      {
        g.setColor(Color.blue);
        g.drawLine(cursorAux.timeCursorX, 0, cursorAux.timeCursorX, getHeight() - 1);
        // xxx
      }
    }
  }
  public static BaroCursor getMainCursor()
  {
    return cursorMain;
  }
  IgcCursor igcCursor;
  int igcFileCnt = 0;
  BaroImage baroImage;
  static BaroCursor cursorMain;
  static BaroCursor cursorAux;
}

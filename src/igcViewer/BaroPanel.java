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
import utils.dbg;

/**
 *
 * @author liptak
 */
public class BaroPanel extends javax.swing.JPanel
{
  public BaroPanel(IgcFiles igcFiles)
  {
    super();
    this.igcFiles = igcFiles;
    baroImage = new BaroImage(this, igcFiles);

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
    dbg.println(9, "BaroPanel.mouseWheelMovedHandler="+e.getWheelRotation() + " x=" + e.getX() + " y=" + e.getY());
    repaint();
  }
  public void mouseHandler(MouseEvent e) {
    dbg.println(19, "BaroPanel.mouseHandler "+e.toString()+" x=" + e.getX() + " y=" + e.getY() + " button=" + e.getButton());
  }
  @Override
  public void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    dbg.println(9, "BaroPanel - paintComponent");
    boolean repaintNeeded = false;
    if (igcFileCnt != igcFiles.size())
    { /* baro shall be repainted */
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
    }
  }
  IgcFiles igcFiles;
  int igcFileCnt = 0;
  BaroImage baroImage;
}

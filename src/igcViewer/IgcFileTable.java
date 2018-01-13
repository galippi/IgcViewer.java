/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import igc.IgcCursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import utils.dbg;

/**
 *
 * @author liptakok
 */
public class IgcFileTable extends javax.swing.JTable
{
  java.awt.PopupMenu popup;
  public IgcFileTable(IgcCursor igcCursor)
  {
    super();
    this.igcCursor = igcCursor;
    igcCursor.set(this);
    
    addMouseListener(new MouseListener() {
        public void mouseMoved(MouseEvent e) {
        }
        public void mouseClicked(MouseEvent e) {
          mouseHandler(e);
        }
        public void mousePressed(MouseEvent e) {
          mouseHandler(e);
        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    });

    popup = new java.awt.PopupMenu("demo");
    java.awt.MenuItem item;
    java.awt.event.ActionListener changeColorMenuListener;
    changeColorMenuListener = new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent event) {
        dbg.dprintf(9, "Popup menu item ["
            + event.getActionCommand() + "] was pressed.");
        popupMenuHandler(event);
      }
    };
    popup.add(item = new java.awt.MenuItem("Change color"));
    item.addActionListener(changeColorMenuListener);
    popup.add(item = new java.awt.MenuItem("About"));
    item.addActionListener(changeColorMenuListener);
    add(popup);
  }

  void mouseHandler(MouseEvent evt)
  {
    dbg.println(9, "IgcFileTable - MouseClicked " + evt.toString());
    dbg.println(9, "  findComponentAt="+findComponentAt(evt.getX(), evt.getY()).toString());
    if (evt.getID() == evt.MOUSE_CLICKED)
    {
       int rowAtPoint = rowAtPoint(evt.getPoint());
       int colAtPoint = columnAtPoint(evt.getPoint());
       dbg.dprintf(9, "  rowAtPoint=%d colAtPoint=%d\n", rowAtPoint, colAtPoint);
       if (rowAtPoint > -1) {
        setRowSelectionInterval(rowAtPoint, rowAtPoint);
       }
       if (evt.getButton() == evt.BUTTON3)
       {
         popup.show(this, evt.getX(), evt.getY());
       }
    }
  }
  
  void popupMenuHandler(java.awt.event.ActionEvent event)
  {
    dbg.println(9, "popupMenuHandler event="+event.toString());
    dbg.println(9, "popupMenuHandler event.getActionCommand="+event.getActionCommand());
  }

  void updateStaticData()
  {
    dbg.dprintf(9, "IgcFileTable.updateStaticData\n");
    if (igcCursor.size() != getRowCount())
    {
      javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)getModel();
      model.setNumRows(igcCursor.size());
    }
    for (int i=0; i < igcCursor.size(); i++)
    {
      setValueAt(igcCursor.get(i).getCompetitionId(), i, 0);
      setValueAt(igcCursor.get(i).getPilotsName(), i, 1);
      setValueAt(igcCursor.get(i).getGliderId(), i, 2);
      setValueAt(igcCursor.get(i).getGliderType(), i, 3);
    }
  }

  @Override
  public void repaint()
  {
    dbg.dprintf(9, "IgcFileTable.repaint\n");
    if (igcCursor != null)
    {
      for (int i=0; i < igcCursor.size(); i++)
      {
        igc.igc igcFile = igcCursor.get(i);
        int idx = igcFile.getIdx(igcCursor.timeCursor);
        setValueAt(igcFile.getAltitude(idx), i, 4);
        setValueAt(igcFile.getGroundSpeed_km_per_h(idx), i, 5);
        setValueAt((int)(igcFile.getDir(idx) * 180 / Math.PI), i, 6);
        setValueAt(igcFile.getVario(idx), i, 7);
      }
    }
    super.repaint();
  }
  IgcCursor igcCursor;
}

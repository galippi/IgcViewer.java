/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import igc.IGC_point;
import igc.IgcCursor;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import utils.dbg;

/**
 *
 * @author liptakok
 */
class ColorCellRenderer extends javax.swing.table.DefaultTableCellRenderer
{
  public ColorCellRenderer()
  {
    super();
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    color = (java.awt.Color)value;
    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    component.setBackground(color);
    return component;
  }
  java.awt.Color color;
}

class RepainterJTable extends igc.Repainter {
  IgcFileTable parent;
  RepainterJTable(IgcFileTable parent) {
    this.parent = parent;
  }
  @Override
    public void repaint(boolean forced)
  {
    parent.repaint(forced);
  }
}

class MyPopupMenu extends java.awt.PopupMenu
{
  MyPopupMenu(String label)
  {
    super(label);
  }
  @Override
  public void show(Component origin, int x, int y)
  {
    this.x = x; this.y = y;
    super.show(origin, x, y);
  }
  int x;
  int y;
}

public class IgcFileTable extends javax.swing.JTable
{
  MyPopupMenu popup;
  RepainterJTable repainter;
  IgcFileTableColumnArray columns;
  boolean[] canEdit;
  public IgcFileTable(IgcCursor igcCursor)
  {
    super();
    this.igcCursor = igcCursor;
    repainter = new RepainterJTable(this);
    columns = new IgcFileTableColumnArray();
    int numCols =  IgcViewerPrefs.get("FileTable", "ColNum", -1);
    if (numCols > 0)
      colList = new int[numCols];
    for(int i = 0; i < numCols; i++)
    {
      String colName = IgcViewerPrefs.get("FileTable", "ColName" + i, null);
      if (colName != null)
      {
        int colIdx = columns.getColIdx(colName);
        if (colIdx >= 0)
          colList[i] = colIdx;
        else
          numCols = -1; // table setting is invalid -> set default set
      }else
        numCols = -1; // table setting is invalid -> set default set
    }
    if (numCols < 0)
    { // table is not yet set -> set default column set
      numCols = columns.size();
      colList = new int[numCols];
      for (int i = 0; i < numCols; i++)
      {
        colList[i] = i;
      }
    }
    String[] names = new String[colList.length];
    canEdit = new boolean[colList.length];
    for (int i = 0; i < colList.length; i++)
    {
      names[i] = columns.get(colList[i]).getColName();
      canEdit[i] = columns.get(colList[i]).isEditable();
    }
    setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        names
    ) {
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            dbg.println(9, "isCellEditable rowIndex=" + rowIndex + " columnIndex=" + columnIndex);
            return canEdit[columnIndex];
        }
    });
    setEditingColumn(0);
    setEditingRow(0);
    setMaximumSize(new java.awt.Dimension(1000, 1000));
    setMinimumSize(new java.awt.Dimension(100, 100));
    setPreferredSize(new java.awt.Dimension(200, 120));
    igcCursor.set(repainter);
    
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

    popup = new MyPopupMenu("demo");
    java.awt.MenuItem item;
    java.awt.event.ActionListener popupMenuListener;
    popupMenuListener = new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent event) {
        dbg.println(9, "Popup menu item ["
            + event.getActionCommand() + "] was pressed.");
        popupMenuHandler(event);
      }
    };
    popup.add(item = new java.awt.MenuItem("Change color"));
    item.addActionListener(popupMenuListener);
    popup.add(item = new java.awt.MenuItem("Close file"));
    item.addActionListener(popupMenuListener);
    popup.add(item = new java.awt.MenuItem("Close all file"));
    item.addActionListener(popupMenuListener);
    popup.add(item = new java.awt.MenuItem("Add column"));
    item.addActionListener(popupMenuListener);
    popup.add(item = new java.awt.MenuItem("Remove column"));
    item.addActionListener(popupMenuListener);
    popup.add(item = new java.awt.MenuItem("About"));
    item.addActionListener(popupMenuListener);
    add(popup);

    setColorCellRenderer("Track color");
    setColorCellRenderer("Task color");
  }
  void setColorCellRenderer(String colName)
  {
    int colIdx;
    colIdx = columns.getColIdx(colName);
    if (colIdx >= 0)
      getColumnModel().getColumn(colIdx).setCellRenderer(new ColorCellRenderer());
  }
  void saveColumnSet()
  {
    int colNum = getColumnCount();
    IgcViewerPrefs.put("FileTable", "ColNum", colNum);
    for(int i = 0; i < colNum; i++)
    {
      IgcViewerPrefs.put("FileTable", "ColName" + i, getColumnName(i));
    }
  }
  void setupTable()
  {
    getModel().addTableModelListener(
      new javax.swing.event.TableModelListener()
      {
          public void tableChanged(javax.swing.event.TableModelEvent evt) 
          {
            tableChangedHandler(evt);
          }
    });
  }

  void tableChangedHandler(javax.swing.event.TableModelEvent evt)
  {
    dbg.println(9, "tableChangedHandler evt=" + evt);
    dbg.println(9, "  UPDATE=" + evt.UPDATE);
    if ((evt.getType() == evt.UPDATE) && (evt.getColumn() == colIgcTimeOffset))
    {
      if ((evt.getFirstRow() >= 0) && (evt.getFirstRow() == evt.getLastRow()))
      {
        String str = getValueAt(evt.getFirstRow(), evt.getColumn()).toString();
        try
        {
          int offset = Integer.parseInt(str);
          if (offset != igcCursor.get(evt.getFirstRow()).getTimeOffset())
          {
            igcCursor.setTimeOffset(evt.getFirstRow(), offset);
            igcCursor.repaint(true);
          }
        }catch (NumberFormatException e)
        {
          //error handling
        }
      }
    }
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
      if (rowAtPoint >= 0) {
        setRowSelectionInterval(rowAtPoint, rowAtPoint);
        if (evt.getButton() == evt.BUTTON3)
        {
          if ((colAtPoint == colTrackColor) || (colAtPoint == colTaskColor))
          {
            java.awt.Color newColor = 
              javax.swing.JColorChooser.showDialog(
                this,
                (colAtPoint == colTrackColor) ?
                  "Choose Track Color" : "Choose Task Color",
                igcCursor.get(rowAtPoint).color);
            if (newColor != null) {
              if (colAtPoint == colTrackColor)
                igcCursor.get(rowAtPoint).color = newColor;
              else
                igcCursor.get(rowAtPoint).colorTask = newColor;
              igcCursor.repaint(true);
            }
          }else
          {
            popup.show(this, evt.getX(), evt.getY());
          }
        }
      }
    }
  }
  
  void popupMenuHandler(java.awt.event.ActionEvent event)
  {
    dbg.println(17, "popupMenuHandler event="+event.toString());
    java.awt.Point pt = new java.awt.Point(popup.x, popup.y);
    int colAtPoint = columnAtPoint(pt);
    dbg.println(9, "popupMenuHandler event.getActionCommand="+event.getActionCommand() + " colAtPoint=" + colAtPoint);
    switch(event.getActionCommand())
    {
      case "Add column":
        break;
      case "Remove column":
        break;
      default:
         dbg.println(1, "popupMenuHandler invalid event="+event.toString());
        break;
    }
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
      igc.igc igcFile = igcCursor.get(i);
      if (false)
      {
        setValueAt(igcFile.getCompetitionId(), i, colCompetitionId);
        setValueAt(igcFile.getPilotsName(), i, colPilotName);
        setValueAt(igcFile.getGliderId(), i, colGliderId);
        setValueAt(igcFile.getGliderType(), i, colGliderType);
        setValueAt(igcFile.color, i, colTrackColor);
        setValueAt(igcFile.colorTask, i, colTaskColor);
        setValueAt(igcFile.getTimeOffset(), i, colIgcTimeOffset);
      }else
      {
        for(int j = 0; j < colList.length; j++)
        {
          if (columns.get(colList[j]).isStaticField())
            setValueAt(columns.get(colList[j]).getValue(igcCursor, i, igcFile, 0), i, colList[j]);
        }
      }
    }
  }

  @Override
  public void repaint()
  {
    dbg.dprintf(9, "IgcFileTable.repaint\n");
    if (igcCursor != null)
    {
      int selRow = getSelectedRow();
      dbg.dprintf(9, "  selRow = %d\n", selRow);
      IGC_point ptRef;
      if (selRow >= 0)
      {
        igc.igc igcFileRef = igcCursor.get(selRow);
        ptRef = igcFileRef.getIgcPoint(igcFileRef.getIdx(igcCursor.getTime()));
      }
      for (int i=0; i < igcCursor.size(); i++)
      {
        igc.igc igcFile = igcCursor.get(i);
        int idx = igcFile.getIdx(igcCursor.getTime());
        if (false)
        {
          setValueAt(igcFile.getAltitude(idx), i, colAltitude);
          double v = igcFile.getGroundSpeed(idx);
          setValueAt((int)(v * 3.6), i, colSpeed);
          setValueAt((int)(igcFile.getDir(idx) * 180 / Math.PI), i, colDirection);
          double w = igcFile.getVario(idx);
          setValueAt(w, i, colVerticalSpeed);
          if (Math.abs(w) > 1e-3)
            setValueAt(String.format("%.1f", (-v / w)), i, colLD);
          else
            setValueAt("oo", i, colLD);
          if ((selRow >= 0) && (i != selRow))
          {
            igc.igc igcFileRef = igcCursor.get(selRow);
            ptRef = igcFileRef.getIgcPoint(igcFileRef.getIdx(igcCursor.getTime()));
            IGC_point pt = igcFile.get(idx);
            double distance = ptRef.getDistance(pt);
            String distanceStr;
            if (distance > 5000)
              distanceStr = String.format("%.2f", distance / 1000) + "km";
            else
              distanceStr = String.format("%.0f", distance) + "m";
            setValueAt(distanceStr, i, colDistance);
          }else
            setValueAt("", i, colDistance);
        }else
        {
          for(int j = 0; j < colList.length; j++)
          {
            if (!columns.get(colList[j]).isStaticField())
              setValueAt(columns.get(colList[j]).getValue(igcCursor, i, igcFile, idx, selRow), i, colList[j]);
          }
        }
      }
    }
    super.repaint();
  }
  public void repaint(boolean forced)
  {
    if (forced)
    {
      updateStaticData();
    }
    repaint();
  }
  IgcCursor igcCursor;
  int[] colList;
  int colCompetitionId = 0;
  int colPilotName = 1;
  int colGliderId = 2;
  int colGliderType = 3;
  int colAltitude = 4;
  int colSpeed = 5;
  int colDirection = 6;
  int colVerticalSpeed = 7;
  int colTrackColor = 8;
  int colTaskColor = 9;
  int colDistance = 10;
  int colLD = 11;
  int colIgcTimeOffset = 12;
}

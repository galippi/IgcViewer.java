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
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
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

class ColumnSet
{
  ColumnSet(IgcFileTableColumnArray columns)
  {
    colIdx = new TreeMap();
    this.columns = columns;
    load();
  }
  void set(int[] colList)
  {
    this.colList = colList;
    updateColIdx();
  }
  void updateColIdx()
  {
    colIdx.clear();
    for(int i = 0; i < colList.length; i++)
    {
      colIdx.put(columns.get(colList[i]).colName, i);
    }
  }
  int get(String colName)
  {
    try
    {
      return (int)colIdx.get(colName);
    }catch(Exception e)
    {
      return -1;
    }
  }
  int[] getColList()
  {
    return colList;
  }
  void load()
  {
    int numCols =  IgcViewerPrefs.get("FileTable", "ColNum", -1);
    if (numCols > 0)
      colList = new int[numCols];
    for(int i = 0; i < numCols; i++)
    {
      String colName = IgcViewerPrefs.get("FileTable", "ColName" + i, null);
      if (colName != null)
      {
        int idx = columns.getColIdx(colName);
        if (idx >= 0)
          colList[i] = idx;
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
  }
  void save()
  {
    
  }
  int[] colList;
  TreeMap colIdx;
  IgcFileTableColumnArray columns;
}

public class IgcFileTable extends javax.swing.JTable
{
  MyPopupMenu popup;
  RepainterJTable repainter;
  IgcFileTableColumnArray columns;
  boolean[] canEdit;
  ColumnSet columnSet;
  public IgcFileTable(IgcCursor igcCursor)
  {
    super();
    this.igcCursor = igcCursor;
    repainter = new RepainterJTable(this);
    columns = new IgcFileTableColumnArray();
    columnSet = new ColumnSet(columns);
    colList = columnSet.getColList();
    setColumnHeader();
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
    popup.add(item = new java.awt.MenuItem("File properties"));
    item.addActionListener(popupMenuListener);
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
  }
  void setColumnHeader()
  {
    columnSet.set(colList);
    String[] names = new String[colList.length];
    canEdit = new boolean[colList.length];
    for (int i = 0; i < colList.length; i++)
    {
      names[i] = columns.get(colList[i]).getColName();
      canEdit[i] = columns.get(colList[i]).isEditable();
    }
    if (this.getModel() == null)
    {
      if (false)
      {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            names
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                dbg.println(9, "isCellEditable rowIndex=" + rowIndex + " columnIndex=" + columnIndex);
                return canEdit[columnIndex];
            }
        };
      }
      javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(names, 0);
      setModel(model);
    }else
    {
      javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)this.getModel();
      //model.setColumnIdentifiers(names);
      //javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(names, 0);
      //setModel(model);
      model.setColumnCount(colList.length);
      for (int i = 0; i < colList.length; i++)
        this.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(columns.get(colList[i]).getColName());
    }
    colTrackColor = columnSet.get("Track color");
    colTaskColor = columnSet.get("Task color");
    staticDataUpdateIsNeeded = true;
    //setColorCellRenderer("Track color");
    //setColorCellRenderer("Task color");
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
    int colNum = colList.length;
    IgcViewerPrefs.put("FileTable", "ColNum", colNum);
    for(int i = 0; i < colNum; i++)
    {
      IgcViewerPrefs.put("FileTable", "ColName" + i, columns.get(colList[i]).getColName());
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
    return;
    if ((evt.getType() == evt.UPDATE) && (evt.getColumn() == columnSet.get("Time offset")))
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
    int rowAtPoint = rowAtPoint(pt);
    dbg.println(9, "popupMenuHandler event.getActionCommand="+event.getActionCommand() + " colAtPoint=" + colAtPoint);
    switch(event.getActionCommand())
    {
      case "Add column":
        ColumnSelectorDialog csd = new ColumnSelectorDialog(IgeViewerUI.mainWindow, this, columns, colList);
        csd.setVisible(true);
        break;
      case "Remove column":
        if ((colAtPoint >= 0) && (colList.length > 1) && (colAtPoint < colList.length))
        { // remove column only if minimum 1 column remains
          int[] colListNew = new int[colList.length - 1];
          for(int i = 0; i < colList.length; i++)
          { // copy remained columns to the new list
            if (i < colAtPoint)
              colListNew[i] = colList[i];
            else if (i > colAtPoint)
              colListNew[i - 1] = colList[i];
          }
          colList = colListNew;
          //javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)this.getModel();
          //model.setColumnCount(colList.length);
          //this.removeColumn(this.getColumnModel().getColumn(colAtPoint));
          this.removeColumn(this.getColumnModel().getColumn(colList.length));
          setColumnHeader();
          invalidate();
        }
        break;
      case "File properties":
        FilePropertiesDialog fpd = new FilePropertiesDialog(IgeViewerUI.mainWindow, igcCursor.get(rowAtPoint));
        fpd.setVisible(true);
        break;
      default:
         dbg.println(1, "popupMenuHandler invalid event="+event.toString());
        break;
    }
  }

  public void columnSelectorDialogOkHandler(int[] sel)
  {
    //this.setVisible(false);
    //final JTable table = this;
          int[] colListNew = new int[sel.length];
          for(int i = 0; i < colListNew.length; i++)
          {
            colListNew[i] = sel[i];
          }
          colList = colListNew;
          //while(sel.length < getColumnModel().getColumnCount())
            //removeColumn(getColumnModel().getColumn(getColumnModel().getColumnCount() - 1));
          while(sel.length > getColumnModel().getColumnCount())
            getColumnModel().addColumn(new TableColumn());
          for(int i = 0; i < getColumnModel().getColumnCount(); i++)
          {
            TableColumn column = getColumnModel().getColumn(i);
            if (i < colListNew.length)
            {
              column.setMinWidth(10);
              column.setMaxWidth(100);
              column.setWidth(20);
            }else
            {
              column.setMinWidth(0);
              column.setMaxWidth(0);
              column.setWidth(0);
            }
          }
          setColumnHeader();
          //this.setVisible(true);
          invalidate();
          return;
    try
    {
    //SwingUtilities.invokeAndWait(new Runnable() {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {

          int[] colListNew = new int[sel.length];
          for(int i = 0; i < colListNew.length; i++)
          {
            colListNew[i] = sel[i];
          }
          colList = colListNew;
          //while(sel.length < getColumnModel().getColumnCount())
            //removeColumn(getColumnModel().getColumn(getColumnModel().getColumnCount() - 1));
          while(sel.length > getColumnModel().getColumnCount())
            getColumnModel().addColumn(new TableColumn());
          setColumnHeader();
          //this.setVisible(true);
          invalidate();
        }
    });
    }catch (Exception e)
    {
      dbg.println(1, "columnSelectorDialogOkHandler exception e="+e.toString());
    }
  }

  @Override
  public Object getValueAt(int row, int column)
  {
    if (column >= 8)
      dbg.println(19, "getValueAt column="+column);
    if (column < colList.length)
    {
      return getModel().getValueAt(row, column);
      //return super.getValueAt(row, column);
    }
    else
    {
      dbg.println(2, "getValueAt over column="+column);
      return "";
    }
  }
  @Override
  public void setValueAt(Object data, int row, int column)
  {
    if (column >= 8)
      dbg.println(19, "setValueAt column="+column);
    if (column < colList.length)
    {
      getModel().setValueAt(data, row, column);
      return;
    }
    else
    {
      dbg.println(2, "setValueAt over column="+column);
      return;
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
      for(int j = 0; j < colList.length; j++)
      {
        if (columns.get(colList[j]).isStaticField())
          setValueAt(columns.get(colList[j]).getValue(igcCursor, i, igcFile, 0), i, j);
      }
    }
  }
  void updateData()
  {
    if (igcCursor != null)
    {
      if (staticDataUpdateIsNeeded)
      {
        updateStaticData();
        staticDataUpdateIsNeeded = false;
      }
      int selRow = getSelectedRow();
      dbg.dprintf(9, "  selRow = %d\n", selRow);
      for (int i=0; i < igcCursor.size(); i++)
      {
        igc.igc igcFile = igcCursor.get(i);
        int idx = igcFile.getIdx(igcCursor.getTime());
        for(int j = 0; j < colList.length; j++)
        {
          if (!columns.get(colList[j]).isStaticField())
            setValueAt(columns.get(colList[j]).getValue(igcCursor, i, igcFile, idx, selRow), i, j);
        }
      }
    }
  }

  public void repaint(boolean forced)
  {
    if (forced)
    {
      staticDataUpdateIsNeeded = true;
    }
    updateData();
  }
  boolean staticDataUpdateIsNeeded;
  IgcCursor igcCursor;
  int[] colList;
  int colTrackColor = -1;
  int colTaskColor = -1;
  int colIgcTimeOffset = -1;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import igc.IgcCursor;
import utils.dbg;

/**
 *
 * @author liptakok
 */
public class IgcFileTable extends javax.swing.JTable
{
  public IgcFileTable(IgcCursor igcCursor)
  {
    super();
    this.igcCursor = igcCursor;
    igcCursor.set(this);
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
      }
    }
    super.repaint();
  }
  IgcCursor igcCursor;
}

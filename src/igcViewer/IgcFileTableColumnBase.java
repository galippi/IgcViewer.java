/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import igc.IgcCursor;

/**
 *
 * @author liptakok
 */
public class IgcFileTableColumnBase {
  public IgcFileTableColumnBase(String colName)
  {
    this.colName = colName;
  }
  public String getColName()
  {
    return colName;
  }
  public Object getValue(igc.igc igcFile)
  {
    return "not implemented";
  }
  public Object getValue(igc.igc igcFile, int ptIdx)
  {
    return getValue(igcFile);
  }
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return getValue(igcFile, ptIdx);
  }
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx, int selRow)
  {
    return getValue(igcCursor, fileIdx, igcFile, ptIdx);
  }
  public boolean isEditable()
  {
    return false;
  }
  public boolean isStaticField()
  {
    return true;
  }
  final String colName;
}

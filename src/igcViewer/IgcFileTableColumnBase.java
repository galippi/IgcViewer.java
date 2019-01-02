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
  public String getValue(IgcCursor igcCursor, int fileIdx)
  {
    return "not implemented";
  }
  public boolean isEditable()
  {
    return false;
  }
  final String colName;
}

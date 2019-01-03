/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

/**
 *
 * @author liptakok
 */
public class IgcFileTableColumnDynamicBase extends IgcFileTableColumnBase
{
  IgcFileTableColumnDynamicBase(String colName)
  {
    super(colName);
  }
  public boolean isStaticField()
  {
    return false;
  }
}

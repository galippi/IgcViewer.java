/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc;

/**
 *
 * @author liptak
 */
public class IGC_Altitude {
  public IGC_Altitude(int val)
  {
    h = val;
  }
  public IGC_Altitude(IGC_Altitude val)
  {
    h = val.h;
  }
  public void min(IGC_Altitude val)
  {
    if (val.h < h)
      h = val.h;
  }
  public void max(IGC_Altitude val)
  {
    if (val.h > h)
      h = val.h;
  }
  public int val()
  {
    return h;
  }
  public int h;
}


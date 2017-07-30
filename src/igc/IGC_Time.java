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
public class IGC_Time {
    public long t;
    public IGC_Time(long val)
    {
      t = val;
    }
    public IGC_Time(IGC_Time val)
    {
      t = val.t;
    }
    public void min(IGC_Time val)
    {
      if (val.t < t)
          t = val.t;
    }
    public void max(IGC_Time val)
    {
      if (val.t > t)
          t = val.t;
    }
    public boolean isSmaller(IGC_Time val)
    {
      return t < val.t;
    }
    public boolean isSmallerOrEqual(IGC_Time val)
    {
      return t <= val.t;
    }
    public boolean isLarger(IGC_Time val)
    {
      return t > val.t;
    }
    public boolean isLargerOrEqual(IGC_Time val)
    {
      return t >= val.t;
    }
}

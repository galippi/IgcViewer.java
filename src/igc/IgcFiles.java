/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc;

import java.util.ArrayList;

/**
 *
 * @author liptak
 */
public class IgcFiles
{
  ArrayList<igc> igcFiles;
  public IgcFiles()
  {
    igcFiles = new ArrayList<>();
    reinit();
  }
  public void add(igc file)
  {
    igcFiles.add(file);
    updateLimits(size() - 1);
  }
  public int size()
  {
    return igcFiles.size();
  }
  public void reinit()
  {
    igcFiles.clear();
    resetLimits();
  }
  public igc get(int idx)
  {
    return igcFiles.get(idx);
  }
  public void close(int idx)
  {
      igcFiles.remove(idx);
      updateLimits();
  }
  void updateLimits()
  {
      for (int i = 0; i < size(); i++)
      {
          updateLimits(i);
      }
  }
  void resetLimits()
  {
    t_min = 0;
    t_max = 0;
    lon_min = 0;
    lon_max = 0;
    lat_min = 0;
    lat_max = 0;
    alt_min = 0;
    alt_max = 0;
  }
  void updateLimits(int i)
  {
    igc file = get(i);
    if (i == 0)
    {
      lon_min = file.lon_min.val();
      lon_max = file.lon_max.val();
      lat_min = file.lat_min.val();
      lat_max = file.lat_max.val();
      t_min = (int)file.t_min.t;
      t_max = (int)file.t_max.t;
      alt_min = file.alt_min.val();
      alt_max = file.alt_max.val();
    }else
    {
      lon_min = Math.min(lon_min, file.lon_min.val());
      lon_max = Math.max(lon_max, file.lon_max.val());
      lat_min = Math.min(lat_min, file.lat_min.val());
      lat_max = Math.max(lat_max, file.lat_max.val());
      if ((int)file.t_min.t < t_min)
        t_min = (int)file.t_min.t;
      if ((int)file.t_max.t > t_max)
        t_max = (int)file.t_max.t;
      if (file.alt_min.val() < alt_min)
        alt_min = file.alt_min.val();
      if (file.alt_max.val() > alt_max)
        alt_max = file.alt_max.val();
    }
  }
  public void setTimeOffset(int idx, int offset)
  {
    get(idx).setTimeOffset(offset);
  }
  public int t_min, t_max;
  public double lon_min, lon_max;
  public double lat_min, lat_max;
  public int alt_min, alt_max;
  static final double SNA_doubleLimit = 1e98;
  static final public double SNA_double = (SNA_doubleLimit * 2);
  static public boolean isSna(double val)
  {
    return (val > SNA_doubleLimit);
  }
}

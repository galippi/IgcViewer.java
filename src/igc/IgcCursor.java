/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc;

import igc.IgcFiles;
import java.util.ArrayList;
import utils.dbg;

/**
 *
 * @author liptak
 */
public class IgcCursor {
  public IgcCursor()
  {
    uiParents = new ArrayList<java.awt.Component>();
  }
  public IgcCursor(IgcFiles igcFiles)
  {
    this();
    set(igcFiles);
  }
  public int size()
  {
    return igcFiles.size();
  }
  public void set(IgcFiles igcFiles)
  {
    this.igcFiles = igcFiles;
    if (igcFiles.size() < 1)
    { // no file is loaded
      timeCursor = -1;
    }else
    if (timeCursor < igcFiles.t_min)
    { // file is loaded -> set the cursor to the beginning of the file
      timeCursor = igcFiles.t_min;
    }else
    if (timeCursor > igcFiles.t_max)
    {
      timeCursor = igcFiles.t_max;
    }
  }
  public void set(java.awt.Component parent)
  {
    uiParents.add(parent);
  }
  public void repaint(int time)
  {
    timeCursor = time;
    for (int i = 0; i < uiParents.size(); i++)
    {
      uiParents.get(i).repaint();
    }
  }
  public igc get(int idx)
  {
    return igcFiles.get(idx);
  }
  public IgcFiles igcFiles;
  public int timeCursor = -1;
  ArrayList<java.awt.Component> uiParents;
}

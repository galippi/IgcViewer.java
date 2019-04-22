/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc;

import igcViewer.BaroCursor;
import java.util.ArrayList;

/**
 *
 * @author liptak
 */
public class IgcCursor {
  public IgcCursor()
  {
    uiParents = new ArrayList<>();
  }
  public IgcCursor(IgcFiles igcFiles)
  {
    this();
    set(igcFiles);
  }
  public void reinit()
  {
    this.igcFiles.reinit();
    setTime(-1);
    repaint(true);
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
      setTime(-1);
    }else
    if (getTime() < igcFiles.t_min)
    { // file is loaded -> set the cursor to the beginning of the file
      setTime(igcFiles.t_min);
    }else
    if (getTime() > igcFiles.t_max)
    {
      setTime(igcFiles.t_max);
    }
  }
  public void set(Repainter parent)
  {
    uiParents.add(parent);
  }
  public void set(BaroCursor cursorMain, BaroCursor cursorAux)
  {
    this.cursorMain = cursorMain;
    this.cursorAux = cursorAux;
  }
  public void add(igc file)
  {
    igcFiles.add(file);
    setTime(0);
    repaint(true);
  }
  public void repaint(boolean forced)
  {
    for (int i = 0; i < uiParents.size(); i++)
    {
      uiParents.get(i).repaint(forced);
    }
  }
  public void repaint(int time)
  {
    setTime(time);
    repaint(false);
  }
  public igc get(int idx)
  {
    return igcFiles.get(idx);
  }
  public void setTimeOffset(int idx, int offset)
  {
    igcFiles.setTimeOffset(idx, offset);
  }
  public void close(int idx)
  {
      if (size() == 1)
      {
          reinit();
      }else
      {
          igcFiles.close(idx);
          setTime(0);
          repaint(true);
      }
  }
  public int getTime(BaroCursor cursor)
  {
    return cursor != null ? cursor.timeCursor : -1;
  }
  public int getTime()
  {
    return getTime(cursorMain);
  }
  public void setTime(int t)
  {
    if (cursorMain != null)
      cursorMain.timeCursor = t;
  }
  public int getTimeAux()
  {
    return getTime(cursorAux);
  }
  public boolean isValid(BaroCursor cursor)
  {
    return cursor.isValid();
  }
  public boolean isValid()
  {
    return cursorMain.isValid();
  }
  public boolean isAuxValid()
  {
    return cursorAux.isValid();
  }
  public BaroCursor getCursor()
  {
    return cursorMain;
  }
  public BaroCursor getAuxCursor()
  {
    return cursorAux;
  }
  public IgcFiles igcFiles;
  ArrayList<Repainter> uiParents;
  BaroCursor cursorMain;
  BaroCursor cursorAux;
}

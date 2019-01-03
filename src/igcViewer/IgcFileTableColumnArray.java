package igcViewer;

import igc.IgcCursor;
import java.util.ArrayList;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liptakok
 */
class TaskColor extends IgcFileTableColumnBase
{
  TaskColor(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return igcFile.colorTask;
  }
}

class TrackColor extends IgcFileTableColumnBase
{
  TrackColor(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return igcFile.color;
  }
}

class Altitude extends IgcFileTableColumnBase
{
  Altitude(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return igcFile.getAltitude(ptIdx);
  }
  @Override
  public boolean isStaticField()
  {
    return false;
  }
}

public class IgcFileTableColumnArray {
  public IgcFileTableColumnArray()
  {
    columns = new ArrayList<>();
    colIdx = new TreeMap();
    add(new IgcFileTableColumnBase("Competition ID"));
    add(new IgcFileTableColumnBase("Pilot"));
    add(new IgcFileTableColumnBase("Glider ID"));
    add(new IgcFileTableColumnBase("Glider type"));
    add(new Altitude("Altitude"));
    add(new IgcFileTableColumnBase("Ground speed"));
    add(new IgcFileTableColumnBase("Direction"));
    add(new IgcFileTableColumnBase("Vario"));
    add(new TrackColor("Track color"));
    add(new TaskColor("Task color"));
    add(new IgcFileTableColumnBase("Distance"));
    add(new IgcFileTableColumnBase("L/D"));
    add(new IgcFileTableColumnBase("Time offset"));
  }
  final void add(IgcFileTableColumnBase newCol)
  {
    colIdx.put(newCol.getColName(), new Integer(columns.size()));
    columns.add(newCol);
  }
  public IgcFileTableColumnBase get(int idx)
  {
    return columns.get(idx);
  }
  public int size()
  {
    return columns.size();
  }
  public int getColIdx(String colName)
  {
    Integer obj = (Integer)colIdx.get(colName);
    if (obj == null)
      return -1;
    else
      return obj.intValue();
  }
  ArrayList<IgcFileTableColumnBase> columns;
  TreeMap colIdx;
}

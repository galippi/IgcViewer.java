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
class CompetitionId extends IgcFileTableColumnBase
{
  CompetitionId(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return igcFile.getCompetitionId();
  }
}

class Pilot extends IgcFileTableColumnBase
{
  Pilot(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return igcFile.getPilotsName();
  }
}

class GliderId extends IgcFileTableColumnBase
{
  GliderId(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return igcFile.getGliderId();
  }
}

class GliderType extends IgcFileTableColumnBase
{
  GliderType(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return igcFile.getGliderType();
  }
}

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
    return "" + igcFile.getAltitude(ptIdx) + " m";
  }
  @Override
  public boolean isStaticField()
  {
    return false;
  }
}

class Vario extends IgcFileTableColumnBase
{
  Vario(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return utils.Sprintf.sprintf("%3.1f m/s", igcFile.getVario(ptIdx));
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
    add(new CompetitionId("Competition ID"));
    add(new Pilot("Pilot"));
    add(new GliderId("Glider ID"));
    add(new GliderType("Glider type"));
    add(new Altitude("Altitude"));
    add(new IgcFileTableColumnBase("Ground speed"));
    add(new IgcFileTableColumnBase("Direction"));
    add(new Vario("Vario"));
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

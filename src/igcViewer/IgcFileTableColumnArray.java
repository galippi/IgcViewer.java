package igcViewer;

import igc.IGC_point;
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

class GroundSpeed extends IgcFileTableColumnBase
{
  GroundSpeed(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return "" + igcFile.getGroundSpeed_km_per_h(ptIdx) + " km/h";
  }
  @Override
  public boolean isStaticField()
  {
    return false;
  }
}

class Direction extends IgcFileTableColumnBase
{
  Direction(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx)
  {
    return (int)(igcFile.getDir(ptIdx) * 180 / Math.PI);
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

class Distance extends IgcFileTableColumnBase
{
  Distance(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(IgcCursor igcCursor, int fileIdx, igc.igc igcFile, int ptIdx, int selRow)
  {
    String distanceStr;
    if ((selRow >= 0) && (fileIdx != selRow))
    {
      igc.igc igcFileRef = igcCursor.get(selRow);
      IGC_point ptRef = igcFileRef.getIgcPoint(igcFileRef.getIdx(igcCursor.getTime()));
      IGC_point pt = igcFile.get(ptIdx);
      double distance = ptRef.getDistance(pt);
      if (distance > 5000)
        distanceStr = String.format("%.2f", distance / 1000) + "km";
      else
        distanceStr = String.format("%.0f", distance) + "m";
      return distanceStr;
    }else
      return "";
  }
  @Override
  public boolean isStaticField()
  {
    return false;
  }
}

class LiftToDrag extends IgcFileTableColumnBase
{
  LiftToDrag(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(igc.igc igcFile, int ptIdx)
  {
    double v = igcFile.getGroundSpeed(ptIdx);
    double w = igcFile.getVario(ptIdx);
    if (Math.abs(w) > 1e-3)
      return String.format("%.1f", (-v / w));
    else
      return "oo";
  }
  @Override
  public boolean isStaticField()
  {
    return false;
  }
}

class TimeOffset extends IgcFileTableColumnBase
{
  TimeOffset(String colName)
  {
    super(colName);
  }
  @Override
  public Object getValue(igc.igc igcFile)
  {
    return igcFile.getTimeOffset() + " s";
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
    add(new GroundSpeed("Ground speed"));
    add(new Direction("Direction"));
    add(new Vario("Vario"));
    add(new TrackColor("Track color"));
    add(new TaskColor("Task color"));
    add(new Distance("Distance"));
    add(new LiftToDrag("L/D"));
    add(new TimeOffset("Time offset"));
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

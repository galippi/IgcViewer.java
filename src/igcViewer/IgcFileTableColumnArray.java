package igcViewer;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liptakok
 */
public class IgcFileTableColumnArray {
  public IgcFileTableColumnArray()
  {
    columns = new ArrayList<>();
    columns.add(new IgcFileTableColumnBase("Competition ID"));
    columns.add(new IgcFileTableColumnBase("Pilot"));
    columns.add(new IgcFileTableColumnBase("Glider ID"));
    columns.add(new IgcFileTableColumnBase("Glider type"));
    columns.add(new IgcFileTableColumnBase("Altitude"));
    columns.add(new IgcFileTableColumnBase("Ground speed"));
    columns.add(new IgcFileTableColumnBase("Direction"));
    columns.add(new IgcFileTableColumnBase("Vario"));
    columns.add(new IgcFileTableColumnBase("Track color"));
    columns.add(new IgcFileTableColumnBase("Task color"));
    columns.add(new IgcFileTableColumnBase("Distance"));
    columns.add(new IgcFileTableColumnBase("L/D"));
    columns.add(new IgcFileTableColumnBase("Time offset"));
  }
  public IgcFileTableColumnBase get(int idx)
  {
    return columns.get(idx);
  }
  public int size()
  {
    return columns.size();
  }
  ArrayList<IgcFileTableColumnBase> columns;
}

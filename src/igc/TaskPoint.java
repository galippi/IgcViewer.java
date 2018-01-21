/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc;

/**
 *
 * @author liptakok
 */
public class TaskPoint {
  public IGC_Coordinate lon, lat;
  public String Name;
  public TaskPoint(String name, IGC_Coordinate lon, IGC_Coordinate lat)
  {
    Name = name;
    this.lon = lon;
    this.lat = lat;
  }  
}

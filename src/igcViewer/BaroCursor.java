/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

/**
 *
 * @author liptakok
 */
public class BaroCursor {
  BaroCursor()
  {
    m_capture = false;
    invalidate();
  }
  public boolean isValid()
  {
    return (timeCursorX >= 0) && (timeCursor >= 0);
  }
  public void invalidate()
  {
    timeCursorX = -1;
    timeCursor = -1;
  }
  public int timeCursorX = -1;
  public int timeCursor = -1;
  public boolean m_capture = false;
}

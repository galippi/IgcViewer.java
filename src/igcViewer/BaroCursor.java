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
    timeCursorX = -1;
    timeCursor = -1;
    m_capture = false;
  }
  public int timeCursorX = -1;
  public int timeCursor = -1;
  public boolean m_capture = false;
}

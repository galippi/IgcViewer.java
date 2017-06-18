/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author liptak
 */
public class BufferedRandomAccessFile
{
  public BufferedRandomAccessFile(InputStream is) throws IOException
  { 
    int len = is.available();
    buf = new byte[len];
    pos = 0;
    this.len = is.read(buf);
  }
  public int read() throws IOException
  {
    if (pos < len)
    {
      return buf[pos++];
    }else
    {
      return -1;
    }
  }
  public int read(byte[] b) throws IOException
  {
    int length = (len - pos);
    if (length > b.length)
    {
      length = b.length;
    }
    System.arraycopy(buf, pos, b, 0, length);
    pos += length;
    return length;
  }
  public int seek(int newPos)
  {
    int oldPos = pos;
    if (newPos < 0)
    { // seeking back from end of the file
      pos = len - newPos;
      if (pos < 0)
      {
        pos = 0;
      }
    }else
    {
      pos = newPos;
      if (pos > len)
      {
        pos = len;
      }
    }
    return oldPos;
  }
  public void reset()
  {
    seek(0);
  }
  public long skip(long bytes)
  {
    int oldPos = pos;
    seek(pos + (int)bytes);
    return oldPos - pos;
  }
  public void close()
  {
    pos = 0;
    len = 0;
    buf = null;
  }
  byte[] buf;
  int pos;
  int len;
}


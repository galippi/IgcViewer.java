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
    this.len = 0;
    while (this.len < len)
    {
      int readLen;
      if (this.len == 0)
      {
        readLen = is.read(buf);
      }else
      {
        byte[] buf2 = new byte[len - this.len];
        readLen = is.read(buf2);
        if (readLen > 0)
        {
          System.arraycopy(buf2, 0, buf, this.len, readLen);
        }else
        {
          dbg.dprintf(1, "Error: unable to load the full file (pos = %d)!", this.len);
          throw new IOException();
        }
      }
      this.len += readLen;
    }
    if (this.len != len)
    {
        dbg.dprintf(1, "Error: unable to load the full file (%d != %d)!", this.len, len);
        throw new IOException();
    }
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
    if (length <= 0)
    {
      dbg.println(11, "Info: BufferedRandomAccessFile.read after end of the file!");
      //throw new IOException();
      return -1;
    }
    if (length > b.length)
    {
      length = b.length;
    }else
    if (b.length > length)
    {
      dbg.dprintf(11, "Info: BufferedRandomAccessFile.read after end of the file (%d > %d)!", b.length, length);
      //throw new IOException();
    }
    System.arraycopy(buf, pos, b, 0, length);
    pos += length;
    return length;
  }
  public int seek(int newPos) throws IOException
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
        dbg.dprintf(11, "Error: BufferedRandomAccessFile.seek after end of the file (%d > %d)!", pos, len);
        throw new IOException();
        //pos = len;
      }
    }
    return oldPos;
  }
  public void reset() throws IOException
  {
    seek(0);
  }
  public long skip(long bytes) throws IOException
  {
    int oldPos = pos;
    seek(pos + (int)bytes);
    return oldPos - pos;
  }
  public void close() throws IOException
  {
    pos = 0;
    len = 0;
    buf = null;
  }
  byte[] buf;
  int pos;
  int len;
}


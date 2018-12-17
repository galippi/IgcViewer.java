/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import igc.IgcCursor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import utils.dbg;

/**
 *
 * @author liptakok
 */
class RepainterInstrumentExternal extends igc.Repainter {
  InstrumentExternal parent;
  RepainterInstrumentExternal(InstrumentExternal parent) {
    this.parent = parent;
  }
  @Override
    public void repaint(boolean forced)
  {
    parent.repaint(forced);
  }
}

class InstrumentClientConnector implements Runnable
{
  InstrumentClientConnector(InstrumentServer parent, Socket socket)
  {
    this.parent = parent;
    this.socket = socket;
    try
    {
      dbg.println(9, "Connected to Server: " + socket.getInetAddress());
      in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
      ostrm = socket.getOutputStream();
      out = new PrintWriter(socket.getOutputStream(), true);
    }catch (Exception e)
    {
      System.err.println("Exception="+e.toString());
    }
  }
  public void run()
  {
    
  }
  boolean send(byte[] data)
  {
    try
    {
      dbg.println(11, "InstrumentClientConnector - send bytes=" + data.length);
      ostrm.write(data);
      return false;
    }catch(Exception e)
    {
      dbg.println(2, "InstrumentClientConnector - send exception e=" + e.toString());
      return true;
    }
  }
  void dummy()
  {
      String line = in.readLine();
      System.out.println("Server:" + line + "!");
      out.println("Client request");
      out.flush();
      line = in.readLine();
      System.out.println("Server:" + line + "!");
  }
  InstrumentServer parent;
  Socket socket;
  OutputStream ostrm;
  BufferedReader in;
  PrintWriter out;
}

class InstrumentServer implements Runnable
{
  InstrumentServer(InstrumentExternal parent, ServerSocket socket)
  {
    this.parent = parent;
    this.socket = socket;
    clientSockets = new ArrayList<>();
  }
  @Override
  public void run()
  {
    while(true)
    {
      try
      {
        Socket client = socket.accept();
        InstrumentClientConnector conn = new InstrumentClientConnector(this, client);
        clientSockets.add(conn);
        if (dataLast != null)
          send(dataLast);
      }catch (Exception e)
      {
        dbg.println(2, "Warning: InstrumentServer.accept exception e=" + e.toString());
      }
    }
  }
  void send(byte[] data)
  {
    int i = 0;
    while (i < clientSockets.size())
    {
      InstrumentClientConnector conn = clientSockets.get(i);
      if (conn.send(data))
      { // send error - remove the client from the list
        clientSockets.remove(conn);
      }else
      {
        i++;
      }
    }
    dataLast = data;
  }
  InstrumentExternal parent;
  ServerSocket socket;
  ArrayList<InstrumentClientConnector> clientSockets;
  byte[] dataLast;
}

public class InstrumentExternal {
  public InstrumentExternal(IgcCursor igcCursor)
  {
    this.igcCursor = igcCursor;
    igcCursor.set(new RepainterInstrumentExternal(this));
    try
    {
      ServerSocket socket = new ServerSocket(7777);
      clients = new InstrumentServer(this, socket);
      Thread t = new Thread(clients);
      t.start();
    }catch  (Exception e)
    {
      String msg = "Error: unable to create instrument socket e=" + e.toString();
      System.err.println(msg);
      dbg.println(2, msg);
    }
    dbg.println(5, "Waiting for client connection...");
  }
  void repaint(boolean forced)
  {
    if (igcCursor.igcFiles.size() >= 1)
    { // inform the instrument panels about the new data
      igc.igc file = igcCursor.igcFiles.get(0);
      int idx = file.getIdx(igcCursor.timeCursor);
      igc.IGC_point pt = file.get(idx);
      IgcDataStream.IgcDataStream strm = new IgcDataStream.IgcDataStream();
      strm.setTime(igcCursor.timeCursor);
      strm.setAltitude(pt.Altitude.h);
      strm.setLatitude(pt.lat.val());
      strm.setLongitude(pt.lon.val());
      strm.setDirection(file.getDir(idx));
      strm.setSpeed(file.getGroundSpeed(idx));
      strm.setVerticalSpeed(file.getVario(idx));
      byte[] data = strm.transmit();
      clients.send(data);
    }
  }
  IgcCursor igcCursor;
  InstrumentServer clients;
}

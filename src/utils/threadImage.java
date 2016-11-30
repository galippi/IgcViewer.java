package utils;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author liptakok
 */
public class threadImage
{
  public threadImage(java.awt.Component parent, java.awt.Graphics g)
  {
    this.parent = parent;
    //Object o = g.getClipBounds();
    //img = new java.awt.image.BufferedImage(g.getClipBounds().width, g.getClipBounds().height,
    img = new java.awt.image.BufferedImage(320, 200,
                                           java.awt.image.BufferedImage.TYPE_INT_RGB);
    ready = false;
    stop = false;
    cancel = false;
    t = new Thread(new DrawingLoop());
    t.start();
  }
  java.awt.Component parent;
  Thread t;
  private class DrawingLoop
      implements Runnable {
      public void run()
      {
        System.out.println("DrawingLoop - Runnable - run");
        try
        {
          Thread.sleep(2000);
          int ctr = 0;
          while(!stop)
          {
            if (!ready)
            {
              System.out.println("DrawingLoop ctr=" + ctr);ctr++;
              Drawing();
              ready = true;
              parent.repaint();
            }
            Thread.sleep(100);
          }
        } catch (InterruptedException e) {
              //threadMessage("I wasn't done!");
        }
      }
  }
  protected void Drawing()
  { /* drawing function */
    java.awt.Graphics2D g = img.createGraphics();
    g.setColor(Color.red);
    g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
    g.setColor(Color.green);
    g.drawString("threadImage.drawString", 40, 40);
    g.dispose();
  }
  boolean isCancelled()
  {
    if (cancel)
    {
      cancel = false;
      return true;
    }
    return false;
  }
  public void Cancel()
  {
    cancel = true;
  }
  public Boolean ready;
  public java.awt.image.BufferedImage img;
  public Boolean stop;
  Boolean cancel;
}

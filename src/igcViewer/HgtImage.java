package igcViewer;

import java.awt.Color;

import igc.GeoUtil;
import utils.threadImage;

public class HgtImage  extends threadImage {
    public HgtImage(java.awt.Component parent, GeoUtil gu)
    {
      super(parent);
      this.gu = new GeoUtil(gu);
    }
    @Override
      protected void Drawing()
    { /* drawing function */
      java.awt.Graphics2D g = img.createGraphics();
      Color baseColor = Color.gray;
      g.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 127));
      //g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
      g.setColor(Color.BLUE);
      g.drawString("HgtImage!", 40, 300);
      g.dispose();
  }
    public void setGeoUtil(GeoUtil gu)
    {
      if (!this.gu.isEqual(gu))
      {
        this.gu = new GeoUtil(gu);
        setImage(this.gu.getW(), this.gu.getH());
        repaint();
      }
    }
    GeoUtil gu;
}

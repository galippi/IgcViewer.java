package igcViewer;

import java.awt.Color;

import hgt.HgtFile;
import hgt.HgtFileCache;
import hgt.HgtFileState;
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
      double lat = gu.lat_min;
      double lon = gu.lon_min;
      while(lat < gu.lat_max)
      {
          double latNext = (int)(lat + 0.9999999);
          while(lon < gu.lon_max)
          {
              double lonNext = (int)(lon + 0.9999999);
              HgtFile hgtFile = hgtFileCache.get(lat, lon, false);
              if (hgtFile != null)
              {
                  hgtFile.loadAsync();
                  if (hgtFile.getState() == HgtFileState.HgtFileLoaded)
                  {
                      int x0 = gu.getPosX(lon);
                      int y0 = gu.getPosY(lat);
                      int x1 = gu.getPosX(lonNext);
                      int y1 = gu.getPosY(latNext);
                  }
              }
              lon = lonNext;
          }
          lat = latNext;
      }
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
    HgtFileCache hgtFileCache = new HgtFileCache(IgcViewerPrefs.getSrtmCache());
}

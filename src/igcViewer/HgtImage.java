package igcViewer;

import java.awt.Color;

import hgt.HgtFile;
import hgt.HgtFileCache;
import hgt.HgtFileState;
import igc.GeoUtil;
import igc.MapHeightColor;
import utils.threadImage;

public class HgtImage  extends threadImage {
    public HgtImage(java.awt.Component parent, GeoUtil gu)
    {
      super(parent);
      this.gu = new GeoUtil(gu);
      mapHeightColor = new MapHeightColor();
    }
    MapHeightColor mapHeightColor;

    @Override
      protected void Drawing()
    { /* drawing function */
        java.awt.Graphics2D g = img.createGraphics();
        Color baseColor = Color.gray;
        g.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 127));
        //g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
        g.setColor(Color.BLUE);
        g.drawString("HgtImage!", 40, 300);
        boolean allFilesAreLoaded = false;
        while(allFilesAreLoaded == false)
        {
            allFilesAreLoaded = true;
            int lat = (int)gu.lat_min;
            while(lat < gu.lat_max)
            {
                int lon = -(int)gu.lon_min;
                int lonIdx = -lon;
                while(lonIdx < gu.lon_max)
                {
                    lonIdx++;
                    HgtFile hgtFile = hgtFileCache.get(lat, lon, false);
                    if (hgtFile != null)
                    {
                        if (hgtFile.loadAsync() == HgtFileState.HgtFileLoaded)
                        {
                            int x0 = gu.getPosX(-lon);
                            int y1 = gu.getPosY(lat);
                            int x1 = gu.getPosX(-lon + 1);
                            int y0 = gu.getPosY(lat + 1);
                            int dx = x1 - x0;
                            int dy = y1 - y0;
                            for (int y = y0; y < y1; y++)
                            {
                                double pointLat = lat + (y - y0) / (double)dy;
                                for(int x = x0; x < x1; x++)
                                {
                                    double pointLon = lon + (x - x0) / (double)dx;
                                    Color c = mapHeightColor.get(hgtFile.get(pointLat, pointLon));
                                    g.setColor(c);
                                    g.drawLine(x, y, x, y);
                                }
                            }
                        }else
                        {
                            if (hgtFile.getState() != HgtFileState.HgtFileError)
                                allFilesAreLoaded = false;
                        }
                    }
                    lon++;
                }
                lat++;
            }
        }
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

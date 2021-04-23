package igc;

import java.awt.Color;
import java.util.Vector;

import utils.dbg;

class MapHeightColorPoint {
    MapHeightColorPoint(int h, int r, int g, int b) {
        this.h = h;
        this.r = r;
        this.g = g;
        this.b = b;
        c = new Color(r, g, b);
    }
    int h;
    int r;
    int g;
    int b;
    Color c;
}
public class MapHeightColor {
    public MapHeightColor()
    {
        add(   0,        0x00, 0x00, 0xff);
        add(  29,        0x00, 0x00, 0x80);
        add(  30,        0x70, 0xc0, 0xa7);
        add( 250,        0xca, 0xe7, 0xb9);
        add( 500,        0xf4, 0xea, 0xaf);
        add( 750,        0xdc, 0xb2, 0x82);
        add(1000,        0xca, 0x8e, 0x72);
        add(1250,        0xde, 0xc8, 0xbd);
        add(1500,        0xe3, 0xe4, 0xe9);
        add(1750,        0xdb, 0xd9, 0xef);
        add(2000,        0xce, 0xcd, 0xf5);
        add(2250,        0xc2, 0xc1, 0xfa);
        add(2500,        0xb7, 0xb9, 0xff);
        add(5000,        0xb7, 0xb9, 0xff);
        add(6000,        0xb7, 0xb9, 0xff);
    }

    public Color get(int h)
    {
        if (h <= points.get(0).h)
            return points.get(0).c;
        for (int i = 1; i < points.size(); i++)
            if (h < points.get(i).h)
                return points.get(i - 1).c;
        return points.get(points.size() - 1).c;
    }

    //@SneakyThrows
    void add(int h, int r, int g, int b)
    {
        if ((points.size() != 0) && (points.get(points.size() - 1).h >= h))
        {
            dbg.println(1, "Error: invalid MapHeightColor configuration!");
            System.exit(1);
            //throw new Exception("Error: invalid MapHeightColor configuration!");
        }
        points.add(new MapHeightColorPoint(h, r, g, b));
    }
    Vector<MapHeightColorPoint> points = new Vector();
}

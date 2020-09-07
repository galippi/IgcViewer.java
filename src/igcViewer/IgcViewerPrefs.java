/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

/**
 *
 * @author liptak
 */
public class IgcViewerPrefs {
  public IgcViewerPrefs()
  {
  }
  static public String get(String path, String key, String defVal)
  {
    if (path.isEmpty())
      path = root;
    else
      path = root + "/" + path;
    return java.util.prefs.Preferences.userRoot().node(path).get(key, defVal);
  }
  static public String get(String key, String defVal)
  {
    return get("", key, defVal);
  }
  static int get(String path, String key, int defVal)
  {
    return Integer.parseInt(get(path, key, "" + defVal));
  }
  static int get(String key, int defVal)
  {
    return Integer.parseInt(get(key, "" + defVal));
  }
  static double get(String path, String key, double defVal)
  {
    return Double.parseDouble(get(path, key, "" + defVal));
  }
  static double get(String key, double defVal)
  {
    return Double.parseDouble(get(key, "" + defVal));
  }
  static public void put(String path, String key, String val)
  {
    if (path.isEmpty())
      path = root;
    else
      path = root + "/" + path;
    java.util.prefs.Preferences.userRoot().node(path).put(key, val);
  }
  static public void put(String key, String val)
  {
    put("", key, val);
  }
  static public void put(String path, String key, int val)
  {
    put(path, key, "" + val);
  }
  static public void put(String key, int val)
  {
    put(key, "" + val);
  }
  static public void put(String path, String key, double val)
  {
    put(path, key, "" + val);
  }
  static public void put(String key, double val)
  {
    put(key, "" + val);
  }  static public String getRecentFile(int idx, String defVal)
  {
    return get("RecentFiles", "RecentFile" + idx, defVal);
  }
  static public void putRecentFile(int idx, String val)
  {
    put("RecentFiles", "RecentFile" + idx, val);
  }
  static final String root = "IgcViewer";

  static String srtmCache;
  static public String getSrtmCache()
  {
      if (srtmCache == null)
          srtmCache = IgcViewerPrefs.get("SRTM_cache", "C:\\Temp\\SRTM");
      return srtmCache;
  }
  static public void setSrtmCache(String _srtmCache)
  {
      srtmCache = _srtmCache;
      IgcViewerPrefs.put("SRTM_cache", srtmCache);
  }
}

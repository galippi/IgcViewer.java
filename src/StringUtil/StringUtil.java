package StringUtil;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liptakok
 */

import utils.dbg;

public class StringUtil
{
  public StringUtil(String str)
  {
    this.str = str;
  }

  public int find(char delimit, int npos)
  {
    if (npos == 0)
    {
      return str.indexOf(delimit);
    }else
    {
      int ret = str.substring(npos).indexOf(delimit);
      return (ret < 0 ? ret : ret + npos);
    }
  }

  public String getField(int FieldNum, char delimit)
  {
    int n_pos = 0;
    while (FieldNum > 0)
    {
      n_pos = find(delimit, n_pos);
      if (n_pos < 0)
      {
        dbg.println(9, "Warning: getField(\"" + str + "\","+FieldNum+","+delimit+") - result=(not found)!");
        return new String("");
      }
      FieldNum--;
      n_pos++;
    }
    int end_pos = find(delimit, n_pos);
    String result;
    if (end_pos < 0)
    {
      result = str.substring(n_pos);
    }else
    {
      result = str.substring(n_pos, end_pos);
    }
    result = result.trim();
    dbg.println(21, "getField(\"" + str + "\","+delimit+") - result="+result+"!");
    return result;
  }
  public long getFieldU32(int FieldNum, char delimit)
  {
    String field = getField(FieldNum, delimit);
    long val = Long.parseLong(field);
    if ((val >= 0) && (val < Integer.MAX_VALUE))
    {
      return val;
    }
    return val_err;
  }
  public static String getField(String str, int FieldNum, char delimit)
  {
    StringUtil s = new StringUtil(str);
    return s.getField(FieldNum, delimit);
  }
  public static long getFieldU32(String str, int FieldNum, char delimit)
  {
    StringUtil s = new StringUtil(str);
    return s.getFieldU32(FieldNum, delimit);
  }
  public String GetBeforeFirst(char ch)
  {
    int idx = str.indexOf(ch);
    if (idx >= 0)
    {
        if (idx > 0)
        {
          return str.substring(0, idx - 1);
        }else
        {
            return "";
        }
    }else
    {
        return str;
    }
  }
  public void SetBeforeFirst(char ch)
  {
      str = GetBeforeFirst(ch);
  }
  public void trim()
  {
      str = str.trim();
  }
  public boolean isEmpty()
  {
      return str.isEmpty();
  }
  public String get()
  {
      return str;
  }
  String str;
  static public final long val_err = Integer.MAX_VALUE;
}

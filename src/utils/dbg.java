package utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gabor Liptak
 */
import java.io.*;

public class dbg {
    static int level;
    public dbg()
    {
       level = 0; 
    }
    public dbg(int level)
    {
       this.level = level;
    }
    public static void set(int level_new)
    {
        level = level_new;
    }
    public static void println(int dbg_level, String line)
    {
        if (dbg_level <= level)
        {
            System.out.println(line);
        }
    }
    public static String d_format(String fmt, Object ... arguments)
    {
      return new String(fmt); 
    }
}

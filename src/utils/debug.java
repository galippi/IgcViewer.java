/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author liptakok
 */
public class debug {
    int level;
    public debug()
    {
       level = 0; 
    }
    public debug(int level)
    {
       this.level = level;
    }
    public void set(int level)
    {
        this.level = level;
    }
    public void println(int level, String line)
    {
        if (level <= this.level)
        {
            System.out.println(line);
        }
    }
    public String d_format(String fmt, Object ... arguments)
    {
      return new String(fmt); 
    }
}

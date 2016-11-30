/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.numberaddition;

//import igc.IGC_Altitude;
//import igc.IGC_Coordinate;
//import igc.IGC_Time;
import utils.threadImage;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import igc.igc;
import java.util.ArrayList;
import utils.threadImage;

import utils.dbg;

/**
 *
 * @author liptakok
 */
class IgcFiles
{
  ArrayList<igc> igcFiles;
  public IgcFiles()
  {
    igcFiles = new ArrayList<igc>();
    reinit();
  }
  public void add(igc file)
  {
    igcFiles.add(file);
  }
  public int size()
  {
    return igcFiles.size();
  }
  public void reinit()
  {
    igcFiles.clear();
    t_min = 0;
    t_max = 0;
    lon_min = 0;
    lon_max = 0;
    lat_min = 0;
    lat_max = 0;
    alt_min = 0;
    alt_max = 0;
  }
  public double t_min, t_max;
  public double lon_min, lon_max;
  public double lat_min, lat_max;
  public double alt_min, alt_max;
  static final double SNA_doubleLimit = 1e98;
  static final public double SNA_double = (SNA_doubleLimit * 2);
  static public boolean isSna(double val)
  {
    return (val > SNA_doubleLimit);
  }
}

class igcImage extends threadImage
{
  public igcImage(java.awt.Component parent, java.awt.Graphics g, IgcFiles igcFiles)
  {
    super(parent, g);
    this.igcFiles = igcFiles;
    ctr = 0;
  }
  IgcFiles igcFiles;
  @Override
    protected void Drawing()
  {
    java.awt.Graphics2D g = img.createGraphics();
    g.setColor(Color.red);
    g.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
    g.setColor(Color.white);
    g.fillRect(40,30,200, 100);
    g.setColor(Color.green);
    g.drawString("igcImage.drawString ctr=" + ctr, 40, 40);ctr++;
    g.dispose();
  }
  int ctr;
}

class MapImage extends threadImage
{
  public MapImage(java.awt.Graphics g, IgcFiles igcFiles)
  {
    super(g);
    this.igcFiles = igcFiles;
  }
  IgcFiles igcFiles;
}

class MapPanel extends javax.swing.JPanel
{
    IgcFiles igcFiles;
    public MapPanel(IgcFiles igcFiles)
    {
      this.igcFiles = igcFiles;
      ctr = 0;
      img = new java.awt.image.BufferedImage(100, 50,
                 java.awt.image.BufferedImage.TYPE_INT_ARGB);
      java.awt.Graphics2D g2 = img.createGraphics();
      g2.setColor(Color.red);
      g2.fillOval(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2 - 5, img.getHeight() / 2 - 5);
      g2.dispose();
      igc = new igcImage(this, img.getGraphics(), null);
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
          public void run() {
            //repaint();
            igc.ready = false;
          }
      }, 5000);
    }
    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        g.drawString("ctr=" + ctr + " size=" + igcFiles.size(), 20, 20);
        ctr++;
        g.drawRect(200, 200, 200, 200);
        //java.awt.Graphics gc = java.awt.GraphicsConfiguration.createCompatibleImage(600, 400);
        //java.awt.Image img = new java.awt.Image();
        g.drawImage(img, 100, 100, null);
        g.drawImage(igc.img, 200, 200, null);
        if (ctr % 10 == 0) igc.ready = false;
    }
    public void Repaint()
    {
      igc.ready = false;
    }
    int ctr;
    java.awt.image.BufferedImage img;
    igcImage igc;
}

public class NumberAdditionUI extends javax.swing.JFrame {

  IgcFiles igcFiles;

    // Display a message, preceded by
    // the name of the current thread
    static void threadMessage(String message) {
        String threadName =
            Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                          threadName,
                          message);
    }

    private static class MessageLoop
        implements Runnable {
        public void run() {
            try {
                for (int i = 0;
                     i < 10;
                     i++) {
                    // Pause for 4 seconds
                    Thread.sleep(1000);
                    // Print a message
                    String info = "Counter=" + i;
                    threadMessage(info);
                }
            } catch (InterruptedException e) {
                threadMessage("I wasn't done!");
            }
        }
    }

    /**
     * Creates new form NumberAdditionUI
     */
    public NumberAdditionUI() {
        igcFiles = new IgcFiles();
        initComponents();
        timer = new Timer();
        t = new Thread(new MessageLoop());
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                timerCallBack();
            }
        }  , 1000, 1000);
        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        t.start();
        timerCtr = 0;
    }

    public void timerCallBack()
    {
      System.out.println("timerCallBack ctr=" + timerCtr);
      jLabel1.setText("timerCallBack ctr=" + timerCtr);
      if (t.isAlive())
      {
        System.out.println("t thread is running");
      }else
      {
        System.out.println("t thread is stopped");
        timer.cancel();
      }
      timerCtr++;
    }
    void repaintMap()
    {
      //jPanel1.Repaint();
      repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jSplitPane1 = new javax.swing.JSplitPane();
    jPanel1 = new MapPanel(igcFiles);
    jLabel1 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    m_FileOpen = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JPopupMenu.Separator();
    m_FileExit = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jSplitPane1.setDividerLocation(150);
    jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setResizeWeight(1.0);
    jSplitPane1.setMinimumSize(new java.awt.Dimension(400, 300));

    jPanel1.setMinimumSize(new java.awt.Dimension(95, 97));

    jLabel1.setText("jLabel1");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGap(26, 26, 26)
        .addComponent(jLabel1)
        .addContainerGap(538, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGap(53, 53, 53)
        .addComponent(jLabel1)
        .addContainerGap(82, Short.MAX_VALUE))
    );

    jSplitPane1.setTopComponent(jPanel1);

    jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 200));
    jScrollPane1.setMinimumSize(new java.awt.Dimension(50, 50));
    jScrollPane1.setNextFocusableComponent(jSplitPane1);

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jTable1.setEditingColumn(2);
    jTable1.setEditingRow(2);
    jTable1.setMaximumSize(new java.awt.Dimension(1000, 1000));
    jTable1.setMinimumSize(new java.awt.Dimension(100, 100));
    jTable1.setPreferredSize(new java.awt.Dimension(200, 120));
    jScrollPane1.setViewportView(jTable1);

    jSplitPane1.setBottomComponent(jScrollPane1);

    jMenu1.setText("File");

    m_FileOpen.setText("File open");
    m_FileOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_FileOpenActionPerformed(evt);
      }
    });
    jMenu1.add(m_FileOpen);
    jMenu1.add(jSeparator1);

    m_FileExit.setText("Exit");
    m_FileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_FileExitActionPerformed(evt);
      }
    });
    jMenu1.add(m_FileExit);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("Edit");
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void m_FileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_FileExitActionPerformed
    System.out.println("m_FileExitActionPerformed");
    System.exit(0);
  }//GEN-LAST:event_m_FileExitActionPerformed

  private void m_FileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_FileOpenActionPerformed
    System.out.println("m_FileOpenActionPerformed");
    //Create a file chooser
    final JFileChooser fc = new JFileChooser();
    javax.swing.filechooser.FileNameExtensionFilter filter =
            new javax.swing.filechooser.FileNameExtensionFilter(
        "IGC file", "igc");
    fc.setFileFilter(filter);
    //In response to a button click:
    int returnVal = fc.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      java.io.File file = fc.getSelectedFile();
      //This is where a real application would open the file.
      System.out.println("Opening: " + file.getName() + ".");
      igc igco = new igc(file.getPath());
      if (!igco.isValid())
      { /* error message */
        JOptionPane.showMessageDialog(this, "Unable to load file " + file.getName());
      }else
      { /* file is loaded -> add it to the list */
        igcFiles.add(igco);
        repaintMap();
      }
    } else
    {
      System.out.println("Open command cancelled by user.");
    }
  }//GEN-LAST:event_m_FileOpenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      dbg.set(9); /* enable debug */
      /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NumberAdditionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NumberAdditionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NumberAdditionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NumberAdditionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NumberAdditionUI().setVisible(true);
            }
        });
    }

  // Variables
  Timer timer;
  Thread t;
  int timerCtr;
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JPopupMenu.Separator jSeparator1;
  private javax.swing.JSplitPane jSplitPane1;
  private javax.swing.JTable jTable1;
  private javax.swing.JMenuItem m_FileExit;
  private javax.swing.JMenuItem m_FileOpen;
  // End of variables declaration//GEN-END:variables
}

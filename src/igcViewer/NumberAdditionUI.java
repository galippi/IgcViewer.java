/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import igc.igc;
import java.util.ArrayList;

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
    igcFiles = new ArrayList<>();
    reinit();
  }
  public void add(igc file)
  {
    igcFiles.add(file);
    if (igcFiles.size() == 1)
    {
      lon_min = file.lon_min.val();
      lon_max = file.lon_max.val();
      lat_min = file.lat_min.val();
      lat_max = file.lat_max.val();
      t_min = (int)file.t_min.t;
      t_max = (int)file.t_max.t;
      alt_min = file.alt_min.val();
      alt_max = file.alt_max.val();
    }else
    {
      lon_min = Math.min(lon_min, file.lon_min.val());
      lon_max = Math.max(lon_max, file.lon_max.val());
      lat_min = Math.min(lat_min, file.lat_min.val());
      lat_max = Math.max(lat_max, file.lat_max.val());
      if ((int)file.t_min.t < t_min)
        t_min = (int)file.t_min.t;
      if ((int)file.t_max.t < t_max)
        t_max = (int)file.t_max.t;
      if (file.alt_min.val() < alt_min)
        alt_min = file.alt_min.val();
      if (file.alt_max.val() < alt_max)
        alt_max = file.alt_max.val();
    }
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
  public igc get(int idx)
  {
    return igcFiles.get(idx);
  }
  public int t_min, t_max;
  public double lon_min, lon_max;
  public double lat_min, lat_max;
  public int alt_min, alt_max;
  static final double SNA_doubleLimit = 1e98;
  static final public double SNA_double = (SNA_doubleLimit * 2);
  static public boolean isSna(double val)
  {
    return (val > SNA_doubleLimit);
  }
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

    private void m_RecentFileActionPerformed(java.awt.event.ActionEvent evt) {
      dbg.println(9, "m_RecentFileActionPerformed " + evt.toString());
      String val = evt.getActionCommand();
      dbg.dprintf(9, "  val=%s\n", val);
      if (val.charAt(1) == ':')
      {
        int idx = val.charAt(0) - '0';
        String file = val.substring(3);
        dbg.dprintf(9, "  idx=%d file=%s\n", idx, file);
        openIgcFile(file);
      }
    }
    /**
     * Creates new form NumberAdditionUI
     */
    public NumberAdditionUI() {
        igcFiles = new IgcFiles();
        initComponents();
        //jMenu3
        int nextRecentFile = 0;
        for (int i = 0; i < 10; i++)
        {
          String val = java.util.prefs.Preferences.userRoot().node("RecentFiles").get("RecentFile"+i, "");
          if (!val.isEmpty())
          {
            javax.swing.JMenuItem jMenuItem = new javax.swing.JMenuItem();
            jMenuItem.setText(nextRecentFile + ": " + val);
            nextRecentFile++;
            jMenuItem.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_RecentFileActionPerformed(evt);
              }
            });
            jMenu3.add(jMenuItem);
          }
        }
    }

    void repaintMap()
    {
      //jPanel1.Repaint();
      mapPanel.Repaint();
      dbg.println(9, "repaintMap() size=" + igcFiles.size());
      for (int i=0; i < igcFiles.size(); i++)
      {
        jTable1.setValueAt(igcFiles.get(i).getCompetitionId(), i, 0);
        jTable1.setValueAt(igcFiles.get(i).getPilotsName(), i, 1);
        jTable1.setValueAt(igcFiles.get(i).getGliderId(), i, 2);
        jTable1.setValueAt(igcFiles.get(i).getGliderType(), i, 3);
        dbg.println(9, "igcFiles.get("+i+").getPilotsName()=" + igcFiles.get(i).getPilotsName());
      }
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
    jPanel1 = mapPanel = new MapPanel(igcFiles);
    jSplitPane2 = new javax.swing.JSplitPane();
    jPanel2 = baroPanel = new BaroPanel(igcFiles);
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    m_FileOpen = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JPopupMenu.Separator();
    jMenu3 = new javax.swing.JMenu();
    m_FileExit = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jSplitPane1.setDividerLocation(150);
    jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setResizeWeight(1.0);
    jSplitPane1.setMinimumSize(new java.awt.Dimension(400, 300));

    jPanel1.setMinimumSize(new java.awt.Dimension(95, 97));

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 95, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 97, Short.MAX_VALUE)
    );

    jSplitPane1.setTopComponent(jPanel1);

    jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );

    jSplitPane2.setTopComponent(jPanel2);
    jPanel2.getAccessibleContext().setAccessibleParent(jSplitPane2);

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
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jTable1MouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(jTable1);

    jSplitPane2.setBottomComponent(jScrollPane1);

    jSplitPane1.setBottomComponent(jSplitPane2);

    jMenu1.setText("File");

    m_FileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, 0));
    m_FileOpen.setText("File open");
    m_FileOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_FileOpenActionPerformed(evt);
      }
    });
    jMenu1.add(m_FileOpen);
    jMenu1.add(jSeparator1);

    jMenu3.setText("Recent Files");
    jMenu3.setToolTipText("");
    jMenu3.setActionCommand("recentFiles");
    jMenu1.add(jMenu3);

    m_FileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
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
      .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void m_FileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_FileExitActionPerformed
    dbg.println(1, "m_FileExitActionPerformed");
    System.exit(0);
  }//GEN-LAST:event_m_FileExitActionPerformed

  private void openIgcFile(String fileName)
  {
    igc igco = new igc(fileName);
    if (!igco.isValid())
    { /* error message */
      JOptionPane.showMessageDialog(this, "Unable to load file " + fileName);
    }else
    { /* file is loaded -> add it to the list */
      igcFiles.add(igco);
      repaintMap();
      for (int i = 9; i > 0; i--)
      { /* move recent file lower */
        java.util.prefs.Preferences.userRoot().node("RecentFiles").put("RecentFile" + i, 
          java.util.prefs.Preferences.userRoot().node("RecentFiles").get("RecentFile" + (i - 1), ""));
      }
      java.util.prefs.Preferences.userRoot().node("RecentFiles").put("RecentFile" + 0, fileName);
    }
  }

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
      dbg.println(9, "Opening: " + file.getName() + ".");
      openIgcFile(file.getPath());
    } else
    {
      dbg.println(1, "Open command cancelled by user.");
    }
  }//GEN-LAST:event_m_FileOpenActionPerformed

  private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    dbg.println(9, "jTable1MouseClicked " + evt.toString());
    dbg.println(9, "  findComponentAt="+jTable1.findComponentAt(evt.getX(), evt.getY()).toString());
  }//GEN-LAST:event_jTable1MouseClicked

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
  MapPanel mapPanel;
  BaroPanel baroPanel;

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JPopupMenu.Separator jSeparator1;
  private javax.swing.JSplitPane jSplitPane1;
  private javax.swing.JSplitPane jSplitPane2;
  private javax.swing.JTable jTable1;
  private javax.swing.JMenuItem m_FileExit;
  private javax.swing.JMenuItem m_FileOpen;
  // End of variables declaration//GEN-END:variables
}

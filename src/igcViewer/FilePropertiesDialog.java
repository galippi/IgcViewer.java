/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author liptakok
 */
public class FilePropertiesDialog extends JDialog {
  FilePropertiesDialog(JFrame parent, IgcFileTableColumnArray colArray, int[] selected)
  {
    super(parent, Dialog.ModalityType.APPLICATION_MODAL);
    this.setTitle("Select signals to be displayed");

    JLabel l2 = new JLabel("Select signals to be displayed");
    l2.setHorizontalAlignment(SwingConstants.CENTER);

    JButton b2 = new JButton("OK");
    b2.setHorizontalAlignment(SwingConstants.LEFT);
    b2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    });

    JButton bCancel = new JButton("Cancel");
    bCancel.setHorizontalAlignment(SwingConstants.RIGHT);

    DefaultListModel lmSelected = new DefaultListModel();
    for(int i = 0; i < selected.length; i++)
    {
      lmSelected.addElement(colArray.get(selected[i]).colName);
    }
    JList lbSelected = new JList(lmSelected);

    DefaultListModel lmDeselected = new DefaultListModel();
    for(int i = 0; i < colArray.size(); i++)
    {
      int j;
      for(j = 0; j < selected.length; j++)
      {
        if (i == selected[j])
          break;
      }
      if (j == selected.length)
        lmDeselected.addElement(colArray.get(i).colName);
    }
    JList lbDeselected = new JList(lmDeselected);

    Container cp2 = getContentPane();
    // add label, text field and button one after another into a single column
    cp2.setLayout(new BorderLayout());
    cp2.add(l2, BorderLayout.NORTH);
    JPanel p2 = new JPanel();
    //Container cp3 = p2.getContentPane();
    p2.setLayout(new FlowLayout());

    JPanel pUpDown = new JPanel();
    pUpDown.setLayout(new GridLayout(2, 1));

    JButton bUp = new JButton("Up");
    JButton bDown = new JButton("Down");
    pUpDown.add(bUp);
    pUpDown.add(bDown);
    p2.add(pUpDown);
    p2.add(new JScrollPane(lbSelected));
    JPanel p3 = new JPanel();
    //p3.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    p3.setLayout(new GridLayout(4, 1));
    JButton b3 = new JButton("<<");
    p3.add(b3);
    JButton b4 = new JButton("<");
    p3.add(b4);
    JButton b5 = new JButton(">");
    p3.add(b5);
    JButton b6 = new JButton(">>");
    p3.add(b6);
    p2.add(p3);
    p2.add(new JScrollPane(lbDeselected));
    cp2.add(p2, BorderLayout.CENTER);
    JPanel bOkCancel = new JPanel();
    bOkCancel.add(b2);
    bOkCancel.add(bCancel);
    cp2.add(bOkCancel, BorderLayout.SOUTH);
    Point pt = parent.getLocationOnScreen();
    int pw = parent.getWidth();
    setBounds(pt.x + pw / 2 - 150, pt.y + 200, 300, 200);
  }
}

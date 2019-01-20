/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igcViewer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author liptakok
 */
public class ColumnSelectorDialog extends JDialog {
  JList lbSelected;
  JList lbDeselected;
  JButton bAddAll;
  JButton bAdd;
  JButton bRemove;
  JButton bRemoveAll;
  ColumnSelectorDialog(JFrame parent, IgcFileTableColumnArray colArray, int[] selected)
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
    bCancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    });

    DefaultListModel lmSelected = new DefaultListModel();
    for(int i = 0; i < selected.length; i++)
    {
      lmSelected.addElement(colArray.get(selected[i]).colName);
    }
    lbSelected = new JList(lmSelected);

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
    lbDeselected = new JList(lmDeselected);

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
    bUp.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        }
    });
    JButton bDown = new JButton("Down");
    bDown.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        }
    });
    pUpDown.add(bUp);
    pUpDown.add(bDown);
    p2.add(pUpDown);
    p2.add(new JScrollPane(lbSelected));
    JPanel p3 = new JPanel();
    //p3.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    p3.setLayout(new GridLayout(4, 1));

    bAddAll = new JButton("<<");
    bAddAll.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          addAllHandler();
        }
    });
    p3.add(bAddAll);

    bAdd = new JButton("<");
    bAdd.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          addHandler();
        }
    });
    p3.add(bAdd);

    bRemove = new JButton(">");
    bRemove.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        }
    });
    p3.add(bRemove);

    bRemoveAll = new JButton(">>");
    bRemoveAll.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        }
    });
    p3.add(bRemoveAll);

    p2.add(p3);
    p2.add(new JScrollPane(lbDeselected));
    cp2.add(p2, BorderLayout.CENTER);
    JPanel bOkCancel = new JPanel();
    bOkCancel.add(b2);
    bOkCancel.add(bCancel);
    cp2.add(bOkCancel, BorderLayout.SOUTH);
    Point pt = parent.getLocationOnScreen();
    int pw = parent.getWidth();
    setBounds(pt.x + pw / 2 - 150, pt.y + 200, 400, 400);
    this.setMinimumSize(new Dimension(350, 300));
    updateButtons();
  }
  final void updateButtons()
  {
    if (lbSelected.getModel().getSize() == 0)
    {
      bRemoveAll.setEnabled(false);
      bRemove.setEnabled(false);
    }else
    {
      bRemoveAll.setEnabled(true);
      bRemove.setEnabled(true);
    }
    if (lbDeselected.getModel().getSize() == 0)
    {
      bAddAll.setEnabled(false);
      bAdd.setEnabled(false);
    }else
    {
      bAddAll.setEnabled(true);
      bAdd.setEnabled(true);
    }
  }
  void addHandler()
  {
    updateButtons();
  }
  void addAllHandler()
  {
    DefaultListModel mDeselected = (DefaultListModel)lbDeselected.getModel();
    DefaultListModel mSelected = (DefaultListModel)lbSelected.getModel();
    for(int i = 0; i < mDeselected.getSize(); i++)
      mSelected.addElement(mDeselected.getElementAt(i));
    mDeselected.clear();
    updateButtons();
  }
}

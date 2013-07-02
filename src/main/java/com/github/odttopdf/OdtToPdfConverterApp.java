/*
 * $Id$
 *
 * Copyright 2013 Valentyn Kolesnikov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.odttopdf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

/**
 * Converts ODT to PDF, application.
 *
 * @author Valentyn Kolesnikov
 * @version $Revision$ $Date$
 */
public class OdtToPdfConverterApp extends javax.swing.JFrame {
    private JFileChooser chooser1 = new JFileChooser();
    private JFileChooser chooser2 = new JFileChooser();
    private JFileChooser chooser3 = new JFileChooser();
    private JFileChooser chooser4 = new JFileChooser();
    
    public static class HistoryComboBox extends JComboBox {
        public HistoryComboBox() {
            setEditable(true);
            addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent) {
                    Object obj = getSelectedItem();
                    if(obj != null && actionevent.getActionCommand().equals("comboBoxEdited")) {
                        addToList(obj.toString());
                    }
                }
            });
        }

        public void addToList(String s) {
            int j = getMaxUrlsLength();
            if (!isValid(s)) {
                removeItem(s);
                return;
            }
            int index;
            for (index = 0; index < getItemCount(); index += 1) {
                if (s.equals(getItemAt(index))) {
                    if (index > 0) {
                        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) getModel();
                        dcbm.insertElementAt(dcbm.getElementAt(index), 0);
                        dcbm.removeElementAt(index + 1);
                        dcbm.setSelectedItem(dcbm.getElementAt(0));
                      }
                      return;
                }
            }
            if (index > j) {
               removeItemAt(index - 1);
            }
            insertItemAt(s, 0);
            getEditor().setItem(s);
            setSelectedItem(s);
        }

        private int getMaxUrlsLength() {
            return 16;
        }

        private boolean isValid(String str) {
            return !str.equals("");
        }
    }

    public static class FilterODT extends javax.swing.filechooser.FileFilter {
        public String getDescription() {
            return "ODT files";
        }

        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            String name = file.getName();
            name = name.toLowerCase();
            return name.endsWith(".odt");
        }
    }

    public static class FilterPDF extends javax.swing.filechooser.FileFilter {
        public String getDescription() {
            return "PDF files";
        }

        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            String name = file.getName();
            name = name.toLowerCase();
            return name.endsWith(".pdf");
        }
    }

    public OdtToPdfConverterApp() {
        initComponents();
        chooser1.addChoosableFileFilter(new FilterODT());
        chooser1.setDialogTitle("Select ODT file");
        chooser1.setCurrentDirectory(new File("."));
        chooser1.setFileFilter(new FilterODT());
        chooser2.setDialogTitle("Select ODT directory");
        chooser2.setCurrentDirectory(new File("."));
        chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser2.setFileFilter(new FilterODT());
        chooser3.setDialogTitle("Select PDF file");
        chooser3.setCurrentDirectory(new File("."));
        chooser3.setFileFilter(new FilterPDF());
        chooser4.setDialogTitle("Select PDF directory");
        chooser4.setCurrentDirectory(new File("."));
        chooser4.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser4.setFileFilter(new FilterPDF());

        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        final int x = (screenSize.width - getWidth()) / 2;
        final int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox3 = new HistoryComboBox();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jComboBox4 = new HistoryComboBox();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ODT to PDF converter");

        jLabel1.setText("ODT file(s):");

        jLabel2.setText("Output:");

        jCheckBox1.setText("Merge");
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jComboBox3.setEditable(true);

        jButton6.setText("File...");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Dir...");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jComboBox4.setEditable(true);

        jButton8.setText("File...");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Dir...");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBox4, 0, 249, Short.MAX_VALUE)
                            .add(jComboBox3, 0, 249, Short.MAX_VALUE)))
                    .add(jCheckBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 124, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 8, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jButton8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jButton9, 0, 0, Short.MAX_VALUE)
                    .add(jButton7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jComboBox3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton7)
                    .add(jButton6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jComboBox4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton9)
                    .add(jButton8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCheckBox1)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("OK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jProgressBar1.setFocusable(false);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jButton4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton3)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jButton3, jButton4}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton3)
                    .add(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    System.exit(0);
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    if (chooser1.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
        return;
    }
    ((HistoryComboBox) jComboBox3).addToList(chooser1.getSelectedFile().getPath());
}//GEN-LAST:event_jButton6ActionPerformed

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    if (chooser2.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
        return;
    }
    ((HistoryComboBox) jComboBox3).addToList(chooser2.getSelectedFile().getPath());
}//GEN-LAST:event_jButton7ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    if (jComboBox3.getSelectedItem() == null) {
        return;
    }
    jButton4.setEnabled(false);
    jProgressBar1.setIndeterminate(true);
    class MyWorker extends SwingWorker<String, Void> {
        protected String doInBackground() {
            return "Done.";
        }
    }
    final MyWorker worker = new MyWorker();
    worker.execute();
    new Thread() {
        @Override
        public void run() {
            OdtToPdfConverter odttopdf = new OdtToPdfConverter();
            File directory = new File(jComboBox3.getSelectedItem().toString());
            List<String> convertFiles = new ArrayList<String>();
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.getAbsolutePath().endsWith(".odt")) {
                        convertFiles.add(file.getAbsolutePath());
                    }
                }
            } else {
                convertFiles.add(directory.getAbsolutePath());
            }
            odttopdf.createDocument(convertFiles,
            Collections.<String>emptyList(), (String) jComboBox4.getSelectedItem());
            jProgressBar1.setIndeterminate(false);
            jButton4.setEnabled(true);
        }
    }.start();
}//GEN-LAST:event_jButton4ActionPerformed

private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
    if (chooser3.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
        return;
    }
    ((HistoryComboBox) jComboBox4).addToList(chooser3.getSelectedFile().getPath());
}//GEN-LAST:event_jButton8ActionPerformed

private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    if (chooser4.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
        return;
    }
    ((HistoryComboBox) jComboBox4).addToList(chooser4.getSelectedFile().getPath());
}//GEN-LAST:event_jButton9ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args.length != 0) {
            List<String> templates = new ArrayList<String>();
            List<String> xmls = new ArrayList<String>();
            for (String arg : args) {
                if (arg.endsWith(".odt")) {
                    templates.add(arg);
                } else if (arg.endsWith(".xml")) {
                    xmls.add(arg);
                }
            }
            if (!templates.isEmpty()) {
                Log.info("Converting " + templates.toString().replaceAll("[\\[\\]]", "") + " ...");
                new OdtToPdfConverter().createDocument(templates, xmls, "");
                System.exit(0);
            }
        }

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx=0; idx<installedLookAndFeels.length; idx++)
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OdtToPdfConverterApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OdtToPdfConverterApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OdtToPdfConverterApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OdtToPdfConverterApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OdtToPdfConverterApp().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
    
}

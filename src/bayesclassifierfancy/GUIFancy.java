/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIFancy.java
 *
 * Created on 2013-12-14, 15:55:54
 */

package bayesclassifierfancy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Administrator
 */
public class GUIFancy extends javax.swing.JFrame {

	/**
	 * 所有单词数统计Map
	 */
	private static Map<String,Map<String,Integer>> allWordsMap=new HashMap<String,Map<String,Integer> >();
	private static String SAVE_TRAIN_FILE=".\\trainResult";

    /** Creates new form GUIFancy */
    public GUIFancy() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

	public void writeTrainFile() throws FileNotFoundException, IOException{
		FileOutputStream out = null;

		Set<String> keySet = allWordsMap.keySet();
		for(String key:keySet){
			System.out.println(key);
			String strFilename = SAVE_TRAIN_FILE + File.separator + key +".dat";
            //ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("计算机.dat"));
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(strFilename));
			Object obj = allWordsMap.get(key);
            output.writeObject(obj);
            output.close();
		}
	}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("训练");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jButton1)
                .addContainerGap(274, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jButton1)
                .addContainerGap(216, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int n = chooser.showOpenDialog(getContentPane());
        if (n == JFileChooser.APPROVE_OPTION) {
			TrainSampleDataManager.TRAIN_DIR = chooser.getSelectedFile().getPath();
			TrainSampleDataManager.process();
			allWordsMap = TrainSampleDataManager.allWordsMap;
		}
		try {
			this.writeTrainFile();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(GUIFancy.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GUIFancy.class.getName()).log(Level.SEVERE, null, ex);
		}
	}//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIFancy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

}

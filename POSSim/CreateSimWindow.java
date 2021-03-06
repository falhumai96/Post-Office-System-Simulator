package POSSim;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CreateSimWindow extends javax.swing.JFrame {

    public IntroWindow parentWindow = null;

    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void writeToFile(String text, String path) {
        try {
            File pathFile = new File(path);
            pathFile.createNewFile();
            PrintWriter writer = new PrintWriter(pathFile);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            try {
                File pathFile = new File(path);
                PrintWriter writer = new PrintWriter(pathFile);
                writer.write(text);
                writer.close();
            } catch (FileNotFoundException e2) {

            }
        }
    }
    public CreateSimWindow() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelActionPerformed(null);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // will be disposed only by the previous method
            }
        });
        initComponents();
        setLocationRelativeTo(parentWindow);
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        simName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        chooseSimPath = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        simPath = new javax.swing.JTextArea();
        cancel = new javax.swing.JButton();
        select = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create a new simulation");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(200, 3));
        setResizable(false);

        jLabel1.setText("Simulation name");

        jLabel2.setText("Simulation path");

        chooseSimPath.setBackground(new java.awt.Color(204, 204, 204));
        chooseSimPath.setText("Choose the simulation path");
        chooseSimPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseSimPathActionPerformed(evt);
            }
        });

        simPath.setEditable(false);
        simPath.setColumns(20);
        simPath.setRows(5);
        jScrollPane1.setViewportView(simPath);

        cancel.setBackground(new java.awt.Color(204, 204, 204));
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        select.setBackground(new java.awt.Color(204, 204, 204));
        select.setText("Select");
        select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(simName, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chooseSimPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                        .addGap(87, 87, 87))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chooseSimPath)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(select)
                    .addComponent(cancel))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        parentWindow.createWindow.remove(this);
    }

    private void chooseSimPathActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            JFileChooser fc = new JFileChooser(".");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fc.showDialog(this, "Select the simulation folder") != fc.APPROVE_OPTION) {
                return;
            }
            simPath.setText(fc.getSelectedFile().getAbsolutePath());
        } catch (NullPointerException e) {

        }
    }

    private void selectActionPerformed(java.awt.event.ActionEvent evt) {
        if (simName.getText().trim().equals("".trim()) == true) {
            JOptionPane.showMessageDialog(this, "Simulation name must not be empty!!");
            return;
        }

        if (simPath.getText().trim().equals("".trim()) == true) {
            JOptionPane.showMessageDialog(this, "Simulation path must not be empty!!");
            return;
        }

        if ((new File(simPath.getText().trim() + File.separator.trim() + simName.getText().trim()).mkdir()) == false) {
            JOptionPane.showMessageDialog(this, "Failed to create the simulation directory in the specified path!!");
            return;
        }

        SimulationEditorWindow newEditorWindow = new SimulationEditorWindow();
        newEditorWindow.main(parentWindow.argsGlobal);
        newEditorWindow.simName = simName.getText().trim();
        newEditorWindow.simPath = simPath.getText().trim();
        newEditorWindow.parentWindow = this.parentWindow;
        newEditorWindow.parentWindow.children.add(newEditorWindow);

        File wantedFilePath = new File(simPath.getText().trim() + File.separator.trim() + simName.getText().trim() + File.separator + "wanted.txt");
        File officesFilePath = new File(simPath.getText().trim() + File.separator.trim() + simName.getText().trim() + File.separator + "offices.txt");
        File commandsFilePath = new File(simPath.getText().trim() + File.separator.trim() + simName.getText().trim() + File.separator + "commands.txt");
        newEditorWindow.wantedFilePath = wantedFilePath;
        newEditorWindow.officesFilePath = officesFilePath;
        newEditorWindow.commandsFilePath = commandsFilePath;

        writeToFile("2\nFaisal\nSome_Other_Wanted_Person", wantedFilePath.getAbsolutePath());
        writeToFile("2\nBerlin 4 10 1000 10000 300\nVancouver 4 10 1000 10000 300", officesFilePath.getAbsolutePath());
        writeToFile("0", commandsFilePath.getAbsolutePath());

        newEditorWindow.setVisible(true);
        parentWindow.children.add(newEditorWindow);

        JOptionPane.showMessageDialog(newEditorWindow, "Simulation creation done!!");
        dispose();
        parentWindow.createWindow.remove(this);

    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("CDE/Motif".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateSimWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateSimWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateSimWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateSimWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    private javax.swing.JButton cancel;
    private javax.swing.JButton chooseSimPath;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton select;
    private javax.swing.JTextField simName;
    private javax.swing.JTextArea simPath;
}

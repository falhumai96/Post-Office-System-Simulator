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
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class SideEditorWindow extends javax.swing.JFrame {

    public File filePath;
    public SimulationEditorWindow parentWindow;
    public String editorWindowTitle;

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

    public SideEditorWindow() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitActionPerformed(null);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // will be disposed only by the previous method
            }
        });
        initComponents();
        setLocationRelativeTo(parentWindow);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textToEdit = new javax.swing.JEditorPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveAndExit = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        help = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        textToEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New", 0, 18)));
        textToEdit.setFont(new java.awt.Font("Courier New", 0, 18));
        jScrollPane1.setViewportView(textToEdit);

        jMenu1.setText("File");

        saveAndExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveAndExit.setText("Save and exit");
        saveAndExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAndExitActionPerformed(evt);
            }
        });
        jMenu1.add(saveAndExit);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exit.setText("Exit without saving");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Info");

        help.setText("Help");
        help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed(evt);
            }
        });
        jMenu2.add(help);

        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        jMenu2.add(about);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );

        pack();
    }

    private void saveAndExitActionPerformed(java.awt.event.ActionEvent evt) {
        writeToFile(textToEdit.getText(), filePath.getAbsolutePath());
        if (filePath.getName().trim().equals("wanted.txt".trim())) {
            parentWindow.wantedFileText = textToEdit.getText();
        } else {
            parentWindow.officeFileText = textToEdit.getText();
        }
        parentWindow.isSideEditorWindowsOpen = false;
        parentWindow.sideEditorWindow = null;
        dispose();
    }

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {
        parentWindow.isSideEditorWindowsOpen = false;
        parentWindow.sideEditorWindow = null;
        dispose();
    }

    private void helpActionPerformed(java.awt.event.ActionEvent evt) {
        HelpPopup helpPopup = new HelpPopup();
        helpPopup.setSize(500, 500);
        String message = ""
                + "\n\n#1 Commands usage:\n"
                + "    - \"DAY\":          Description:            To advance the current day of the system.\n"
                + "                      Arguments (in order):   No arguments.\n"
                + "    - \"PICKUP\":       Description:            To pick up all existing items of a person\n"
                + "                                              in an existing office.\n"
                + "                      Arguments (in order):   (1) String of the existing office.\n"
                + "                                              (2) String of the recipient name.\n"
                + "    - \"LETTER\":       Description:            To send a letter from one existing office\n"
                + "                                              to another.\n"
                + "                      Arguments (in order):   (1) String of the existing sending office.\n"
                + "                                              (2) String of the recipient name.\n"
                + "                                              (3) String   of  the  existing  receiving \n"
                + "                                                  office.\n"
                + "                                              (4) String  of   the   return   recipient \n"
                + "                                                  (optional  -  use \"NONE\" if you don't \n"
                + "                                                  want to use it).\n"
                + "    - \"PACKAGE\":      Description:            To  send   a   package  from one  existing \n"
                + "                                              office to another.\n"
                + "                      Arguments (in order):   (1) String of the existing sending office.\n"
                + "                                              (2) String of the recipient name.\n"
                + "                                              (3) String   of   the  existing  receiving \n"
                + "                                                  office.\n"
                + "                                              (4) Unsigned integer for the package size.\n"
                + "                                              (5) Unsigned  integer   for  the  required \n"
                + "                                                  postage.\n"
                + "    - \"BUILD\":        Description:            To build a new office that does not exists\n"
                + "                                              before, or overwrite an existing one  with \n"
                + "                                              new parameters.\n"
                + "                      Arguments (in order):   (1) String naming the new office.\n"
                + "                                              (2) An  unsigned  integer  for the transit \n"
                + "                                                  time     of       any     deliverables \n"
                + "                                                  (letter/package) from this office.\n"
                + "                                              (3) An   unsigned    integer     for   the\n"
                + "                                                  required postage from any package.\n"
                + "                                              (4) An unsigned integer  for  the  maximum \n"
                + "                                                  capacity of this office.\n"
                + "                                              (5) An unsigned integer for the persuasion\n"
                + "                                                  amount needed (i.e. bribery)  to  pass \n"
                + "                                                  an oversized package (the package will\n"
                + "                                                  be sent by this office,  but  it  will \n"
                + "                                                  not  be   received  by  the  receiving \n"
                + "                                                  office).\n"
                + "                                              (6) An unsigned integer  for  the  maximum \n"
                + "                                                  package length  to  be  sent   to   or \n"
                + "                                                  received from this office.\n"
                + "    - \"SCIENCE\":      Description:            To advance or rewind to a certain day.\n"
                + "                      Arguments (in order):   (1) An integer to show the number of days \n"
                + "                                                  to advance or rewind to a certain day \n"
                + "                                                  (0: repeat the day, positive: advance\n"
                + "                                                  forward,       negative:      advance \n"
                + "                                                  backwards). It will only jump to  the \n"
                + "                                                  \"DAY\" label   represented    by   the \n"
                + "                                                  integer   argument.    WARNING:   The \n"
                + "                                                  current simulation will crash if  the \n"
                + "                                                  integer   argument  goes  beyond  the \n"
                + "                                                  bounds of the current available \"DAY\" \n"
                + "                                                  command.\n"
                + "    - \"NSADELAY\":     Description:            Delays all deliverables of  a  person  in \n"
                + "                                              the entire system.\n"
                + "                      Arguments (in order):   (1) A string naming the person  to  delay \n"
                + "                                                  there deliverables.\n"
                + "                                              (2) An unsigned integer for the number of \n"
                + "                                                  days to delay.\n"
                + "    - \"GOOD\":         Description:            To advance to the next day if and only if\n"
                + "                                              there exists a successful previous pickup.\n"
                + "                      Arguments (in order):   No arguments.\n"
                + "    - \"SNEAK\":        Description:            This   argument  succeed  a  \"LETTER\"  or \n"
                + "                                              \"PACKAGE\" commands to  skip  the  sending\n"
                + "                                              requirements  (useful   for  one  command \n"
                + "                                              only). This will only  allow  sending  if \n"
                + "                                              the sending requirements have failed, but\n"
                + "                                              not the receiving requirements.\n"
                + "                      Arguments (in order):   No arguments.\n"
                + "    - \"INFLATION\":    Description:            Increase the persuasion  amount  and  the \n"
                + "                                              required postage of all  offices  by  the \n"
                + "                                              number of items in that office.\n"
                + "                      Arguments (in order):   No arguments.\n"
                + "    - \"DEFLATION\":    Description:            Decrease the persuasion  amount  and  the \n"
                + "                                              required postage of all  offices  by  the \n"
                + "                                              number of items in that office.\n"
                + "                      Arguments (in order):   No arguments.\n"
                + "\n"
                + "#2 Offices file formatting: The same formatting as the \"BUILD\" command  above  (without \n"
                + "                            the \"BUILD\" command itself).\n"
                + "\n"
                + "#3 Wanted file formatting: Just list the names of the people you do not  want  them  to \n"
                + "                           send or receive deliverables.\n"
                + "\n"
                + "#4 All files must be preceded by the number of commands, offices, or wanted people in that\n"
                + "   certain file.\n"
                + "\n"
                + "#5 The requirements for sending or receiving deliverables are shown below:\n"
                + "    (1) Both the sending and receiving post offices exist. \n"
                + "    (2) The package size must meet both the sending and recieving post offices, or  at\n"
                + "        least the package has enough money with it to meet the  required  postage  and\n"
                + "        persuation amount.\n"
                + "    (3) It must not be sent by a wanted person (it can be recieved, but the  receiving\n"
                + "        post office will be disabled from  any  further  actions  (sending,  receiving \n"
                + "        ...etc.)).\n\n\n";
        helpPopup.messageText.setText(message);
        JOptionPane.showMessageDialog(this, helpPopup, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {
        AboutPopup aboutPopup = new AboutPopup();
        String message = ""
                + "\n\n                   Post Office System Simulator v0.1   \n"
                + "\n"
                + "                      Made by: Faisal Al-Humaimidi.\n\n\n"
                + "    Copyright (c): March 27th 2016, Faisal Al-Humaimidi. All rights reserved.\n";
        aboutPopup.messageText.setText(message);
        JOptionPane.showMessageDialog(this, aboutPopup, "About", JOptionPane.INFORMATION_MESSAGE);
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
            java.util.logging.Logger.getLogger(SideEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SideEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SideEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SideEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    private javax.swing.JMenuItem about;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenuItem help;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem saveAndExit;
    public javax.swing.JEditorPane textToEdit;
}

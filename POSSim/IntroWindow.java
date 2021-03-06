package POSSim;

import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class IntroWindow extends javax.swing.JFrame {

    public static String[] argsGlobal;
    public List<CreateSimWindow> createWindow;
    public List<SimulationEditorWindow> children;

    public IntroWindow() {
        createWindow = new LinkedList<>();
        children = new LinkedList<>();
        initComponents();
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitActionPerformed(null);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }

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

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        createSim = new javax.swing.JButton();
        loadSim = new javax.swing.JButton();
        help = new javax.swing.JButton();
        about = new javax.swing.JButton();
        exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POSS (v0.1)");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Eras Bold ITC", 1, 24));
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Post");

        jLabel2.setFont(new java.awt.Font("Eras Bold ITC", 1, 24));
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Office");

        jLabel3.setFont(new java.awt.Font("Eras Bold ITC", 1, 24));
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("System");

        jLabel4.setFont(new java.awt.Font("Eras Bold ITC", 1, 24));
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Simulator");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel5.setText("v0.1");

        createSim.setBackground(new java.awt.Color(204, 204, 204));
        createSim.setText("Create");
        createSim.setToolTipText("Create a new simulation");
        createSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createSimActionPerformed(evt);
            }
        });

        loadSim.setBackground(new java.awt.Color(204, 204, 204));
        loadSim.setText("Load");
        loadSim.setToolTipText("Load an existing simulation");
        loadSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSimActionPerformed(evt);
            }
        });

        help.setBackground(new java.awt.Color(204, 204, 204));
        help.setText("Help");
        help.setToolTipText("Help");
        help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed(evt);
            }
        });

        about.setBackground(new java.awt.Color(204, 204, 204));
        about.setText("About");
        about.setToolTipText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });

        exit.setBackground(new java.awt.Color(204, 204, 204));
        exit.setText("Exit");
        exit.setToolTipText("Exit the program");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(createSim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(loadSim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(help, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(about, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(exit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createSim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadSim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(help)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(about))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        pack();
    }

    private void createSimActionPerformed(java.awt.event.ActionEvent evt) {
        if (createWindow.isEmpty() && children.isEmpty()) {
            CreateSimWindow temp = new CreateSimWindow();
            createWindow.add(temp);
            temp.main(argsGlobal);
            temp.setVisible(true);
            temp.parentWindow = this;
        }
    }

    private void loadSimActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (children.isEmpty() && createWindow.isEmpty()) {
                JFileChooser fc = new JFileChooser(".");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fc.showDialog(this, "Select the simulation folder") != fc.APPROVE_OPTION) {
                    return;
                }

                File lookUpPath = new File(fc.getSelectedFile().getAbsolutePath());

                if (lookUpPath.exists() == false) {
                    JOptionPane.showMessageDialog(this, "Simulation folder does not exist!!");
                    return;
                }

                boolean wantedDNE = false;
                boolean officesDNE = false;
                boolean commandsDNE = false;

                File lookUpPathWanted = new File(fc.getSelectedFile().getAbsolutePath() + File.separator + "wanted.txt");
                if (lookUpPathWanted.exists() == false) {
                    wantedDNE = true;
                }

                File lookUpPathOffices = new File(fc.getSelectedFile().getAbsolutePath() + File.separator + "offices.txt");
                if (lookUpPathOffices.exists() == false) {
                    officesDNE = true;
                }

                File lookUpPathcommands = new File(fc.getSelectedFile().getAbsolutePath() + File.separator + "commands.txt");
                if (lookUpPathcommands.exists() == false) {
                    commandsDNE = true;
                }

                if (wantedDNE == true && officesDNE == true && commandsDNE == true) {
                    JOptionPane.showMessageDialog(this, "\"wanted.txt,\" \"offices.txt,\" and \"commands.txt\" files do not exist!!");
                    return;
                } else if (wantedDNE == true && officesDNE == true) {
                    JOptionPane.showMessageDialog(this, "\"wanted.txt\" and \"offices.txt\" files do not exist!!");
                    return;
                } else if (wantedDNE == true && commandsDNE == true) {
                    JOptionPane.showMessageDialog(this, "\"wanted.txt\" and \"commands.txt\" files do not exist!!");
                    return;
                } else if (officesDNE == true && commandsDNE == true) {
                    JOptionPane.showMessageDialog(this, "\"offices.txt\" and \"commands.txt\" files do not exist!!");
                    return;
                } else if (wantedDNE == true) {
                    JOptionPane.showMessageDialog(this, "\"wanted.txt\" file does not exist!!");
                    return;
                } else if (officesDNE == true) {
                    JOptionPane.showMessageDialog(this, "\"offices.txt\" file does not exist!!");
                    return;
                } else if (commandsDNE == true) {
                    JOptionPane.showMessageDialog(this, "\"commands.txt\" file does not exist!!");
                    return;
                }

                SimulationEditorWindow newEditorWindow = new SimulationEditorWindow();
                newEditorWindow.parentWindow = this;
                children.add(newEditorWindow);
                newEditorWindow.main(argsGlobal);
                newEditorWindow.simName = fc.getSelectedFile().getName();
                newEditorWindow.simPath = fc.getSelectedFile().getParent();

                newEditorWindow.wantedFilePath = lookUpPathWanted;
                newEditorWindow.officesFilePath = lookUpPathOffices;
                newEditorWindow.commandsFilePath = lookUpPathcommands;
                newEditorWindow.commandsText.setText(readFile(newEditorWindow.commandsFilePath.getAbsolutePath(), StandardCharsets.UTF_8));
                newEditorWindow.wantedFileText = readFile(newEditorWindow.wantedFilePath.getAbsolutePath(), StandardCharsets.UTF_8);
                newEditorWindow.officeFileText = readFile(newEditorWindow.officesFilePath.getAbsolutePath(), StandardCharsets.UTF_8);

                JOptionPane.showMessageDialog(newEditorWindow, "Simulation loaded!!");
                newEditorWindow.setVisible(true);
            }
        } catch (NullPointerException e) {

        } catch (HeadlessException e) {

        } catch (IOException e) {

        }
    }

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {
        boolean unsavedChanges = false;
        for (SimulationEditorWindow simE : children) {
            try {
                String oldWantedText = readFile(simE.wantedFilePath.getAbsolutePath(), StandardCharsets.UTF_8);
                String oldOfficesText = readFile(simE.officesFilePath.getAbsolutePath(), StandardCharsets.UTF_8);
                String oldCommandsText = readFile(simE.commandsFilePath.getAbsolutePath(), StandardCharsets.UTF_8);

                if (simE.commandsFilePath.exists() == false || simE.commandsText.getText().equals(oldCommandsText) == false) {
                    unsavedChanges = true;
                    switch (JOptionPane.showConfirmDialog(rootPane, "There are unsaved changes. Discard them?")) {
                        case JOptionPane.OK_OPTION:
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                }
                if (unsavedChanges == true) {
                    break;
                }
            } catch (IOException e) {

            }
        }
        if (unsavedChanges == false) {
            System.exit(0);
        }
    }

    private void helpActionPerformed(java.awt.event.ActionEvent evt) {
        if (createWindow.isEmpty() && children.isEmpty()) {
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
    }

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {
        if (createWindow.isEmpty() && children.isEmpty()) {
            AboutPopup aboutPopup = new AboutPopup();
            String message = ""
                    + "\n\n                   Post Office System Simulator v0.1   \n"
                    + "\n"
                    + "                      Made by: Faisal Al-Humaimidi.\n\n\n"
                    + "    Copyright (c): March 27th 2016, Faisal Al-Humaimidi. All rights reserved.\n";
            aboutPopup.messageText.setText(message);
            JOptionPane.showMessageDialog(this, aboutPopup, "About", JOptionPane.INFORMATION_MESSAGE);
        }
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
            java.util.logging.Logger.getLogger(IntroWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IntroWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IntroWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IntroWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        argsGlobal = args;

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IntroWindow().setVisible(true);
            }
        });
    }

    private javax.swing.JButton about;
    private javax.swing.JButton createSim;
    private javax.swing.JButton exit;
    private javax.swing.JButton help;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton loadSim;
}

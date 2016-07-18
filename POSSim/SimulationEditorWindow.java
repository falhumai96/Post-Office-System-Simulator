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
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import POSSim.simulator.*;

public class SimulationEditorWindow extends javax.swing.JFrame {

    public String officeFileText;
    public String wantedFileText;
    public IntroWindow parentWindow;
    public boolean isSimRunning;
    public boolean isSideEditorWindowsOpen;
    public String simPath;
    public String simName;
    public File wantedFilePath;
    public File officesFilePath;
    public File commandsFilePath;
    public List<SimulationOutputWindow> openedSimWindows;
    public RunCommand running;
    public SideEditorWindow sideEditorWindow;

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

    private boolean commandChecker(String text) {
        List<String> cmdsWords = stringTokenizer(text);

        if (cmdsWords.isEmpty()) {
            return false;
        }

        for (int i = 0; i < cmdsWords.size(); ++i) {
            cmdsWords.set(i, ((String) cmdsWords.get(i)).trim());
        }

        int num;
        try {
            num = Integer.parseInt(cmdsWords.get(0));
            if (num < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        int count = 0;
        for (int i = 1; i < cmdsWords.size();) {
            if (((String) cmdsWords.get(i)).trim().equals("DAY".trim())) {
                ++count;
                ++i;
            } else if (((String) cmdsWords.get(i)).trim().equals("PICKUP".trim())) {
                if (i > cmdsWords.size() - 3) {
                    return false;
                }
                ++count;
                i = i + 3;
            } else if (((String) cmdsWords.get(i)).trim().equals("LETTER".trim())) {
                if (i > cmdsWords.size() - 5) {
                    return false;
                }
                ++count;
                i = i + 5;
            } else if (((String) cmdsWords.get(i)).trim().equals("PACKAGE".trim())) {
                if (i > cmdsWords.size() - 6) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 4)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 5)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                ++count;
                i = i + 6;
            } else if (((String) cmdsWords.get(i)).trim().equals("BUILD".trim())) {
                if (i > cmdsWords.size() - 7) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 2)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 3)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 4)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 5)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 6)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                ++count;
                i += 7;
            } else if (((String) cmdsWords.get(i)).trim().equals("SCIENCE".trim())) {
                if (i > cmdsWords.size() - 2) {
                    return false;
                }

                try {
                    Integer.parseInt((String) cmdsWords.get(i + 1));
                } catch (NumberFormatException e) {
                    return false;
                }
                ++count;
                i = i + 2;
            } else if (((String) cmdsWords.get(i)).trim().equals("GOOD".trim())) {
                ++count;
                ++i;
            } else if (((String) cmdsWords.get(i)).trim().equals("NSADELAY".trim())) {
                if (i > cmdsWords.size() - 3) {
                    return false;
                }

                try {
                    if (Integer.parseInt((String) cmdsWords.get(i + 2)) < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                ++count;
                i = i + 3;
            } else if (((String) cmdsWords.get(i)).trim().equals("SNEAK".trim())) {
                ++count;
                ++i;
            } else if (((String) cmdsWords.get(i)).trim().equals("INFLATION".trim())) {
                ++count;
                ++i;
            } else if (((String) cmdsWords.get(i)).trim().equals("DEFLATION".trim())) {
                ++count;
                ++i;
            } else {
                return false;
            }
        }
        return count == num;
    }

    private boolean wantedChecker(String text) {
        List<String> wntdWords = stringTokenizer(text);

        if (wntdWords.isEmpty()) {
            return false;
        }

        for (int i = 0; i < wntdWords.size(); ++i) {
            wntdWords.set(i, ((String) wntdWords.get(i)).trim());
        }

        int num;
        try {
            num = Integer.parseInt(wntdWords.get(0));
            if (num < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        if (num != wntdWords.size() - 1) {
            return false;
        }

        return true;
    }

    private boolean officesChecker(String text) {
        List<String> ofcs = stringTokenizer(text);

        if (ofcs.isEmpty()) {
            return false;
        }

        for (int i = 0; i < ofcs.size(); ++i) {
            ofcs.set(i, ((String) ofcs.get(i)).trim());
        }

        int num;
        try {
            num = Integer.parseInt(ofcs.get(0));
            if (num <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        int count = 0;
        for (int i = 1; i < ofcs.size();) {

            if (i > ofcs.size() - 6) {
                return false;
            }

            try {
                if (Integer.parseInt((String) ofcs.get(i + 1)) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            try {
                if (Integer.parseInt((String) ofcs.get(i + 2)) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            try {
                if (Integer.parseInt((String) ofcs.get(i + 3)) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            try {
                if (Integer.parseInt((String) ofcs.get(i + 4)) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            try {
                if (Integer.parseInt((String) ofcs.get(i + 5)) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            ++count;
            i += 6;
        }
        return count == num;
    }

    public List<String> stringTokenizer(String inp) {
        List<String> out = new LinkedList<>();
        Scanner temp = new Scanner(inp);
        while (temp.hasNext()) {
            out.add(temp.next());
        }
        return out;
    }

    public SimulationEditorWindow() {
        this.sideEditorWindow = null;
        this.running = null;
        this.wantedFileText = "2\nFaisal\nSome_Other_Wanted_Person";
        this.officeFileText = "2\nBerlin 4 10 1000 10000 300\nVancouver 4 10 1000 10000 300";
        this.parentWindow = null;
        this.isSimRunning = false;
        this.isSideEditorWindowsOpen = false;
        this.simName = "";
        this.simPath = "";
        this.openedSimWindows = new LinkedList<>();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitActionPerformed(null);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
        initComponents();
        this.commandsText.setText("0");
        setLocationRelativeTo(parentWindow);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        commandsText = new javax.swing.JEditorPane();
        menu = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        saveCommand = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        setIniOffices = new javax.swing.JMenuItem();
        setWanted = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        help = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();
        sim = new javax.swing.JMenu();
        runSim = new javax.swing.JMenuItem();
        stopSim = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Simulation editor");

        commandsText.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Commands editor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New", 0, 18)));
        commandsText.setFont(new java.awt.Font("Courier New", 0, 18));
        jScrollPane1.setViewportView(commandsText);

        file.setText("File");

        saveCommand.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveCommand.setText("Save current command file");
        saveCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCommandActionPerformed(evt);
            }
        });
        file.add(saveCommand);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exit.setText("Exit editor");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        file.add(exit);

        menu.add(file);

        edit.setText("Edit");

        setIniOffices.setText("Set initial post office(s) file");
        setIniOffices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setIniOfficesActionPerformed(evt);
            }
        });
        edit.add(setIniOffices);

        setWanted.setText("Set wanted file");
        setWanted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setWantedActionPerformed(evt);
            }
        });
        edit.add(setWanted);

        menu.add(edit);

        jMenu1.setText("Info");

        help.setText("Help");
        help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed(evt);
            }
        });
        jMenu1.add(help);

        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        jMenu1.add(about);

        menu.add(jMenu1);

        sim.setText("Simulation");

        runSim.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        runSim.setText("Run simulation");
        runSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runSimActionPerformed(evt);
            }
        });
        sim.add(runSim);

        stopSim.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        stopSim.setText("Stop simulation");
        stopSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopSimActionPerformed(evt);
            }
        });
        sim.add(stopSim);

        menu.add(sim);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );

        pack();
    }

    private void setIniOfficesActionPerformed(java.awt.event.ActionEvent evt) {
        if (isSideEditorWindowsOpen == false && isSimRunning == false) {
            try {
                isSideEditorWindowsOpen = true;
                SideEditorWindow initOfficesWindow = new SideEditorWindow();
                initOfficesWindow.main(null);
                initOfficesWindow.parentWindow = this;
                initOfficesWindow.filePath = officesFilePath;
                initOfficesWindow.textToEdit.setText(readFile(officesFilePath.getAbsolutePath(), StandardCharsets.UTF_8));
                initOfficesWindow.textToEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Initial office(s) editor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New", 0, 18)));
                sideEditorWindow = initOfficesWindow;
                initOfficesWindow.setVisible(true);
            } catch (IOException e) {

            }
        }
    }

    private void runSimActionPerformed(java.awt.event.ActionEvent evt) {
        if (isSideEditorWindowsOpen == true) {
            return;
        }

        if (commandChecker(commandsText.getText()) == false) {
            JOptionPane.showMessageDialog(this, "Wrong commands file!!");
            return;
        }

        if (wantedChecker(wantedFileText) == false) {
            JOptionPane.showMessageDialog(this, "Wrong wanted file!!");
            return;
        }

        if (officesChecker(officeFileText) == false) {
            JOptionPane.showMessageDialog(this, "Wrong offices file!!");
            return;
        }

        if (isSimRunning == true) {
            stopSimActionPerformed(null);
        }

        running = new RunCommand();
        try {
            running.initOffices(officeFileText);
            running.initWanted(wantedFileText);
            running.initCommands(commandsText.getText());

            writeToFile(commandsText.getText(), commandsFilePath.getAbsolutePath());
            writeToFile(wantedFileText, wantedFilePath.getAbsolutePath());
            writeToFile(officeFileText, officesFilePath.getAbsolutePath());

            running.commandsFile = commandsFilePath;
            running.wantedFile = wantedFilePath;
            running.officeFile = officesFilePath;
            running.parentWindow = this;
            running.simPath = new File(simPath + File.separator + simName);
            running.prepareFiles();
            running.run();
            isSimRunning = true;
        } catch (Exception e) {

        }
    }

    private void saveCommandActionPerformed(java.awt.event.ActionEvent evt) {
        writeToFile(commandsText.getText(), commandsFilePath.getAbsolutePath());
    }

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String oldCommandsText = readFile(commandsFilePath.getAbsolutePath(), StandardCharsets.UTF_8);
            if (commandsFilePath.exists() == false || commandsText.getText().equals(oldCommandsText) == false) {
                switch (JOptionPane.showConfirmDialog(this, "There are unsaved changes. Discard them?")) {
                    case JOptionPane.OK_OPTION:
                        stopSimActionPerformed(null);

                        boolean val = true;
                        while (val == true) {
                            val = parentWindow.children.remove(this);
                        }
                        if (sideEditorWindow != null) {
                            sideEditorWindow.dispose();
                        }
                        dispose();
                        break;
                    default:
                        break;
                }
            } else {
                stopSimActionPerformed(null);
                boolean val = true;
                while (val == true) {
                    val = parentWindow.children.remove(this);
                }
                if (sideEditorWindow != null) {
                    sideEditorWindow.dispose();
                }
                dispose();
            }
        } catch (HeadlessException | IOException e) {
        }
    }

    private void setWantedActionPerformed(java.awt.event.ActionEvent evt) {
        if (isSideEditorWindowsOpen == false && isSimRunning == false) {
            try {
                isSideEditorWindowsOpen = true;
                SideEditorWindow wantedWindow = new SideEditorWindow();
                wantedWindow.main(null);
                wantedWindow.parentWindow = this;
                wantedWindow.filePath = wantedFilePath;
                wantedWindow.textToEdit.setText(readFile(wantedFilePath.getAbsolutePath(), StandardCharsets.UTF_8));
                wantedWindow.textToEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wanted editor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New", 0, 18)));
                sideEditorWindow = wantedWindow;
                wantedWindow.setVisible(true);
            } catch (IOException e) {

            }
        }
    }

    private void stopSimActionPerformed(java.awt.event.ActionEvent evt) {
        if (isSimRunning == true && isSideEditorWindowsOpen == false) {
            for (SimulationOutputWindow outWindow : running.officessWindows) {
                outWindow.dispose();
            }
            running.officessWindows.clear();
            running.currentIndex = running.commands.size(); // break out of the commands loop
            running = null;
            isSimRunning = false;
        }
    }

    private void helpActionPerformed(java.awt.event.ActionEvent evt) {
        if (isSideEditorWindowsOpen == false && isSimRunning == false) {
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
        if (isSideEditorWindowsOpen == false && isSimRunning == false) {
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
            java.util.logging.Logger.getLogger(SimulationEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimulationEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimulationEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimulationEditorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    private javax.swing.JMenuItem about;
    public javax.swing.JEditorPane commandsText;
    private javax.swing.JMenu edit;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu file;
    private javax.swing.JMenuItem help;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem runSim;
    private javax.swing.JMenuItem saveCommand;
    private javax.swing.JMenuItem setIniOffices;
    private javax.swing.JMenuItem setWanted;
    private javax.swing.JMenu sim;
    private javax.swing.JMenuItem stopSim;
}

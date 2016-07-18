package POSSim.simulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import POSSim.SimulationOutputWindow;

public class Logging {

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

    public void appendFile(String text, String path) {
        try {
            String oldText = readFile(path, StandardCharsets.UTF_8);
            oldText += text;
            writeToFile(oldText, path);
        } catch (IOException e) {
        }
    }

    public void sleepProg() {
        for (int i = 0; i < 0; ++i) {
            System.out.print("");
        }
    }

    public void endOfDay(String type, int day, String officeName, RunCommand parent) {
        String message = "- - DAY " + day + " OVER - -\n";
        if (type.trim().equals("MASTER".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));

            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("MASTER".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else if (type.trim().equals("FRONT".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("FRONT".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + officeName + ".txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals(officeName.trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        }
        sleepProg();
    }

    public void newDeliverable(String type, Deliverable d, String dest, RunCommand parent) {
        if (d != null && d.getIniatingOffice() != null) {
            String message = "";
            if (d instanceof Letter) {
                message += "- New letter -\n";
            } else {
                message += "- New package -\n";
            }


            message += "Source: " + d.getIniatingOffice().name + "\n";
            message += "Destination: " + dest + "\n";

            if (type.trim().equals("MASTER".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("MASTER".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (type.trim().equals("FRONT".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("FRONT".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (d.getIniatingOffice() != null) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            }
            sleepProg();
        }
    }

    public void rejectDeliverable(String type, Deliverable d, String dest, String src, RunCommand parent) {
        if (d != null) {
            String message = "";
            if (d instanceof Letter) {
                message += "- Rejected letter -\n";
            } else {
                message += "- Rejected package -\n";
            }
            message += "Source: " + src + "\n";

            if (type.trim().equals("MASTER".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("MASTER".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (type.trim().equals("FRONT".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("FRONT".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (d.getIniatingOffice() != null) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (d.getIniatingOffice() != null && simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            }
            sleepProg();
        }
    }

    public void deliverableAccepted(String type, Deliverable d, String dest, RunCommand parent) {
        if (d != null) {
            String message = "";
            if (d instanceof Letter) {
                message += "- Accepted letter -\n";
            } else {
                message += "- Accepted package -\n";
            }
            message += "Destination: " + dest + "\n";

            if (type.trim().equals("MASTER".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("MASTER".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (type.trim().equals("FRONT".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("FRONT".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (d.getIniatingOffice() != null) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            }
            sleepProg();
        }
    }

    public void deliverableDestroyed(String type, Deliverable d, String destroyedAt, RunCommand parent) {
        if (d != null) {
            String message = "";
            if (d instanceof Letter) {
                message += "- Incinerated letter -\n";
            } else {
                message += "- Incinerated package -\n";
            }
            message += "Destroyed at: " + destroyedAt + "\n";

            if (type.trim().equals("MASTER".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("MASTER".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (type.trim().equals("FRONT".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("FRONT".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (d.getIniatingOffice() != null) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            }
            sleepProg();
        }
    }

    public void briberyDetected(String type, Deliverable d, String src, RunCommand parent) {
        String message = "- Something funny going on... -\n";
        message += "Where did that extra money at " + src + " come from?\n";

        if (type.trim().equals("MASTER".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("MASTER".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else if (type.trim().equals("FRONT".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("FRONT".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else if (d.getIniatingOffice() != null) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        }
        sleepProg();
    }

    public void itemComplete(String type, Deliverable d, int day, RunCommand parent) {
        if (d != null) {
            String message = "- Delivery process complete -\n";
            message += "Delivery took " + (day - d.getInitDay()) + " days\n";

            if (type.trim().equals("MASTER".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("MASTER".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (type.trim().equals("FRONT".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("FRONT".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (d.getIniatingOffice() != null) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            }
            sleepProg();
        }
    }

    public void transitSent(String type, Deliverable d, RunCommand parent) {
        if (d != null) {
            String message = "- Standard transit departure -\n";

            if (type.trim().equals("MASTER".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("MASTER".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (type.trim().equals("FRONT".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("FRONT".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (d.getIniatingOffice() != null) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            }
            sleepProg();
        }
    }

    public void transitArrived(String type, Deliverable d, RunCommand parent) {
        if (d != null) {
            String message = "- Standard transit arrival -\n";

            if (type.trim().equals("MASTER".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("MASTER".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (type.trim().equals("FRONT".trim())) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals("FRONT".trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            } else if (d.getIniatingOffice() != null) {
                appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + d.getIniatingOffice().name + ".txt"));
                for (SimulationOutputWindow simWin : parent.officessWindows) {
                    if (simWin.name.trim().equals(d.getIniatingOffice().name.trim())) {
                        simWin.outText.setText(simWin.outText.getText() + message);
                        break;
                    }
                }
            }
            sleepProg();
        }
    }

    public void criminalAppended(String type, String criminalName, String office, RunCommand parent) {
        String message = "- Criminal appended -\n";
        message += "Criminal name: " + criminalName + " at office: " + office + "\n";

        if (type.trim().equals("FRONT".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("FRONT".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        }
        sleepProg();
    }

    // new log entries
    public void officeDestroyed(String type, String officeName, RunCommand parent) {
        String message = "- " + officeName + " OFFICE DESTROYED -\n";

        if (type.trim().equals("MASTER".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("MASTER".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else if (type.trim().equals("FRONT".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("FRONT".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + officeName + ".txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals(officeName.trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        }
        sleepProg();
    }

    public void newOffice(String type, String officeName, RunCommand parent) {
        String message = "- " + officeName + " OFFICE BUILT -\n";

        if (type.trim().equals("MASTER".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("MASTER".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else if (type.trim().equals("FRONT".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_front.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("FRONT".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        } else {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_" + officeName + ".txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals(officeName.trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        }
        sleepProg();
    }

    public void goodEnough(String type, RunCommand parent) {
        String message = "- It was a good day! -\n";

        if (type.trim().equals("MASTER".trim())) {
            appendFile(message, (parent.simPath.getAbsolutePath() + File.separator + "Output" + File.separator + "log_master.txt"));
            for (SimulationOutputWindow simWin : parent.officessWindows) {
                if (simWin.name.trim().equals("MASTER".trim())) {
                    simWin.outText.setText(simWin.outText.getText() + message);
                    break;
                }
            }
        }
        sleepProg();
    }
}

package POSSim.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.Scanner;

import POSSim.SimulationEditorWindow;
import POSSim.SimulationOutputWindow;
import POSSim.simulator.*;

public class RunCommand {

    public Set<Office> offices;
    public Set<SimulationOutputWindow> officessWindows;
    public Set<String> wanted;
    public Network network;

    public File simPath;
    public File wantedFile;
    public File officeFile;
    public File commandsFile;
    public SimulationEditorWindow parentWindow;

    public String commandsFilePath;
    public String officesFilePath;
    public String wantedFilePath;
    public int currentDay;
    public int currentIndex;
    public List<String> commands;
    public boolean isGoodPickup;

    public RunCommand() {
        this.simPath = null;
        this.wanted = new HashSet<>();
        this.network = new Network();
        this.wantedFilePath = null;
        this.officesFilePath = null;
        this.commandsFilePath = null;
        this.parentWindow = null;
        this.commandsFile = null;
        this.officeFile = null;
        this.wantedFile = null;
        this.currentDay = 0;
        this.currentIndex = 0;
        this.commands = new LinkedList<>();
        this.isGoodPickup = false;
        this.officessWindows = new HashSet<>();
        this.parentWindow = null;
        this.offices = new HashSet<>();
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

    public void appendFile(String text, String path) {
        try {
            String oldText = readFile(path, StandardCharsets.UTF_8);
            oldText += text;
            writeToFile(oldText, path);
        } catch (IOException e) {

        }
    }

    public List<String> stringTokenizer(String inp) {
        List<String> out = new LinkedList<>();
        Scanner temp = new Scanner(inp);
        while (temp.hasNext()) {
            out.add(temp.next());
        }
        return out;
    }

    public Office getOffice(String officeName) {
        for (Office o : offices) {
            if (o.getName().trim().equals(officeName.trim())) {
                return o;
            }
        }
        return null;
    }

    public SimulationOutputWindow getOfficeWindow(String officeName) {
        for (SimulationOutputWindow o : officessWindows) {
            if (o.name.equals(officeName)) {
                return o;
            }
        }
        return null;
    }

    public void initOffices(String officesText) throws Exception {
        List<String> words = stringTokenizer(officesText);
        for (int i = 1; i < words.size();) {
            Office newOffice = new Office(words.get(i), Integer.parseInt(words.get(i + 1)), Integer.parseInt(words.get(i + 2)), Integer.parseInt(words.get(i + 3)), Integer.parseInt(words.get(i + 4)), Integer.parseInt(words.get(i + 5)));
            offices.add(newOffice);
            network.officeMap.put(newOffice.name, newOffice);
            i += 6;
        }
    }

    public void initWanted(String wantedText) throws Exception {
        List<String> words = stringTokenizer(wantedText);
        for (int i = 1; i < words.size(); ++i) {
            wanted.add(words.get(i));
        }
    }

    public void initCommands(String commandsText) {
        List<String> words = stringTokenizer(commandsText);
        for (int i = 1; i < words.size();) {
            if (isDayCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 1;
            } else if (isPickupCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd += words.get(i + 1) + " ";
                cmd += words.get(i + 2) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 3;
            } else if (isLetterCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd += words.get(i + 1) + " ";
                cmd += words.get(i + 2) + " ";
                cmd += words.get(i + 3) + " ";
                cmd += words.get(i + 4) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 5;
            } else if (isPackageCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd += words.get(i + 1) + " ";
                cmd += words.get(i + 2) + " ";
                cmd += words.get(i + 3) + " ";
                cmd += words.get(i + 4) + " ";
                cmd += words.get(i + 5) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 6;
            } else if (isBuildCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd += words.get(i + 1) + " ";
                cmd += words.get(i + 2) + " ";
                cmd += words.get(i + 3) + " ";
                cmd += words.get(i + 4) + " ";
                cmd += words.get(i + 5) + " ";
                cmd += words.get(i + 6) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 7;
            } else if (isScienceCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd += words.get(i + 1) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 2;
            } else if (isNSADelayCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd += words.get(i + 1) + " ";
                cmd += words.get(i + 2) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 3;
            } else if (isGoodCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 1;
            } else if (isSneakCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 1;
            } else if (isInflationCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 1;
            } else if (isDeflationCommand(words.get(i))) {
                String cmd = "";
                cmd += words.get(i) + " ";
                cmd = cmd.trim();
                commands.add(cmd);
                i = i + 1;
            }
        }
    }

    public void prepareFiles() {
        File outFolder = new File(simPath.getAbsolutePath() + File.separator + "Output");
        outFolder.mkdir();
        writeToFile("", outFolder.getAbsolutePath() + File.separator + "log_master.txt");
        SimulationOutputWindow newWindow = new SimulationOutputWindow();
        newWindow.name = "MASTER";
        newWindow.setTitle(simPath.getName() + File.separator + "log_master.txt");
        newWindow.setVisible(true);
        officessWindows.add(newWindow);

        writeToFile("", outFolder.getAbsolutePath() + File.separator + "log_front.txt");
        newWindow = new SimulationOutputWindow();
        newWindow.name = "FRONT";
        newWindow.setTitle(simPath.getName() + File.separator + "log_front.txt");
        newWindow.setVisible(true);
        officessWindows.add(newWindow);

        for (Office o : offices) {
            writeToFile("", outFolder.getAbsolutePath() + File.separator + "log_" + o.name + ".txt");
            newWindow = new SimulationOutputWindow();
            newWindow.name = o.name;
            newWindow.setTitle(simPath.getName() + File.separator + "log_" + o.name + ".txt");
            newWindow.setVisible(true);
            officessWindows.add(newWindow);
            o.setNetwork(network);
            o.setWanted(wanted);
        }
    }

    public void run() throws Exception {
        for (currentIndex = 0; currentIndex < commands.size();) {
            String cmd = commands.get(currentIndex);
            String[] parts = cmd.split(" ");
            if (isDayCommand(cmd)) {
                dayOps();
                currentIndex++;
            } else if (isPickupCommand(cmd)) {
                pickUpOps(parts);
                currentIndex++;
            } else if (isLetterCommand(cmd)) {
                letterOps(parts);
                currentIndex++;
            } else if (isPackageCommand(cmd)) {
                packageOps(parts);
                currentIndex++;
            } else if (isBuildCommand(cmd)) {
                buildOps(parts);
                currentIndex++;
            } else if (isScienceCommand(cmd)) {
                scienceOps(parts);
                // No currentIndex incrementation as it will have a special type of currentIndex change
            } else if (isGoodCommand(cmd)) {
                goodOps(parts);
                // No currentIndex incrementation as it will have a special type of currentIndex change
            } else if (isNSADelayCommand(cmd)) {
                NSADelayOps(parts);
                currentIndex++;
            } else if (isInflationCommand(cmd)) {
                inflationOps(parts);
                currentIndex++;
            } else if (isDeflationCommand(cmd)) {
                deflationOps(parts);
                currentIndex++;
            } else if (isSneakCommand(cmd)) {
                currentIndex++; // Just skip the SNEAK command when it is in the command, because SNEAK does not work by itself
            }
        }
    }

    public void dayOps() {
        for (Office o : offices) {
            o.sendToNetwork(currentDay, this);
        }

        currentDay++;
        (new Logging()).endOfDay("MASTER", currentDay, null, this);
        for (Office o : offices) {
            (new Logging()).endOfDay("OFFICE", currentDay, o.getName(), this);
        }

        for (Office o : offices) {
            for (int j = 0; j < o.toPickUp.size(); ++j) {
                o.drop(currentDay, this);
            }
        }

        network.checkAndDeliver(currentDay, this);

        for (Office o : offices) {
            for (Deliverable d : o.toPickUp) {
                d.decrementDelay(true, this);
            }
        }

        for (Deliverable d : network.deliverablesInTransit) {
            d.decrementDelay(false, this);
        }
    }

    public void pickUpOps(String[] parts) {
        String dest = parts[1].trim();
        String recipient = parts[2].trim();

        if (wanted.contains(recipient.trim())) {
            (new Logging()).criminalAppended("FRONT", recipient, dest, this);
        } else {
            Office thisOffice = getOffice(dest);
            if (thisOffice != null) {
                if (thisOffice.pickUp(recipient.trim(), currentDay, wanted, this) == true) {
                    isGoodPickup = true;
                }
            }
        }
    }

    public void letterOps(String[] parts) {
        String src = parts[1];
        String recipient = parts[2];
        String dest = parts[3];
        String returnRecipient = parts[4];
        Office srcOffice = getOffice(src);
        Office destOffice = getOffice(dest);


        Letter letter = new Letter();
        letter.setIniatingOffice(srcOffice);
        letter.setDestOffice(destOffice);
        letter.setInitDay(currentDay);
        letter.setRecipient(recipient);
        letter.setReturnRecipient(returnRecipient);
        letter.setIntendedDest(dest);

        (new Logging()).newDeliverable("OFFICE", letter, dest, this);

        boolean hasCriminalRecipient = wanted.contains(letter.getRecipient());
        boolean officeFull = srcOffice != null && srcOffice.isFull();
        if (destOffice != null && !hasCriminalRecipient && !officeFull && destOffice.isMarked == false && srcOffice != null) {
            srcOffice.accept(letter, dest, this);
        } else if (currentIndex > 0 && isSneakCommand(commands.get(currentIndex - 1)) == true && srcOffice != null) { // if there exists a SNEAK command before the current command (note that I am checking if the current command is not the first one)
            srcOffice.accept(letter, dest, this); // Item is sneaked
        } else {
            (new Logging()).rejectDeliverable("MASTER", letter, dest, src, this);
            (new Logging()).rejectDeliverable("OFFICE", letter, dest, src, this);
        }
    }

    public void packageOps(String[] parts) {
        String src = parts[1];
        String recipient = parts[2];
        String dest = parts[3];
        int money = Integer.parseInt(parts[4]);
        int length = Integer.parseInt(parts[5]);

        Office srcOffice = getOffice(src);
        Office destOffice = getOffice(dest);

        Package pkg = new Package();
        pkg.setIniatingOffice(srcOffice);
        pkg.setDestOffice(destOffice);
        pkg.setInitDay(currentDay);
        pkg.setRecipient(recipient);
        pkg.setLength(length);
        pkg.setMoney(money);
        pkg.setIntendedDest(dest);

        (new Logging()).newDeliverable("OFFICE", pkg, dest, this);

        boolean hasCriminalRecipient = wanted.contains(pkg.getRecipient());
        boolean srcOfficeFull = true;
        if (srcOffice != null) {
            srcOfficeFull = srcOffice.isFull();
        }
        boolean destOfficeFull = true;
        if (destOffice != null) {
            destOfficeFull = destOffice.isFull();
        }
        boolean lengthFitSrc = (srcOffice != null && length <= srcOffice.getMaxPackageLength());  // ***************Bug found*********************
        boolean lengthFitDst = (destOffice != null && length <= destOffice.getMaxPackageLength());
        if (hasCriminalRecipient == false && srcOfficeFull == false
                && lengthFitSrc == true && lengthFitDst == true && destOfficeFull == false
                && money >= srcOffice.getRequiredPostage()) {
            srcOffice.accept(pkg, dest, this);
        } else if (srcOffice != null && pkg.getMoney() >= (srcOffice.getRequiredPostage() + srcOffice.getPersuasionAmount())) {
            (new Logging()).briberyDetected("MASTER", pkg, srcOffice.name, this);
            srcOffice.accept(pkg, dest, this);
        } else if (currentIndex > 0 && isSneakCommand(commands.get(currentIndex - 1)) == true) { // if there exists a SNEAK command before the current command (note that I am checking if the current command is not the first one)
            srcOffice.accept(pkg, dest, this); // Item is sneaked
        } else {
            (new Logging()).rejectDeliverable("MASTER", pkg, dest, src, this);
            (new Logging()).rejectDeliverable("OFFICE", pkg, dest, src, this);
        }
    }

    public void buildOps(String[] parts) {
        Office newOffice = new Office(parts[1], Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
                Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
        boolean found = false;
        for (Office curreOffice : offices) {
            if (curreOffice.getName().trim().equals(newOffice.getName().trim())) { // If there exists an office with the same name case.
                curreOffice = newOffice;
                (new Logging()).officeDestroyed("MASTER", curreOffice.getName(), this);
                (new Logging()).officeDestroyed("OFFICE", curreOffice.getName(), this);

                (new Logging()).newOffice("MASTER", curreOffice.getName(), this);
                (new Logging()).newOffice("OFFICE", curreOffice.getName(), this);
                found = true;
            }
        }

        if (found == false) {
            File outFolder = new File(simPath.getAbsolutePath() + File.separator + "Output");
            writeToFile("", outFolder.getAbsolutePath() + File.separator + "log_" + newOffice.name + ".txt");
            SimulationOutputWindow newWindow = new SimulationOutputWindow();
            newWindow.name = newOffice.name;
            newWindow.setTitle(simPath.getName() + File.separator + "log_" + newOffice.name + ".txt");
            newWindow.setVisible(true);
            officessWindows.add(newWindow);
            offices.add(newOffice);
            (new Logging()).newOffice("MASTER", newOffice.getName(), this);
            (new Logging()).newOffice("OFFICE", newOffice.getName(), this);
        }
    }

    public void scienceOps(String[] parts) {
        boolean destroy = false;
        int daysToGoBack = Integer.parseInt(parts[1]); // we retrieved the integer
        if (daysToGoBack == 0) { // if it is 0
            while (isDayCommand(commands.get(currentIndex)) == false) {
                --currentIndex;
            }
        } else if (daysToGoBack < 0) { // if it is negative
            daysToGoBack = Math.abs(daysToGoBack);

            int daysAvailableToGoBack = 0;

            int tempIndex = currentIndex;

            while (tempIndex >= 0) {
                if (isDayCommand(commands.get(tempIndex))) {
                    ++daysAvailableToGoBack;
                }
                --tempIndex;
            }

            if (daysToGoBack <= daysAvailableToGoBack) {

                while (daysToGoBack > 0) {
                    if (isDayCommand(commands.get(currentIndex))) {
                        --daysToGoBack;
                    }
                    if (daysToGoBack != 0) {
                        --currentIndex;
                    }
                }
            } else {
                destroy = true;
            }

        } else { // if it is positive

            int daysAvailableToGoBack = 0;
            int tempIndex = currentIndex;

            while (tempIndex < commands.size()) {
                if (isDayCommand(commands.get(tempIndex))) {
                    ++daysAvailableToGoBack;
                }
                ++tempIndex;
            }

            if (daysToGoBack <= daysAvailableToGoBack) {

                while (daysToGoBack > 0) {
                    if (isDayCommand(commands.get(currentIndex))) {
                        --daysToGoBack;
                    }
                    if (daysToGoBack != 0) {
                        ++currentIndex;
                    }
                }
                ++currentIndex;
            } else {
                destroy = true;
            }
        }

        if (destroy == true) {
            for (Office o : offices) {
                for (Deliverable d : o.toPickUp) {
                    if (d instanceof Letter) {
                        (new Logging()).deliverableDestroyed("MASTER", d, d.getDestOffice().name, this);
                        (new Logging()).deliverableDestroyed("OFFICE", d, d.getDestOffice().name, this);
                    }
                }
            }

            for (Office o : offices) {
                for (Deliverable d : o.toPickUp) {
                    if (d instanceof Package) {
                        (new Logging()).deliverableDestroyed("MASTER", d, d.getDestOffice().name, this);
                        (new Logging()).deliverableDestroyed("OFFICE", d, d.getDestOffice().name, this);
                    }
                }
            }

            for (Office o : offices) {
                (new Logging()).officeDestroyed("MASTER", o.getName(), this);
                (new Logging()).officeDestroyed("OFFICE", o.getName(), this);
            }
            currentIndex = commands.size(); // break
        }
    }

    public void goodOps(String[] parts) {
        if (isGoodPickup == true) {
            while (currentIndex < commands.size() && isDayCommand(commands.get(currentIndex)) == false) {
                ++currentIndex;
            }
            (new Logging()).goodEnough("MASTER", this);
        } else {
            ++currentIndex;
        }
    }

    public void NSADelayOps(String[] parts) {
        String recipent = parts[1];
        int delayDays = Integer.parseInt(parts[2]);
        for (Office o : offices) {
            for (Deliverable d : o.toPickUp) {
                if (d.getRecipient().trim().equals(recipent.trim())) {
                    d.setDelay(delayDays);
                }
            }
        }

        for (Deliverable d : network.deliverablesInTransit) {
            if (d.getRecipient().trim().equals(recipent.trim())) {
                d.setDelay(delayDays);
            }
        }
    }

    public void inflationOps(String[] parts) {
        for (Office o : offices) {
            o.persuasionAmount += o.toPickUp.size() + o.toMail.size();
            o.requiredPostage += o.toPickUp.size() + o.toMail.size();
        }
    }

    public void deflationOps(String[] parts) {
        for (Office o : offices) {
            int tempRequiredPostage = o.requiredPostage;
            int tempPersuasionAmount = o.persuasionAmount;
            o.persuasionAmount -= o.toPickUp.size() + o.toMail.size();
            o.requiredPostage -= o.toPickUp.size() + o.toMail.size();

            if (o.persuasionAmount < 0) {
                o.persuasionAmount = tempPersuasionAmount;
            }

            if (o.requiredPostage < 0) {
                o.requiredPostage = tempRequiredPostage;
            }
        }
    }

    public boolean isDayCommand(String command) {
        return command.startsWith("DAY");
    }

    public boolean isPickupCommand(String command) {
        return command.startsWith("PICKUP");
    }

    public boolean isLetterCommand(String command) {
        return command.startsWith("LETTER");
    }

    public boolean isPackageCommand(String command) {
        return command.startsWith("PACKAGE");
    }

    public boolean isBuildCommand(String command) {
        return command.startsWith("BUILD");
    }

    public boolean isScienceCommand(String command) {
        return command.startsWith("SCIENCE");
    }

    public boolean isGoodCommand(String command) {
        return command.startsWith("GOOD");
    }

    public boolean isNSADelayCommand(String command) {
        return command.startsWith("NSADELAY");
    }

    public boolean isSneakCommand(String command) {
        return command.startsWith("SNEAK");
    }

    public boolean isInflationCommand(String command) {
        return command.startsWith("INFLATION");
    }

    public boolean isDeflationCommand(String command) {
        return command.startsWith("DEFLATION");
    }
}

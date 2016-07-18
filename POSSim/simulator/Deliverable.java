package POSSim.simulator;

public class Deliverable {

    public Office iniatingOffice;
    public Office destOffice;
    public String intendedDest;
    public String recipient;
    public int initDay;
    public int officeArrivalDay;
    public int inTransitArrivalDay;
    public int delay;

    public Deliverable() {
        delay = 0;
    }

    public void setDelay(int days) {
        if (days >= 0) {
            delay = days;
        }
    }

    public void decrementDelay(boolean inOffice, RunCommand parent) {
        if (delay > 0) {
            --delay;
            if (delay == 0 && inOffice == true) {
                (new Logging()).transitArrived("OFFICE", this, parent);
            }
        }
    }

    public int getOfficeArrivalDay() {
        return officeArrivalDay;
    }

    public void setOfficeArrivalDay(int officeArrivalDay) {
        this.officeArrivalDay = officeArrivalDay;
    }

    public int getInTransitArrivalDay() {
        return inTransitArrivalDay;
    }

    public void setInTransitArrivalDay(int inTransitArrivalDay) {
        this.inTransitArrivalDay = inTransitArrivalDay;
    }

    public int getInitDay() {
        return initDay;
    }

    public void setInitDay(int initDay) {
        this.initDay = initDay;
    }

    public Office getIniatingOffice() {
        return iniatingOffice;
    }

    public void setIniatingOffice(Office iniatingOffice) {
        this.iniatingOffice = iniatingOffice;
    }

    public Office getDestOffice() {
        return destOffice;
    }

    public void setDestOffice(Office destOffice) {
        this.destOffice = destOffice;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getIntendedDest() {
        return intendedDest;
    }

    public void setIntendedDest(String intendedDest) {
        this.intendedDest = intendedDest;
    }

}

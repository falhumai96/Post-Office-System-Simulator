package POSSim.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Office {

    public String name;
    public int transitTime;
    public int requiredPostage;
    public int capacity;
    public int persuasionAmount;
    public int maxPackageLength;
    public List<Deliverable> toMail = new ArrayList<>();
    public List<Deliverable> toPickUp = new ArrayList<>();
    public boolean isMarked;

    public Set<String> wanted;

    public void setWanted(Set<String> wanted) {
        this.wanted = wanted;
    }
    public Network network;

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Office(String name, int transitTime, int requiredPostage,
            int capacity, int persuasionAmount, int maxPackageLength) {
        super();
        this.name = name;
        this.transitTime = transitTime;
        this.requiredPostage = requiredPostage;
        this.capacity = capacity;
        this.persuasionAmount = persuasionAmount;
        this.maxPackageLength = maxPackageLength;
        isMarked = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(int transitTime) {
        this.transitTime = transitTime;
    }

    public int getRequiredPostage() {
        return requiredPostage;
    }

    public void setRequiredPostage(int requiredPostage) {
        this.requiredPostage = requiredPostage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPersuasionAmount() {
        return persuasionAmount;
    }

    public void setPersuasionAmount(int persuasionAmount) {
        this.persuasionAmount = persuasionAmount;
    }

    public int getMaxPackageLength() {
        return maxPackageLength;
    }

    public void setMaxPackageLength(int maxPackageLength) {
        this.maxPackageLength = maxPackageLength;
    }

    public void drop(int day, RunCommand parent) {
        for (int i = 0; i < toPickUp.size();) {
            if (day - toPickUp.get(i).getOfficeArrivalDay() + 1 >= 14) {
                if (toPickUp.get(i) instanceof Letter) {
                    if (!((Letter) toPickUp.get(i)).getReturnRecipient().trim().equals("NONE".trim())) { // if there is a return recipient
                        // swap Offices' references
                        Office tempOffice = ((Letter) toPickUp.get(i)).getDestOffice();
                        ((Letter) toPickUp.get(i)).setDestOffice(((Letter) toPickUp.get(i)).getIniatingOffice());
                        ((Letter) toPickUp.get(i)).setIniatingOffice(tempOffice);
                        // set return recipient to NONE, so no infinite loops when returning
                        ((Letter) toPickUp.get(i)).setRecipient(((Letter) toPickUp.get(i)).getReturnRecipient());
                        ((Letter) toPickUp.get(i)).setReturnRecipient("NONE".trim());

                        (new Logging()).newDeliverable("OFFICE", toPickUp.get(i), toPickUp.get(i).getDestOffice().name, parent);
                        acceptDelivIfGood(((Letter) toPickUp.get(i)), parent);
                        toPickUp.remove(i);
                    } else {
                        (new Logging()).deliverableDestroyed("MASTER", toPickUp.get(i), toPickUp.get(i).getDestOffice().name, parent);
                        (new Logging()).deliverableDestroyed("OFFICE", toPickUp.get(i), toPickUp.get(i).getDestOffice().name, parent);
                        toPickUp.remove(i);
                    }
                } else {
                    (new Logging()).deliverableDestroyed("MASTER", toPickUp.get(i), toPickUp.get(i).getDestOffice().name, parent);
                    (new Logging()).deliverableDestroyed("OFFICE", toPickUp.get(i), toPickUp.get(i).getDestOffice().name, parent);
                    toPickUp.remove(i);
                }
                i = 0;
            } else {
                ++i;
            }
        }
    }

    public void acceptDelivIfGood(Deliverable d, RunCommand parent) {
        boolean hasCriminalRecipient = wanted.contains(d.getRecipient());
        boolean initOfficeIsFull = true;
        boolean destOfficeIsFull = true;
        if (d.getIniatingOffice() != null) {
            initOfficeIsFull = d.getIniatingOffice().isFull();
        }

        if (d.getDestOffice() != null) {
            destOfficeIsFull = d.getDestOffice().isFull();
        }
        Office destOffice = d.getDestOffice();
        if (destOffice != null && hasCriminalRecipient == false && initOfficeIsFull == false && destOfficeIsFull == false) {
            accept(d, d.getDestOffice().getName(), parent);
        } else if (destOffice != null && d.getIniatingOffice() != null) {
            (new Logging()).rejectDeliverable("MASTER", d, d.getDestOffice().name, d.getIniatingOffice().name, parent);
            (new Logging()).rejectDeliverable("OFFICE", d, d.getDestOffice().name, d.getIniatingOffice().name, parent);
        }
    }

    //Receive from person
    public void accept(Deliverable d, String dest, RunCommand parent) {
        (new Logging()).deliverableAccepted("OFFICE", d, dest, parent);
        toMail.add(d);
    }

    //Receive from network
    public void receiveFromNetwork(Deliverable d, RunCommand parent) {
        if (d instanceof Package) {
            Package p = (Package) d;
            if (this.maxPackageLength < p.getLength()) {
                (new Logging()).deliverableDestroyed("MASTER", d, d.getDestOffice().name, parent);
                (new Logging()).deliverableDestroyed("OFFICE", d, d.getDestOffice().name, parent);
                return;
            }
        }

        if (isFull()) {
            (new Logging()).deliverableDestroyed("MASTER", d, d.getDestOffice().name, parent);
            (new Logging()).deliverableDestroyed("OFFICE", d, d.getDestOffice().name, parent);
            return;
        }

        toPickUp.add(d);
    }

    public void sendToNetwork(int day, RunCommand parent) {
        for (int i = toMail.size() - 1; i >= 0; i--) {
            Deliverable d = toMail.get(i);
            d.setInTransitArrivalDay(day);
            network.put(d);
            (new Logging()).transitSent("OFFICE", d, parent);
        }
        toMail.clear();
    }

    public boolean pickUp(String recipient, int day, Set<String> wanted, RunCommand parent) {
        boolean isGoodPick = false;
        for (int i = 0; i < toPickUp.size();) {
            if (toPickUp.get(i).destOffice.isMarked == false && toPickUp.get(i).getRecipient().trim().equals(recipient.trim()) == true && toPickUp.get(i).delay == 0) {
                (new Logging()).itemComplete("OFFICE", toPickUp.get(i), day + 1, parent);
                if (toPickUp.get(i) instanceof Letter && wanted.contains((((Letter) toPickUp.get(i)).getReturnRecipient().trim()))) {
                    (new Logging()).officeDestroyed("MASTER", name, parent);
                    (new Logging()).officeDestroyed("OFFICE", name, parent);
                    toPickUp.get(i).destOffice.isMarked = true;
                }
                toPickUp.remove(i);
                i = 0;
                isGoodPick = true;
            } else {
                ++i;
            }
        }
        return isGoodPick;
    }

    public boolean isFull() {
        return (this.toPickUp.size()) >= capacity;
    }

    public boolean isEmpty() {
        return (this.toPickUp.size()) == 0;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Office) && (this.name.equals(((Office) obj).getName()));
    }

    @Override
    public String toString() {
        return this.name;
    }
}

package POSSim.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Network {

    public List<Deliverable> deliverablesInTransit = new ArrayList<>();
    public Map<String, Office> officeMap = new HashMap<>();

    public void put(Deliverable d) {
        deliverablesInTransit.add(d);
    }

    public void checkAndDeliver(int day, RunCommand parent) {
        for (int i = deliverablesInTransit.size() - 1; i >= 0;) {
            Deliverable d = deliverablesInTransit.get(i);
            Office initOffice = d.getIniatingOffice();
            if (initOffice != null && day - d.getInTransitArrivalDay() > initOffice.getTransitTime() && d.delay == 0) {
                Office destOffice = d.getDestOffice();
                if (destOffice != null && destOffice.isMarked == false) {
                    (new Logging()).transitArrived("OFFICE", d, parent);
                    deliverablesInTransit.remove(i);
                    i = deliverablesInTransit.size() - 1;
                    //put the deliverable into this office
                    d.setOfficeArrivalDay(day);
                    destOffice.receiveFromNetwork(d, parent);
                } else if (destOffice == null || (destOffice != null && destOffice.isMarked == true)) {  // i.e. if it does not exists litrally, or is it marked as destoyed
                    if (d instanceof Letter && ((Letter) d).getReturnRecipient().trim().equals("NONE") == false) { // i.e. if it is a letter, and has a return address
                        d.inTransitArrivalDay = day; // will recalibrate later
                        d.setRecipient(new String(((Letter) d).getReturnRecipient()));
                        ((Letter) d).setReturnRecipient("NONE".trim());
                        Office temp = d.destOffice;
                        d.destOffice = d.iniatingOffice;
                        d.iniatingOffice = temp;
                        --i;
                    } else {
                        deliverablesInTransit.remove(i);
                        i = deliverablesInTransit.size() - 1;
                    }
                }
            } else {
                --i;
            }
        }
    }

    public void populateOffices(Set<Office> offices) {
        for (Office o : offices) {
            officeMap.put(o.getName(), o);
        }
    }

    public boolean isNetworkEmpty() {
        return deliverablesInTransit.size() == 0;
    }
}

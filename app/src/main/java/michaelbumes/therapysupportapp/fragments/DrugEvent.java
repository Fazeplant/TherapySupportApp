package michaelbumes.therapysupportapp.fragments;

import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 13.03.2018.
 */

class DrugEvent {
    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    private Drug drug;

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    private String startingDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private String endDate = "-1";

    public boolean isRunningTimeDefined() {
        return runningTimeDefined;
    }

    public void setRunningTimeDefined(boolean runningTimeDefined) {
        this.runningTimeDefined = runningTimeDefined;
    }

    private boolean runningTimeDefined = false;
}

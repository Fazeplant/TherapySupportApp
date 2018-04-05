package michaelbumes.therapysupportapp.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 13.03.2018.
 */

public class DrugEvent {
    final Calendar c = Calendar.getInstance();
    Date currentDate = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Drug drug;
    private String startingDate = sdf.format(currentDate);
    private String endDate = "-1";

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    private int runningTime = 1;
    private int takingPattern = 1;
    private int takingPatternDaysWithIntake = -1;
    private int takingPatternDaysWithoutIntake = -1;
    private int takingPatternEveryOtherDay = -1;
    private String takingPatternHourStart = "-1";
    private int takingPatternHourNumber = -1;
    private int takingPatternHourInterval = -1;
    private boolean isRegularly = true;

    private String discreteTitle = "Benachrichtigung";

    public String getDiscreteTitle() {
        return discreteTitle;
    }

    public void setDiscreteTitle(String discreteTitle) {
        this.discreteTitle = discreteTitle;
    }

    public String getDiscreteBody() {
        return discreteBody;
    }

    public void setDiscreteBody(String discreteBody) {
        this.discreteBody = discreteBody;
    }

    private String discreteBody = "Alarm";

    public boolean isRegularly() {
        return isRegularly;
    }

    public void setRegularly(boolean regularly) {
        isRegularly = regularly;
    }

    private boolean[] takingPatternWeekdays = new boolean[7];

    public boolean[] getAlarmDiscretePatternWeekdays() {
        return alarmDiscretePatternWeekdays;
    }

    public void setAlarmDiscretePatternWeekdays(boolean[] alarmDiscretePatternWeekdays) {
        this.alarmDiscretePatternWeekdays = alarmDiscretePatternWeekdays;
    }

    private boolean[] alarmDiscretePatternWeekdays = new boolean[7];

    private boolean isRecurringReminder = false;
    private int alarmType = 2;
    private List<String> alarmTime = new ArrayList<>();
    private List<Integer> dosage = new ArrayList<>();


    public int getTakingPatternHourInterval() {
        return takingPatternHourInterval;
    }

    public void setTakingPatternHourInterval(int takingPatternHourInterval) {
        this.takingPatternHourInterval = takingPatternHourInterval;
    }

    public int getTakingPatternHourNumber() {
        return takingPatternHourNumber;
    }

    public void setTakingPatternHourNumber(int takingPatternHourNumber) {
        this.takingPatternHourNumber = takingPatternHourNumber;
    }



    public String getTakingPatternHourStart() {
        return takingPatternHourStart;
    }

    public void setTakingPatternHourStart(String takingPatternHourStart) {
        this.takingPatternHourStart = takingPatternHourStart;
    }


    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }


    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public int getTakingPattern() {
        return takingPattern;
    }

    public void setTakingPattern(int takingPattern) {
        this.takingPattern = takingPattern;
    }


    public int getTakingPatternDaysWithIntake() {
        return takingPatternDaysWithIntake;
    }

    public void setTakingPatternDaysWithIntake(int takingPatternDaysWithIntake) {
        this.takingPatternDaysWithIntake = takingPatternDaysWithIntake;
    }

    public int getTakingPatternDaysWithoutIntake() {
        return takingPatternDaysWithoutIntake;
    }

    public void setTakingPatternDaysWithoutIntake(int takingPatternDaysWithoutIntake) {
        this.takingPatternDaysWithoutIntake = takingPatternDaysWithoutIntake;
    }



    public int getTakingPatternEveryOtherDay() {
        return takingPatternEveryOtherDay;
    }

    public void setTakingPatternEveryOtherDay(int takingPatternEveryOtherDay) {
        this.takingPatternEveryOtherDay = takingPatternEveryOtherDay;
    }


    public boolean[] getTakingPatternWeekdays() {
        return takingPatternWeekdays;
    }

    public void setTakingPatternWeekdays(boolean[] takingPatternWeekdays) {
        this.takingPatternWeekdays = takingPatternWeekdays;
    }



    public boolean isRecurringReminder() {
        return isRecurringReminder;
    }

    public void setRecurringReminder(boolean recurringReminder) {
        isRecurringReminder = recurringReminder;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }


    public List<String> getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(List<String> alarmTime) {
        this.alarmTime = alarmTime;
    }

    public List<Integer> getDosage() {
        return dosage;
    }

    public void setDosage(List<Integer> dosage) {
        this.dosage = dosage;
    }

}

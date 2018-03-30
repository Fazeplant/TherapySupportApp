package michaelbumes.therapysupportapp.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 13.03.2018.
 */

class DrugEvent {
    final Calendar c = Calendar.getInstance();
    Date currentDate = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Drug drug;
    private String startingDate = sdf.format(currentDate);
    private String endDate = "-1";
    private int takingPattern = 1;
    private int takingPatternStart = 1;
    private int takingPatternDaysWithIntake = -1;
    private int takingPatternDaysWithOutIntake = -1;
    private int takingPatternEveryOtherDay = -1;
    private boolean[] takingPatternWeekdays = new boolean[7];
    private boolean isRecurringReminder = false;
    private int alarmType = 2;
    private List<String> alarmTime;
    private List<Integer> dosage;





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


    public int getTakingPatternStart() {
        return takingPatternStart;
    }

    public void setTakingPatternStart(int takingPatternStart) {
        this.takingPatternStart = takingPatternStart;
    }

    public int getTakingPatternDaysWithIntake() {
        return takingPatternDaysWithIntake;
    }

    public void setTakingPatternDaysWithIntake(int takingPatternDaysWithIntake) {
        this.takingPatternDaysWithIntake = takingPatternDaysWithIntake;
    }

    public int getTakingPatternDaysWithOutIntake() {
        return takingPatternDaysWithOutIntake;
    }

    public void setTakingPatternDaysWithOutIntake(int takingPatternDaysWithOutIntake) {
        this.takingPatternDaysWithOutIntake = takingPatternDaysWithOutIntake;
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

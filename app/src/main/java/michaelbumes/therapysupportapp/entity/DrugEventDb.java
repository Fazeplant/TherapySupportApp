package michaelbumes.therapysupportapp.entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Michi on 03.04.2018.
 */
@Entity(tableName = "drug_event")
public class DrugEventDb implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "starting_date")
    private String startingDate = "-1";


    @ColumnInfo(name = "end_date")
    private String endDate = "-1";


    @ColumnInfo(name = "discrete_title")
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

    @ColumnInfo(name = "discrete_body")
    private String discreteBody = "Alarm";

    @ColumnInfo(name = "taking_pattern")
    private int takingPattern = -1;

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    @ColumnInfo(name = "running_time")
    private int runningTime = 1;

    public int getTakingPatternDaysWithIntakeChange() {
        return takingPatternDaysWithIntakeChange;
    }

    public void setTakingPatternDaysWithIntakeChange(int takingPatternDaysWithIntakeChange) {
        this.takingPatternDaysWithIntakeChange = takingPatternDaysWithIntakeChange;
    }

    public int getTakingPatternDaysWithOutIntakeChange() {
        return takingPatternDaysWithOutIntakeChange;
    }

    public void setTakingPatternDaysWithOutIntakeChange(int takingPatternDaysWithOutIntakeChange) {
        this.takingPatternDaysWithOutIntakeChange = takingPatternDaysWithOutIntakeChange;
    }

    @ColumnInfo(name = "taking_pattern_days_with_intake")

    private int takingPatternDaysWithIntake = -1;

    @ColumnInfo(name = "taking_pattern_days_without_intake")
    private int takingPatternDaysWithOutIntake = -1;

    @ColumnInfo(name = "taking_pattern_days_with_intake_change")
    private int takingPatternDaysWithIntakeChange = -1;

    @ColumnInfo(name = "taking_pattern_days_without_intake_change")
    private int takingPatternDaysWithOutIntakeChange = -1;


    @ColumnInfo(name = "taking_pattern_every_other_day")
    private int takingPatternEveryOtherDay = -1;

    @ColumnInfo(name = "taking_pattern_hour_start")
    private String takingPatternHourStart = "-1";

    @ColumnInfo(name = "taking_pattern_hour_number")
    private int takingPatternHourNumber = -1;

    @ColumnInfo(name = "taking_pattern_hour_interval")
    private int takingPatternHourInterval = -1;

    public boolean isMondaySelected() {
        return mondaySelected;
    }

    public void setMondaySelected(boolean mondaySelected) {
        this.mondaySelected = mondaySelected;
    }

    @ColumnInfo(name = "monday_selected")
    private boolean  mondaySelected = true;

    @ColumnInfo(name = "tuesday_selected")
    private boolean  tuesdaySelected = true;

    @ColumnInfo(name = "wednesday_selected")
    private boolean  wednesdaySelected = true;

    @ColumnInfo(name = "thursday_selected")
    private boolean  thursdaySelected = true;

    @ColumnInfo(name = "friday_selected")
    private boolean  fridaySelected = true;

    @ColumnInfo(name = "saturday_selected")
    private boolean  saturdaySelected = false;

    @ColumnInfo(name = "sunday_selected")
    private boolean  sundaySelected = false;

    @ColumnInfo(name = "monday_selected_discrete")
    private boolean  mondaySelectedDiscrete = true;

    @ColumnInfo(name = "tuesday_selected_discrete")
    private boolean  tuesdaySelectedDiscrete = true;

    @ColumnInfo(name = "wednesday_selected_discrete")
    private boolean  wednesdaySelectedDiscrete = true;

    @ColumnInfo(name = "thursday_selected_discrete")
    private boolean  thursdaySelectedDiscrete = true;

    @ColumnInfo(name = "friday_selected_discrete")
    private boolean  fridaySelectedDiscrete = true;

    @ColumnInfo(name = "saturday_selected_discrete")
    private boolean  saturdaySelectedDiscrete = false;

    @ColumnInfo(name = "sunday_selected_discrete")
    private boolean  sundaySelectedDiscrete = false;

    public boolean isRegularly() {
        return isRegularly;
    }

    public void setRegularly(boolean regularly) {
        isRegularly = regularly;
    }

    @ColumnInfo(name = "is_regularly")
    private boolean  isRegularly = true;

    public boolean isMondaySelectedDiscrete() {
        return mondaySelectedDiscrete;
    }

    public void setMondaySelectedDiscrete(boolean mondaySelectedDiscrete) {
        this.mondaySelectedDiscrete = mondaySelectedDiscrete;
    }

    public boolean isTuesdaySelectedDiscrete() {
        return tuesdaySelectedDiscrete;
    }

    public void setTuesdaySelectedDiscrete(boolean tuesdaySelectedDiscrete) {
        this.tuesdaySelectedDiscrete = tuesdaySelectedDiscrete;
    }

    public boolean isWednesdaySelectedDiscrete() {
        return wednesdaySelectedDiscrete;
    }

    public void setWednesdaySelectedDiscrete(boolean wednesdaySelectedDiscrete) {
        this.wednesdaySelectedDiscrete = wednesdaySelectedDiscrete;
    }

    public boolean isThursdaySelectedDiscrete() {
        return thursdaySelectedDiscrete;
    }

    public void setThursdaySelectedDiscrete(boolean thursdaySelectedDiscrete) {
        this.thursdaySelectedDiscrete = thursdaySelectedDiscrete;
    }

    public boolean isFridaySelectedDiscrete() {
        return fridaySelectedDiscrete;
    }

    public void setFridaySelectedDiscrete(boolean fridaySelectedDiscrete) {
        this.fridaySelectedDiscrete = fridaySelectedDiscrete;
    }

    public boolean isSaturdaySelectedDiscrete() {
        return saturdaySelectedDiscrete;
    }

    public void setSaturdaySelectedDiscrete(boolean saturdaySelectedDiscrete) {
        this.saturdaySelectedDiscrete = saturdaySelectedDiscrete;
    }

    public boolean isSundaySelectedDiscrete() {
        return sundaySelectedDiscrete;
    }

    public void setSundaySelectedDiscrete(boolean sundaySelectedDiscrete) {
        this.sundaySelectedDiscrete = sundaySelectedDiscrete;
    }

    public boolean isTuesdaySelected() {
        return tuesdaySelected;
    }

    public void setTuesdaySelected(boolean tuesdaySelected) {
        this.tuesdaySelected = tuesdaySelected;
    }

    public boolean isWednesdaySelected() {
        return wednesdaySelected;
    }

    public void setWednesdaySelected(boolean wednesdaySelected) {
        this.wednesdaySelected = wednesdaySelected;
    }

    public boolean isThursdaySelected() {
        return thursdaySelected;
    }

    public void setThursdaySelected(boolean thursdaySelected) {
        this.thursdaySelected = thursdaySelected;
    }

    public boolean isFridaySelected() {
        return fridaySelected;
    }

    public void setFridaySelected(boolean fridaySelected) {
        this.fridaySelected = fridaySelected;
    }

    public boolean isSaturdaySelected() {
        return saturdaySelected;
    }

    public void setSaturdaySelected(boolean saturdaySelected) {
        this.saturdaySelected = saturdaySelected;
    }

    public boolean isSundaySelected() {
        return sundaySelected;
    }

    public void setSundaySelected(boolean sundaySelected) {
        this.sundaySelected = sundaySelected;
    }

    @ColumnInfo(name = "is_recurring_reminder")
    private boolean isRecurringReminder = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTakingPatternHourStart() {
        return takingPatternHourStart;
    }

    public void setTakingPatternHourStart(String takingPatternHourStart) {
        this.takingPatternHourStart = takingPatternHourStart;
    }

    public int getTakingPatternHourNumber() {
        return takingPatternHourNumber;
    }

    public void setTakingPatternHourNumber(int takingPatternHourNumber) {
        this.takingPatternHourNumber = takingPatternHourNumber;
    }

    public int getTakingPatternHourInterval() {
        return takingPatternHourInterval;
    }

    public void setTakingPatternHourInterval(int takingPatternHourInterval) {
        this.takingPatternHourInterval = takingPatternHourInterval;
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

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @ColumnInfo(name = "alarm_type")
    private int alarmType = 2;

    @ColumnInfo(name = "alarm_time")
    private String alarmTime;

    @ColumnInfo(name = "dosage")
    private String dosage;







}

package michaelbumes.therapysupportapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import michaelbumes.therapysupportapp.TimestampConverter;

/**
 * Created by Michi on 10.04.2018.
 */

@Entity(tableName = "taken_drug")
public class TakenDrug implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "drug_name")
    private String drugName;

    @ColumnInfo(name = "drug_manufacturer")
    private String manufacturer;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    @ColumnInfo(name = "dosage")
    private int dosage;


    @ColumnInfo(name = "dosage_form")
    private String dosageForm;

    @ColumnInfo(name = "pzn")
    private String pzn;

    @ColumnInfo(name = "taking_note")
    private String takingNote;

    @ColumnInfo(name = "side_effects")
    private String sideEffects;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getPzn() {
        return pzn;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public String getTakingNote() {
        return takingNote;
    }

    public void setTakingNote(String takingNote) {
        this.takingNote = takingNote;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
}


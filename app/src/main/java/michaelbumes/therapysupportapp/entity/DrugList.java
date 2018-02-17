package michaelbumes.therapysupportapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Michi on 24.01.2018.
 */


@Entity(tableName = "drug_list")
public class DrugList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "manufacturer_id")
    private int manufacturerId;


    @ColumnInfo(name = "pzn")
    private int pzn;

    @ColumnInfo(name = "dosage_form_id")
    private int dosageFormId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setPzn(int pzn) {
        this.pzn = pzn;
    }

    public void setDosageFormId(int dosageFormId) {
        this.dosageFormId = dosageFormId;
    }

    public void setTakingNote(String takingNote) {
        this.takingNote = takingNote;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public int getPzn() {
        return pzn;
    }

    public int getDosageFormId() {
        return dosageFormId;
    }

    public String getTakingNote() {
        return takingNote;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }


}


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

    @ColumnInfo(name = "manufacturer")
    private String manufacturer;


    @ColumnInfo(name = "pzn")
    private String pzn;

    @ColumnInfo(name = "dope")
    private String dope;

    public String getDope() {
        return dope;
    }

    public void setDope(String dope) {
        this.dope = dope;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    @ColumnInfo(name = "package_size")
    private String packageSize;

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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setPzn(String pzn) {
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

    public String getPzn() {
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

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


}


package michaelbumes.therapysupportapp.entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Michi on 16.01.2018.
 */
@Entity(tableName = "drug")
public class Drug implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "drug_name")
    private String drugName;

    @ColumnInfo(name = "drug_manufacturer_id")
    private int manufacturerId;

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public int getDosageFormId() {
        return dosageFormId;
    }

    public void setDosageFormId(int dosageFormId) {
        this.dosageFormId = dosageFormId;
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

    @ColumnInfo(name = "dosage_form_id")
    private int dosageFormId;

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



}

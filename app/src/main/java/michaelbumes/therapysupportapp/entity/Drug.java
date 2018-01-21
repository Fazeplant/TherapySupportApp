package michaelbumes.therapysupportapp.entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
/**
 * Created by Michi on 16.01.2018.
 */
@Entity(tableName = "drug")
public class Drug {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "drug_name")
    private String drugName;

    @ColumnInfo(name = "drug_manufacturer")
    private String manufacturer;

    public int getId() {
        return id;
    }

    public void setId(int did) {
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



}

package michaelbumes.therapysupportapp.entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
/**
 * Created by Michi on 16.01.2018.
 */
@Entity(tableName = "manufacturer")
public class Manufacturer {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @ColumnInfo(name = "manufacturer_name")
    private String manufacturerName;

}

package michaelbumes.therapysupportapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import michaelbumes.therapysupportapp.TimestampConverter;

/**
 * Created by Bumes on 21.01.2018.
 */
@Entity(tableName = "moodDiary")
public class MoodDiary {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    @ColumnInfo(name = "artID")
    private int artID;

    @ColumnInfo(name = "info1")
    private String info1;

    @ColumnInfo(name = "info2")
    private String info2;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getArtID() {
        return artID;
    }

    public String getInfo1() {
        return info1;
    }

    public String getInfo2() {
        return info2;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setArtID(int artID) {
        this.artID = artID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }
}

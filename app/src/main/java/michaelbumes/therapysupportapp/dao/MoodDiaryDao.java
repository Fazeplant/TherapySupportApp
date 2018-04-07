package michaelbumes.therapysupportapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import michaelbumes.therapysupportapp.TimestampConverter;
import michaelbumes.therapysupportapp.entity.MoodDiary;

/**
 * Created by Bumes on 21.01.2018.
 */

@Dao
public interface MoodDiaryDao {
    @Query("SELECT * FROM moodDiary")
    List<MoodDiary> getAll();

    @Query("SELECT * FROM moodDiary where id = :id")
    MoodDiary findById(int id);

    @Query("SELECT * FROM moodDiary where date = :date")
    MoodDiary findByDate(int date);

    @Query("SELECT COUNT(*) from moodDiary")
    int countMoodDiary();

    @Query("DELETE FROM drug")
    public void nukeTable();

    @Query("SELECT * FROM moodDiary WHERE date BETWEEN :dayst AND :dayet")
    List<MoodDiary> getFromTable(Date dayst, Date dayet);

    @Insert
    void insertAll(MoodDiary... moodDiaries);

    @Delete
    void delete(MoodDiary moodDiary);
}

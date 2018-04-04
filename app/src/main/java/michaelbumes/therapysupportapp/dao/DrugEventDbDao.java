package michaelbumes.therapysupportapp.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import michaelbumes.therapysupportapp.entity.DrugEventDb;

/**
 * Created by Michi on 03.04.2018.
 */

@Dao
public interface DrugEventDbDao {


    @Query("SELECT * FROM drug_event")
    List<DrugEventDb> getAll();

    @Query("SELECT * FROM drug_event where id=:id")
    DrugEventDb findById(long id);



    @Query("DELETE FROM drug_event")
    public void nukeTable();

    @Query("DELETE FROM drug_event WHERE id = :id")
    abstract void deleteById(long id);

    @Insert
    long insert(DrugEventDb drugEventDb);

    @Delete
    void delete(DrugEventDb drugEventDb);

    @Update
    void update(DrugEventDb drugEventDb);






}

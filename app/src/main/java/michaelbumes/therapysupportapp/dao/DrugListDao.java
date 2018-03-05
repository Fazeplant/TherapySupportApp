package michaelbumes.therapysupportapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import michaelbumes.therapysupportapp.entity.DrugList;

/**
 * Created by Michi on 24.01.2018.
 */
@Dao
public interface DrugListDao {

    @Query("SELECT * FROM drug_list")
    List<DrugList> getAll();

    @Query("SELECT name FROM drug_list")
    String[] getAllNames();

    @Query("SELECT * FROM drug_list where name LIKE  :name")
    DrugList findByName(String name);

    @Query("SELECT * FROM drug_list where pzn=:pzn")
    DrugList findByPzn(String pzn);

    @Query("SELECT * FROM drug_list where manufacturer_id =:manufactuereId")
    DrugList findByManufacturerId(int manufactuereId);

    @Query("SELECT COUNT(*) from drug_list")
    int countDrugList();

    @Query("DELETE FROM drug_list")
    public void nukeTable();

    @Insert
    void insertAll(DrugList... drugs);

    @Delete
    void delete(DrugList drug);

}

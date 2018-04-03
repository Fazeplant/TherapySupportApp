package michaelbumes.therapysupportapp.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import michaelbumes.therapysupportapp.entity.Drug;

import java.util.List;

/**
 * Created by Michi on 16.01.2018.
 */

@Dao
public interface DrugDao {


    @Query("SELECT * FROM drug")
    List<Drug> getAll();

    @Query("SELECT * FROM drug where drug_name LIKE  :drugName")
    Drug findByName(String drugName);

    @Query("SELECT COUNT(*) from drug")
    int countDrugs();

    @Query("SELECT * FROM drug where pzn=:pzn")
    Drug findByPzn(String pzn);

    @Query("SELECT * FROM drug where id=:id")
    Drug findById(int id);

    @Query("SELECT * FROM drug where drug_manufacturer LIKE :manufacturer")
    Drug findByManufacturer(String manufacturer);

    @Query("DELETE FROM drug")
    public void nukeTable();

    @Insert
    void insertAll(Drug... drugs);

    @Delete
    void delete(Drug drug);

    @Update
    int update(Drug drug);






}

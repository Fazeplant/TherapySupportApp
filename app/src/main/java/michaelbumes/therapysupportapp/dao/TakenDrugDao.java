package michaelbumes.therapysupportapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import michaelbumes.therapysupportapp.entity.TakenDrug;

/**
 * Created by Michi on 10.04.2018.
 */
@Dao
public interface TakenDrugDao {

    @Query("SELECT * FROM taken_drug")
    List<TakenDrug> getAll();

    @Query("SELECT * FROM taken_drug where drug_name LIKE  :drugName")
    TakenDrug findByName(String drugName);

    @Query("SELECT COUNT(*) from taken_drug")
    int countTakenDrugs();

    @Query("SELECT * FROM taken_drug where pzn=:pzn")
    TakenDrug findByPzn(String pzn);

    @Query("SELECT * FROM taken_drug where id=:id")
    TakenDrug findById(int id);

    @Query("SELECT * FROM taken_drug where date = :date")
    TakenDrug findByDate(int date);

    @Query("SELECT * FROM taken_drug WHERE date BETWEEN :dayst AND :dayet")
    List<TakenDrug> getFromTable(Date dayst, Date dayet);


    @Query("SELECT * FROM taken_drug where drug_manufacturer LIKE :manufacturer")
    TakenDrug findByManufacturer(String manufacturer);

    @Query("DELETE FROM taken_drug")
    public void nukeTable();

    @Insert
    long insert(TakenDrug taken_drug);

    @Delete
    void delete(TakenDrug taken_drug);

    @Update
    int update(TakenDrug taken_drug);



}

package michaelbumes.therapysupportapp.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import michaelbumes.therapysupportapp.entity.DosageForm;

import java.util.List;

/**
 * Created by Michi on 05.03.2018.
 */

@Dao
public interface DosageFormDao {


    @Query("SELECT * FROM dosage_form")
    List<DosageForm> getAll();

    @Query("SELECT * FROM dosage_form where dosage_form_name LIKE  :dosageFormName")
    DosageForm findByName(String dosageFormName);

    @Query("SELECT * FROM dosage_form where id=:dosageFormId")
    DosageForm findById(int dosageFormId);

    @Query("SELECT dosage_form_name FROM dosage_form")
    String[] getAllNames();

    @Query("SELECT dosage_form_name FROM dosage_form where id=:dosageFormId")
    String getNameById(int dosageFormId);

    @Insert
    void insertAll(DosageForm... dosageForms);

    @Delete
    void delete(DosageForm dosageForm);






}

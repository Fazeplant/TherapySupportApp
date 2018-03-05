package michaelbumes.therapysupportapp.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.entity.DosageForm;
import michaelbumes.therapysupportapp.entity.Manufacturer;

import java.util.List;

/**
 * Created by Michi on 05.03.2018.
 */

@Dao
public interface ManufacturerDao {


    @Query("SELECT * FROM manufacturer")
    List<Manufacturer> getAll();

    @Query("SELECT * FROM manufacturer where manufacturer_name LIKE  :manufacturerName")
    Manufacturer findByName(String manufacturerName);

    @Query("SELECT * FROM manufacturer where id=:manufacturerId")
    Manufacturer findById(int manufacturerId);

    @Insert
    void insertAll(Manufacturer... manufacturers);

    @Delete
    void delete(Manufacturer manufacturer);






}

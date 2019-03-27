package vn.com.it.truongpham.appbooknote.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TypeBookDAO {

    @Insert void insertTypeBook(TypeBook... typeBook);

    @Query("SELECT * FROM TypeBook ")
    List<TypeBook> getListTypeBook();


    @Query("update TypeBook set name= :name where id in (:id)")
    void updateTypeBook(String name , int id);


}

package vn.com.it.truongpham.appbooknote.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookEntityDao {
    @Insert
    void insertBook(BookEntity... book_entity);

    @Query("SELECT * FROM bookentity where id_type_book in (:id_type_book) ")
    List<BookEntity> getListBookEntity(int id_type_book );


    @Query("update bookentity set name= :name where  id in (:id)")
    void updateBook(String name , int id);
}

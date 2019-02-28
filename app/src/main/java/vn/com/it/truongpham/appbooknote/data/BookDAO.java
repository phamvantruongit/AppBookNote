package vn.com.it.truongpham.appbooknote.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insertBook(BookContentEntity... BookContentEntity);

    @Query("SELECT * FROM BookContentEntity where id_type_book in (:id_type_book) ")
    List<BookContentEntity> getListBook(int id_type_book);

    @Query("SELECT chapter FROM BookContentEntity where id_type_book in (:id_type_book) ")
    List<String> getListChapter(int id_type_book);

    @Query("SELECT * FROM BookContentEntity where id in (:id) ")
    BookContentEntity getContent(int id);


    @Query("update BookContentEntity set chapter= :chapter ,content= :content where id in (:id)")
    void updateBook(String chapter, String content, int id);
}

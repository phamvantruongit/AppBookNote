package vn.com.it.truongpham.appbooknote.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class BookContentEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "chapter")
    public String chapter;


    @ColumnInfo(name = "content")
    public String content;


    @ColumnInfo(name = "id_type_book")
    public int id_type_book;





}

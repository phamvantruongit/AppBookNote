package vn.com.it.truongpham.appbooknote.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {TypeBook.class ,Book.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TypeBookDAO typeBookDAO();
    public abstract BookDAO bookDAO();
}
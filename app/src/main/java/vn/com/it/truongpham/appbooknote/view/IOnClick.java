package vn.com.it.truongpham.appbooknote.view;


import vn.com.it.truongpham.appbooknote.data.BookEntity;
import vn.com.it.truongpham.appbooknote.data.TypeBook;

public interface IOnClick {
    interface IOnClickAdapter {
        void OnClickItem(TypeBook typeBook);
    }

    interface IOnClickBookAdapter {
        void OnClickItem(BookEntity book);
    }
}

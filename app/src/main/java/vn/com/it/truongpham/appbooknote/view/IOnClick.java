package vn.com.it.truongpham.appbooknote.view;


import mobile.sarproj.com.layout.SwipeLayout;
import vn.com.it.truongpham.appbooknote.data.BookEntity;
import vn.com.it.truongpham.appbooknote.data.TypeBook;

public interface IOnClick {
    interface IOnClickAdapter {
        void OnClickItem(TypeBook typeBook, SwipeLayout swipeLayout);
        void OnClickItemEdit(TypeBook typeBook);
    }

    interface IOnClickBookAdapter {
        void OnClickItem(BookEntity book);
        void OnClickItemEdit(BookEntity bookEntity);
    }
}

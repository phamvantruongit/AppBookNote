package vn.com.it.truongpham.appbooknote.view;


import vn.com.it.truongpham.appbooknote.data.TypeBook;

public interface IOnClick {
    public interface IOnClickAdapter{
        void OnClickItem(TypeBook typeBook);
    }
}

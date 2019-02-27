package vn.com.it.truongpham.appbooknote.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.appbooknote.ApplicationBookNote;
import vn.com.it.truongpham.appbooknote.R;
import vn.com.it.truongpham.appbooknote.adapter.AdapterTypeBook;
import vn.com.it.truongpham.appbooknote.data.TypeBook;
import vn.com.it.truongpham.appbooknote.view.IOnClick;
import vn.com.it.truongpham.appbooknote.view.RecyclerItemClickListener;
import vn.com.it.truongpham.appbooknote.view.ShowToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTypeBook extends Fragment implements IOnClick.IOnClickAdapter {


    List<TypeBook> bookList;
    RecyclerView rvBook;
    AdapterTypeBook adapterTypeBook;
    RecyclerView.LayoutManager layoutManager;

    IOnClick.IOnClickAdapter clickAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        clickAdapter= (IOnClick.IOnClickAdapter) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clickAdapter= (IOnClick.IOnClickAdapter) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_book, container, false);
        rvBook = view.findViewById(R.id.rvbook);
        bookList = new ArrayList<>();
        getData();
        return view;
    }

    private void showDialog(final String name,int id){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_popup_type_book);
        dialog.setCancelable(false);
        dialog.show();
        TextView tvOK = dialog.findViewById(R.id.tvOK);
        TextView tvTitle=dialog.findViewById(R.id.tvTitle);
        final EditText edTypeBook = dialog.findViewById(R.id.edTypeBook);
        if(name!=null){
            edTypeBook.setText(name);
            tvTitle.setText("Edit Type Book");
        }
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edTypeBook.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    ShowToast.showToast(getActivity(), R.layout.show_toast_error);
                    return;
                }
                TypeBook typeBook = new TypeBook();
                typeBook.name = text;
                if(name!=null){

                }else {
                    ApplicationBookNote.db.typeBookDAO().insertTypeBook(typeBook);
                }
                dialog.dismiss();
                getData();

            }
        });
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void getData() {
        bookList = ApplicationBookNote.db.typeBookDAO().getListTypeBook();
        layoutManager = new LinearLayoutManager(getActivity());

        adapterTypeBook = new AdapterTypeBook(getActivity(), bookList,this);
        rvBook.setLayoutManager(layoutManager);
        rvBook.setAdapter(adapterTypeBook);


        rvBook.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvBook ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        clickAdapter.OnClickItem(bookList.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        showDialog(bookList.get(position).name,bookList.get(position).id);
                    }
                })
        );
    }


    @Override
    public void OnClickItem(TypeBook typeBook) {

    }
}

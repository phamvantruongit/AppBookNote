package vn.com.it.truongpham.appbooknote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.appbooknote.adapter.AdapterTypeBook;
import vn.com.it.truongpham.appbooknote.data.TypeBook;
import vn.com.it.truongpham.appbooknote.view.IOnClick;
import vn.com.it.truongpham.appbooknote.view.RecyclerItemClickListener;

public class TypeBookActivity extends AppCompatActivity implements IOnClick.IOnClickAdapter {
    List<TypeBook> bookList;
    RecyclerView rvBook;
    AdapterTypeBook adapterTypeBook;
    RecyclerView.LayoutManager layoutManager;

    IOnClick.IOnClickAdapter clickAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_book);

        rvBook =findViewById(R.id.rvbook);
        bookList = new ArrayList<>();
        getData();
    }

    private void getData() {
        bookList = ApplicationBookNote.db.typeBookDAO().getListTypeBook();
        layoutManager = new LinearLayoutManager(this);

        adapterTypeBook = new AdapterTypeBook(this, bookList,this);
        rvBook.setLayoutManager(layoutManager);
        rvBook.setAdapter(adapterTypeBook);


        rvBook.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvBook ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        clickAdapter.OnClickItem(bookList.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        //showDialog(bookList.get(position).name,bookList.get(position).id_type_book);
                    }
                })
        );
    }

    @Override
    public void OnClickItem(TypeBook typeBook) {

    }
}

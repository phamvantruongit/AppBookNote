package vn.com.it.truongpham.appbooknote;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.appbooknote.adapter.AdapterBook;
import vn.com.it.truongpham.appbooknote.data.BookEntity;
import vn.com.it.truongpham.appbooknote.data.TypeBook;
import vn.com.it.truongpham.appbooknote.view.IOnClick;
import vn.com.it.truongpham.appbooknote.view.ShowToast;

public class ListBookActivity extends BaseActivity implements IOnClick.IOnClickBookAdapter {
   RecyclerView rvbook;
   List<BookEntity> bookList;
   AdapterBook adapterBook;
   int id_type_book =1;
   int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvbook=findViewById(R.id.rvbook);
        id=getIntent().getIntExtra("id",1);
        getData(id);

        NiceSpinner niceSpinner = findViewById(R.id.nice_spinner);
        List<String> dataset = new ArrayList<>();

        List<TypeBook> listTypeBook=ApplicationBookNote.db.typeBookDAO().getListTypeBook();
        for(TypeBook name : listTypeBook){
            dataset.add(name.name);
        }
        niceSpinner.attachDataSource(dataset);
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 position++;
                 id_type_book=position;
                 getData(id_type_book);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_book;
    }

    private void getData(int id_type_book){
        bookList=ApplicationBookNote.db.bookEntityDao().getListBookEntity(id_type_book);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        rvbook.setLayoutManager(layoutManager);
        adapterBook=new AdapterBook(this,bookList,this);
        rvbook.setAdapter(adapterBook);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            showDialog(null,1 ,"Add Name  Book");
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(final String name, final int id , String title){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_popup_type_book);
        dialog.setCancelable(false);
        dialog.show();
        TextView tvOK = dialog.findViewById(R.id.tvOK);
        TextView tvTitle=dialog.findViewById(R.id.tvTitle);
        final EditText edTypeBook = dialog.findViewById(R.id.edTypeBook);
        if(name!=null || title!=""){
            edTypeBook.setText(name);
            tvTitle.setText(title);
        }
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edTypeBook.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    ShowToast.showToast(ListBookActivity.this, R.layout.show_toast_error);
                    return;
                }


                BookEntity bookEntity=new BookEntity();
                bookEntity.name=text;
                bookEntity.id_type_book=id_type_book;

                if(name!=null){
                    ApplicationBookNote.db.bookEntityDao().updateBook(text,id);
                }else {
                    ApplicationBookNote.db.bookEntityDao().insertBook(bookEntity);
                }
                dialog.dismiss();
                getData(id_type_book);


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

    @Override
    public void OnClickItem(BookEntity book) {
        Intent intent=new Intent(this,AddBookActivity.class);
        intent.putExtra("id",book.id);
        intent.putExtra("id_type_book",id_type_book);
        startActivity(intent);

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);


    }

    @Override
    public void OnClickItemEdit(BookEntity bookEntity) {
        showDialog(bookEntity.name,bookEntity.id,"Edit Name  Book");
    }
}

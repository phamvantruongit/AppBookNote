package vn.com.it.truongpham.appbooknote;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import org.angmarch.views.NiceSpinner;

import java.util.List;

import vn.com.it.truongpham.appbooknote.data.BookContentEntity;
import vn.com.it.truongpham.appbooknote.view.ShowToast;

public class AddBookActivity extends BaseActivity {
    private EditText edChapter;
    private vn.com.it.truongpham.appbooknote.view.LinedEditText edContent;
    private int id_type_book ;

    private int id_book =1;
    private  BookContentEntity bookContentEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_book =getIntent().getIntExtra("id",1);
        id_type_book=getIntent().getIntExtra("id_type_book",1);


        edChapter = findViewById(R.id.edChapter);
        edContent = findViewById(R.id.edContent);
        NiceSpinner niceSpinner = findViewById(R.id.nice_spinner);


        List<String> listChapter=ApplicationBookNote.db.bookDAO().getListChapter(id_type_book);
        if(listChapter.size()>0) {
            niceSpinner.attachDataSource(listChapter);
            niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   position++;
                   id_book=position;
                   showContent(id_book);



                }
            });
        }


    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_book;
    }

    private void showContent(int id_book){
        bookContentEntity=ApplicationBookNote.db.bookDAO().getContent(id_book);
        edContent.setText(bookContentEntity.content);
        edChapter.setText(bookContentEntity.chapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            if ( !edContent.getText().toString().equals("")) {
                BookContentEntity bookContentEntity = new BookContentEntity();
                bookContentEntity.chapter = edChapter.getText().toString();
                bookContentEntity.content = edContent.getText().toString();
                bookContentEntity.id_type_book = id_type_book;
                ApplicationBookNote.db.bookDAO().insertBook(bookContentEntity);
                ShowToast.showToast(AddBookActivity.this,R.layout.show_toast_error);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

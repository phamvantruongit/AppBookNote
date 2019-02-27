package vn.com.it.truongpham.appbooknote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import vn.com.it.truongpham.appbooknote.data.Book;
import vn.com.it.truongpham.appbooknote.view.ShowToast;

public class AddBookActivity extends AppCompatActivity {
    private EditText edName, edChapter;
    private vn.com.it.truongpham.appbooknote.view.LinedEditText edContent;
    private int id_type_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        id_type_book=getIntent().getIntExtra("id",1);
        edName = findViewById(R.id.edName);
        edChapter = findViewById(R.id.edChapter);
        edContent = findViewById(R.id.edContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            if (!edName.getText().toString().equals("") && !edContent.getText().toString().equals("")) {
                Book book = new Book();
                book.chapter = edChapter.getText().toString();
                book.name = edName.getText().toString();
                book.content = edContent.getText().toString();
                book.id_type_book = id_type_book;
                ApplicationBookNote.db.bookDAO().insertBook(book);
                ShowToast.showToast(AddBookActivity.this,R.layout.show_toast_error);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

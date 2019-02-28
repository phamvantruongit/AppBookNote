package vn.com.it.truongpham.appbooknote;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobile.sarproj.com.layout.SwipeLayout;
import vn.com.it.truongpham.appbooknote.adapter.AdapterTypeBook;
import vn.com.it.truongpham.appbooknote.data.TypeBook;
import vn.com.it.truongpham.appbooknote.view.IOnClick;
import vn.com.it.truongpham.appbooknote.view.ShowToast;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IOnClick.IOnClickAdapter {


    List<TypeBook> bookList;
    RecyclerView rvBook;
    AdapterTypeBook adapterTypeBook;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rvBook =findViewById(R.id.rvbook);
        bookList = new ArrayList<>();
        getData();


    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    private void getData() {
        bookList = ApplicationBookNote.db.typeBookDAO().getListTypeBook();
        layoutManager = new LinearLayoutManager(this);

        adapterTypeBook = new AdapterTypeBook(this, bookList,this);
        rvBook.setLayoutManager(layoutManager);
        rvBook.setAdapter(adapterTypeBook);
//        rvBook.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, rvBook ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        clickAdapter.OnClickItem(bookList.get(position));
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        //showDialog(bookList.get(position).name,bookList.get(position).id_type_book);
//                    }
//                })
//        );
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            showDialog(null,1 ,"Add Name Type Book");
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
                    ShowToast.showToast(MainActivity.this, R.layout.show_toast_error);
                    return;
                }
                TypeBook typeBook = new TypeBook();
                typeBook.name = text;
                if(name!=null){
                    ApplicationBookNote.db.typeBookDAO().updateTypeBook(text,id);
                }else {
                    ApplicationBookNote.db.typeBookDAO().insertTypeBook(typeBook);
                }
                getData();

                dialog.dismiss();

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnClickItem(TypeBook typeBook , SwipeLayout swipeLayout) {
        Intent intent=new Intent(this,ListBookActivity.class);
        intent.putExtra("id",typeBook.id);
        startActivity(intent);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        swipeLayout.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void OnClickItemEdit(TypeBook typeBook) {
        showDialog(typeBook.name,typeBook.id,"Edit Name Type Book");
    }


}

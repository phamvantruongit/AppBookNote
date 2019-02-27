package vn.com.it.truongpham.appbooknote;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
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

import vn.com.it.truongpham.appbooknote.data.Book;
import vn.com.it.truongpham.appbooknote.data.TypeBook;
import vn.com.it.truongpham.appbooknote.fragment.FragmentBook;
import vn.com.it.truongpham.appbooknote.fragment.FragmentTypeBook;
import vn.com.it.truongpham.appbooknote.view.IOnClick;
import vn.com.it.truongpham.appbooknote.view.ShowToast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IOnClick.IOnClickAdapter {
    Fragment fragment;
    private boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    }


    private void loadFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("id", 1);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.main, fragment, name)
                .addToBackStack(name)
                .commit();
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
             if(check){
                 check=false;
             }else {
                showDialog(null,1);
             }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(final String name,int id){
        final Dialog dialog = new Dialog(this);
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
                    ShowToast.showToast(MainActivity.this, R.layout.show_toast_error);
                    return;
                }
                TypeBook typeBook = new TypeBook();
                typeBook.name = text;
                if(name!=null){

                }else {
                    ApplicationBookNote.db.typeBookDAO().insertTypeBook(typeBook);
                }
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
            fragment = new FragmentTypeBook();
            loadFragment(fragment, "FragmentTypeBook");
        } else if (id == R.id.nav_gallery) {
            check=true;
            fragment = new FragmentBook();
            loadFragment(fragment, "FragmentBook");

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
    public void OnClickItem(TypeBook typeBook) {
        check=true;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("id", typeBook.id);
        FragmentBook fragmentBook = new FragmentBook();
        fragmentBook.setArguments(bundle);
        fragmentTransaction.replace(R.id.main, fragmentBook).addToBackStack(null).commit();
    }


}

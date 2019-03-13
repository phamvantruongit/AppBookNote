package vn.com.it.truongpham.appbooknote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mobile.sarproj.com.layout.SwipeLayout;
import vn.com.it.truongpham.appbooknote.adapter.AdapterNavigationView;
import vn.com.it.truongpham.appbooknote.adapter.AdapterTypeBook;
import vn.com.it.truongpham.appbooknote.data.TypeBook;
import vn.com.it.truongpham.appbooknote.view.IOnClick;
import vn.com.it.truongpham.appbooknote.view.RecyclerItemClickListener;
import vn.com.it.truongpham.appbooknote.view.ShowToast;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IOnClick.IOnClickAdapter {


    List<TypeBook> bookList;
    RecyclerView rvBook, rv_NavigationView;
    AdapterTypeBook adapterTypeBook;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawer;

    private final static int INS_ID = 2;
    private final static int FACEBOOK_ID = 1;
    private final static int TWITTER_ID = 0;
    private final static String[] sharePackages = {"com.twitter.android", "com.facebook.katana", "com.instagram.android"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rvBook = findViewById(R.id.rvbook);
        rv_NavigationView = findViewById(R.id.rv_NavigationView);
        bookList = new ArrayList<>();
        getData();


       final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_update_app);
        dialog.setCancelable(false);
//        dialog.show();
        TextView tvGoto = dialog.findViewById(R.id.tvGoto);
        TextView tvAfter = dialog.findViewById(R.id.tvAfter);
        tvAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String url;

                try {
                    //Check whether Google Play store is installed or not:
                    getPackageManager().getPackageInfo("com.android.vending", 0);
                    url = "market://details?id=" + getPackageName();
                } catch (final Exception e) {
                    url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                }

                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivity(intent);
            }

        });



    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    private void getData() {
        bookList = ApplicationBookNote.db.typeBookDAO().getListTypeBook();
        layoutManager = new LinearLayoutManager(this);
        adapterTypeBook = new AdapterTypeBook(this, bookList, this);
        rvBook.setLayoutManager(layoutManager);
        rvBook.setAdapter(adapterTypeBook);

        List<String> list = new ArrayList<>();
        list.add("My Note");
        list.add("Write Note");
        list.add("Rate App");
        list.add("Share App");
        list.add("More App");
        list.add("Send Feedback");
        AdapterNavigationView adapterNavigationView = new AdapterNavigationView(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_NavigationView.setLayoutManager(layoutManager);
        rv_NavigationView.setAdapter(adapterNavigationView);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        rv_NavigationView.addItemDecoration(itemDecorator);
        rv_NavigationView.addOnItemTouchListener(new RecyclerItemClickListener(this, rv_NavigationView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, ListBookActivity.class);
                        startActivity(intent);
                        MainActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                R.anim.anim_slide_out_left);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(intent);
                        break;
                    case 3:
                        final String url = "https://play.google.com/store/apps/details?id=" + "com.gps.route.finder.mobile.location.tracker.maps.navigation";
                        drawer.closeDrawer(GravityCompat.START);
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.layout_popup_share);
                        dialog.setCancelable(false);
                        dialog.show();
                        ImageView ivFB = dialog.findViewById(R.id.iv_fb);
                        ImageView ivIn = dialog.findViewById(R.id.iv_instagram);
                        ImageView ivTw = dialog.findViewById(R.id.iv_twitter);
                        ivFB.setOnClickListener(new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v) {
                                if (isShareAppInstall(FACEBOOK_ID, MainActivity.this)) {
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setPackage(sharePackages[FACEBOOK_ID]);
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                                    startActivity(shareIntent);
                                } else {
                                    shareAppDl(FACEBOOK_ID, MainActivity.this);
                                }
                                dialog.dismiss();
                            }

                        });

                        ivIn.setOnClickListener(new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v) {

                                if (isShareAppInstall(INS_ID, MainActivity.this)) {
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setPackage(sharePackages[INS_ID]);
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                                    startActivity(shareIntent);
                                } else {
                                    shareAppDl(INS_ID, MainActivity.this);
                                }
                                dialog.dismiss();
                            }
                        });

                        ivTw.setOnClickListener(new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v) {
                                if (isShareAppInstall(TWITTER_ID, MainActivity.this)) {
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setPackage(sharePackages[TWITTER_ID]);
                                    // shareIntent.putExtra(Intent.EXTRA_TITLE,"Share App");
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                                    startActivity(shareIntent);
                                } else {
                                    shareAppDl(TWITTER_ID, MainActivity.this);
                                }
                                dialog.dismiss();
                            }
                        });
                        break;
                    case 4:
                        break;
                    case 5:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"phamvantruongit@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Write title");
                        intent.putExtra(Intent.EXTRA_TEXT, "Write some things");
                        startActivity(Intent.createChooser(intent, ""));
                        break;
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void shareApp(String packageName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(packageName);

            shareIntent.putExtra(android.content.Intent.EXTRA_TITLE, "title");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "description");
            startActivity(Intent.createChooser(shareIntent, "hiuhi"));

        } else {
            String url;
            url = "https://play.google.com/store/apps/details?id=" + packageName;
            final Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            startActivity(in);
        }
    }

    private static Boolean isShareAppInstall(int shareId, Activity activity) {
        try {
            PackageManager pm = activity.getPackageManager();
            pm.getApplicationInfo(sharePackages[shareId], PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static void shareAppDl(int shareId, Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + sharePackages[shareId]);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // rateApp();
            super.onBackPressed();
        }


    }

    public void rateApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Notification");
        builder.setMessage("Rate App ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivity(in);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            showDialog(null, 1, "Wellcome you to MyNote");
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(final String name, final int id, String title) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_popup_type_book);
        dialog.setCancelable(false);
        dialog.show();
        TextView tvOK = dialog.findViewById(R.id.tvOK);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final EditText edTypeBook = dialog.findViewById(R.id.edTypeBook);
        if (name != null || title != "") {
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
                if (name != null) {
                    ApplicationBookNote.db.typeBookDAO().updateTypeBook(text, id);
                } else {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnClickItem(TypeBook typeBook, SwipeLayout swipeLayout) {
        Intent intent = new Intent(this, ListBookActivity.class);
        intent.putExtra("id", typeBook.id);
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
        showDialog(typeBook.name, typeBook.id, "Wellcome you to MyNote");
    }


}

package com.example.vanphu.mymoney;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vanphu.mymoney.controller.SpendController;

public class SpendActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SpendController mSpendController;
    @SuppressLint("StaticFieldLeak")
    public static ImageView sImg_Avatar;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxtUser;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxtName;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_MoneyIn;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxt_Date;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxt_Day;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxt_Month;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxt_Year;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxt_SumSpendMoney;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxt_MoneyOut;
    @SuppressLint("StaticFieldLeak")
    public static TextView sTxt_MoneySum;
    private String mUser = "";
    String URL = "http://192.168.56.1/chitieu/getchitieu.php?email=";
    String URL_item_1 = "http://192.168.56.1/chitieu/getuser.php?email=";
    String URL_item_2 = "http://192.168.56.1/chitieu/getdatatienvao.php?email=";
    String URL_item_3 = "http://192.168.56.1/chitieu/tinhngay.php?email=";
    String URL_item_4 = "http://192.168.56.1/chitieu/tinhtongchitieu.php?email=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend);
        setTitle("");
        Intent intent = getIntent();
        mUser = intent.getStringExtra("User");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
        View v = navigationView.getHeaderView(0);
        initHeader(v);
    }

    public void initHeader(View v) {
        sImg_Avatar = v.findViewById(R.id.img_Avatar);
        sTxtUser = v.findViewById(R.id.txtUser);
        sTxtName = v.findViewById(R.id.txtName);
        sImg_Avatar.setImageResource(R.drawable.img_spend_item22);
        mSpendController.readJsonNav(URL_item_1 + mUser);
    }

    public void init() {
        ListView mlv_Spend = findViewById(R.id.lv_Spend);
        txt_MoneyIn = findViewById(R.id.txt_MoneyIn);
        sTxt_Day = findViewById(R.id.txt_Day);
        sTxt_Date = findViewById(R.id.txt_Date);
        sTxt_Month = findViewById(R.id.txt_Month);
        sTxt_Year = findViewById(R.id.txt_Year);
        sTxt_SumSpendMoney = findViewById(R.id.txt_SumSpendMoney);
        sTxt_MoneyOut = findViewById(R.id.txt_MoneyOut);
        sTxt_MoneySum = findViewById(R.id.txt_MoneySum);
        mSpendController = new SpendController(this);
        mSpendController.addImage(mlv_Spend);
        mSpendController.readJsonMoney(URL_item_2 + mUser);
        mSpendController.ReadJsonDate(URL_item_3 + mUser);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSpendController.readJsonSpend(URL + mUser);
                mSpendController.readJsonSum(URL_item_4 + mUser);
            }
        }, 2500);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.spend, menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final Dialog dialog = new Dialog(SpendActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_watting);
            TextView txt_Text = dialog.findViewById(R.id.txt_Text);
            txt_Text.setText("Đang Đăng Xuất");
            dialog.show();
            SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
            SharedPreferences mSharedPreferences_item_1 = getSharedPreferences("datalide", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            SharedPreferences.Editor editor1 = mSharedPreferences_item_1.edit();
            editor.remove("username");
            editor.remove("password");
            editor.remove("checked");
            editor1.remove("slide");
            editor.apply();
            editor1.commit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SpendActivity.this, LoginActivity.class));
                }
            }, 3500);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

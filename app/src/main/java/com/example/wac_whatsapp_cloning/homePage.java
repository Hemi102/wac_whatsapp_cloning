package com.example.wac_whatsapp_cloning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class homePage extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private tabFragment TabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle(ParseUser.getCurrentUser().getUsername());
        tabLayout=findViewById(R.id.tabLayout);
        appBarLayout=findViewById(R.id.appbarLayout);
        viewPager=findViewById(R.id.viewPager);
        TabFragment=new tabFragment(getSupportFragmentManager());
        viewPager.setAdapter(TabFragment);
        tabLayout.setupWithViewPager(viewPager,false);




        /*



    */

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.my_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId()==R.id.logoutUser)
            {
                ParseUser.getCurrentUser().logOut();
                finish();
                transgerToMainActivity();
            }
            return super.onOptionsItemSelected(item);
        }
    private void transgerToMainActivity() {
        Intent intent=new Intent(homePage.this,register.class);
        startActivity(intent);

    }



}

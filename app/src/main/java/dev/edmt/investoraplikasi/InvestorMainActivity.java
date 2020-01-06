package dev.edmt.investoraplikasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class InvestorMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private investor_SectionsPagerAdapter mSectionsPagerAdapter;
    private FirebaseAuth firebaseAuth;/*
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;*/

    private TabLayout mTabLayout;

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investor_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);



        firebaseAuth = FirebaseAuth.getInstance();

        //Tabs
        mSectionsPagerAdapter = new investor_SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /*mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new investor_SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);*//*

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final TabLayout.Tab Facebook=mTabLayout.newTab();
        final TabLayout.Tab Youtube=mTabLayout.newTab();

        Facebook.setText("Beranda");
        Youtube.setText("Tentang");

        /*Facebook.setIcon(R.drawable.ic_account_circle_black_24dp);
        Youtube.setIcon(R.drawable.ic_chat_black_24dp);
        Twitter.setIcon(R.drawable.ic_home_black_24dp);*/

        View FaceView = getLayoutInflater().inflate(R.layout.custom_tabs,null);
        ImageView face = (ImageView) FaceView.findViewById(R.id.image);
        TextView textface = (TextView) FaceView.findViewById(R.id.text_view);
        face.setImageResource(R.drawable.homeputih);
        textface.setText("Beranda");

        View YtbView = getLayoutInflater().inflate(R.layout.custom_tabs,null);
        ImageView Ytb = (ImageView) YtbView.findViewById(R.id.image);
        TextView textYtb = (TextView) YtbView.findViewById(R.id.text_view);
        Ytb.setImageResource(R.drawable.aboutputih);
        textYtb.setText("Tentang");

        Facebook.setCustomView(FaceView);
        Youtube.setCustomView(YtbView);

        mTabLayout.addTab(Facebook,0);
        mTabLayout.addTab(Youtube,1);

        mTabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicate));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:{
                        Facebook.setIcon(R.drawable.homeputih);
                        Youtube.setIcon(R.drawable.aboutputih);
                        break;
                    }
                    case 1:{
                        Facebook.setIcon(R.drawable.homeputih);
                        Youtube.setIcon(R.drawable.aboutputih);
                        break;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {



        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_custumerservice) {

        } else if (id == R.id.nav_profile) {

            startActivity(new Intent(InvestorMainActivity.this, profile_nav.class));

        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            finish();

            startActivity(new Intent(InvestorMainActivity.this, dev.edmt.investoraplikasi.investor_LoginActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

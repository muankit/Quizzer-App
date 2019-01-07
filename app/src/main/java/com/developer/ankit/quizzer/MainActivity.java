package com.developer.ankit.quizzer;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button mLogoutBtn;        // Button instance for logout
    private FirebaseAuth mAuth;       // Firebase instances for Auth

    // toolbar instance
    private android.support.v7.widget.Toolbar mToolbar;   // Toolbar instance

    private ViewPager mViewPager;    // View Pager instance for tabs
    private TabLayout mTabLayout;
    private TextView mToolbarTitle;

    private TabsFragmentAdapter mTabsFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        setUpNavigationDrawer();

        mAuth = FirebaseAuth.getInstance();


        // mLogoutBtn = (Button) findViewById(R.id.logoutBtn);

        mViewPager = (ViewPager) findViewById(R.id.main_tab_pager);
        mTabsFragmentAdapter = new TabsFragmentAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mTabsFragmentAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

//        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
//        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
//        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));


//        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(mAuth.getCurrentUser() != null){
//                    mAuth.signOut();
//                }
//            }
//        });

    }

    private void setUpNavigationDrawer() {

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navDrawerFragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerFragment.setUpDrawer(R.id.navDrawerFragment, drawerLayout, mToolbar);

    }

    private void setUpToolbar() {

        //Custom Toolbar
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_page_toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setTitle("Home");
        mToolbarTitle = (TextView) findViewById(R.id.home_toolbar_title);
        mToolbarTitle.setText("Q u i z z e r");
        mToolbar.setElevation(0F);
    }


    // This Function is called when back button pressed
    // and handle navgation drawer request to close

    @Override
    public void onBackPressed() {

        DrawerLayout layout = (DrawerLayout) findViewById(R.id.drawerlayout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

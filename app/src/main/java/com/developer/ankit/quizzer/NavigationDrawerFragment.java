package com.developer.ankit.quizzer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.developer.ankit.quizzer.Adapters.NavigationDrawerItemAdpater;
import com.developer.ankit.quizzer.Model.NavigationDrawerItem;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private Button mLogoutBtn , mSettingsBtn;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        mLogoutBtn = (Button) view.findViewById(R.id.drawerLogoutBtn);
        mSettingsBtn = (Button) view.findViewById(R.id.drawerSettingsBtn);

        mSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginBackIntent = new Intent(getActivity() , SettingsActivity.class);
                startActivity(loginBackIntent);
            }
        });
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LogutBtn", "Button Clicked");
                if(mAuth.getCurrentUser() != null){
                    mAuth.signOut();
                    Intent loginBackIntent = new Intent(getActivity() , LoginActivity.class);
                    loginBackIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginBackIntent);
                }
            }
        });
        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.navigationDrawerList);

        NavigationDrawerItemAdpater navigationDrawerItemAdpater = new NavigationDrawerItemAdpater(getActivity(), NavigationDrawerItem.getData());
        recyclerView.setAdapter(navigationDrawerItemAdpater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setUpDrawer(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {

        mDrawerLayout = drawerLayout;

       /* Drawable drawable = ResourcesCompat.getDrawable(getResources() , R.drawable.home , getActivity().getTheme());

        mDrawerToggle.setHomeAsUpIndicator(drawable);*/

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerToggle.syncState();

            }
        });

    }
}

package com.nguyencuong.truyenfull.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nguyencuong.truyenfull.BaseActivity;
import com.nguyencuong.truyenfull.R;
import com.nguyencuong.truyenfull.ui.fragment.mainctg.CategoryFragment;
import com.nguyencuong.truyenfull.ui.fragment.mainhome.HomeFragment;
import com.nguyencuong.truyenfull.ui.fragment.mainlibs.LibraryFragment;
import com.nguyencuong.truyenfull.ui.fragment.mainmore.MoreInfoFragment;
import com.nguyencuong.truyenfull.widget.BottomBar;

public class MainActivity extends BaseActivity implements BottomBar.Listener, AdapterView.OnItemSelectedListener {

    private static String[] arraySources = {"TruyenFull.vn", "sstruyen.com", "Truyenyy.com"};

    private Toolbar toolbar;
    private Spinner spinner;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableButterKnight = false;
        setContentView(R.layout.activity_main);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Set the spinner site sources;
        spinner = (Spinner) findViewById(R.id.main_sp_source);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arraySources);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Set the bottom bar;
        bottomBar = (BottomBar) findViewById(R.id.main_bottom_bar);
        bottomBar.setListener(this);

        replaceFragment(new HomeFragment());

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_frameLayout);
                if (f != null){
                    updateBottomBar(f);
                }
            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbar_btn_search) {
            // Action search;
            return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBottomBarHomeClick() {
        replaceFragment(new HomeFragment());
    }

    @Override
    public void onBottomBarCategoryClick() {
        replaceFragment(new CategoryFragment());
    }

    @Override
    public void onBottomBarLibraryClick() {
        replaceFragment(new LibraryFragment());
    }

    @Override
    public void onBottomBarMoreClick() {
        replaceFragment(new MoreInfoFragment());
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Replace fragment in main activity;
     * if fragment is exits in backstack, move it to top;
     * else create it;
     *
     * @param fragment The Fragment instance;
     */
    private void replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null){
            //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.main_frameLayout, fragment, backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    /**
     * Update actived item bottombar;
     *
     * @param fragment The Fragment is actived;
     */
    public void updateBottomBar(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();

        if (fragClassName.equals(HomeFragment.class.getName())){
            bottomBar.setPositionItemActived(0);
        } else if (fragClassName.equals(CategoryFragment.class.getName())){
            bottomBar.setPositionItemActived(1);
        } else if (fragClassName.equals(LibraryFragment.class.getName())){
            bottomBar.setPositionItemActived(2);
        } else if (fragClassName.equals(MoreInfoFragment.class.getName())){
            bottomBar.setPositionItemActived(3);
        }
    }
}

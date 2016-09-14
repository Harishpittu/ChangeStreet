package com.harish.changestreetassignment.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.harish.changestreetassignment.R;
import com.harish.changestreetassignment.utils.SlideMenuAnimator;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

//    VerticalViewPager viewPager;
    SlideMenuAnimator slideMenuAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);

//        viewPager = (VerticalViewPager) findViewById(R.id.viewpager);

        List<String> stringlist = new ArrayList<>();
        stringlist.add("DINING");
        stringlist.add("TRAVEL");
        stringlist.add("EDUCATION");
        stringlist.add("SERVICES");
        stringlist.add("UTILITIES");

        slideMenuAnimator = new SlideMenuAnimator(this, stringlist, new SlideMenuAnimator.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClickListener(String menuItem) {
                Toast.makeText(DashboardActivity.this, menuItem, Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void setupViewPager(ViewPager viewPager) {
//        FragmentsViewPagerAdapter adapter = new FragmentsViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new Testfragment(), "fragment1");
//        adapter.addFragment(new Testfragment(), "fragment2");
//        viewPager.setAdapter(adapter);
//    }
    public void clicked(View v) {
        slideMenuAnimator.closeAnimation();
    }

    public void open(View v) {
        slideMenuAnimator.openAnimation();
    }
}

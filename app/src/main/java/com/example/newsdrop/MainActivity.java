package com.example.newsdrop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.searchOption){
            tabLayout.getTabAt(8).select();
        }else if(item.getItemId()==R.id.Logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,AuthActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=(ViewPager) findViewById(R.id.viewpager);
        Resources r=getResources();
        String[] titles=r.getStringArray(R.array.Fragments);
        FragmentManager fm=getSupportFragmentManager();
        setupViewPager(fm,viewPager,titles);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(FragmentManager fm,ViewPager viewPager,String[] titles) {
        MyAdapter adapter = new MyAdapter(fm,titles);

        adapter.addFragment(new TopHeadlinesFragment(),titles[0]);
        adapter.addFragment(new EntertainmentFragment(), titles[1]);
        adapter.addFragment(new HealthFragment(),titles[2]);
        adapter.addFragment(new SportsFragment(), titles[3]);
        adapter.addFragment(new TechnologyFragment(), titles[4]);
        adapter.addFragment(new BusinessFragment(), titles[5]);
        adapter.addFragment(new GeneralFragment(), titles[6]);
        adapter.addFragment(new ScienceFragment(), titles[7]);
        adapter.addFragment(new SearchFragment(), titles[8]);

        viewPager.setAdapter(adapter);

    }

    public static class MyAdapter extends FragmentPagerAdapter {
        String[] titles;
        private final List<Fragment> FragmentList = new ArrayList<>();
        public MyAdapter(@NonNull FragmentManager fm,String[] titles) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.titles=titles;
            ;
        }
        private void addFragment(Fragment fragment,String Title){
            FragmentList.add(fragment);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return FragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}


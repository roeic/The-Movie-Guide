package com.example.topmovies.Details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.topmovies.Model.MovieContent;
import com.example.topmovies.Model.MovieModel;
import com.example.topmovies.R;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM_POSITION = "init-position-data";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.details_vp_container);
        viewPager.setAdapter(mSectionsPageAdapter);

        int position = getIntent().getIntExtra(EXTRA_ITEM_POSITION,0);
        viewPager.setCurrentItem(position,false);


    }

    public class SectionsPageAdapter extends FragmentPagerAdapter{

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return MovieDetailsFragments.newInstance(MovieContent.MOVIES.get(i));
        }

        @Override
        public int getCount() {
            return MovieContent.MOVIES.size();
        }
    }

}

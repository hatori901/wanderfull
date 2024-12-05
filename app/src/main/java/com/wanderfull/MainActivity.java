package com.wanderfull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPagerRecommended;
    private RecyclerView recyclerViewDiscover;
    private Handler sliderHandler = new Handler();
    private BottomNavigationView bottomNavigationView;
    private ImageView blurry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        viewPagerRecommended = findViewById(R.id.viewPagerRecommended);
        recyclerViewDiscover = findViewById(R.id.recyclerViewDiscover);
        bottomNavigationView = findViewById(R.id.bottomNavigation);




        // Setup Recommended ViewPager
        RecommendedAdapter recommendedAdapter = new RecommendedAdapter(getRecommendedItems());
        viewPagerRecommended.setAdapter(recommendedAdapter);
        viewPagerRecommended.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        viewPagerRecommended.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DetailActivity.class));
        });

        // Setup Discover RecyclerView
        recyclerViewDiscover.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(getDiscoverItems());
        recyclerViewDiscover.setAdapter(discoverAdapter);

        // Setup Bottom Navigation - Fixed version
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    // Already on home
                    return true;
                } else if (itemId == R.id.navigation_explore) {
                    startActivity(new Intent(MainActivity.this, DiscoverActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_bookmark) {
                    // Handle bookmark
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = viewPagerRecommended.getCurrentItem();
            int totalItems = viewPagerRecommended.getAdapter().getItemCount();
            int nextPosition = (currentPosition + 1) % totalItems;
            viewPagerRecommended.setCurrentItem(nextPosition);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    // Sample data methods
    private List<RecommendedItem> getRecommendedItems() {
        List<RecommendedItem> items = new ArrayList<>();
        items.add(new RecommendedItem("Keraton Yogyakarta",
                "A historic palace in Yogyakarta, Indonesia",
                "Yogyakarta, Indonesia",
                R.drawable.keraton));
        items.add(new RecommendedItem("Keraton Solo",
                "A historic palace in Yogyakarta, Indonesia",
                "Yogyakarta, Indonesia",
                R.drawable.keraton));
        items.add(new RecommendedItem("Keraton Jakarta",
                "A historic palace in Yogyakarta, Indonesia",
                "Yogyakarta, Indonesia",
                R.drawable.keraton));
        // Add more items
        return items;
    }

    private List<DiscoverItem> getDiscoverItems() {
        List<DiscoverItem> items = new ArrayList<>();
        items.add(new DiscoverItem("Mt. Sumbing",
                "Beautiful sunrise view from the summit",
                R.drawable.mt_sumbing));
        items.add(new DiscoverItem("Borobudur Temple",
                "The world's largest Buddhist temple",
                R.drawable.borobudur));
        items.add(new DiscoverItem("Prambanan Temple",
                "The world's largest Buddhist temple",
                R.drawable.borobudur));
        // Add more items
        return items;
    }
}
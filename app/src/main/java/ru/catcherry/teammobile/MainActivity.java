package ru.catcherry.teammobile;

import android.content.Intent;
import android.os.Bundle;

import com.auth0.android.jwt.JWT;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import ru.catcherry.teammobile.reviews.ReviewsFragment;
import ru.catcherry.teammobile.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity  {

    final static int RESULT_OK = 1;
    JWT jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        jwt = (JWT) getIntent().getParcelableExtra("jwt");
    }

    public void onFabClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddReviewActivity.class);
        intent.putExtra("jwt", jwt);
        startActivityForResult(intent, RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if (fragment instanceof ReviewsFragment) {
                        ((ReviewsFragment) fragment).loadReviews();
                        break;
                    }
            }
            Toast.makeText(MainActivity.this, "Отзыв успешно добавлен!", Toast.LENGTH_LONG).show();
        }
    }
}
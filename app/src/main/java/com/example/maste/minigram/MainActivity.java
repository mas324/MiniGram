package com.example.maste.minigram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.maste.minigram.fragments.ComposeFragment;
import com.example.maste.minigram.fragments.PostsFragment;
import com.example.maste.minigram.fragments.ProfileFragment;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView btmNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        fragment = new PostsFragment();
                        break;
                    case R.id.create:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "How'd you get this one?", Toast.LENGTH_SHORT).show();
                        fragment = new PostsFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                return true;
            }
        });
        btmNavView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

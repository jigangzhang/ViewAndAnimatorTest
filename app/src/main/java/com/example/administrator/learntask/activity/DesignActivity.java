package com.example.administrator.learntask.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.learntask.R;
import com.example.administrator.learntask.databinding.ActivityDesignBinding;

public class DesignActivity extends AppCompatActivity {
    ActivityDesignBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_design);
        setSupportActionBar(binding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeAsUpIndicator(R.drawable.logo_left);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        binding.menuSlider.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
        BottomSheetBehavior<LinearLayout> behavior = BottomSheetBehavior.from(binding.bottomSheet);
        behavior.setPeekHeight(60);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e("tag", "change state: " + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.e("tag", "slide state: " + slideOffset);
            }
        });
    }

    public void fabClick() {
        Log.e("tag", "click--");
        Snackbar.make(binding.coordinator, "click->", Snackbar.LENGTH_SHORT).show();
    }

    public boolean onNavigationItemSelected(MenuItem item) {

        Snackbar.make(binding.coordinator, "click..." + item.getTitle(), Snackbar.LENGTH_SHORT)
                .setAction("toast", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.menuSlider.openDrawer(Gravity.START, true);
                    }
                }).show();
        binding.menuSlider.closeDrawers();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.menuSlider.openDrawer(Gravity.START, true);
            default:
                Snackbar.make(binding.coordinator, "click->" + item.getTitle(), Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

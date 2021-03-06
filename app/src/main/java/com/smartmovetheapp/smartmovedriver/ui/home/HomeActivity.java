package com.smartmovetheapp.smartmovedriver.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.data.remote.ApiClient;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Order;
import com.smartmovetheapp.smartmovedriver.data.remote.model.User;
import com.smartmovetheapp.smartmovedriver.data.repository.SessionRepository;
import com.smartmovetheapp.smartmovedriver.ui.base.BaseActivity;
import com.smartmovetheapp.smartmovedriver.ui.help.HelpActivity;
import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.ui.splash.SplashActivity;
import com.smartmovetheapp.smartmovedriver.ui.tripdetail.TripDetailActivity;
import com.smartmovetheapp.smartmovedriver.ui.trips.MyBidsActivity;
import com.smartmovetheapp.smartmovedriver.ui.trips.TripAdapter;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvTrips;
    private TextView txtEmptyTrips;
    private TripAdapter tripAdapter;
    private Snackbar loadingSnackbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private final Callback<List<Order>> tripCallback = new Callback<List<Order>>() {
        @Override
        public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
            hideLoading();
            if (response.isSuccessful()) {
                setTrips(response.body());
            } else {
                showError(R.string.default_error);
            }
        }

        @Override
        public void onFailure(Call<List<Order>> call, Throwable t) {
            hideLoading();
            showError(R.string.default_error);
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            doRefresh();
        });

        loadingSnackbar = Snackbar.make(findViewById(android.R.id.content),
                "Getting active trips..", Snackbar.LENGTH_INDEFINITE);

        rvTrips = findViewById(R.id.rv_trips);
        txtEmptyTrips = findViewById(R.id.txt_empty_trips);

        TextView txtProfileName = navigationView.getHeaderView(0).findViewById(R.id.txt_user_name);
        User user = SessionRepository.getInstance().getLoggedInUser();
        txtProfileName.setText(user.getFirstName() + " " + user.getLastName());

        ImageView profilePicture = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        if(user.getProfilePictureURL() != null && !user.getProfilePictureURL().isEmpty()) {
            Picasso.get()
                    .load("https://smartmoveweb.azurewebsites.net" + user.getProfilePictureURL())
                    .into(profilePicture);
        }
        profilePicture.setScaleType(ImageView.ScaleType.FIT_XY);

        TextView txtAverageRating = navigationView.getHeaderView(0).findViewById(R.id.avg_rating);
        txtAverageRating.setText(user.getAverageRating() + "/5");

        rvTrips.setLayoutManager(new LinearLayoutManager(this));
        tripAdapter = new TripAdapter(order ->
                TripDetailActivity.start(this, order));
        rvTrips.setAdapter(tripAdapter);

        performServerCallToGetOrders();
    }

    private void doRefresh() {
         tripAdapter.submitList(Collections.EMPTY_LIST);
         performServerCallToGetOrders();
    }

    private void performServerCallToGetOrders() {
        showLoading();
        ApiClient.create().getTrips(Long.valueOf(SessionRepository.getInstance().getDriverId()))
                .enqueue(tripCallback);
    }

    private void setTrips(List<Order> trips) {
        if (trips == null || trips.isEmpty()) {
            txtEmptyTrips.setVisibility(View.VISIBLE);
        } else {
            Collections.sort(trips, (firstOrder, secondOrder) -> {
                if (firstOrder.getOrderStatus().equals("CONFIRMED")) {
                    return -1;
                } else if (secondOrder.getOrderStatus().equals("CONFIRMED")) {
                    return 1;
                } else {
                    return 0;
                }
            });
            tripAdapter.submitList(trips);
        }
    }

    private void showLoading() {
        loadingSnackbar.show();
        swipeRefreshLayout.setRefreshing(true);
    }

    private void hideLoading() {
        loadingSnackbar.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_profile) {

        } else */if (id == R.id.nav_trips) {
            MyBidsActivity.start(this);
        } else /*if (id == R.id.nav_invite) {

        } else */if (id == R.id.nav_help) {
            HelpActivity.start(this);
        } else /*if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_settings) {

        } else */if (id == R.id.nav_logout) {
            SessionRepository.getInstance().logout();
            SplashActivity.start(this);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
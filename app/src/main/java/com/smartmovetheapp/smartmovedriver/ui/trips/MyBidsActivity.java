package com.smartmovetheapp.smartmovedriver.ui.trips;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.data.remote.ApiClient;
import com.smartmovetheapp.smartmovedriver.data.remote.model.BidsResponse;
import com.smartmovetheapp.smartmovedriver.data.remote.model.OrderBid;
import com.smartmovetheapp.smartmovedriver.data.repository.SessionRepository;
import com.smartmovetheapp.smartmovedriver.ui.base.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBidsActivity extends BaseActivity {

    private Snackbar loadingSnackbar;
    private AlertDialog loading;

    private final Callback<BidsResponse> bidsCallback = new Callback<BidsResponse>() {
        @Override
        public void onResponse(Call<BidsResponse> call, Response<BidsResponse> response) {
            hideLoading();
            if (response.isSuccessful() && response.body() != null) {
                currentOrders = response.body().getPendingBids();
                pastOrders =  response.body().getAcceptedBids();

                mViewPager.setAdapter(mSectionsPagerAdapter);
            } else {
                showError(R.string.default_error);
            }
        }

        @Override
        public void onFailure(Call<BidsResponse> call, Throwable t) {
            hideLoading();
            showError(R.string.default_error);
        }
    };

    private final Callback<Void> cancelBidCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            hideLoading();
            if (response.isSuccessful()) {
                new AlertDialog.Builder(MyBidsActivity.this, R.style.SMDatePickerTheme)
                        .setTitle("Bid has been removed.")
                        //.setMessage("Your scheduled Bid has been cancelled successfully.")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            finish();
                        })
                        .show();
            } else {
                showError(R.string.default_error);
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            hideLoading();
            showError(R.string.default_error);
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, MyBidsActivity.class);
        context.startActivity(starter);
    }

    private void showLoading() {
        loadingSnackbar.show();
    }

    private void hideLoading() {
        loadingSnackbar.dismiss();
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private List<OrderBid> currentOrders;
    private List<OrderBid> pastOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        //set when server call completes
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        loadingSnackbar = Snackbar.make(findViewById(android.R.id.content), "Getting bids..", Snackbar.LENGTH_INDEFINITE);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        loading = new AlertDialog.Builder(this, R.style.SMDatePickerTheme)
                .setMessage("Cancelling your bid..")
                .setCancelable(false)
                .create();

        performServerCallToGetOrders();
    }

    private void performServerCallToGetOrders() {
        //dummy data
        //currentOrders = OrderRepository.getStoredListOfOrder();
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        showLoading();
        ApiClient.create().getMyBids(SessionRepository.getInstance().getDriverId())
                .enqueue(bidsCallback);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView rvTrips;
        private TextView txtEmptyTrips;
        private FragmentContract contract;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, FragmentContract contract) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            fragment.contract = contract;
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_trip, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            rvTrips = view.findViewById(R.id.rv_trips);
            txtEmptyTrips = view.findViewById(R.id.txt_empty_trips);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            rvTrips.setLayoutManager(new LinearLayoutManager(getContext()));
            BidAdapter tripAdapter = new BidAdapter(this::onCancelBidClick);
            rvTrips.setAdapter(tripAdapter);

            List<OrderBid> orders = contract.getOrders(getArguments().getInt(ARG_SECTION_NUMBER, 1));
            if (orders == null || orders.isEmpty()) {
                txtEmptyTrips.setVisibility(View.VISIBLE);
            } else {
                tripAdapter.submitList(orders);
            }

            /*if (getArguments().getInt(ARG_SECTION_NUMBER, 1) == 2) {
                txtEmptyTrips.setVisibility(View.VISIBLE);
                return;
            }

            List<Order> orders = OrderRepository.getStoredListOfOrder();
            if (orders.isEmpty()) {
                txtEmptyTrips.setVisibility(View.VISIBLE);
            } else {
                tripAdapter.submitList(orders);
            }*/
        }

        private void onCancelBidClick(OrderBid bid) {
            contract.onCancelClick(bid);
        }

        /*@Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof FragmentContract) {
                contract = (FragmentContract) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement FragmentContract");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            contract = null;
        }*/
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, new FragmentContract() {
                @Override
                public List<OrderBid> getOrders(int pageNo) {
                    return pageNo == 1 ? currentOrders : pastOrders;
                }

                @Override
                public void onCancelClick(OrderBid orderBid) {
                    showCancelLoading();
                    performServerCallToCancelBid(orderBid);
                }
            });
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    private void performServerCallToCancelBid(OrderBid orderBid) {
        ApiClient.create().cancelOrder(orderBid.orderId)
                .enqueue(cancelBidCallback);
    }

    private void showCancelLoading() {
        loading.show();
    }

    private void hideCancelLoading() {
        loading.dismiss();
    }

    public interface FragmentContract {
        List<OrderBid> getOrders(int pageNo);

        void onCancelClick(OrderBid orderBid);
    }
}

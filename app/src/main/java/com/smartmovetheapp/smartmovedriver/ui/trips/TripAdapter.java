package com.smartmovetheapp.smartmovedriver.ui.trips;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Order;
import com.smartmovetheapp.smartmovedriver.util.CalenderUtil;


public class TripAdapter extends ListAdapter<Order, TripAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Order> DIFF_CALLBACK = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(Order oldItem, Order newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(Order oldItem, Order newItem) {
            return false;
        }
    };

    private ActionListener actionListener;

    public TripAdapter(ActionListener actionListener) {
        super(DIFF_CALLBACK);
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final View root;
        private TextView txtPickup;
        private TextView txtDrop;
        private TextView txtDateTime;
        private TextView txtStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            root = itemView;
            txtPickup = itemView.findViewById(R.id.txt_pickup_dest);
            txtDrop = itemView.findViewById(R.id.txt_drop_dest);
            txtDateTime = itemView.findViewById(R.id.txt_date_time);
            txtStatus = itemView.findViewById(R.id.txt_status);

            root.setOnClickListener(button -> {
                Order order = (Order) button.getTag();
                actionListener.onOrderClick(order);
            });
        }

        public void bindTo(Order order) {
            root.setTag(order);

            txtPickup.setText(order.getPickupPlace());
            txtDrop.setText(order.getDropPlace());
            txtDateTime.setText(CalenderUtil.getDisplayDateTime(order.getDate()));
            txtStatus.setText(order.getOrderStatus());
        }
    }

    public interface ActionListener {
        void onOrderClick(Order order);
    }
}

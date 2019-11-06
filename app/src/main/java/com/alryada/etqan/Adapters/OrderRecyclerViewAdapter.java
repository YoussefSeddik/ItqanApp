package com.alryada.etqan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alryada.etqan.Helpers.CommonsMethods;
import com.alryada.etqan.Model.Order;
import com.alryada.etqan.OrderDetailsActivity;
import com.alryada.etqan.R;
import com.alryada.etqan.RateOrderActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alryada.etqan.Helpers.CommonsMethods.getDifferenceDate;
import static com.alryada.etqan.Helpers.Constants.KEY_ORDER_ID;



public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.OrderViewHolder> {

    ArrayList<Order> orders;
    Context context;

    public OrderRecyclerViewAdapter(Context context, ArrayList<Order> orders) {
        this.orders = orders;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_order, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {

        final Order order = orders.get(position);

        holder.txtOrderTitle.setText(order.getServiceName());
        holder.txtOrderDate.setText(order.getDate());
        holder.txtOrderTime.setText(order.getFrom());
        holder.txtOrderStatus.setText(order.getStatus());

        holder.materialRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RateOrderActivity.class);
                intent.putExtra(KEY_ORDER_ID, order.getId());
                intent.putExtra("comment", order.getComment());
                intent.putExtra("ontime_rate", order.getOntimeRate());
                intent.putExtra("quality_rate", order.getQualityRate());
                intent.putExtra("team_rate", order.getTeamRate());

                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, OrderDetailsActivity.class).putExtra
                        (KEY_ORDER_ID, order.getId()));
            }
        });

        holder.txtOrderDuration.setText(String.format("%s %s", getDifferenceDate(order.getFrom(),
                (order.getTo())), context.getString(R.string.txt_hours)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.txtUserLocation.setText(CommonsMethods
                        .getAddress(context,
                                Double.parseDouble(order.getClientLatitude()),
                                Double.parseDouble(order.getClientLongitude())));
            }
        }, 100);

        try {
            if (order.getStatusNameSpace() != 4) {
                holder.materialRate.setVisibility(View.GONE);
            }
            if (order.getStatusNameSpace() == 0) {
                holder.imgOrderStatus.setImageResource(R.color.red);
            }
            if (order.getStatusNameSpace() == 1) {
                holder.imgOrderStatus.setImageResource(R.color.light_black);
            }
            if (order.getStatusNameSpace() == 2) {
                holder.imgOrderStatus.setImageResource(R.color.blue);
            }
            if (order.getStatusNameSpace() == 3) {
                holder.imgOrderStatus.setImageResource(R.color.yellow);
            }
            if (order.getStatusNameSpace() == 4) {
                holder.imgOrderStatus.setImageResource(R.color.green);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (order.getAvgRate() != null) {
            try {
                holder.materialRate.setRating(Float.valueOf(order.getAvgRate()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //todo adding price
        //todo adding duration
        //todo adding link to imageStatus
        //todo adding overall rating
    }


    @Override
    public int getItemCount() {

        return orders.size();
    }


    static class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtOrderTitle)
        TextView txtOrderTitle;
        @BindView(R.id.txtOrderDate)
        TextView txtOrderDate;
        @BindView(R.id.txtOrderTime)
        TextView txtOrderTime;
        @BindView(R.id.orderDuration)
        TextView orderDuration;
        @BindView(R.id.txtOrderDuration)
        TextView txtOrderDuration;
        @BindView(R.id.txtOrderPrice)
        TextView txtOrderPrice;
        @BindView(R.id.txtOrderStatus)
        TextView txtOrderStatus;
        @BindView(R.id.imgOrderStatus)
        ImageView imgOrderStatus;
        @BindView(R.id.materialRate)
        me.zhanghai.android.materialratingbar.MaterialRatingBar materialRate;

        @BindView(R.id.txtUserLocation)
        TextView txtUserLocation;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

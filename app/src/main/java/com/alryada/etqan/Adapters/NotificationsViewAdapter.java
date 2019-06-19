package com.alryada.etqan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alryada.etqan.Model.Notification;
import com.alryada.etqan.OrderDetailsActivity;
import com.alryada.etqan.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alryada.etqan.Helpers.Constants.KEY_ORDER_ID;

/**
 * Created by Abd El-Sattar
 * on 6/2/2017.
 */

public class NotificationsViewAdapter extends RecyclerView.Adapter<NotificationsViewAdapter.OrderViewHolder> {


    ArrayList<Notification> notifications;
    Context context;


    public NotificationsViewAdapter(Context context, ArrayList<Notification> notifications) {
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notification, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {

//
        holder.txtNotificationMessage.setText(notifications.get(position).getMessage());
        holder.txtNotificationTime.setText(notifications.get(position).getCreatedAt());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, OrderDetailsActivity.class).putExtra
                        (KEY_ORDER_ID, notifications.get(position).getId()));
            }
        });

//        holder.txtNewsDetails.setText(notifications.get(position).getDetails());
//        Picasso.with(context).load(notifications.get(position).getPhoto()).error(R.drawable.errorpic).into(holder.imgNewsPhoto);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
//        return 12;
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNotificationMessage)
        TextView txtNotificationMessage;
        @BindView(R.id.txtNotificationTime)
        TextView txtNotificationTime;


        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

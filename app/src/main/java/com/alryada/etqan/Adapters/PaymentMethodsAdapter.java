package com.alryada.etqan.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alryada.etqan.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abd El-Sattar
 * on 6/2/2017.
 */

public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodViewHolder> {


    ArrayList<String> notifications;
    Context context;


    public PaymentMethodsAdapter(Context context, ArrayList<String> notifications) {
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public PaymentMethodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_payemnt_method, parent, false);

        return new PaymentMethodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaymentMethodViewHolder holder, final int position) {

//        holder.txtOrderPrice.setText(notifications.get(position).getAddress());
//        holder.txtPharmacyName.setText(notifications.get(position).getName());
//
//        holder.txtNewsDetails.setText(notifications.get(position).getDetails());
//        Picasso.with(context).load(notifications.get(position).getPhoto()).error(R.drawable.errorpic).into(holder.imgNewsPhoto);
    }

    @Override
    public int getItemCount() {
//        return notifications.size();
        return 1;
    }


    class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtOrderStatus)
        TextView txtOrderStatus;
        @BindView(R.id.imgPaymentMethod)
        ImageView imgPaymentMethod;
        @BindView(R.id.imgPaymentStatus)
        ImageView imgPaymentStatus;


        public PaymentMethodViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

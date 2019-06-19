// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan.Adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.alryada.etqan.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class OrderRecyclerViewAdapter$OrderViewHolder_ViewBinding implements Unbinder {
  private OrderRecyclerViewAdapter.OrderViewHolder target;

  @UiThread
  public OrderRecyclerViewAdapter$OrderViewHolder_ViewBinding(OrderRecyclerViewAdapter.OrderViewHolder target,
      View source) {
    this.target = target;

    target.txtOrderTitle = Utils.findRequiredViewAsType(source, R.id.txtOrderTitle, "field 'txtOrderTitle'", TextView.class);
    target.txtOrderDate = Utils.findRequiredViewAsType(source, R.id.txtOrderDate, "field 'txtOrderDate'", TextView.class);
    target.txtOrderTime = Utils.findRequiredViewAsType(source, R.id.txtOrderTime, "field 'txtOrderTime'", TextView.class);
    target.orderDuration = Utils.findRequiredViewAsType(source, R.id.orderDuration, "field 'orderDuration'", TextView.class);
    target.txtOrderDuration = Utils.findRequiredViewAsType(source, R.id.txtOrderDuration, "field 'txtOrderDuration'", TextView.class);
    target.txtOrderPrice = Utils.findRequiredViewAsType(source, R.id.txtOrderPrice, "field 'txtOrderPrice'", TextView.class);
    target.txtOrderStatus = Utils.findRequiredViewAsType(source, R.id.txtOrderStatus, "field 'txtOrderStatus'", TextView.class);
    target.imgOrderStatus = Utils.findRequiredViewAsType(source, R.id.imgOrderStatus, "field 'imgOrderStatus'", ImageView.class);
    target.materialRate = Utils.findRequiredViewAsType(source, R.id.materialRate, "field 'materialRate'", MaterialRatingBar.class);
    target.txtUserLocation = Utils.findRequiredViewAsType(source, R.id.txtUserLocation, "field 'txtUserLocation'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderRecyclerViewAdapter.OrderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtOrderTitle = null;
    target.txtOrderDate = null;
    target.txtOrderTime = null;
    target.orderDuration = null;
    target.txtOrderDuration = null;
    target.txtOrderPrice = null;
    target.txtOrderStatus = null;
    target.imgOrderStatus = null;
    target.materialRate = null;
    target.txtUserLocation = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.alryada.etqan.R;
import com.github.ybq.android.spinkit.SpinKitView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrdersFragment_ViewBinding implements Unbinder {
  private OrdersFragment target;

  @UiThread
  public OrdersFragment_ViewBinding(OrdersFragment target, View source) {
    this.target = target;

    target.rvOrders = Utils.findRequiredViewAsType(source, R.id.rvOrders, "field 'rvOrders'", RecyclerView.class);
    target.btnNoData = Utils.findRequiredViewAsType(source, R.id.btnNoData, "field 'btnNoData'", Button.class);
    target.spinKitLoadingBar = Utils.findRequiredViewAsType(source, R.id.spinKitLoadingBar, "field 'spinKitLoadingBar'", SpinKitView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrdersFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvOrders = null;
    target.btnNoData = null;
    target.spinKitLoadingBar = null;
  }
}

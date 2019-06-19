// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.gms.maps.MapView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderDetailsActivity_ViewBinding implements Unbinder {
  private OrderDetailsActivity target;

  @UiThread
  public OrderDetailsActivity_ViewBinding(OrderDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderDetailsActivity_ViewBinding(OrderDetailsActivity target, View source) {
    this.target = target;

    target.imgBackBtn = Utils.findRequiredViewAsType(source, R.id.imgBackBtn, "field 'imgBackBtn'", ImageView.class);
    target.map = Utils.findRequiredViewAsType(source, R.id.map, "field 'map'", MapView.class);
    target.txtStatus = Utils.findRequiredViewAsType(source, R.id.txtStatus, "field 'txtStatus'", TextView.class);
    target.txtDate = Utils.findRequiredViewAsType(source, R.id.txtDate, "field 'txtDate'", TextView.class);
    target.txtStartTime = Utils.findRequiredViewAsType(source, R.id.txtStartTime, "field 'txtStartTime'", TextView.class);
    target.txtDuration = Utils.findRequiredViewAsType(source, R.id.txtDuration, "field 'txtDuration'", TextView.class);
    target.txtWaiting = Utils.findRequiredViewAsType(source, R.id.txtWaiting, "field 'txtWaiting'", TextView.class);
    target.txtDistance = Utils.findRequiredViewAsType(source, R.id.txtDistance, "field 'txtDistance'", TextView.class);
    target.txtWorkCounter = Utils.findRequiredViewAsType(source, R.id.txtWorkCounter, "field 'txtWorkCounter'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgBackBtn = null;
    target.map = null;
    target.txtStatus = null;
    target.txtDate = null;
    target.txtStartTime = null;
    target.txtDuration = null;
    target.txtWaiting = null;
    target.txtDistance = null;
    target.txtWorkCounter = null;
  }
}

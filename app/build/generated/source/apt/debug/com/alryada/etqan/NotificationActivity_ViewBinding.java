// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.ybq.android.spinkit.SpinKitView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationActivity_ViewBinding implements Unbinder {
  private NotificationActivity target;

  @UiThread
  public NotificationActivity_ViewBinding(NotificationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NotificationActivity_ViewBinding(NotificationActivity target, View source) {
    this.target = target;

    target.rvNotification = Utils.findRequiredViewAsType(source, R.id.rvNotification, "field 'rvNotification'", RecyclerView.class);
    target.txtNoData = Utils.findRequiredViewAsType(source, R.id.txtNoData, "field 'txtNoData'", TextView.class);
    target.imgBackBtn = Utils.findRequiredViewAsType(source, R.id.imgBackBtn, "field 'imgBackBtn'", ImageView.class);
    target.spinKitLoadingBar = Utils.findRequiredViewAsType(source, R.id.spinKitLoadingBar, "field 'spinKitLoadingBar'", SpinKitView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NotificationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvNotification = null;
    target.txtNoData = null;
    target.imgBackBtn = null;
    target.spinKitLoadingBar = null;
  }
}

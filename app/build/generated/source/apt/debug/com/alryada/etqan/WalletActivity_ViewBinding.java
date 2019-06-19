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
import java.lang.IllegalStateException;
import java.lang.Override;

public class WalletActivity_ViewBinding implements Unbinder {
  private WalletActivity target;

  @UiThread
  public WalletActivity_ViewBinding(WalletActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WalletActivity_ViewBinding(WalletActivity target, View source) {
    this.target = target;

    target.rvPaymentMethods = Utils.findRequiredViewAsType(source, R.id.rvPaymentMethods, "field 'rvPaymentMethods'", RecyclerView.class);
    target.txtNoData = Utils.findRequiredViewAsType(source, R.id.txtNoData, "field 'txtNoData'", TextView.class);
    target.imgBackBtn = Utils.findRequiredViewAsType(source, R.id.imgBackBtn, "field 'imgBackBtn'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WalletActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvPaymentMethods = null;
    target.txtNoData = null;
    target.imgBackBtn = null;
  }
}

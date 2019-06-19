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

public class PaymentMethodsAdapter$PaymentMethodViewHolder_ViewBinding implements Unbinder {
  private PaymentMethodsAdapter.PaymentMethodViewHolder target;

  @UiThread
  public PaymentMethodsAdapter$PaymentMethodViewHolder_ViewBinding(PaymentMethodsAdapter.PaymentMethodViewHolder target,
      View source) {
    this.target = target;

    target.txtOrderStatus = Utils.findRequiredViewAsType(source, R.id.txtOrderStatus, "field 'txtOrderStatus'", TextView.class);
    target.imgPaymentMethod = Utils.findRequiredViewAsType(source, R.id.imgPaymentMethod, "field 'imgPaymentMethod'", ImageView.class);
    target.imgPaymentStatus = Utils.findRequiredViewAsType(source, R.id.imgPaymentStatus, "field 'imgPaymentStatus'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PaymentMethodsAdapter.PaymentMethodViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtOrderStatus = null;
    target.imgPaymentMethod = null;
    target.imgPaymentStatus = null;
  }
}

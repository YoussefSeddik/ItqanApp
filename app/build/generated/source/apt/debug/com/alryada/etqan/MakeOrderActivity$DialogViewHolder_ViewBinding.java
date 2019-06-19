// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MakeOrderActivity$DialogViewHolder_ViewBinding implements Unbinder {
  private MakeOrderActivity.DialogViewHolder target;

  @UiThread
  public MakeOrderActivity$DialogViewHolder_ViewBinding(MakeOrderActivity.DialogViewHolder target,
      View source) {
    this.target = target;

    target.txtOrderSerivce = Utils.findRequiredViewAsType(source, R.id.txtOrderSerivce, "field 'txtOrderSerivce'", TextView.class);
    target.txtDaysSelected = Utils.findRequiredViewAsType(source, R.id.txtDaysSelected, "field 'txtDaysSelected'", TextView.class);
    target.txtSubDur = Utils.findRequiredViewAsType(source, R.id.txtSubDur, "field 'txtSubDur'", TextView.class);
    target.txtDuration = Utils.findRequiredViewAsType(source, R.id.txtDuration, "field 'txtDuration'", TextView.class);
    target.txtDate = Utils.findRequiredViewAsType(source, R.id.txtDate, "field 'txtDate'", TextView.class);
    target.txtStartTime = Utils.findRequiredViewAsType(source, R.id.txtStartTime, "field 'txtStartTime'", TextView.class);
    target.txtOrderPrice = Utils.findRequiredViewAsType(source, R.id.txtOrderPrice, "field 'txtOrderPrice'", TextView.class);
    target.linConfirm = Utils.findRequiredViewAsType(source, R.id.linConfirm, "field 'linConfirm'", LinearLayout.class);
    target.linCancel = Utils.findRequiredViewAsType(source, R.id.linCancel, "field 'linCancel'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MakeOrderActivity.DialogViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtOrderSerivce = null;
    target.txtDaysSelected = null;
    target.txtSubDur = null;
    target.txtDuration = null;
    target.txtDate = null;
    target.txtStartTime = null;
    target.txtOrderPrice = null;
    target.linConfirm = null;
    target.linCancel = null;
  }
}

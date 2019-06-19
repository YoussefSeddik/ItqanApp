// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateOrderActivity_ViewBinding implements Unbinder {
  private RateOrderActivity target;

  @UiThread
  public RateOrderActivity_ViewBinding(RateOrderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RateOrderActivity_ViewBinding(RateOrderActivity target, View source) {
    this.target = target;

    target.imgBackBtn = Utils.findRequiredViewAsType(source, R.id.imgBackBtn, "field 'imgBackBtn'", ImageView.class);
    target.mRateOnTime = Utils.findRequiredViewAsType(source, R.id.mRateOnTime, "field 'mRateOnTime'", MaterialRatingBar.class);
    target.txtRateOnTime = Utils.findRequiredViewAsType(source, R.id.txtRateOnTime, "field 'txtRateOnTime'", TextView.class);
    target.mRateQuality = Utils.findRequiredViewAsType(source, R.id.mRateQuality, "field 'mRateQuality'", MaterialRatingBar.class);
    target.txtRateQuality = Utils.findRequiredViewAsType(source, R.id.txtRateQuality, "field 'txtRateQuality'", TextView.class);
    target.mRateTeam = Utils.findRequiredViewAsType(source, R.id.mRateTeam, "field 'mRateTeam'", MaterialRatingBar.class);
    target.txtRateTeam = Utils.findRequiredViewAsType(source, R.id.txtRateTeam, "field 'txtRateTeam'", TextView.class);
    target.etRateComment = Utils.findRequiredViewAsType(source, R.id.etRateComment, "field 'etRateComment'", EditText.class);
    target.btnRateOrder = Utils.findRequiredViewAsType(source, R.id.btnRateOrder, "field 'btnRateOrder'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RateOrderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgBackBtn = null;
    target.mRateOnTime = null;
    target.txtRateOnTime = null;
    target.mRateQuality = null;
    target.txtRateQuality = null;
    target.mRateTeam = null;
    target.txtRateTeam = null;
    target.etRateComment = null;
    target.btnRateOrder = null;
  }
}

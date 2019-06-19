// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MakeOrderActivity_ViewBinding implements Unbinder {
  private MakeOrderActivity target;

  @UiThread
  public MakeOrderActivity_ViewBinding(MakeOrderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MakeOrderActivity_ViewBinding(MakeOrderActivity target, View source) {
    this.target = target;

    target.spChoosePaymentMethod = Utils.findRequiredViewAsType(source, R.id.spChoosePaymentMethod, "field 'spChoosePaymentMethod'", Spinner.class);
    target.rvWeekDays = Utils.findRequiredViewAsType(source, R.id.rvWeekDays, "field 'rvWeekDays'", RecyclerView.class);
    target.lyChooseDays = Utils.findRequiredViewAsType(source, R.id.lyChooseDays, "field 'lyChooseDays'", LinearLayout.class);
    target.imgBackBtn = Utils.findRequiredViewAsType(source, R.id.imgBackBtn, "field 'imgBackBtn'", ImageView.class);
    target.txtUserLocation = Utils.findRequiredViewAsType(source, R.id.txtUserLocation, "field 'txtUserLocation'", TextView.class);
    target.spSubscription = Utils.findRequiredViewAsType(source, R.id.spSubscription, "field 'spSubscription'", Spinner.class);
    target.spServices = Utils.findRequiredViewAsType(source, R.id.spServices, "field 'spServices'", Spinner.class);
    target.spNationalities = Utils.findRequiredViewAsType(source, R.id.spNationalities, "field 'spNationalities'", Spinner.class);
    target.btnMinusEmployess = Utils.findRequiredViewAsType(source, R.id.btnMinusEmployess, "field 'btnMinusEmployess'", Button.class);
    target.txtNumEmpolyess = Utils.findRequiredViewAsType(source, R.id.txtNumEmpolyess, "field 'txtNumEmpolyess'", TextView.class);
    target.btnAddEmployees = Utils.findRequiredViewAsType(source, R.id.btnAddEmployees, "field 'btnAddEmployees'", Button.class);
    target.radioMale = Utils.findRequiredViewAsType(source, R.id.radioMale, "field 'radioMale'", RadioButton.class);
    target.radioFemale = Utils.findRequiredViewAsType(source, R.id.radioFemale, "field 'radioFemale'", RadioButton.class);
    target.radioGender = Utils.findRequiredViewAsType(source, R.id.radioGender, "field 'radioGender'", RadioGroup.class);
    target.btnMinusDuration = Utils.findRequiredViewAsType(source, R.id.btnMinusDuration, "field 'btnMinusDuration'", Button.class);
    target.txtNumDuration = Utils.findRequiredViewAsType(source, R.id.txtNumDuration, "field 'txtNumDuration'", TextView.class);
    target.btnAddDuration = Utils.findRequiredViewAsType(source, R.id.btnAddDuration, "field 'btnAddDuration'", Button.class);
    target.dateText = Utils.findRequiredViewAsType(source, R.id.dateText, "field 'dateText'", TextView.class);
    target.btnChooseDate = Utils.findRequiredViewAsType(source, R.id.btnChooseDate, "field 'btnChooseDate'", Button.class);
    target.txtSelectedDate = Utils.findRequiredViewAsType(source, R.id.txtSelectedDate, "field 'txtSelectedDate'", TextView.class);
    target.timeText = Utils.findRequiredViewAsType(source, R.id.timeText, "field 'timeText'", TextView.class);
    target.btnChooseTime = Utils.findRequiredViewAsType(source, R.id.btnChooseTime, "field 'btnChooseTime'", Button.class);
    target.linSub = Utils.findRequiredViewAsType(source, R.id.linSub, "field 'linSub'", LinearLayout.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btnSubmit, "field 'btnSubmit'", Button.class);
    target.txtSelectedTime = Utils.findRequiredViewAsType(source, R.id.txtSelectedTime, "field 'txtSelectedTime'", TextView.class);
    target.txtTotalPrice = Utils.findRequiredViewAsType(source, R.id.txtTotalPrice, "field 'txtTotalPrice'", TextView.class);
    target.lyOrder = Utils.findRequiredViewAsType(source, R.id.lyOrder, "field 'lyOrder'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MakeOrderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spChoosePaymentMethod = null;
    target.rvWeekDays = null;
    target.lyChooseDays = null;
    target.imgBackBtn = null;
    target.txtUserLocation = null;
    target.spSubscription = null;
    target.spServices = null;
    target.spNationalities = null;
    target.btnMinusEmployess = null;
    target.txtNumEmpolyess = null;
    target.btnAddEmployees = null;
    target.radioMale = null;
    target.radioFemale = null;
    target.radioGender = null;
    target.btnMinusDuration = null;
    target.txtNumDuration = null;
    target.btnAddDuration = null;
    target.dateText = null;
    target.btnChooseDate = null;
    target.txtSelectedDate = null;
    target.timeText = null;
    target.btnChooseTime = null;
    target.linSub = null;
    target.btnSubmit = null;
    target.txtSelectedTime = null;
    target.txtTotalPrice = null;
    target.lyOrder = null;
  }
}

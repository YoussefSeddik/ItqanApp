// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.alryada.etqan.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileFragment_ViewBinding implements Unbinder {
  private ProfileFragment target;

  @UiThread
  public ProfileFragment_ViewBinding(ProfileFragment target, View source) {
    this.target = target;

    target.imgLogo = Utils.findRequiredViewAsType(source, R.id.imgLogo, "field 'imgLogo'", ImageView.class);
    target.imgEdit = Utils.findRequiredViewAsType(source, R.id.imgEdit, "field 'imgEdit'", ImageView.class);
    target.etUserName = Utils.findRequiredViewAsType(source, R.id.etUserName, "field 'etUserName'", MaterialEditText.class);
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", MaterialEditText.class);
    target.etAddress = Utils.findRequiredViewAsType(source, R.id.etAddress, "field 'etAddress'", MaterialEditText.class);
    target.etMobile = Utils.findRequiredViewAsType(source, R.id.etMobile, "field 'etMobile'", MaterialEditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.etPhone, "field 'etPhone'", MaterialEditText.class);
    target.etOldPassword = Utils.findRequiredViewAsType(source, R.id.etOldPassword, "field 'etOldPassword'", MaterialEditText.class);
    target.imgEditPassword = Utils.findRequiredViewAsType(source, R.id.imgEditPassword, "field 'imgEditPassword'", ImageView.class);
    target.radioMale = Utils.findRequiredViewAsType(source, R.id.radioMale, "field 'radioMale'", RadioButton.class);
    target.radioFemale = Utils.findRequiredViewAsType(source, R.id.radioFemale, "field 'radioFemale'", RadioButton.class);
    target.radioGender = Utils.findRequiredViewAsType(source, R.id.radioGender, "field 'radioGender'", RadioGroup.class);
    target.btnSave = Utils.findRequiredViewAsType(source, R.id.btnSave, "field 'btnSave'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgLogo = null;
    target.imgEdit = null;
    target.etUserName = null;
    target.etEmail = null;
    target.etAddress = null;
    target.etMobile = null;
    target.etPhone = null;
    target.etOldPassword = null;
    target.imgEditPassword = null;
    target.radioMale = null;
    target.radioFemale = null;
    target.radioGender = null;
    target.btnSave = null;
  }
}

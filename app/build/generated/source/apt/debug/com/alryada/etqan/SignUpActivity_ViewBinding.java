// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target, View source) {
    this.target = target;

    target.etUserName = Utils.findRequiredViewAsType(source, R.id.etUserName, "field 'etUserName'", EditText.class);
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.etPassword, "field 'etPassword'", EditText.class);
    target.etConfirmPass = Utils.findRequiredViewAsType(source, R.id.etConfirmPass, "field 'etConfirmPass'", EditText.class);
    target.etAddress = Utils.findRequiredViewAsType(source, R.id.etAddress, "field 'etAddress'", EditText.class);
    target.etMobile = Utils.findRequiredViewAsType(source, R.id.etMobile, "field 'etMobile'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.etPhone, "field 'etPhone'", EditText.class);
    target.cbTerms = Utils.findRequiredViewAsType(source, R.id.cbTerms, "field 'cbTerms'", CheckBox.class);
    target.radioGender = Utils.findRequiredViewAsType(source, R.id.radioGender, "field 'radioGender'", RadioGroup.class);
    target.radioMale = Utils.findRequiredViewAsType(source, R.id.radioMale, "field 'radioMale'", RadioButton.class);
    target.btnSignUp = Utils.findRequiredViewAsType(source, R.id.btnSignUp, "field 'btnSignUp'", Button.class);
    target.btnLogin = Utils.findRequiredViewAsType(source, R.id.btnLogin, "field 'btnLogin'", Button.class);
    target.btnTerms = Utils.findRequiredViewAsType(source, R.id.btnTerms, "field 'btnTerms'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etUserName = null;
    target.etEmail = null;
    target.etPassword = null;
    target.etConfirmPass = null;
    target.etAddress = null;
    target.etMobile = null;
    target.etPhone = null;
    target.cbTerms = null;
    target.radioGender = null;
    target.radioMale = null;
    target.btnSignUp = null;
    target.btnLogin = null;
    target.btnTerms = null;
  }
}

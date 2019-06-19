// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.etPassword, "field 'etPassword'", EditText.class);
    target.txtForgetPassword = Utils.findRequiredViewAsType(source, R.id.txtForgetPassword, "field 'txtForgetPassword'", TextView.class);
    target.btnSignIn = Utils.findRequiredViewAsType(source, R.id.btnSignIn, "field 'btnSignIn'", Button.class);
    target.btnSignUp = Utils.findRequiredViewAsType(source, R.id.btnSignUp, "field 'btnSignUp'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etEmail = null;
    target.etPassword = null;
    target.txtForgetPassword = null;
    target.btnSignIn = null;
    target.btnSignUp = null;
  }
}

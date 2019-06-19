// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgetPasswordActivity_ViewBinding implements Unbinder {
  private ForgetPasswordActivity target;

  @UiThread
  public ForgetPasswordActivity_ViewBinding(ForgetPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ForgetPasswordActivity_ViewBinding(ForgetPasswordActivity target, View source) {
    this.target = target;

    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", MaterialEditText.class);
    target.btnForgetPassword = Utils.findRequiredViewAsType(source, R.id.btnForgetPassword, "field 'btnForgetPassword'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ForgetPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etEmail = null;
    target.btnForgetPassword = null;
  }
}

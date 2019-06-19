// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.alryada.etqan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MapFragment_ViewBinding implements Unbinder {
  private MapFragment target;

  @UiThread
  public MapFragment_ViewBinding(MapFragment target, View source) {
    this.target = target;

    target.btnOneTimeOrder = Utils.findRequiredViewAsType(source, R.id.btnOneTimeOrder, "field 'btnOneTimeOrder'", Button.class);
    target.btnSubscriptionOrder = Utils.findRequiredViewAsType(source, R.id.btnSubscriptionOrder, "field 'btnSubscriptionOrder'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MapFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnOneTimeOrder = null;
    target.btnSubscriptionOrder = null;
  }
}

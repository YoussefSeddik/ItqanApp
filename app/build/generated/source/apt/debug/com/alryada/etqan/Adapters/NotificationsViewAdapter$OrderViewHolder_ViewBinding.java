// Generated code from Butter Knife. Do not modify!
package com.alryada.etqan.Adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.alryada.etqan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationsViewAdapter$OrderViewHolder_ViewBinding implements Unbinder {
  private NotificationsViewAdapter.OrderViewHolder target;

  @UiThread
  public NotificationsViewAdapter$OrderViewHolder_ViewBinding(NotificationsViewAdapter.OrderViewHolder target,
      View source) {
    this.target = target;

    target.txtNotificationMessage = Utils.findRequiredViewAsType(source, R.id.txtNotificationMessage, "field 'txtNotificationMessage'", TextView.class);
    target.txtNotificationTime = Utils.findRequiredViewAsType(source, R.id.txtNotificationTime, "field 'txtNotificationTime'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NotificationsViewAdapter.OrderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtNotificationMessage = null;
    target.txtNotificationTime = null;
  }
}

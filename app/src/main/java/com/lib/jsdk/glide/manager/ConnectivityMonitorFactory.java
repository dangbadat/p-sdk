package com.lib.jsdk.glide.manager;

import android.content.Context;

import androidx.annotation.NonNull;


/**
 * A factory class that produces a functional
 * {@link com.lib.jsdk.glide.manager.ConnectivityMonitor}.
 */
public interface ConnectivityMonitorFactory {

  @NonNull
  com.lib.jsdk.glide.manager.ConnectivityMonitor build(
          @NonNull Context context,
          @NonNull ConnectivityMonitor.ConnectivityListener listener);
}

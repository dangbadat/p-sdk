package com.lib.jsdk.glide.manager;

/**
 * An interface for listener to {@link android.app.Fragment} and {@link android.app.Activity}
 * lifecycle events.
 */
public interface LifecycleListener {

  /**
   * Callback for when {@link android.app.Fragment#onStart()}} or {@link
   */
  void onStart();

  /**
   * Callback for when {@link android.app.Fragment#onStop()}} or {@link
   */
  void onStop();

  /**
   * Callback for when {@link android.app.Fragment#onDestroy()}} or {@link
   */
  void onDestroy();
}

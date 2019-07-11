package com.lib.jsdk.glide.request;

/**
 * An interface for coordinating multiple requests with the same {@link
 * com.lib.jsdk.glide.request.target.Target}.
 */
public interface RequestCoordinator {

  /**
   * Returns true if the {@link com.lib.jsdk.glide.request.Request} can display a loaded bitmap.
   *
   * @param request The {@link com.lib.jsdk.glide.request.Request} requesting permission to display a bitmap.
   */
  boolean canSetImage(com.lib.jsdk.glide.request.Request request);

  /**
   * Returns true if the {@link com.lib.jsdk.glide.request.Request} can display a placeholder.
   *
   * @param request The {@link com.lib.jsdk.glide.request.Request} requesting permission to display a placeholder.
   */
  boolean canNotifyStatusChanged(com.lib.jsdk.glide.request.Request request);

  /**
   * Returns {@code true} if the {@link com.lib.jsdk.glide.request.Request} can clear the
   * {@link com.lib.jsdk.glide.request.target.Target}.
   */
  boolean canNotifyCleared(com.lib.jsdk.glide.request.Request request);

  /**
   * Returns true if any coordinated {@link com.lib.jsdk.glide.request.Request} has successfully completed.
   *
   * @see com.lib.jsdk.glide.request.Request#isComplete()
   */
  boolean isAnyResourceSet();

  /**
   * Must be called when a {@link com.lib.jsdk.glide.request.Request} coordinated by this object completes successfully.
   */
  void onRequestSuccess(com.lib.jsdk.glide.request.Request request);

  /** Must be called when a {@link com.lib.jsdk.glide.request.Request} coordinated by this object fails. */
  void onRequestFailed(Request request);
}

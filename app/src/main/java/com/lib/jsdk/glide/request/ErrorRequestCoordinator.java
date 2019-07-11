package com.lib.jsdk.glide.request;


import androidx.annotation.Nullable;

/**
 * Runs a single primary {@link com.lib.jsdk.glide.request.Request} until it completes and then a fallback error request only
 * if the single primary request fails.
 */
public final class ErrorRequestCoordinator implements com.lib.jsdk.glide.request.RequestCoordinator,
        com.lib.jsdk.glide.request.Request {

  @Nullable
  private final com.lib.jsdk.glide.request.RequestCoordinator parent;
  private com.lib.jsdk.glide.request.Request primary;
  private com.lib.jsdk.glide.request.Request error;

  public ErrorRequestCoordinator(@Nullable RequestCoordinator parent) {
    this.parent = parent;
  }

  public void setRequests(com.lib.jsdk.glide.request.Request primary, com.lib.jsdk.glide.request.Request error) {
    this.primary = primary;
    this.error = error;
  }

  @Override
  public void begin() {
    if (!primary.isRunning()) {
      primary.begin();
    }
  }

  @Override
  public void clear() {
    primary.clear();
    // Don't check primary.isFailed() here because it will have been reset by the clear call
    // immediately before this.
    if (error.isRunning()) {
      error.clear();
    }
  }

  @Override
  public boolean isRunning() {
    return primary.isFailed() ? error.isRunning() : primary.isRunning();
  }

  @Override
  public boolean isComplete() {
    return primary.isFailed() ? error.isComplete() : primary.isComplete();
  }

  @Override
  public boolean isResourceSet() {
    return primary.isFailed() ? error.isResourceSet() : primary.isResourceSet();
  }

  @Override
  public boolean isCleared() {
    return primary.isFailed() ? error.isCleared() : primary.isCleared();
  }

  @Override
  public boolean isFailed() {
    return primary.isFailed() && error.isFailed();
  }

  @Override
  public void recycle() {
    primary.recycle();
    error.recycle();
  }

  @Override
  public boolean isEquivalentTo(com.lib.jsdk.glide.request.Request o) {
    if (o instanceof ErrorRequestCoordinator) {
      ErrorRequestCoordinator other = (ErrorRequestCoordinator) o;
      return primary.isEquivalentTo(other.primary) && error.isEquivalentTo(other.error);
    }
    return false;
  }

  @Override
  public boolean canSetImage(com.lib.jsdk.glide.request.Request request) {
    return parentCanSetImage() && isValidRequest(request);
  }

  private boolean parentCanSetImage() {
    return parent == null || parent.canSetImage(this);
  }

  @Override
  public boolean canNotifyStatusChanged(com.lib.jsdk.glide.request.Request request) {
    return parentCanNotifyStatusChanged() && isValidRequest(request);
  }

  @Override
  public boolean canNotifyCleared(com.lib.jsdk.glide.request.Request request) {
    return parentCanNotifyCleared() && isValidRequest(request);
  }

  private boolean parentCanNotifyCleared() {
    return parent == null || parent.canNotifyCleared(this);
  }

  private boolean parentCanNotifyStatusChanged() {
    return parent == null || parent.canNotifyStatusChanged(this);
  }

  private boolean isValidRequest(com.lib.jsdk.glide.request.Request request) {
    return request.equals(primary) || (primary.isFailed() && request.equals(error));
  }

  @Override
  public boolean isAnyResourceSet() {
    return parentIsAnyResourceSet() || isResourceSet();
  }

  private boolean parentIsAnyResourceSet() {
    return parent != null && parent.isAnyResourceSet();
  }

  @Override
  public void onRequestSuccess(com.lib.jsdk.glide.request.Request request) {
    if (parent != null) {
      parent.onRequestSuccess(this);
    }
  }

  @Override
  public void onRequestFailed(Request request) {
    if (!request.equals(error)) {
      if (!error.isRunning()) {
        error.begin();
      }
      return;
    }

    if (parent != null) {
      parent.onRequestFailed(this);
    }
  }
}

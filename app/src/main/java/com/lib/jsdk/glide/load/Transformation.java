package com.lib.jsdk.glide.load;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.load.engine.Resource;

import java.security.MessageDigest;

/**
 * A class for performing an arbitrary transformation on a resource that implements
 * memory caching to work correctly.
 *
 * @param <T> The type of the resource being transformed.
 */
public interface Transformation<T> extends Key {

  /**
   * Transforms the given resource and returns the transformed resource.
   *
   * <p>If the original resource object is not returned, the original resource will be
   * recycled and it's internal resources may be reused. This means it is not safe to rely on the
   * original resource or any internal state of the original resource in any new resource that is
   * created. Usually this shouldn't occur, but if absolutely necessary either the original resource
   * object can be returned with modified internal state, or the data in the original resource can
   * be copied into the transformed resource.
   *
   * {@link #updateDiskCacheKey(MessageDigest)} should all change. If you're using a
   * simple String key an easy way to do this is to append a version number to your key. Failing to
   * do so will mean users may see images loaded from cache that had the old version of the
   * Transformation applied. Changing the return values of those methods will ensure that the cache
   * key has changed and therefore that any cached resources will be re-generated using the updated
   * Transformation.
   *
   * <p>During development you may need to either using {@link
   * com.lib.jsdk.glide.load.engine.DiskCacheStrategy#NONE} or make sure {@link
   * #updateDiskCacheKey(MessageDigest)} changes each time you make a change to the
   * Transformation. Otherwise the resource you request may be loaded from disk cache and your
   * Transformation may not be called.
   *
   * @param context The Application context
   * @param resource  The resource to transform.
   * @param outWidth  The width of the view or target the resource will be displayed in, or {@link
   *                  com.lib.jsdk.glide.request.target.Target#SIZE_ORIGINAL} to indicate the
   *                  original resource width.
   * @param outHeight The height of the view or target the resource will be displayed in, or {@link
   *                  com.lib.jsdk.glide.request.target.Target#SIZE_ORIGINAL} to indicate the
   *                  original resource height.
   * @return The transformed resource.
   */
  @NonNull
  Resource<T> transform(@NonNull Context context, @NonNull Resource<T> resource,
                        int outWidth, int outHeight);
}

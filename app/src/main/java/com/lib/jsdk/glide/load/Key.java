package com.lib.jsdk.glide.load;

import androidx.annotation.NonNull;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * An interface that uniquely identifies some put of data. Implementations must implement {@link
 * #updateDiskCacheKey(MessageDigest)}}, although this requirement is not as strict
 * for partial cache key signatures.
 */
public interface Key {
  String STRING_CHARSET_NAME = "UTF-8";
  Charset CHARSET = Charset.forName(STRING_CHARSET_NAME);

  /**
   * Adds all uniquely identifying information to the given digest.
   *
   * <p> Note - Using {@link MessageDigest#reset()} inside of this method will result
   * in undefined behavior. </p>
   */
  void updateDiskCacheKey(@NonNull MessageDigest messageDigest);

  /**
   * For caching to work correctly, implementations <em>must</em> implement this method and
   * {@link #hashCode()}.
   */
  @Override
  boolean equals(Object o);

  /**
   * For caching to work correctly, implementations <em>must</em> implement this method and
   */
  @Override
  int hashCode();
}

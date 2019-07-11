package com.lib.jsdk.glide.load.resource.bytes;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.load.engine.Resource;
import com.lib.jsdk.glide.util.Preconditions;

/**
 * An {@link Resource} wrapping a byte array.
 */
public class BytesResource implements Resource<byte[]> {
  private final byte[] bytes;

  public BytesResource(byte[] bytes) {
    this.bytes = Preconditions.checkNotNull(bytes);
  }

  @NonNull
  @Override
  public Class<byte[]> getResourceClass() {
    return byte[].class;
  }

  /**
   * In most cases it will only be retrieved once (see linked methods).
   *
   * @return the same array every time, do not mutate the contents. Not a copy returned, because
   * copying the array can be prohibitively expensive and/or lead to OOMs.
   * @see com.lib.jsdk.glide.load.ResourceEncoder
   * @see com.lib.jsdk.glide.load.resource.transcode.ResourceTranscoder
   * @see com.lib.jsdk.glide.request.SingleRequest#onResourceReady
   */
  @NonNull
  @Override
  @SuppressWarnings("PMD.MethodReturnsInternalArray")
  public byte[] get() {
    return bytes;
  }

  @Override
  public int getSize() {
    return bytes.length;
  }

  @Override
  public void recycle() {
    // Do nothing.
  }
}

package com.lib.jsdk.glide.load.resource.transcode;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.jsdk.glide.load.Options;
import com.lib.jsdk.glide.load.engine.Resource;
import com.lib.jsdk.glide.load.engine.bitmap_recycle.BitmapPool;
import com.lib.jsdk.glide.load.resource.bitmap.BitmapResource;

public final class DrawableBytesTranscoder implements ResourceTranscoder<Drawable, byte[]> {
  private final BitmapPool bitmapPool;
  private final com.lib.jsdk.glide.load.resource.transcode.ResourceTranscoder<Bitmap, byte[]> bitmapBytesTranscoder;

  public DrawableBytesTranscoder(
      @NonNull BitmapPool bitmapPool,
      @NonNull ResourceTranscoder<Bitmap, byte[]> bitmapBytesTranscoder) {
    this.bitmapPool = bitmapPool;
    this.bitmapBytesTranscoder = bitmapBytesTranscoder;
  }

  @Nullable
  @Override
  public Resource<byte[]> transcode(@NonNull Resource<Drawable> toTranscode,
      @NonNull Options options) {
    Drawable drawable = toTranscode.get();
    if (drawable instanceof BitmapDrawable) {
      return bitmapBytesTranscoder.transcode(
          BitmapResource.obtain(((BitmapDrawable) drawable).getBitmap(), bitmapPool), options);
    } else
    return null;
  }

}

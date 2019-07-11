package com.lib.jsdk.glide.load.resource.bitmap;

import android.content.Context;
import android.os.ParcelFileDescriptor;

import com.lib.jsdk.glide.Glide;
import com.lib.jsdk.glide.load.engine.bitmap_recycle.BitmapPool;

/**
 * An {@link com.lib.jsdk.glide.load.ResourceDecoder} that can decode a thumbnail frame
 * {@link android.graphics.Bitmap} from a {@link ParcelFileDescriptor} containing a
 * video.
 *
 * @see android.media.MediaMetadataRetriever
 *
 * @deprecated Use {@link VideoDecoder#parcel(BitmapPool)} instead. This class may be removed and
 * {@link VideoDecoder} may become final in a future version of Glide.
 */
@Deprecated
public class VideoBitmapDecoder extends VideoDecoder<ParcelFileDescriptor> {

  @SuppressWarnings("unused")
  public VideoBitmapDecoder(Context context) {
    this(Glide.get(context).getBitmapPool());
  }

  // Public API
  @SuppressWarnings("WeakerAccess")
  public VideoBitmapDecoder(BitmapPool bitmapPool) {
    super(bitmapPool, new ParcelFileDescriptorInitializer());
  }
}

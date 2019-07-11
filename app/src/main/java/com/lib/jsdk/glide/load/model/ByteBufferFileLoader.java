package com.lib.jsdk.glide.load.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.Priority;
import com.lib.jsdk.glide.load.DataSource;
import com.lib.jsdk.glide.load.Options;
import com.lib.jsdk.glide.load.data.DataFetcher;
import com.lib.jsdk.glide.signature.ObjectKey;
import com.lib.jsdk.glide.util.ByteBufferUtil;
import com.lib.jsdk.glide.util.Synthetic;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Loads {@link ByteBuffer}s using NIO for {@link File}.
 */
public class ByteBufferFileLoader implements com.lib.jsdk.glide.load.model.ModelLoader<File, ByteBuffer> {
  private static final String TAG = "ByteBufferFileLoader";

  @Override
  public LoadData<ByteBuffer> buildLoadData(@NonNull File file, int width, int height,
                                            @NonNull Options options) {
    return new LoadData<>(new ObjectKey(file), new ByteBufferFetcher(file));
  }

  @Override
  public boolean handles(@NonNull File file) {
    return true;
  }

  /**
   * Factory for {@link com.lib.jsdk.glide.load.model.ByteBufferFileLoader}.
   */
  public static class Factory implements ModelLoaderFactory<File, ByteBuffer> {

    @NonNull
    @Override
    public ModelLoader<File, ByteBuffer> build(@NonNull MultiModelLoaderFactory multiFactory) {
      return new ByteBufferFileLoader();
    }

    @Override
    public void teardown() {
      // Do nothing.
    }
  }

  private static final class ByteBufferFetcher implements DataFetcher<ByteBuffer> {

    private final File file;

    @Synthetic
    @SuppressWarnings("WeakerAccess")
    ByteBufferFetcher(File file) {
      this.file = file;
    }

    @Override
    public void loadData(@NonNull Priority priority,
        @NonNull DataCallback<? super ByteBuffer> callback) {
      ByteBuffer result;
      try {
        result = ByteBufferUtil.fromFile(file);
      } catch (IOException e) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
          Log.d(TAG, "Failed to obtain ByteBuffer for file", e);
        }
        callback.onLoadFailed(e);
        return;
      }

      callback.onDataReady(result);
    }

    @Override
    public void cleanup() {
      // Do nothing.
    }

    @Override
    public void cancel() {
      // Do nothing.
    }

    @NonNull
    @Override
    public Class<ByteBuffer> getDataClass() {
      return ByteBuffer.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
      return DataSource.LOCAL;
    }
  }
}

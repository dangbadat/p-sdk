package com.lib.jsdk.glide.load.resource.file;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.load.Options;
import com.lib.jsdk.glide.load.ResourceDecoder;
import com.lib.jsdk.glide.load.engine.Resource;

import java.io.File;

/**
 * A simple {@link ResourceDecoder} that creates resource for a given {@link
 * File}.
 */
public class FileDecoder implements ResourceDecoder<File, File> {

  @Override
  public boolean handles(@NonNull File source, @NonNull Options options) {
    return true;
  }

  @Override
  public Resource<File> decode(@NonNull File source, int width, int height,
                               @NonNull Options options) {
    return new FileResource(source);
  }
}

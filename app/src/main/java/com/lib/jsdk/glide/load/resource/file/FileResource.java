package com.lib.jsdk.glide.load.resource.file;

import com.lib.jsdk.glide.load.resource.SimpleResource;

import java.io.File;

/**
 * A simple {@link com.lib.jsdk.glide.load.engine.Resource} that wraps a {@link File}.
 */
// Public API.
@SuppressWarnings("WeakerAccess")
public class FileResource extends SimpleResource<File> {
  public FileResource(File file) {
    super(file);
  }
}

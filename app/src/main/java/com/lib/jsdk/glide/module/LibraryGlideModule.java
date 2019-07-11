package com.lib.jsdk.glide.module;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.Glide;
import com.lib.jsdk.glide.Registry;

/**
 * Registers a set of components to use when initializing Glide within an app when
 * Glide's annotation processor is used.
 *
 * <p>Any number of LibraryGlideModules can be contained within any library or application.
 *
 * <p>LibraryGlideModules are called in no defined order. If LibraryGlideModules within an
 * application conflict, {@link AppGlideModule}s can use the
 * the conflicting modules.
 */
@SuppressWarnings("deprecation")
public abstract class LibraryGlideModule implements RegistersComponents {
  @Override
  public void registerComponents(@NonNull Context context, @NonNull Glide glide,
                                 @NonNull Registry registry) {
    // Default empty impl.
  }
}

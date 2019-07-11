package com.lib.jsdk.glide.module;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.GlideBuilder;

/**
 * Defines a set of dependencies and options to use when initializing Glide within an application.
 *
 * <p>There can be at most one {@link AppGlideModule} in an application. Only Applications
 * can include a {@link AppGlideModule}. Libraries must use {@link LibraryGlideModule}.
 *
 * <p>Classes that extend {@link AppGlideModule} must be annotated with
 *
 * <p>Classes that extend {@link AppGlideModule} can optionally be annotated with
 * {@link LibraryGlideModule} and/or {@link com.lib.jsdk.glide.module.GlideModule} classes.
 *
 * <p>Once an application has migrated itself and all libraries it depends on to use Glide's
 * annotation processor, {@link AppGlideModule} implementations should override
 * {@link #isManifestParsingEnabled()} and return {@code false}.
 */
// Used only in javadoc.
@SuppressWarnings("deprecation")
public abstract class AppGlideModule extends LibraryGlideModule implements AppliesOptions {
  /**
   * Returns {@code true} if Glide should check the AndroidManifest for {@link GlideModule}s.
   *
   * <p>Implementations should return {@code false} after they and their dependencies have migrated
   * to Glide's annotation processor.
   *
   * <p>Returns {@code true} by default.
   */
  public boolean isManifestParsingEnabled() {
    return true;
  }

  @Override
  public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    // Default empty impl.
  }
}

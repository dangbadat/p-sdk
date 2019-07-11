package com.lib.jsdk.glide.load.model;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.Glide;
import com.lib.jsdk.glide.Registry;

/**
 * An interface for creating a {@link com.lib.jsdk.glide.load.model.ModelLoader} for a given model type.
 *
 * <p>The application {@link Context} can be passed in to the constructor of the
 * factory when necessary. It's unsafe to retain {@link android.app.Activity}
 * {@link Context}s in factories. The {@link Context} can be
 * obtained from
 * {@link com.lib.jsdk.glide.module.LibraryGlideModule#registerComponents(Context, Glide, Registry)}
 * in most cases.
 *
 * @param <T> The type of the model the {@link com.lib.jsdk.glide.load.model.ModelLoader}s built by
 *            this factory can handle
 * @param <Y> The type of data the {@link com.lib.jsdk.glide.load.model.ModelLoader}s built by this
 *            factory can load.
 */
public interface ModelLoaderFactory<T, Y> {

  /**
   * Build a concrete ModelLoader for this model type.
   *
   * @param multiFactory A map of classes to factories that can be used to construct additional
   *                     {@link com.lib.jsdk.glide.load.model.ModelLoader}s that this factory's {@link com.lib.jsdk.glide.load.model.ModelLoader} may depend on
   * @return A new {@link com.lib.jsdk.glide.load.model.ModelLoader}
   */
  @NonNull
  ModelLoader<T, Y> build(@NonNull MultiModelLoaderFactory multiFactory);

  /**
   * A lifecycle method that will be called when this factory is about to replaced.
   */
  void teardown();
}

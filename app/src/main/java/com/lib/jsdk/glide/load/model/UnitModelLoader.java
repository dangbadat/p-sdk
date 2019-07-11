package com.lib.jsdk.glide.load.model;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.Priority;
import com.lib.jsdk.glide.load.DataSource;
import com.lib.jsdk.glide.load.Options;
import com.lib.jsdk.glide.load.data.DataFetcher;
import com.lib.jsdk.glide.signature.ObjectKey;

/**
 * A put of helper classes that performs no loading and instead always returns the given model as
 * the data to decode.
 *
 * @param <Model> The type of model that will also be returned as decodable data.
 */
public class UnitModelLoader<Model> implements com.lib.jsdk.glide.load.model.ModelLoader<Model, Model> {
  @SuppressWarnings("deprecation")
  private static final UnitModelLoader<?> INSTANCE = new UnitModelLoader<>();

  @SuppressWarnings("unchecked")
  public static <T> UnitModelLoader<T> getInstance() {
    return (UnitModelLoader<T>) INSTANCE;
  }

  /**
   * @deprecated Use {@link #getInstance()} instead.
   */
  // Need constructor to document deprecation, will be removed, when constructor is privatized.
  @SuppressWarnings({"PMD.UnnecessaryConstructor", "DeprecatedIsStillUsed"})
  @Deprecated
  public UnitModelLoader() {
    // Intentionally empty.
  }

  @Override
  public LoadData<Model> buildLoadData(@NonNull Model model, int width, int height,
                                       @NonNull Options options) {
    return new LoadData<>(new ObjectKey(model), new UnitFetcher<>(model));
  }

  @Override
  public boolean handles(@NonNull Model model) {
    return true;
  }

  private static class UnitFetcher<Model> implements DataFetcher<Model> {

    private final Model resource;

    UnitFetcher(Model resource) {
      this.resource = resource;
    }

    @Override
    public void loadData(@NonNull Priority priority,
        @NonNull DataCallback<? super Model> callback) {
      callback.onDataReady(resource);
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
    @SuppressWarnings("unchecked")
    @Override
    public Class<Model> getDataClass() {
      return (Class<Model>) resource.getClass();
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
      return DataSource.LOCAL;
    }
  }

  /**
   * Factory for producing {@link com.lib.jsdk.glide.load.model.UnitModelLoader}s.
   *
   * @param <Model> The type of model that will also be returned as decodable data.
   */
  // PMD.SingleMethodSingleton false positive: https://github.com/pmd/pmd/issues/816
  @SuppressWarnings("PMD.SingleMethodSingleton")
  public static class Factory<Model> implements ModelLoaderFactory<Model, Model> {
    @SuppressWarnings("deprecation")
    private static final Factory<?> FACTORY = new Factory<>();

    @SuppressWarnings("unchecked")
    public static <T> Factory<T> getInstance() {
      return (Factory<T>) FACTORY;
    }

    /** @deprecated Use {@link #getInstance()} instead. */
    // Need constructor to document deprecation, will be removed, when constructor is privatized.
    @SuppressWarnings("PMD.UnnecessaryConstructor")
    @Deprecated
    public Factory() {
      // Intentionally empty.
    }

    @NonNull
    @Override
    public ModelLoader<Model, Model> build(MultiModelLoaderFactory multiFactory) {
      return UnitModelLoader.getInstance();
    }

    @Override
    public void teardown() {
      // Do nothing.
    }
  }
}

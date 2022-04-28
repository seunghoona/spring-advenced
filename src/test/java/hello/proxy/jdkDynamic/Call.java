package hello.proxy.jdkDynamic;

@FunctionalInterface
public interface Call<T> {
    T get();
}

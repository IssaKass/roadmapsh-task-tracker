package mapper;

/**
 * Author: abdallah-issakass
 */
public interface JSONMapper<T> {
    T fromJSON(String json);

    String toJSON(T t);
}

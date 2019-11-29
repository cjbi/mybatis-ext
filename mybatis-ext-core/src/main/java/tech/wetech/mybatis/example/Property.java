package tech.wetech.mybatis.example;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author cjbi
 */
public interface Property<T, R> extends Function<T, R>, Serializable {
}

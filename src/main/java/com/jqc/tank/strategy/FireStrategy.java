package com.jqc.tank.strategy;

import java.io.Serializable;

public interface FireStrategy<T>  extends Serializable {
    void fire(T t);
}

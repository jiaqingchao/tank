package com.jqc.tank.net;

public abstract class Msg {
    abstract byte[] toBytes();
    abstract void parse(byte[] bytes);
}

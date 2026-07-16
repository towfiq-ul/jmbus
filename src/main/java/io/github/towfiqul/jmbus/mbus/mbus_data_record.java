package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"drh", "data", "data_len", "timestamp", "next"})
public class mbus_data_record extends Structure {
    public io.github.towfiqul.jmbus.mbus.mbus_data_record_header drh;
    public byte[] data = new byte[234];
    public long data_len;
    public long timestamp;
    public Pointer next;
}

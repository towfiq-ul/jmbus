package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"start1", "length1", "length2", "start2", "control", "address", "control_information", "checksum", "stop",
        "data", "data_size", "type", "timestamp", "next"})
public class mbus_frame extends Structure {
    public byte start1;
    public byte length1;
    public byte length2;
    public byte start2;
    public byte control;
    public byte address;
    public byte control_information;
    public byte checksum;
    public byte stop;
    public byte[] data = new byte[252];
    public long data_size;
    public int type;
    public long timestamp;
    public Pointer next;
}


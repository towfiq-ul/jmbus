package com.github.laziestcoder.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"id_bcd", "manufacturer", "version", "medium", "access_no", "status", "signature"})
public class mbus_data_variable_header extends Structure {
    public byte[] id_bcd = new byte[4];
    public byte[] manufacturer = new byte[2];
    public byte version;
    public byte medium;
    public byte access_no;
    public byte status;
    public byte[] signature = new byte[2];
}

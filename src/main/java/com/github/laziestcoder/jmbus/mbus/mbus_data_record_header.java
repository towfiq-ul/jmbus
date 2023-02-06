package com.github.laziestcoder.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"dib", "vib"})
public class mbus_data_record_header extends Structure {
    public mbus_data_information_block dib;
    public mbus_value_information_block vib;
}

package com.github.laziestcoder.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"data_var", "data_fix", "type", "error"})
public class mbus_frame_data extends Structure {
    public mbus_data_variable data_var;
    public mbus_data_fixed data_fix;
    public int type;
    public int error;
}

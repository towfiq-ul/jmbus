package com.github.laziestcoder.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"header", "record", "nrecords", "data", "data_len", "more_records_follow", "mdh", "mfg_data", "mfg_data_len"})
public class mbus_data_variable extends Structure {
    public mbus_data_variable_header header;
    public mbus_data_record record;
    public long nrecords;
    public String data;
    public long data_len;
    public byte more_records_follow;
    public byte mdh;
    public String mfg_data;
    public long mfg_data_len;
}

package io.github.towfiqul.jmbus.mbus;


import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"id_bcd", "tx_cnt", "status", "cnt1_type", "cnt2_type", "cnt1_val", "cnt2_val"})
public class mbus_data_fixed extends Structure {
    public byte[] id_bcd = new byte[4];
    public byte tx_cnt;
    public byte status;
    public byte cnt1_type;
    public byte cnt2_type;
    public byte[] cnt1_val = new byte[4];
    public byte[] cnt2_val = new byte[4];
}

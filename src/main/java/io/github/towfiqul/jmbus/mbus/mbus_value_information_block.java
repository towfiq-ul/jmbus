package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"vif", "vife", "nvife", "custom_vif"})
public class mbus_value_information_block extends Structure {
    public byte vif;
    public byte[] vife = new byte[10];
    public long nvife;
    public byte[] custom_vif = new byte[128];
}

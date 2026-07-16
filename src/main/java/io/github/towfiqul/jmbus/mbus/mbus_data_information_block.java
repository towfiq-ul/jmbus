package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"dif", "dife", "ndife"})
public class mbus_data_information_block extends Structure {
    public byte dif;
    public byte[] dife = new byte[10];
    public long ndife;
}

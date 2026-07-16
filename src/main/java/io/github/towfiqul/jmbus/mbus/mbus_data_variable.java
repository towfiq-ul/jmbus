package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"header", "record", "nrecords", "data", "data_len", "more_records_follow", "mdh", "mfg_data", "mfg_data_len"})
public class mbus_data_variable extends Structure {
    public io.github.towfiqul.jmbus.mbus.mbus_data_variable_header header;
    // mbus_data_record *record in C - must stay a Pointer, not an embedded Structure, or
    // every field after it (and every field of the enclosing mbus_frame_data) lands at the
    // wrong native offset. Deref with record.getPointer()/Structure.newInstance(...) if the
    // linked list of records is ever needed from Java.
    public Pointer record;
    public long nrecords;
    // unsigned char *data / *mfg_data in C - raw byte buffers, not necessarily
    // null-terminated text, so these stay Pointer rather than String.
    public Pointer data;
    public long data_len;
    public byte more_records_follow;
    public byte mdh;
    public Pointer mfg_data;
    public long mfg_data_len;
}

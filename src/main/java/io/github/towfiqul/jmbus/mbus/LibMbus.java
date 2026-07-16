package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Library;

public interface LibMbus extends Library {
    String mbus_get_current_version();

    long mbus_hex2bin(byte[] buff, int size_of_buf, byte[] raw_buf, int size_of_raw_buf);

    int mbus_parse(io.github.towfiqul.jmbus.mbus.mbus_frame reply, byte[] buf, long buf_len);

    int mbus_frame_data_parse(io.github.towfiqul.jmbus.mbus.mbus_frame reply, io.github.towfiqul.jmbus.mbus.mbus_frame_data frame_data);

    int mbus_frame_print(io.github.towfiqul.jmbus.mbus.mbus_frame frame);

    String mbus_frame_data_xml_normalized(io.github.towfiqul.jmbus.mbus.mbus_frame_data frame_data);

    String mbus_frame_data_xml(io.github.towfiqul.jmbus.mbus.mbus_frame_data frame_data);
}

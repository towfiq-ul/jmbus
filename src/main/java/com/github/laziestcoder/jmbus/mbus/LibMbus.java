package com.github.laziestcoder.jmbus.mbus;

import com.sun.jna.Library;

public interface LibMbus extends Library {
    String mbus_get_current_version();

    long mbus_hex2bin(byte[] buff, int size_of_buf, byte[] raw_buf, int size_of_raw_buf);

    int mbus_parse(mbus_frame reply, byte[] buf, long buf_len);

    int mbus_frame_data_parse(mbus_frame reply, mbus_frame_data frame_data);

    int mbus_frame_print(mbus_frame frame);

    String mbus_frame_data_xml_normalized(mbus_frame_data frame_data);

    String mbus_frame_data_xml(mbus_frame_data frame_data);
}

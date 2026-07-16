/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.towfiqul.jmbus.mbus.LibMbus;
import io.github.towfiqul.jmbus.mbus.mbus_frame;
import io.github.towfiqul.jmbus.mbus.mbus_frame_data;
import io.github.towfiqul.jmbus.service.MBusService;
import com.sun.jna.Native;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * Default {@link MBusService} implementation. Decodes a telegram by driving libmbus's native
 * parse pipeline directly: {@link LibMbus#mbus_hex2bin} to get raw bytes, {@link
 * LibMbus#mbus_parse} to get a frame, {@link LibMbus#mbus_frame_data_parse} to decode its
 * records, then {@link LibMbus#mbus_frame_data_xml} to render XML, which is converted straight
 * to JSON.
 *
 * <p>Annotated {@link Service @Service}, so a Spring application can {@code @Autowired} it
 * directly; outside Spring, just construct it with {@code new MBusServiceImpl()}.
 */
@Service
public class MBusServiceImpl implements MBusService {
    private static final Logger LOG = LoggerFactory.getLogger(MBusService.class.getName());
    private static final String SO_FILE_PATH_NAME = "libmbus.so";
    private final XmlMapper xmlMapper = new XmlMapper();
    private LibMbus libMbus = null;

    /**
     * Loads {@code libmbus.so} from the classpath (via {@link ResourceUtils#getFile}, which
     * requires it to be an actual file, not packed inside a jar — see the README's "Runtime
     * requirement" note) and binds it as {@link LibMbus}. Load failures are logged rather than
     * thrown, leaving {@code libMbus} {@code null}; subsequent {@link #decodeMessage} calls will
     * then fail with a {@link NullPointerException}.
     */
    public MBusServiceImpl() {
        try {
            File LIB_RESOURCE = ResourceUtils.getFile(SO_FILE_PATH_NAME);
            String LIB_DIR = LIB_RESOURCE.getPath();
            LOG.info("Libmbus Path: {}", LIB_DIR);
            libMbus = Native.load(LIB_DIR, LibMbus.class);
        } catch (Exception e) {
            LOG.error("Exception: {}", e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode decodeMessage(JsonNode dataNode, String propertyName) {
        ((ObjectNode) dataNode).put(propertyName, this.getDecodedValue(dataNode.get(propertyName).asText()));
        return dataNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode decodeMessage(String hexValue) {
        return this.getDecodedValue(hexValue);
    }

    /**
     * Runs the hex-to-JSON decode pipeline described in the class Javadoc.
     *
     * @param hexValue the hex-encoded telegram to decode
     * @return the decoded telegram as JSON
     * @throws RuntimeException if any step of the native decode pipeline fails; the original
     *                          exception is logged and its message re-thrown
     */
    private JsonNode getDecodedValue(String hexValue) {
        JsonNode jsonResult = null;
        try {
            byte[] buff = new byte[4096];
            byte[] raw_buff = hexValue.getBytes();
            long buffLength = libMbus.mbus_hex2bin(buff, buff.length, raw_buff, raw_buff.length);
            mbus_frame mBusFrame = new mbus_frame();
            libMbus.mbus_parse(mBusFrame, buff, buffLength);
            mbus_frame_data frame_data = new mbus_frame_data();
            libMbus.mbus_frame_data_parse(mBusFrame, frame_data);
            libMbus.mbus_frame_print(mBusFrame);
            String result = libMbus.mbus_frame_data_xml(frame_data);
            jsonResult = xmlMapper.readTree(result.getBytes());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return jsonResult;
    }
}

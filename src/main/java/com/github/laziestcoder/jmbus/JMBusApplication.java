/**
 * @author Towfiqul Islam
 * @since 2/3/23 4:02 AM
 */

package com.github.laziestcoder.jmbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JMBusApplication {
    public static void main(String[] args) {
        SpringApplication.run(JMeterBusService.class, args);
    }

}

package com.rolex.tips.log.util;

import org.springframework.util.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class BuriedPointMachineUtil {
    private static String processNo;
    private static String IP;
    private static String hostName;

    static {
        processNo = getProcessNo();
    }

    public static String getProcessNo() {
        if (StringUtils.isEmpty(processNo)) {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            processNo = name.split("@")[0];
        }
        return processNo;
    }

    private static InetAddress getInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            hostName = "unknown host!";
        }
        return null;

    }

    public static String getHostIp() {
        if (StringUtils.isEmpty(IP)) {
            InetAddress netAddress = getInetAddress();
            if (null == netAddress) {
                IP = "N/A";
            } else {
                IP = netAddress.getHostAddress(); //get the ip address
            }
        }
        return IP;
    }

    public static String getHostName() {
        if (StringUtils.isEmpty(hostName)) {
            InetAddress netAddress = getInetAddress();
            if (null == netAddress) {
                hostName = "N/A";
            } else {
                hostName = netAddress.getHostName(); //get the host address
            }
        }
        return hostName;
    }

    public static String getHostDesc() {
        return getHostName() + "/" + getHostIp();
    }

    private BuriedPointMachineUtil() {
        // Non
    }
}
 

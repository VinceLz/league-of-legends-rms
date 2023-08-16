package com.hawolt.rms.data;

/**
 * Created: 22/01/2023 12:33
 * Author: Twitter @hawolt
 **/

public enum PayloadType {
    RSO, SESSION, MESSAGE, GZIP, UNKNOWN;

    private final static PayloadType[] PAYLOAD_TYPES = PayloadType.values();

    public static PayloadType findBySubject(String subject) {
        if (!subject.contains(":")) return UNKNOWN;
        String relevant = subject.split(":")[1].toUpperCase();
        for (PayloadType type : PAYLOAD_TYPES) {
            if (type.name().equals(relevant)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}

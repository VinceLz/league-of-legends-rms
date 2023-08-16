package com.hawolt.rms.data.subject.service;

/**
 * Created: 22/01/2023 14:05
 * Author: Twitter @hawolt
 **/

public enum MessageService {
    PARTIES, GAPS_SESSION_SERVICE, TEAMBUILDER, STORE_PURCHASE, CAP_WALLETS, CAP_ENTITLEMENTS, GSM, UNKNOWN;

    @Override
    public String toString() {
        if (name().startsWith("CAP")) return name().toLowerCase().replaceAll("_", "\\.");
        return name().toLowerCase().replaceAll("_", "-");
    }

    private final static MessageService[] MESSAGE_SERVICES = MessageService.values();

    public static MessageService findByService(String service) {
        for (MessageService type : MESSAGE_SERVICES) {
            if (type.toString().equals(service)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}

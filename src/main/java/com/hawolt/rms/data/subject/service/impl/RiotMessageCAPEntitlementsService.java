package com.hawolt.rms.data.subject.service.impl;

import com.hawolt.rms.data.impl.payload.RiotMessageMessagePayload;
import com.hawolt.rms.data.subject.service.IRiotMessageServiceHandler;
import com.hawolt.rms.data.subject.service.RiotMessageServiceMessage;

/**
 * Created: 22/01/2023 13:36
 * Author: Twitter @hawolt
 **/

public class RiotMessageCAPEntitlementsService implements IRiotMessageServiceHandler<RiotMessageServiceMessage> {

    @Override
    public RiotMessageServiceMessage handle(RiotMessageMessagePayload payload) {
        return new RiotMessageServiceMessage(payload);
    }
}

package com.hawolt.rms.data.subject.service.impl;

import com.hawolt.rms.data.impl.payload.RiotMessageMessagePayload;
import com.hawolt.rms.data.subject.service.IRiotMessageServiceHandler;
import com.hawolt.rms.data.subject.service.objects.RiotMessageParties;

/**
 * Created: 22/01/2023 13:36
 * Author: Twitter @hawolt
 **/

public class RiotMessagePartiesService implements IRiotMessageServiceHandler<RiotMessageParties> {

    @Override
    public RiotMessageParties handle(RiotMessageMessagePayload payload) {
        return new RiotMessageParties(payload);
    }
}

package com.hawolt.rms.data.subject;

import com.hawolt.generic.data.Unsafe;
import com.hawolt.logger.Logger;
import com.hawolt.rms.data.AbstractRiotMessagePayload;
import com.hawolt.rms.data.GenericRiotMessageEvent;
import com.hawolt.rms.data.PayloadType;
import com.hawolt.rms.data.impl.RiotMessageMessageEvent;
import com.hawolt.rms.data.impl.UnknownMessageEvent;
import com.hawolt.rms.data.impl.payload.GenericRiotMessagePayload;
import com.hawolt.rms.data.subject.service.IServiceMessageListener;
import com.hawolt.rms.data.subject.service.MessageService;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Function;

/**
 * Created: 22/01/2023 12:05
 * Author: Twitter @hawolt
 **/

public class RiotMessageSubjectHandler {
    private final Function<JSONObject, AbstractRiotMessagePayload<?>> unknownMessageEvent = UnknownMessageEvent::new;
    private final Map<String, Function<JSONObject, AbstractRiotMessagePayload<?>>> map = new HashMap<>();
    private final Map<PayloadType, List<IPayloadListener<?>>> listeners = new HashMap<>();
    private final RiotMessageHandler riotMessageHandler = new RiotMessageHandler();

    public RiotMessageSubjectHandler() {
        listeners.put(PayloadType.MESSAGE, Collections.singletonList(riotMessageHandler));
        map.put("rms:message", RiotMessageMessageEvent::new);
        map.put("unknown", UnknownMessageEvent::new);
    }


    public void addListener(PayloadType type, IPayloadListener<?> listener) {
        if (type != PayloadType.MESSAGE) {
            if (!listeners.containsKey(type)) listeners.put(type, new LinkedList<>());
            listeners.get(type).add(listener);
        } else {
            throw new RuntimeException("Please use RiotMessageSubjectHandler#addMessageServiceListener to listen to messages");
        }
    }

    public void addMessageServiceListener(MessageService service, IServiceMessageListener<?> listener) {
        riotMessageHandler.addListener(service, listener);
    }

    public void onGenericRiotMessageEvent(GenericRiotMessageEvent event) {
        PayloadType type = PayloadType.findBySubject(event.getSubject());
        if (!listeners.containsKey(type)) return;
        List<IPayloadListener<?>> listeners = this.listeners.get(type);
        Logger.debug("[rms-handler] type: {}, listeners: {}", type.name().toLowerCase(), listeners.size());
        try {
            AbstractRiotMessagePayload<?> main = map.getOrDefault(event.getSubject(), unknownMessageEvent).apply(event.getPayload());
            GenericRiotMessagePayload payload = main.getPayload();
            listeners.forEach(listener -> listener.onPayload(Unsafe.cast(payload)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

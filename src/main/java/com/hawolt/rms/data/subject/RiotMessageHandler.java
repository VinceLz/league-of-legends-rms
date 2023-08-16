package com.hawolt.rms.data.subject;

import com.hawolt.generic.data.Unsafe;
import com.hawolt.logger.Logger;
import com.hawolt.rms.data.impl.payload.RiotMessageMessagePayload;
import com.hawolt.rms.data.subject.service.IRiotMessageServiceHandler;
import com.hawolt.rms.data.subject.service.IServiceMessageListener;
import com.hawolt.rms.data.subject.service.MessageService;
import com.hawolt.rms.data.subject.service.RiotMessageServiceMessage;
import com.hawolt.rms.data.subject.service.impl.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created: 22/01/2023 13:18
 * Author: Twitter @hawolt
 **/

public class RiotMessageHandler implements IPayloadListener<RiotMessageMessagePayload> {
    private final Map<MessageService, IRiotMessageServiceHandler<?>> map = new HashMap<MessageService, IRiotMessageServiceHandler<?>>() {{
        put(MessageService.CAP_ENTITLEMENTS, new RiotMessageCAPEntitlementsService());
        put(MessageService.GAPS_SESSION_SERVICE, new RiotMessageGAPSService());
        put(MessageService.STORE_PURCHASE, new RiotMessageStoreService());
        put(MessageService.CAP_WALLETS, new RiotMessageCAPWalletService());
        put(MessageService.PARTIES, new RiotMessagePartiesService());
        put(MessageService.GSM, new RiotMessagePartiesService());
    }};

    private final IRiotMessageServiceHandler<?> UNKNOWN_SERVICE_HANDLER = RiotMessageServiceMessage::new;
    private final Map<MessageService, List<IServiceMessageListener<?>>> listeners = new HashMap<>();

    public void addListener(MessageService service, IServiceMessageListener<?> listener) {
        if (!listeners.containsKey(service)) listeners.put(service, new LinkedList<>());
        listeners.get(service).add(listener);
    }

    @Override
    public void onPayload(RiotMessageMessagePayload payload) {
        MessageService service = MessageService.findByService(payload.getService().toLowerCase());
        if (!listeners.containsKey(service)) return;
        List<IServiceMessageListener<?>> listeners = this.listeners.get(service);
        Logger.debug("[rms-message-handler] service: {}, listeners: {}", service.toString(), listeners.size());
        RiotMessageServiceMessage message = map.getOrDefault(service, UNKNOWN_SERVICE_HANDLER).handle(payload);
        listeners.forEach(listener -> listener.onMessage(Unsafe.cast(message)));
    }
}

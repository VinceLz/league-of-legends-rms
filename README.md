# league-of-legends-rms

league-of-legends-rms allows you to connect ro riots messaging service and listen to events

## Discord

since this code lacks documentation the best help you can get is my knowledge, proper questions can be asked in this [discord](https://discord.gg/3wknX5gxaW) server, please note that I will not guide you to achieve something or answer beginner level questions

## Maven

to use league-of-legends-rms in your maven project include the following repository

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

and the following dependency, `main-SNAPSHOT` might be highlighted as red and invalid but it is not, it refers to the latest commit hash available on the branch infront of the dash.

if this for whatever reason does not work for you, feel free to change the version to a fixed commit hash from [Jitpack](https://jitpack.io/#hawolt/league-of-legends-rms) or simply use the latest hash displayed below

[![](https://jitpack.io/v/hawolt/league-of-legends-rms.svg)](https://jitpack.io/#hawolt/league-of-legends-rms)

```xml

<dependency>
    <groupId>com.github.hawolt</groupId>
    <artifactId>league-of-legends-rms</artifactId>
    <version>main-SNAPSHOT</version>
</dependency>
```

## Usage

an example usage that will connect you to rms looks as follows

```java
package com.hawolt.rms;

import com.hawolt.authentication.LocalCookieSupplier;
import com.hawolt.logger.Logger;
import com.hawolt.manifest.RMANCache;
import com.hawolt.rms.data.GenericRiotMessageEvent;
import com.hawolt.virtual.leagueclient.client.VirtualLeagueClient;
import com.hawolt.virtual.leagueclient.exception.LeagueException;
import com.hawolt.virtual.leagueclient.instance.VirtualLeagueClientInstance;
import com.hawolt.virtual.riotclient.client.VirtualRiotClient;
import com.hawolt.virtual.riotclient.instance.VirtualRiotClientInstance;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created: 13/04/2023 12:48
 * Author: Twitter @hawolt
 **/

public class Example {
    public static void main(String[] args) {
        RMANCache.active = true;
        LocalCookieSupplier localCookieSupplier = new LocalCookieSupplier();
        VirtualRiotClientInstance virtualRiotClientInstance = VirtualRiotClientInstance.create(localCookieSupplier);
        try {
            VirtualRiotClient virtualRiotClient = virtualRiotClientInstance.login(args[0], args[1]);
            VirtualLeagueClientInstance virtualLeagueClientInstance = virtualRiotClient.createVirtualLeagueClientInstance();
            CompletableFuture<VirtualLeagueClient> virtualLeagueClientFuture = virtualLeagueClientInstance.login(true, false);
            virtualLeagueClientFuture.whenComplete(((virtualLeagueClient, throwable) -> {
                if (throwable != null) throwable.printStackTrace();
                else {
                    Logger.info("Client setup complete");
                    String url = String.format(
                            "wss://eu.edge.rms.si.riotgames.com:443/rms/v1/session?token=%s&id=%s&token_type=access&product_id=riot_client&platform=windows&device=desk",
                            virtualLeagueClientInstance.getLeagueClientSupplier().get("lol.access_token", true),
                            UUID.randomUUID()
                    );
                    try {
                        VirtualRiotMessageClient client = new VirtualRiotMessageClient(new URI(url), new IRiotMessageServiceConnectionCallback() {
                            @Override
                            public void onRiotMessageEvent(GenericRiotMessageEvent event) {

                            }

                            @Override
                            public void onClose(int i, String s, boolean b) {

                            }

                            @Override
                            public void onOpen(ServerHandshake handshake) {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                        client.connect();
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        } catch (IOException e) {
            Logger.error(e);
        } catch (LeagueException e) {
            throw new RuntimeException(e);
        }
    }
}
```

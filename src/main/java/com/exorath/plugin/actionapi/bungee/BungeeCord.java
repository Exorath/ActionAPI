/*
 * Copyright 2017 Exorath
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.exorath.plugin.actionapi.bungee;

import com.exorath.plugin.actionapi.Bootstrap;
import com.exorath.plugin.actionapi.bungee.handlers.JoinHandler;
import com.exorath.plugin.bcbase.BCBaseAPI;
import io.reactivex.schedulers.Schedulers;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.bukkit.Bukkit;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * BungeeCord bootstrapper
 * Created by toonsev on 2/26/2017.
 */
public class BungeeCord extends Plugin implements Listener {
    private Bootstrap bootstrap;

    @Override
    public void onEnable() {
        String serverId = BCBaseAPI.getInstance().getServerId();
        bootstrap = new Bootstrap(serverId, false);
        //we have to initially register this server, this does not work immediately for some reason.
        ProxyServer.getInstance().getScheduler().schedule(this, () -> {
            String[] players = ProxyServer.getInstance().getPlayers().stream().map(p -> p.getUniqueId().toString())
                    .collect(Collectors.toList()).toArray(new String[ProxyServer.getInstance().getPlayers().size()]);
            bootstrap.statusUpdate(players);
        }, 3, 20, TimeUnit.SECONDS);


        bootstrap.getActionManager().registerHandler(new JoinHandler());
    }

    @EventHandler
    public void onJoin(PostLoginEvent event){
        String[] players = ProxyServer.getInstance().getPlayers().stream().map(p -> p.getUniqueId().toString())
                .collect(Collectors.toList()).toArray(new String[ProxyServer.getInstance().getPlayers().size()]);
        bootstrap.statusUpdate(players);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event){
        String[] players = ProxyServer.getInstance().getPlayers().stream()
                .map(p -> p.getUniqueId().toString())
                .filter(id -> !id.equals(event.getPlayer().getUniqueId()))
                .collect(Collectors.toList()).toArray(new String[ProxyServer.getInstance().getPlayers().size()]);
        bootstrap.statusUpdate(players);
    }

    public static Collection<ProxiedPlayer> loadProxiedPlayers(String subject){
        Set<ProxiedPlayer> proxiedPlayers = new HashSet<>();
        for (String s : subject.split(",")) {
            try {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(s));
                if (player != null)
                    proxiedPlayers.add(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return proxiedPlayers;
    }
}

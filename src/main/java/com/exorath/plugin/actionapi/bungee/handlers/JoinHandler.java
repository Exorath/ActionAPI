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

package com.exorath.plugin.actionapi.bungee.handlers;

import com.exorath.plugin.actionapi.ActionHandler;
import com.exorath.service.actionapi.res.Action;
import io.reactivex.schedulers.Schedulers;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by toonsev on 2/27/2017.
 */
public class JoinHandler implements ActionHandler{
    @Override
    public String getType() {
        return "JOIN";
    }

    @Override
    public void onAction(Action action) {
        try {
            final String address = action.getMeta().get("address").getAsString();
            if (address == null) {
                System.out.println("Received a JOIN action without an address");
                return;
            }
            if(action.getSubject() == null || action.getSubject() == "" || action.getSubject() == "NONE"){
                System.out.println("JOIN action subject cannot be none. Ignoring action.");
                return;
            }
            String name = "unknown";
            if(action.getMeta().has("gameId"))
                name = action.getMeta().get("gameId").getAsString();

            if(action.getSubject() == "ALL"){
                connectPlayers(address, name, ProxyServer.getInstance().getPlayers());
            }else{//Case subject=uuid1,uuid2,..
                Set<ProxiedPlayer> proxiedPlayers = new HashSet<>();
                for (String s : action.getSubject().split(","))
                    proxiedPlayers.add(ProxyServer.getInstance().getPlayer(UUID.fromString(s)));
                connectPlayers(address, name, proxiedPlayers);
            }
        }catch(Exception e){
            System.out.println("An error parsing the JOIN metadata");
            e.printStackTrace();
        }
    }

    private void connectPlayers(String address, String gameName, Collection<ProxiedPlayer> players){
        String ip = address.split(":")[0];
        int port = Integer.valueOf(address.split(":")[1]);
        for(ProxiedPlayer player : players){
            Schedulers.io().scheduleDirect(() -> {
                player.connect(ProxyServer.getInstance()
                        .constructServerInfo(gameName, new InetSocketAddress(ip, port), "MOTD...", true));
            });
        }
    }
}

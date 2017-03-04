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

package com.exorath.plugin.actionapi.spigot;

import com.exorath.plugin.actionapi.Bootstrap;
import com.exorath.plugin.base.ExoBaseAPI;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

/**'
 * Spigot bootstrapper
 * Created by toonsev on 2/26/2017.
 */
public class Spigot extends JavaPlugin implements Listener {
    private Bootstrap bootstrap;

    @Override
    public void onEnable() {
        bootstrap = new Bootstrap(ExoBaseAPI.getInstance().getServerId(), true);
        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            String[] players = ProxyServer.getInstance().getPlayers().stream().map(p -> p.getUniqueId().toString())
                    .collect(Collectors.toList()).toArray(new String[ProxyServer.getInstance().getPlayers().size()]);
            bootstrap.statusUpdate(players);
        }, 3 * 20, 20* 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String[] players = Bukkit.getOnlinePlayers().stream().map(p -> p.getUniqueId().toString())
                .collect(Collectors.toList()).toArray(new String[Bukkit.getOnlinePlayers().size()]);
        bootstrap.statusUpdate(players);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String[] players = Bukkit.getOnlinePlayers().stream()
                .map(p -> p.getUniqueId().toString())
                .filter(id -> !id.equals(event.getPlayer().getUniqueId()))
                .collect(Collectors.toList()).toArray(new String[Bukkit.getOnlinePlayers().size()]);
        bootstrap.statusUpdate(players);
    }
}
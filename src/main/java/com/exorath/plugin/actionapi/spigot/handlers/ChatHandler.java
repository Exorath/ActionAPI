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

package com.exorath.plugin.actionapi.spigot.handlers;

import com.exorath.plugin.actionapi.ActionHandler;
import com.exorath.plugin.actionapi.spigot.Spigot;
import com.exorath.service.actionapi.res.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by toonsev on 4/17/2017.
 */
public class ChatHandler implements ActionHandler {
    @Override
    public String getAction() {
        return "CHAT";
    }

    @Override
    public void onAction(Action action) {
        try {
            if (!action.getMeta().has("lines")) {
                System.out.println("Received a CHAT action without a lines array");
                return;
            }
            Collection<? extends Player> players;
            if(action.getSubject() == "ALL"){
                players = Bukkit.getOnlinePlayers();
            }else
                players = Spigot.loadPlayers(action.getSubject());

            action.getMeta().getAsJsonArray("lines")
                    .forEach(line -> players.forEach(player -> player.sendMessage(line.getAsString())));
        } catch (Exception e) {
            System.out.println("An error parsing the CHAT metadata");
            e.printStackTrace();
        }
    }
}

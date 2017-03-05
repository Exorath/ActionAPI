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

package com.exorath.plugin.actionapi;

import com.exorath.service.actionapi.res.Action;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by toonsev on 3/4/2017.
 */
public class Main {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap("asd", false);
        System.out.println("sending update");
        bootstrap.getActionManager().registerHandler(new ActionHandler() {
            @Override
            public String getAction() {
                return "JOIN";
            }

            @Override
            public void onAction(Action action) {
                System.out.println(action.getAction());
            }
        });
        Schedulers.io().schedulePeriodicallyDirect(() -> bootstrap.statusUpdate(new String[0]),3, 15, TimeUnit.SECONDS);

    }
}

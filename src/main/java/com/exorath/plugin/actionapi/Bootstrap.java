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

import com.exorath.service.actionapi.api.ActionAPIServiceAPI;
import com.exorath.service.actionapi.impl.Subscription;
import com.exorath.service.actionapi.res.SubscribeRequest;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by toonsev on 2/27/2017.
 */
public class Bootstrap {
    private ActionManager actionManager;
    private Subscription subscription;
    private String serverId;
    private String type;
    private PublishSubject<SubscribeRequest> subscribeRequestSubject = PublishSubject.create();
    private ActionAPIServiceAPI actionAPIServiceAPI;

    public Bootstrap(String serverId, boolean spigot) {
        this.serverId = serverId;
        this.type = spigot ? "spigot" : "bungee";
        this.actionManager = new ActionManager();
        subscription = new SubscriptionImpl(actionManager, subscribeRequestSubject);
        this.actionAPIServiceAPI = new ActionAPIServiceAPI(getAddress());

        subscribeToActions();
    }

    private void subscribeToActions(){
        subscription.getCompletable().subscribe(() -> subscribeToActions());
        actionAPIServiceAPI.subscribe(subscription);
    }
    public void statusUpdate(String[] players) {
        SubscribeRequest subscribeRequest = new SubscribeRequest(serverId, type, players);
        subscribeRequestSubject.onNext(subscribeRequest);
    }

    private String getAddress(){
        String address = System.getenv("ACTIONAPI_SERVICE_ADDRESS");
        if(address == null) {
            System.out.println("ConnectorPlugin no CONNECTOR_SERVICE_ADDRESS env var defined. Exiting");
            System.exit(1);
        }
        return address;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }
}

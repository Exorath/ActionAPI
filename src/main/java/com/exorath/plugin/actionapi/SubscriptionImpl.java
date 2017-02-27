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

import com.exorath.service.actionapi.impl.Subscription;
import com.exorath.service.actionapi.res.Action;
import com.exorath.service.actionapi.res.SubscribeRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by toonsev on 2/27/2017.
 */
public class SubscriptionImpl implements Subscription {
    private ActionManager actionManager;
    private PublishSubject<SubscribeRequest> requestPublishSubject;

    public SubscriptionImpl(ActionManager actionManager, PublishSubject<SubscribeRequest> requestPublishSubject){
        this.actionManager = actionManager;
        this.requestPublishSubject = requestPublishSubject;
    }
    public void onAction(Action action) {
        actionManager.onAction(action);
    }

    public Observable<SubscribeRequest> getSubscribeRequestStream() {
        return requestPublishSubject;
    }

    public Completable getCompletable() {
        return Completable.fromObservable(requestPublishSubject);
    }

    public void onClose(String reason) {
        requestPublishSubject.onComplete();
    }
}

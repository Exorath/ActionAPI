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

/**
 * Created by toonsev on 2/27/2017.
 */
public interface ActionHandler {
    /**
     * Returns the action type this handler handles
     * @return
     */
    String getAction();


    /**
     * This will be called when an action of this type is received
     * @param action received action
     */
    void onAction(Action action);
}

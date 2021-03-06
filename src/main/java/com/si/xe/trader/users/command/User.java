/*
 * Copyright (c) 2010-2012. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.si.xe.trader.users.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.si.xe.trader.api.users.UserAuthenticatedEvent;
import com.si.xe.trader.api.users.UserCreatedEvent;
import com.si.xe.trader.api.users.UserId;
import com.si.xe.trader.users.util.DigestUtils;

/**
 * @author Jettro Coenradie
 */
public class User extends AbstractAnnotatedAggregateRoot {
    private static final long serialVersionUID = 3291411359839192350L;
    @AggregateIdentifier
    private UserId userId;
    private String passwordHash;

    protected User() {
    }

    public User(UserId userId, String username, String name, String password, String exchangeName) {
        apply(new UserCreatedEvent(userId, name, username, hashOf(password.toCharArray()), exchangeName));
    }

    public boolean authenticate(char[] password) {
        boolean success = this.passwordHash.equals(hashOf(password));
        if (success) {
            apply(new UserAuthenticatedEvent(userId));
        }
        return success;
    }

    @EventHandler
    public void onUserCreated(UserCreatedEvent event) {
        this.userId = event.getUserIdentifier();
        this.passwordHash = event.getPassword();
    }

    private String hashOf(char[] password) {
        return DigestUtils.sha1(String.valueOf(password));
    }

    @Override
    public UserId getIdentifier() {
        return userId;
    }
}

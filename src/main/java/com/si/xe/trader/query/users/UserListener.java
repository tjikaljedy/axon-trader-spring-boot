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

package com.si.xe.trader.query.users;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.si.xe.trader.api.users.UserCreatedEvent;
import com.si.xe.trader.query.users.repositories.UserQueryRepository;

/**
 * @author Jettro Coenradie
 */
@Component
public class UserListener {

    private UserQueryRepository userRepository;

    @EventHandler
    public void handleUserCreated(UserCreatedEvent event) {
        UserEntry userEntry = new UserEntry();
        userEntry.setIdentifier(event.getUserIdentifier().toString());
        userEntry.setName(event.getName());
        userEntry.setUsername(event.getUsername());
        userEntry.setPassword(event.getPassword());
        userEntry.setExchangeName(event.getExchangeName());
        userRepository.save(userEntry);
    }

    @Autowired
    public void setUserRepository(UserQueryRepository userRepository) {
        this.userRepository = userRepository;
    }
}

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

package com.si.xe.trader.instrument.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.si.xe.trader.api.instrument.AddOrderBookToInstrumentCommand;
import com.si.xe.trader.api.instrument.CreateInstrumentCommand;

/**
 * @author Jettro Coenradie
 */
public class InstrumentCommandHandler {

    private Repository<Instrument> repository;

    @CommandHandler
    public void handleCreateInstrument(CreateInstrumentCommand command) {
        Instrument instrument = new Instrument(command.getInstrumentId(),
                command.getInstrumentName(),
                command.getContractMonth(),
                command.getContractRate(),
                command.getExchangeName(),
                command.getMarketMaker());
        repository.add(instrument);
    }

    @CommandHandler
    public void handleAddOrderBook(AddOrderBookToInstrumentCommand command) {
        Instrument instrument = repository.load(command.getInstrumentId());
        instrument.addOrderBook(command.getOrderBookId());
    }

    @Autowired
    @Qualifier("instrumentRepository")
    public void setRepository(Repository<Instrument> instrumentRepository) {
        this.repository = instrumentRepository;
    }
}

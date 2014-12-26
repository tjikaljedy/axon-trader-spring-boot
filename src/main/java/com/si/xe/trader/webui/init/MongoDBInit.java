package com.si.xe.trader.webui.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.saga.repository.mongo.MongoTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.si.xe.trader.query.instrument.InstrumentEntry;
import com.si.xe.trader.query.instrument.repositories.InstrumentQueryRepository;
import com.si.xe.trader.query.orderbook.OrderBookEntry;
import com.si.xe.trader.query.orderbook.OrderEntry;
import com.si.xe.trader.query.orderbook.repositories.OrderBookQueryRepository;
import com.si.xe.trader.query.portfolio.PortfolioEntry;
import com.si.xe.trader.query.portfolio.repositories.PortfolioQueryRepository;
import com.si.xe.trader.query.tradeexecuted.TradeExecutedEntry;
import com.si.xe.trader.query.transaction.TransactionEntry;
import com.si.xe.trader.query.users.UserEntry;

/**
 *
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
@Profile("mongodb")
public class MongoDBInit extends BaseDBInit {
    private final static Logger logger = LoggerFactory.getLogger(MongoDBInit.class);

    private org.axonframework.eventstore.mongo.MongoTemplate systemAxonMongo;
    private MongoEventStore eventStore;
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;
    private MongoTemplate systemAxonSagaMongo;
    private org.springframework.data.mongodb.core.MongoTemplate springTemplate;

    @Autowired
    public MongoDBInit(CommandBus commandBus,
                       InstrumentQueryRepository companyRepository,
                       org.axonframework.eventstore.mongo.MongoTemplate systemMongo,
                       MongoEventStore eventStore,
                       org.springframework.data.mongodb.core.MongoTemplate mongoTemplate,
                       MongoTemplate systemAxonSagaMongo,
                       PortfolioQueryRepository portfolioRepository,
                       OrderBookQueryRepository orderBookRepository, org.springframework.data.mongodb.core.MongoTemplate springTemplate) {
        super(commandBus, companyRepository, portfolioRepository, orderBookRepository);
        this.systemAxonMongo = systemMongo;
        this.eventStore = eventStore;
        this.mongoTemplate = mongoTemplate;
        this.systemAxonSagaMongo = systemAxonSagaMongo;
        this.springTemplate = springTemplate;
    }

    @Override
    public Set<String> obtainCollectionNames() {
        return springTemplate.getCollectionNames();
    }

    @Override
    public DataResults obtainCollection(String collectionName, int numItems, int start) {
        DBCursor dbCursor = springTemplate.getCollection(collectionName).find();
        List<DBObject> dbObjects = dbCursor.skip(start - 1).limit(numItems).toArray();

        List<Map> items = new ArrayList<>(dbCursor.length());
        for (DBObject dbObject : dbObjects) {
            items.add(dbObject.toMap());
        }

        int totalItems = dbCursor.count();

        return new DataResults(totalItems, items);
    }

    @Override
    public void createItemsIfNoUsersExist() {
        if (!mongoTemplate.collectionExists(UserEntry.class)) {
            createItems();
            logger.info("The database has been created and refreshed with some data.");
        }

    }

    @Override
    void initializeDB() {
        systemAxonMongo.domainEventCollection().drop();
        systemAxonMongo.snapshotEventCollection().drop();

        systemAxonSagaMongo.sagaCollection().drop();

        mongoTemplate.dropCollection(UserEntry.class);
        mongoTemplate.dropCollection(OrderBookEntry.class);
        mongoTemplate.dropCollection(OrderEntry.class);
        mongoTemplate.dropCollection(InstrumentEntry.class);
        mongoTemplate.dropCollection(TradeExecutedEntry.class);
        mongoTemplate.dropCollection(PortfolioEntry.class);
        mongoTemplate.dropCollection(TransactionEntry.class);
    }

    @Override
    void additionalDBSteps() {
        eventStore.ensureIndexes();
    }
}

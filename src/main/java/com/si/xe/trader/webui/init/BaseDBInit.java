package com.si.xe.trader.webui.init;

import java.util.List;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;

import com.si.xe.trader.api.instrument.CreateInstrumentCommand;
import com.si.xe.trader.api.instrument.InstumentId;
import com.si.xe.trader.api.orders.trades.OrderBookId;
import com.si.xe.trader.api.orders.trades.PortfolioId;
import com.si.xe.trader.api.portfolio.cash.DepositCashCommand;
import com.si.xe.trader.api.portfolio.stock.AddItemsToPortfolioCommand;
import com.si.xe.trader.api.users.CreateUserCommand;
import com.si.xe.trader.api.users.UserId;
import com.si.xe.trader.query.instrument.InstrumentEntry;
import com.si.xe.trader.query.instrument.repositories.InstrumentQueryRepository;
import com.si.xe.trader.query.orderbook.OrderBookEntry;
import com.si.xe.trader.query.orderbook.repositories.OrderBookQueryRepository;
import com.si.xe.trader.query.portfolio.PortfolioEntry;
import com.si.xe.trader.query.portfolio.repositories.PortfolioQueryRepository;

/**
 * Base class for all DBInit implementations
 */
public abstract class BaseDBInit implements DBInit {
	private CommandBus commandBus;
	private InstrumentQueryRepository companyRepository;
	private PortfolioQueryRepository portfolioRepository;
	private OrderBookQueryRepository orderBookRepository;

	protected BaseDBInit(CommandBus commandBus,
			InstrumentQueryRepository companyRepository,
			PortfolioQueryRepository portfolioRepository,
			OrderBookQueryRepository orderBookRepository) {
		this.commandBus = commandBus;
		this.companyRepository = companyRepository;
		this.portfolioRepository = portfolioRepository;
		this.orderBookRepository = orderBookRepository;
	}

	@Override
	public void createItems() {
		initializeDB();

		UserId buyer1 = createuser("Buyer One", "buyer1");
		UserId buyer2 = createuser("Buyer two", "buyer2");
		// UserId buyer3 = createuser("Buyer three", "buyer3");
		// UserId buyer4 = createuser("Buyer four", "buyer4");
		// UserId buyer5 = createuser("Buyer five", "buyer5");
		// UserId buyer6 = createuser("Buyer six", "buyer6");

		createCompanies(buyer1);

		addMoney(buyer1, 100000);
		addItems(buyer2, "GOLD", 100000);
		// addMoney(buyer3, 100000);
		// addItems(buyer4, "Shell", 10000l);
		// addMoney(buyer5, 100000);
		// addItems(buyer6, "Bp", 10000l);

		additionalDBSteps();
	}

	public void depositMoneyToPortfolio(String portfolioIdentifier,
			long amountOfMoney) {
		DepositCashCommand command = new DepositCashCommand(new PortfolioId(
				portfolioIdentifier), amountOfMoney);
		commandBus.dispatch(new GenericCommandMessage<>(command));
	}

	UserId createuser(String longName, String userName) {
		UserId userId = new UserId();
		CreateUserCommand createUser = new CreateUserCommand(userId, longName,
				userName, userName, "SAKBE");
		commandBus.dispatch(new GenericCommandMessage<>(createUser));
		return userId;
	}

	void createCompanies(UserId userIdentifier) {
		CreateInstrumentCommand command = new CreateInstrumentCommand(
				new InstumentId(), userIdentifier, "GOLD", 1000, 10000,
				"SAKBE", "PEGADAIAN");
		commandBus.dispatch(new GenericCommandMessage<>(command));

		// command = new CreateInstrumentCommand(new InstumentId(),
		// userIdentifier, "Shell", 500, 5000);
		// commandBus.dispatch(new GenericCommandMessage<>(command));

		// command = new CreateInstrumentCommand(new InstumentId(),
		// userIdentifier, "Bp", 15000, 100000);
		// commandBus.dispatch(new GenericCommandMessage<>(command));
	}

	void addMoney(UserId buyer1, long amount) {
		PortfolioEntry portfolioEntry = portfolioRepository
				.findByUserIdentifier(buyer1.toString());
		depositMoneyToPortfolio(portfolioEntry.getIdentifier(), amount);
	}

	void addItems(UserId user, String companyName, long amount) {
		PortfolioEntry portfolioEntry = portfolioRepository
				.findByUserIdentifier(user.toString());
		OrderBookEntry orderBookEntry = obtainOrderBookByCompanyName(companyName);
		AddItemsToPortfolioCommand command = new AddItemsToPortfolioCommand(
				new PortfolioId(portfolioEntry.getIdentifier()),
				new OrderBookId(orderBookEntry.getIdentifier()), amount);
		commandBus.dispatch(new GenericCommandMessage<>(command));
	}

	OrderBookEntry obtainOrderBookByCompanyName(String companyName) {
		Iterable<InstrumentEntry> companyEntries = companyRepository.findAll();
		for (InstrumentEntry entry : companyEntries) {
			if (entry.getName().equals(companyName)) {
				// List<OrderBookEntry> orderBookEntries = orderBookRepository
				// .findByCompanyIdentifier(entry.getIdentifier());
				//
				//return orderBookEntries.get(0);
				return orderBookRepository.findByCompanyIdentifier(entry.getIdentifier());
			}
		}
		throw new DBInitException(
				"Problem initializing, could not find company with required name.");
	}

	abstract void initializeDB();

	abstract void additionalDBSteps();
}

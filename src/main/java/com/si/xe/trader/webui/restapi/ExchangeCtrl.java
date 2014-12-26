package com.si.xe.trader.webui.restapi;

import javax.validation.Valid;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;






/*import com.si.xe.business.CreateExchangeCommand;
 import com.si.xe.business.CreateInstrumentCommand;
 import com.si.xe.business.ExchangeId;
 import com.si.xe.business.InstrumentId;
 import com.si.xe.business.entity.ExchangeEntry;
 import com.si.xe.business.entity.InstrumentEntry;
 import com.si.xe.business.entity.repo.ExchangeRepo;
 import com.si.xe.business.entity.repo.InstrumentRepo;
 import com.si.xe.order.entity.OrderBookEntry;
 import com.si.xe.order.entity.OrderEntry;
 import com.si.xe.order.entity.repo.OrderBookRepo;*/
import com.si.xe.trader.api.instrument.CreateInstrumentCommand;
import com.si.xe.trader.api.instrument.InstumentId;
import com.si.xe.trader.api.orders.trades.OrderBookId;
import com.si.xe.trader.api.orders.trades.PortfolioId;
import com.si.xe.trader.api.orders.trades.TransactionId;
import com.si.xe.trader.api.orders.transaction.StartBuyTransactionCommand;
import com.si.xe.trader.api.orders.transaction.StartSellTransactionCommand;
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
import com.si.xe.trader.query.users.UserEntry;
import com.si.xe.trader.query.users.repositories.UserQueryRepository;
import com.si.xe.trader.webui.init.DBInitException;

@RestController
public class ExchangeCtrl {
	@Autowired
	private UserQueryRepository userRepo;
	@Autowired
	private PortfolioQueryRepository portfolioRepository;
	@Autowired
	private OrderBookQueryRepository orderBookRepository;
	@Autowired
	private InstrumentQueryRepository instrumentRepository;

	@Autowired
	private CommandBus commandBus;

	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public @ResponseBody String checkRestApi() {
		return "BageaXchange 3.0";
	}
	
	@RequestMapping(value = "/api/{exchangename}/user", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createUser(@PathVariable("exchangename") String exchangeName,
			@RequestBody @Valid UserDescriptior request) {
		UserId userId = new UserId();
		CreateUserCommand createUser = new CreateUserCommand(userId,
				request.getLongname(), request.getUsername(),
				request.getUserpass(), exchangeName);
		commandBus.dispatch(new GenericCommandMessage<>(createUser));
	}
	

	@RequestMapping(value = "/api/addmoney", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addMoney(@RequestBody @Valid MoneyDescriptior request) {
		PortfolioEntry portfolioEntry = obtainPortfolioForUser(request
				.getUsername());

		DepositCashCommand command = new DepositCashCommand(new PortfolioId(
				portfolioEntry.getIdentifier()), request.getMoney());
		commandBus.dispatch(new GenericCommandMessage<DepositCashCommand>(
				command));
	}

	@RequestMapping(value = "/api/instrument", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createInstrument(
			@RequestBody @Valid InstrumentDescriptior request) {
		UserEntry userEntry = userRepo.findByUsername(request.getUsername());

		CreateInstrumentCommand command = new CreateInstrumentCommand(
				new InstumentId(), new UserId(userEntry.getIdentifier()),
				request.getSymbol(), request.getContractMonth(),
				request.getContractRate(), request.getExchangeName(),
				request.getMarketMaker());

		commandBus.dispatch(new GenericCommandMessage<CreateInstrumentCommand>(
				command));
	}

	@RequestMapping(value = "/api/additem", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addItemToPortfolio(@RequestBody @Valid ItemDescriptior request) {
		UserEntry userEntry = userRepo.findByUsername(request.getUsername());
		InstrumentEntry instrumentEntry = instrumentRepository
				.findByNameAndExchangeName(request.getSymbol(),
						userEntry.getExchangeName());
		OrderBookEntry orderBookEntry = orderBookRepository
				.findByCompanyIdentifier(instrumentEntry.getIdentifier());

		PortfolioEntry portfolioEntry = obtainPortfolioForUser(request
				.getUsername());

		AddItemsToPortfolioCommand command = new AddItemsToPortfolioCommand(
				new PortfolioId(portfolioEntry.getIdentifier()),
				new OrderBookId(orderBookEntry.getIdentifier()),
				request.getAmount());
		commandBus.dispatch(new GenericCommandMessage<>(command));

		commandBus.dispatch(new GenericCommandMessage<>(command));
	}

	@RequestMapping(value = "/api/placeorder", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void placeOrder(@RequestBody @Valid OrderDescriptior request) {

		UserEntry userEntry = userRepo.findByUsername(request.getUsername());
		InstrumentEntry instrumentEntry = instrumentRepository
				.findByNameAndExchangeName(request.getSymbol(),
						userEntry.getExchangeName());
		OrderBookEntry orderBookEntry = orderBookRepository
				.findByCompanyIdentifier(instrumentEntry.getIdentifier());
		
		PortfolioEntry portfolioEntry = obtainPortfolioForUser(request
				.getUsername());
		
		if (request.getSide().toUpperCase().equals("BUY")) {
			StartBuyTransactionCommand command = new StartBuyTransactionCommand(
					new TransactionId(),
					new OrderBookId(orderBookEntry.getIdentifier()), new PortfolioId(
							portfolioEntry.getIdentifier()), request.getQuantity(),
					request.getPrice());
			commandBus.dispatch(new GenericCommandMessage<>(command));
		} else {
			StartSellTransactionCommand command = new StartSellTransactionCommand(
					new TransactionId(),
					new OrderBookId(orderBookEntry.getIdentifier()), new PortfolioId(
							portfolioEntry.getIdentifier()), request.getQuantity(),
					request.getPrice());
			commandBus.dispatch(new GenericCommandMessage<>(command));
		}
	}
	
	/**
	 * Initial Setup
	 * 
	 */
	@RequestMapping(value = "/api/initial", method = RequestMethod.POST)
	public void initialSetup() {
		UserId buyer1 = createuser("Buyer1", "buyer1");
		UserId marketmaker1 = createuser("MarkerMaker1", "marketmaker1");
		UserId buyer3 = createuser("Buyer3", "buyer3");
		
		createCompanies(marketmaker1);
		addMoney(buyer1, 100000);
		addMoney(marketmaker1, 100000);
		addMoney(buyer3, 100000);
		addItems(marketmaker1, "GOLD", 0);		
	}
	
	private UserId createuser(String longName, String userName) {
		UserId userId = new UserId();
		CreateUserCommand createUser = new CreateUserCommand(userId, longName,
				userName, userName, "SAKBE");
		commandBus.dispatch(new GenericCommandMessage<>(createUser));
		return userId;
	}
	
	private void createCompanies(UserId userIdentifier) {
		CreateInstrumentCommand command = new CreateInstrumentCommand(
				new InstumentId(), userIdentifier, "GOLD", 1000, 10000,
				"SAKBE", "PEGADAIAN");
		commandBus.dispatch(new GenericCommandMessage<>(command));
	}	
	
	private void addMoney(UserId buyer1, long amount) {
		PortfolioEntry portfolioEntry = portfolioRepository
				.findByUserIdentifier(buyer1.toString());
		depositMoneyToPortfolio(portfolioEntry.getIdentifier(), amount);
	}
	
	private void depositMoneyToPortfolio(String portfolioIdentifier,
			long amountOfMoney) {
		DepositCashCommand command = new DepositCashCommand(new PortfolioId(
				portfolioIdentifier), amountOfMoney);
		commandBus.dispatch(new GenericCommandMessage<>(command));
	}
	
	private void addItems(UserId user, String companyName, long amount) {
		PortfolioEntry portfolioEntry = portfolioRepository
				.findByUserIdentifier(user.toString());
		OrderBookEntry orderBookEntry = obtainOrderBookByCompanyName(companyName);
		AddItemsToPortfolioCommand command = new AddItemsToPortfolioCommand(
				new PortfolioId(portfolioEntry.getIdentifier()),
				new OrderBookId(orderBookEntry.getIdentifier()), amount);
		commandBus.dispatch(new GenericCommandMessage<>(command));
	}
	
	private OrderBookEntry obtainOrderBookByCompanyName(String companyName) {
		Iterable<InstrumentEntry> companyEntries = instrumentRepository.findAll();
		for (InstrumentEntry entry : companyEntries) {
			if (entry.getName().equals(companyName)) {
				return orderBookRepository.findByCompanyIdentifier(entry.getIdentifier());
			}
		}
		throw new DBInitException(
				"Problem initializing, could not find company with required name.");
	}

	private PortfolioEntry obtainPortfolioForUser(String username) {
		UserEntry username1 = userRepo.findByUsername(username);
		return portfolioRepository.findByUserIdentifier(username1.getIdentifier());
	}

}

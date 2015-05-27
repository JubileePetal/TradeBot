package tests;

import static org.junit.Assert.*;
import models.DataHolder;
import models.Instrument;
import models.OpCodes;
import models.Order;

import org.junit.Before;
import org.junit.Test;

import control.Bot;
import control.Controller;

public class BotTest {
	private DataHolder dataHolder;
	private Bot bot;
	
	@Before
	public void setUp(){
		dataHolder = new DataHolder();
		bot = new Bot(dataHolder, new Controller());
		
	}
	
	@Test
	public void testRandInt() {
		
		for(int i = 0; i < 100; i ++){
			
//			int rand	= bot.randInt(0, 1,);
//			assertTrue((rand <= 1));
//			assertTrue((rand >= 0));
//			
//			rand		= bot.randInt(-2, 2);
//			assertTrue((rand < 3));
			
		}

	
		
	}
	
	
	@Test
	public void testRandomOrderTest(){
		
		int random = bot.getRandomOrderType();
		assertTrue((random == OpCodes.BUY_ORDER || random == OpCodes.SELL_ORDER));
		
	}
	
	@Test
	public void testGenerateRandomPrice(){
		
		double topPrice = 10.0;
		
//		double newPrice  = bot.generateRandomPrice(topPrice);
//		
//		System.out.println(newPrice);
//		assertTrue((newPrice >= 8.0 && newPrice <= 12.0));
	}
	
	@Test
	public void testGenerateNewOrder(){
		
		double price 	= 10.0;
		Instrument inst = new Instrument();
		Order order 	= bot.generateNewOrder(price, OpCodes.SELL_ORDER, inst);
		
		assertNotNull(order);
		assertTrue(order.getQuantity() <= 100);
		assertTrue(order.getQuantity() > 0);
	}
	
	
	
}

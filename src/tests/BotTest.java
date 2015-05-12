package tests;

import static org.junit.Assert.*;
import models.DataHolder;
import models.OpCodes;

import org.junit.Before;
import org.junit.Test;

import control.Bot;

public class BotTest {
	private DataHolder dataHolder;
	private Bot bot;
	
	@Before
	public void setUp(){
		dataHolder = new DataHolder();
		bot = new Bot(dataHolder);
		
	}
	
	
	@Test
	public void testRandInt() {
		
		int rand	= bot.randInt(0, 1);
		assertTrue((rand <= 1));
		assertTrue((rand >= 0));
		
		rand		= bot.randInt(0, 10);
		assertTrue((rand < 10));
	
		
	}
	
	
	@Test
	public void testRandomOrderTest(){
		
		int random = bot.getRandomOrderType();
		assertTrue((random == OpCodes.BUY_ORDER || random == OpCodes.SELL_ORDER));
		
	}
	
	
	
	
}

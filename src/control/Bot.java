package control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import models.DataHolder;
import models.Instrument;
import models.InstrumentState;
import models.OpCodes;
import models.Order;

public class Bot implements Runnable{

	private static double DEFAULT_PRICE = 1100.0;
	private DataHolder dataHolder;
	private Controller controller;
	private ArrayList<String> instrumentNames;
	private int nrOfInstruments;
	private static Random intSeed;
	private static Random doubleSeed;
	
	public Bot(DataHolder dataHolder, Controller controller) {
		
		this.dataHolder = dataHolder;
		this.controller = controller;
		instrumentNames = dataHolder.getInstrumentNames();
		nrOfInstruments = instrumentNames.size();
		intSeed 		= new Random();
		doubleSeed 		= new Random();
	}	

	@Override
	public void run() {
		
		int counter = 1000;
		while(counter > 0){
			int randomIndex 		= 0; /** randInt(0,nrOfInstruments-1); */
			String instrumentName 	= instrumentNames.get(randomIndex);
			InstrumentState inst	= dataHolder.getInstrumentState(instrumentName);
			
	
		
			
				int randomType			= getRandomOrderType();
				double topPrice			= DEFAULT_PRICE;
				
				
				/** If the instrument has available market data*/
				if(!inst.getMarketData().isEmpty()){
					
					switch (randomType) {
					case OpCodes.BUY_ORDER:  topPrice 	= inst.getBuyTopLevel(); 
						break;
					case OpCodes.SELL_ORDER: topPrice	= inst.getSellTopLevel(); 
						break;
					default:
						break;
					}
	
				}
				
			double randomPrice = generateRandomPrice(topPrice, randomType);	
			Order order = generateNewOrder(randomPrice, randomType, 
					dataHolder.getInstrument(instrumentName));
			
			controller.sendOrder(order);
			counter --;
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//while
		
	}

	
	
	
	public double generateRandomPrice(double topPrice, int type) {
		
		double newPrice = 100.0;

		if(type == OpCodes.BUY_ORDER){
			

			
			newPrice = randomPrice((topPrice - 20), (topPrice + 10));
			if(newPrice <=  0){
				
				newPrice = topPrice;
			}
			
			
		}else{
		

			newPrice = randomPrice((topPrice - 10), (topPrice + 20));
			if(newPrice <= 0){
				
				newPrice = topPrice;
				
			}
		}
		
		
		return newPrice;
	}

	public Order generateNewOrder(double randomPrice, int type, Instrument inst) {
		
		int randomQuantity		= randInt(10,50);
		
		Order order = new Order();
		order.setPrice(randomPrice);
		order.setOrderQuantity(randomQuantity);
		order.setInstrument(inst);
		order.setOrderOwner(dataHolder.getNickName());
		order.setTypeOfOrder(type);
		
		if(type == OpCodes.SELL_ORDER){
			
			order.setToSellOrder();
		}else{
			
			order.setToBuyOrder();
		}
		
		return order;
	}

	public int getRandomOrderType() {
		
		int rand = randInt(0,1);
		
		if(rand == 1){
			
			return OpCodes.BUY_ORDER;
			
		}else{
			
			return OpCodes.SELL_ORDER;
		}
		
	
	}

	public double randomPrice(double min, double max){
		
		double randomValue = min + (max - min) * doubleSeed.nextDouble();
	
		double newValue = Math.round( randomValue * 100.0 ) / 100.0;
		
		double finalValue = round(newValue);
		
		return finalValue;
	}
	
	
	public static double round(double d){
	    return 0.5 * Math.round(d * 2);
	}
	
	public static int randInt(int min, int max) {

	    int randomNum = intSeed.nextInt((max - min) + 1) + min;

	    return randomNum;
	}


}

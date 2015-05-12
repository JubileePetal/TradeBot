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

public class Bot implements Runnable{

	private static double DEFAULT_PRICE = 10.0;
	private DataHolder dataHolder;
	private ArrayList<String> instrumentNames;
	private int nrOfInstruments;
	private static Random seed;
	public Bot(DataHolder dataHolder) {
		
		this.dataHolder = dataHolder;
		instrumentNames = dataHolder.getInstrumentNames();
		nrOfInstruments = instrumentNames.size();
		seed = new Random();
	}	

	@Override
	public void run() {
		
		
		
		//Randomize instrument
		int randomIndex 		= randInt(0,nrOfInstruments-1);
		String instrument 		= instrumentNames.get(randomIndex);
		InstrumentState inst	= dataHolder.getInstrumentState(instrument);
		

	
		
			int randomType			= getRandomOrderType();
			double topPrice;
			
			
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
			}else{
				
				topPrice = DEFAULT_PRICE;
			}
			
			
		
		//When toplevel changes change current instrument price
		//Randomize buy/sell
		//Randomize price
		//Send order
		
	}
	

	
	
	
	public int getRandomOrderType() {
		
		int rand = randInt(0,1);
		
		if(rand == 1){
			
			return OpCodes.BUY_ORDER;
			
		}else{
			
			return OpCodes.SELL_ORDER;
		}
		
	
	}

	public static int randInt(int min, int max) {

	    int randomNum = seed.nextInt((max - min) + 1) + min;

	    return randomNum;
	}


}

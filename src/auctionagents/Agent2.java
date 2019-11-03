package auctionagents;

import java.util.ArrayList;

import auctionframework.AbstractAgent;
import auctionframework.Auction;
import auctionframework.AuctionItem;

/**
 *
 * @author 
 */
public class Agent2  extends AbstractAgent {

    /**
     * Default constructor of the second agent.
     * 
     * @param name          Generated name of the agent.
     * @param auction       The auction the agent participates in.
     * @param money         The money the agent has.
     */
    public Agent2(String name, Auction auction, int money) {
        super(name, auction, money);
    }
    
    /**
     * The return value of this function indicates if the agent bids for the 
     * actual item in the auction.
     * 
     * @param item          The item to bid for
     * @return              True if the agent bids for the current price.
     */
    public boolean ask(AuctionItem item) {
    	ArrayList<AbstractAgent> agents = auction.getParticipants();
    	//ArrayList<AuctionItem> items = auction.getItems();
    	int difference = item.getPrice() - item.getStartingPrice();
    	int gross = this.getMoney();
    	for (AuctionItem myItem : this.getItems()) {
			gross += myItem.getPrice();
		}
    	
    	if(item.getPrice() == item.getStartingPrice()) return true;
    	if(item.getPrice() == item.getStartingPrice() *2) return false;
    	
    	Integer min = null, max = null;
    	for (AbstractAgent agent : agents) {
    		int value = agent.getMoney();
    		for (AuctionItem aitem : agent.getItems()) {
				value += aitem.getStartingPrice();
			}
    		if (min == null || max == null) min = max = value;
    		else if(value < min) min = value;
    		else if(value > max) max = value;
		}
    	if(gross - difference < min) return false;
    	else if(gross + difference > max) return true;
    	else return true;
    }
}

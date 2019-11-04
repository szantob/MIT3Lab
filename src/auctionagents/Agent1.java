package auctionagents;

import auctionframework.AbstractAgent;
import auctionframework.Auction;
import auctionframework.AuctionItem;

/**
 *
 * @author 
 */
public class Agent1 extends AbstractAgent {

    /**
     * Default constructor of the first agent.
     * 
     * @param name          Generated name of the agent.
     * @param auction       The auction the agent participates in.
     * @param money         The money the agent has.
     */
    public Agent1(String name, Auction auction, int money) {
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
    	// 
    	if(item.getPrice() == item.getStartingPrice()) return true;
    	
    	// Calculate this Agents total asset value
    	int myTotalAssets = getMoney() + getTotalStartingPrice();
    	
    	// Calculate min and max total asset value of others
    	Integer min = null, max = null;
    	for (AbstractAgent agent : auction.getParticipants()) {
    		int value = agent.getMoney() + agent.getTotalStartingPrice();
    		if (min == null || max == null) min = max = value;
    		else if(value < min) min = value;
    		else if(value > max) max = value;
		}
    	
    	// Choose strategy by compare to others
    	int difference = item.getPrice() - item.getStartingPrice();
    	if(myTotalAssets - difference < min) return false;
    	else return true;
    }
}

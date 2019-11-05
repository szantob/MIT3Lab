package auctionagents;

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
    	// Trivial case
    	if(item.getPrice() < 1.05 * item.getStartingPrice()) return true;
    	
    	// Calculate min and max total asset value of others
    	Integer min = null, max = null;
    	for (AbstractAgent agent : auction.getParticipants()) {
    		int value = agent.getMoney() + agent.getTotalStartingPrice();
    		if (min == null || max == null) min = max = value;
    		else if(value < min) min = value;
    		else if(value > max) max = value;
		}
    	
    	// Calculate price difference
    	int difference = item.getPrice() - item.getStartingPrice();
    	
    	// Bid if Agent1 would bid
    	if(max - difference > min) return true;
    	
    	return false;
    }
}

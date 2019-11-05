package auctionagents;

import auctionframework.AbstractAgent;
import auctionframework.Auction;
import auctionframework.AuctionItem;

/**
 *
 * @author 
 */
public class Agent3  extends AbstractAgent {

    /**
     * Default constructor of the third agent.
     * 
     * @param name          Generated name of the agent.
     * @param auction       The auction the agent participates in.
     * @param money         The money the agent has.
     */
    public Agent3(String name, Auction auction, int money) {
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
    	if(item.getPrice() == item.getStartingPrice()) return true;
    	
    	// Price ceiling for a bid
    	if(item.getPrice() == item.getStartingPrice() * 1.06) return false;
    	
    	// Calculate total value of items
    	int totalValue = 0;
    	for (AuctionItem auItem : auction.getItems()) {
			totalValue += auItem.getPrice();
		}
    	
    	// Calculate money left of all participants
    	int moneyLeft = 0;
    	for (AbstractAgent agent : auction.getParticipants()) {
			moneyLeft += agent.getMoney();
		}
    	
    	// Calculate overprice factor based of money left of all participants and total value of items
    	double overpriceFactor = moneyLeft / totalValue ;
    	
    	// Bid if overprice is not bigger than the overprice factor 
    	if(item.getPrice() < item.getStartingPrice() * overpriceFactor) return true;
    	return false;
    }
}

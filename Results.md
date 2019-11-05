**Agent1:**

    public boolean ask(AuctionItem item) {
    	// Trivial case
    	if(item.getPrice() == item.getStartingPrice()) return true;
    	
    	// Calculate this Agents total asset value
    	int myTotalAssets = getMoney() + getTotalStartingPrice();
    	
    	// Calculate min total asset value of others
    	Integer min = null;
    	for (AbstractAgent agent : auction.getParticipants()) {
    		int value = agent.getMoney() + agent.getTotalStartingPrice();
    		if (min == null) min = value;
    		else if(value < min) min = value;
		}
    	
    	// Calculate price difference
    	int difference = item.getPrice() - item.getStartingPrice();
    	
    	// Bid if not the worst 
    	if(myTotalAssets - difference < min) return false;
    	else return true;
    }

**Agent2:**

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

**Agent3:**

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


## Agent1:

**Stratégia működése:**
Az ágens a többi ágens viselkedését figyeli, azokkal nagyjából azonos stratégiát próbál követni, úgy, hogy arra törekszik, hogy ne ő legyen a legrosszabb vagyoni helyzetben az árverés után.

**Forráskód:**
```java
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
```
**Értékelés:**
Az ágensek egymás eredményeire figyelve nagyjából azonos eredménnyel zártak. Mivel egyiknek sem volt célja a másikat túlzott mértékben túllicitálni, a hatékonyság 100%-os volt, így kevesebb erőforrás-töblettel rendelkező környezetben is jól teljesítettek (pl.: 4 x 0.25 esetén csak az utolsó nem kelt el).
A stratégia minden beállítás mellett minden résztvevő közel 100%-os hatékonysághoz vezet amennyiben minden résztvevőnek ez a célja. Hibája, hogy mivel nagyban hagyatkozik a többiek stratégiájára, könnyen befolyásolható, és rávehető egy rossz stratégia követésére.

## Agent2:

**Stratégia működése:**
Az Agent1-ek közel 100%-os hatásfoka után az Agent2 esetében a hatásfokjavítás nem volt opció, így két stratégia maradt. Vagy el kell nyerni az Agent1-ek elől az aukciókat, vagy rá kell venni őket, hogy nagyon rossz hatásfokkal működjenek.
Az Agent2 mindig úgy licitál, hogy a legtöbb vagyonnal rendelkező ágens még pont hajlandó legyen megvenni, ha Agent1 stratégiát követ. Így az adott körben vagy egy Agent1 nyer, a legnagyobb veszteséggel, amire még hajlandó, vagy egy Agent2. Tehát az Agent1 ágánsek vagy vesztenek, vagy az álltaluk legkisebb elfogadható hatásfokon nyernek.

**Forráskód:**
```java
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
```
**Értékelés:**
A stratégia miatt a hatásfok értelemszerűen csökkent, az Agent1-ek  hatásfoka 87-88%-ra esett, az Agent2-ké 92-93% körül mozgott. Az elnyert vagyon közel kétszerese lett az Agent1-ek által elnyertnek. A stratégia hátránya, hogy gyorsabban fogyasztja az erőforrásokat, így szűkösebb körülmények között, miután az Agent2-k erőforrásai elfogytak, az Agent1-ek be tudják hozni lemaradásukat.

## Agent3:

**Stratégia működése:**

**Forráskód:**
```java
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
```
**Értékelés:**

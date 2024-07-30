import java.util.List;
import java.util.ArrayList;

// Subject Interface
interface Stock {
    abstract void registerObserver(Observer observer);
    abstract void deregisterObserver(Observer observer);
    abstract void notifyObservers();
}

// Concrete Subject
class StockMarket implements Stock {
    private List<Observer> observers;
    private double stockPrice;

    public StockMarket() {
        this.observers = new ArrayList<>();
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(stockPrice);
        }
    }
}

// Observer Interface
interface Observer {
    abstract void update(double stockPrice);
}

// Concrete Observers

class MobileApp implements Observer {
    private double stockPrice;

    @Override
    public void update(double stockPrice){
        this.stockPrice = stockPrice;
        displayStockPrice();
    }

    public void displayStockPrice(){
        System.out.println("Mobile App");
        System.out.println("Stock Price: "+stockPrice);
        System.out.println();
    }
}

class WebApp implements Observer {
    private double stockPrice;

    @Override
    public void update(double stockPrice){
        this.stockPrice = stockPrice;
        displayStockPrice();
    }

    public void displayStockPrice(){
        System.out.println("Web App");
        System.out.println("Stock Price: "+stockPrice);
        System.out.println();
    }
}


public class ObserverPattern {
    public static void main(String[] args) {

        StockMarket sm =new StockMarket();

        MobileApp mobileApp = new MobileApp();
        WebApp webApp = new WebApp();

        sm.registerObserver(mobileApp);
        sm.registerObserver(webApp);

        sm.setStockPrice(2500.49);
        sm.setStockPrice(1250.69);
        sm.setStockPrice(140.34);

    }
}

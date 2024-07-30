// Component Interface
interface Notifier {
    abstract void send();
}

// Concrete Component
class EmailNotifier implements Notifier {

    @Override
    public void send(){
        System.out.println("Sending Email Notification");
    }
}

// Decorator Class
abstract class NotifierDecorator implements Notifier {
    protected Notifier notifier;

    public NotifierDecorator(Notifier notifier){
        this.notifier = notifier;
    }

    @Override
    public void send() {
        this.notifier.send();
    }

}

// Concrete Decorators

class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send() {
        System.out.println("Sending SMS Notification");
    }
}

class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier){
        super(notifier);
    }

    @Override
    public void send() {
        System.out.println("Sending Slack Notification");
    }
}

public class DecoratorPattern {
    public static void main(String[] args) {
        Notifier notifier1 = new SMSNotifierDecorator(new EmailNotifier());

        Notifier notifier2 = new SlackNotifierDecorator(new EmailNotifier());

        Notifier notifier3 = new EmailNotifier();

        notifier1.send();
        notifier2.send();
        notifier3.send();
    }
}

// Subject Interface
interface Image {
    abstract void display();
}

// Real Subject
class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
    }

    public void loadImage(){
        System.out.println("Loading image from Remote Server...");
    }

    @Override
    public void display() {
        System.out.println("Displaying Image : " + fileName);
    }
}

// Proxy Class
class ProxyImage implements Image {
    private String fileName;
    private RealImage realImage;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    public void loadImage() {
        if(realImage == null){
            realImage = new RealImage(fileName);
            realImage.loadImage();
            cacheImage();
        }
    }

    public void cacheImage(){
        System.out.println("Caching Image : " + fileName);
    }

    @Override
    public void display() {
        loadImage();
        realImage.display();
    }
}

public class ProxyPattern {
    public static void main(String[] args) {
        Image proxyImage = new ProxyImage("profile.png");

        System.out.println("First Display: ");
        proxyImage.display();
        System.out.println("\n\nSecond Display: ");
        proxyImage.display();

    }
}

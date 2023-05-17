import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Observer {
    void update();
}

interface Egg {
    String decorate();
}

class QuailEgg implements Egg {
    @Override
    public String decorate(){
        return "Quail Egg";
    }
}

class ChickenEgg implements Egg {
    @Override
    public String decorate(){
        return "Chicken Egg";
    }
}

class DinoEgg implements Egg {
    @Override
    public String decorate(){
        return "Dino Egg";
    }
}

abstract class EggDecorator implements Egg{
    protected Egg decoratedEgg;

    public EggDecorator(Egg decoratedEgg){
        this.decoratedEgg = decoratedEgg;
    }

    @Override
    public String decorate(){
        return decoratedEgg.decorate();
    }
}

class ChocolateDecorator extends EggDecorator {
    public ChocolateDecorator(Egg decoratedEgg){
        super(decoratedEgg);
    }

    @Override
    public String decorate(){
        return super.decorate() + ", Decorated with Chocolate";
    }
}

class GlitterDecorator extends EggDecorator {
    public GlitterDecorator(Egg decoratedEgg){
        super(decoratedEgg);
    }

    @Override
    public String decorate(){
        return super.decorate() + ", Decorated with Glitter";
    }
}

class GoodEggObserver implements Observer {
    @Override
    public void update(){
        Random random = new Random();
        boolean isGood = random.nextBoolean();

        if(!isGood){
            System.out.println("Bad egg detected and thrown away!");
        }
    }
}

class DecoratedEggsObserver implements Observer {
    private List<String> decoratedEggs;

    public DecoratedEggsObserver() {
        decoratedEggs = new ArrayList<>();
    }

    @Override
    public void update() {
        EasterBunnyFactory factory = EasterBunnyFactory.getInstance();
        decoratedEggs = factory.getDecoratedEggs();
        System.out.println("Total decorated eggs: " + decoratedEggs.size());
    }
}

class FiveEggsObserver implements Observer {
    @Override
    public void update(){
        EasterBunnyFactory factory = EasterBunnyFactory.getInstance();
        int decoratedEggsCount = factory.getDecoratedEggs().size();

        if(decoratedEggsCount == 5){
            System.out.println("Five eggs have been decorated!");
            factory.startDecorating();
        }
    }
}

public class EasterBunnyFactory {
    private static EasterBunnyFactory instance;
    private static final int MAX_DECORATED_EGGS = 5;

    private int decoratedEggsCount;
    private List<String> decoratedEggs;
    private List <Observer> observers;
    private boolean isDecorating;

    private EasterBunnyFactory(){
        decoratedEggsCount = 0;
        decoratedEggs = new ArrayList<>();
        observers = new ArrayList<>();
        isDecorating = false;
    }

    public static EasterBunnyFactory getInstance(){
        if(instance == null){
            synchronized (EasterBunnyFactory.class){
                if(instance == null){
                    instance = new EasterBunnyFactory();
                }
            }
        }
        return instance;
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for (Observer observer : observers){
            observer.update();
        }
    }

    public void decorateEgg(){
        if(decoratedEggsCount < MAX_DECORATED_EGGS && isDecorating){
            decoratedEggsCount++;

            Random random = new Random();
            Egg basicEgg;

            int eggType = random.nextInt(3);
            if(eggType == 0){
                basicEgg = new QuailEgg();
            }
            else if (eggType == 1) {
                basicEgg = new ChickenEgg();
            }
            else {
                basicEgg = new DinoEgg();
            }

            if (random.nextBoolean()){
                basicEgg = new ChocolateDecorator(basicEgg);
            }
            if(random.nextBoolean()){
                basicEgg = new GlitterDecorator(basicEgg);
            }

            String decoratedEgg = basicEgg.decorate();
            decoratedEggs.add(decoratedEgg);
            System.out.println("Egg decorated: " + decoratedEgg);

            notifyObservers();

            if (decoratedEggsCount == MAX_DECORATED_EGGS){
                System.out.println("The eggs are ready! - shouted the little chicken");
                stopDecorating();
            }
        }
    }

    public List<String> getDecoratedEggs(){
        return decoratedEggs;
    }

    public void startDecorating(){
        isDecorating = true;
    }

    public void stopDecorating(){
        isDecorating = false;
    }
}

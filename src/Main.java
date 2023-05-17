public class Main {
    public static void main(String[] args) {
        EasterBunnyFactory factory = EasterBunnyFactory.getInstance();

        GoodEggObserver goodEggObserver = new GoodEggObserver();
        DecoratedEggsObserver decoratedEggsObserver = new DecoratedEggsObserver();
        FiveEggsObserver fiveEggsObserver = new FiveEggsObserver();

        factory.addObserver(goodEggObserver);
        factory.addObserver(decoratedEggsObserver);
        factory.addObserver(fiveEggsObserver);

        factory.startDecorating();
        factory.decorateEgg();
        factory.decorateEgg();
        factory.decorateEgg();
        factory.decorateEgg();
        factory.decorateEgg();
        factory.decorateEgg();

        factory.removeObserver(goodEggObserver);
        factory.removeObserver(decoratedEggsObserver);
        factory.removeObserver(fiveEggsObserver);
    }
}

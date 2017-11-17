package gui.pizza;

public class Configuration
{
    private String[] sizeNames;

    private int[] sizePrices;

    private String[] toppingNames;

    private int[] toppingPrices;

    private int numberOfDefaultToppings;

    public Configuration(String[] sizeNames, int[] sizePrices, String[] toppingNames, int[] toppingPrices, int numberOfDefaultToppings)
    {
        if (sizeNames.length == sizePrices.length && toppingNames.length == toppingPrices.length && numberOfDefaultToppings <= toppingNames.length)
        {
            this.sizeNames = sizeNames;
            this.sizePrices = sizePrices;
            this.toppingNames = toppingNames;
            this.toppingPrices = toppingPrices;
            this.numberOfDefaultToppings = numberOfDefaultToppings;
        }
        else
        {
            throw new IllegalArgumentException("Number of Prices don't match with number of size or toppings or Number of default toppings exceed the number of toppings");
        }
    }

    public String[] getSizeNames()
    {
        return sizeNames;
    }

    public int[] getSizePrices()
    {
        return sizePrices;
    }

    public String[] getToppingNames()
    {
        return toppingNames;
    }

    public int[] getToppingPrices()
    {
        return toppingPrices;
    }

    public int getNumberOfDefaultToppings()
    {
        return numberOfDefaultToppings;
    }

}

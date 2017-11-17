package gui.pizza;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Pizza extends Application
{
    private final ToggleGroup size = new ToggleGroup();

    private Configuration config;

    private CheckBox[] toppings;

    private RadioButton[] pizzaSizes;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.config = ParameterConverter.createConfiguration(getParameters().getNamed());

        VBox layout = new VBox();
        this.toppings = configureToppings();
        this.pizzaSizes = configurePizzaSizes();
        GridPane toppingsLayout = configureToppingsLayout();
        HBox sizeLayout = configureSizeLayout();
        Button btnOrder = new Button("Bestellen!");
        TextArea textDisplay = new TextArea();

        textDisplay.setId("bestelltext");
        btnOrder.setOnAction(e -> orderPizza(textDisplay));

        textDisplay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        textDisplay.setEditable(false);
        VBox.setVgrow(textDisplay, Priority.ALWAYS);
        layout.setPadding(new Insets(20, 10, 20, 10));
        layout.setSpacing(10);

        layout.getChildren().addAll(toppingsLayout, sizeLayout, btnOrder, textDisplay);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pizza");
        primaryStage.show();
    }

    private CheckBox[] configureToppings()
    {
        toppings = new CheckBox[config.getToppingNames().length];

        for (int i = 0; i < toppings.length; i++)
        {
            toppings[i] = new CheckBox(config.getToppingNames()[i]);
        }

        for (int i = 0; i < config.getNumberOfDefaultToppings(); i++)
        {
            toppings[i].setSelected(true);
            toppings[i].setDisable(true);
        }

        return toppings;
    }

    private GridPane configureToppingsLayout()
    {
        GridPane layout = new GridPane();

        for (int i = 0; i < toppings.length; i++)
        {
            if (i % 2 == 0)
            {
                layout.addColumn(0, toppings[i]);
            }
            else
            {
                layout.addColumn(1, toppings[i]);
            }

        }

        layout.setHgap(10);
        layout.setVgap(5);

        return layout;
    }

    private RadioButton[] configurePizzaSizes()
    {
        pizzaSizes = new RadioButton[config.getSizeNames().length];

        for (int i = 0; i < config.getSizeNames().length; i++)
        {
            pizzaSizes[i] = new RadioButton(config.getSizeNames()[i]);
            pizzaSizes[i].setToggleGroup(this.size);
        }

        pizzaSizes[0].setSelected(true);

        return pizzaSizes;
    }

    private HBox configureSizeLayout()
    {
        HBox layout = new HBox();

        layout.getChildren().addAll(pizzaSizes);
        layout.setSpacing(20);

        return layout;
    }

    private void orderPizza(TextArea textDisplay)
    {
        StringBuffer orderText = new StringBuffer("Sie haben eine Pizza bestellt.");
        float price = 0;

        // Zutaten
        orderText.append("\nZutaten: ");
        for (int i = 0; i < toppings.length; i++)
        {
            if (toppings[i].isSelected())
            {
                price += config.getToppingPrices()[i];
                orderText.append(toppings[i].getText() + ", ");
            }
        }

        orderText.setLength(orderText.length() - 2);

        // Größe
        for (int i = 0; i < pizzaSizes.length; i++)
        {
            if (pizzaSizes[i].isSelected())
            {
                price += config.getSizePrices()[i];
                orderText.append("\nDie Größe ist: " + pizzaSizes[i].getText());
            }
        }

        String priceText = String.format("%.2f", price / 100);

        orderText.append("\nDer Preis beträgt: " + priceText + " €\nVielen Dank.");

        textDisplay.setText(orderText.toString());
    }

}

package pojo;

import java.util.Arrays;

public class CreateOrdersJson {

    private final String[] ingredients;

    public CreateOrdersJson(String[] ingredients) {
        this.ingredients = ingredients;
    }

//    public CreateOrdersJson() {
//    }

    @Override
    public String toString() {
        return "ingredients: " + Arrays.toString(ingredients);
    }
}

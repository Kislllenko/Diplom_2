package org.example.pojo;

import java.util.List;

public class CreateOrdersJson {

    private List<String> ingredients;

    public CreateOrdersJson(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}

package site.nomoreparties.stellarburgers.order;

import java.util.List;

public class OrderFields {

    private List<String> ingredients;

    public OrderFields(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderFields() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "OrderFields{" +
                "ingredients=" + ingredients +
                '}';
    }
}

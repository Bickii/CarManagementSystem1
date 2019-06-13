package model;



import model.enums.Color;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Car {
    private String model;
    private BigDecimal price;
    private Color color;
    private int mileage;
    private Set<String> components;

    public Car(CarBuilder carBuilder) {
        this.model = carBuilder.model;
        this.price = carBuilder.price;
        this.color = carBuilder.color;
        this.mileage = carBuilder.mileage;
        this.components = carBuilder.components;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Set<String> getComponents() {
        return components;
    }

    public void setComponents(Set<String> components) {
        this.components = components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(model, car.model) &&
                Objects.equals(price, car.price) &&
                color == car.color &&
                Objects.equals(mileage, car.mileage) &&
                Objects.equals(components, car.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, color, mileage, components);
    }

    @Override
    public String toString() {
        return model + ", " + price + ", " + color + ", " + mileage + ", " + components.stream().collect(Collectors.joining(", "));
    }

    public static CarBuilder builder() { return new CarBuilder(); }

    public static class CarBuilder {
        private String model;
        private BigDecimal price;
        private Color color;
        private int mileage;
        private Set<String> components;

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }
        public CarBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        public CarBuilder color(Color color) {
            this.color = color;
            return this;
        }
        public CarBuilder mileage(int mileage) {
            this.mileage = mileage;
            return this;
        }
        public CarBuilder components(Set<String> components) {
            this.components = components;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}

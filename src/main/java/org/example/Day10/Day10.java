package org.example.Day10;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

public class Day10 {
    public record Product(String name, int price) {}
    public record Region(String name) {}
    public record Sale(Region region, Product product, int amount) {}

    Product car = new Product("car", 500);
    Product bike = new Product("bike", 100);
    Product boat = new Product("boat", 600);

    Region paris = new Region("Paris");
    Region london = new Region("London");
    Region cologne = new Region("Cologne");

    Sale sale1 = new Sale(paris, car, 20);
    Sale sale2 = new Sale(paris, bike, 30);
    Sale sale3 = new Sale(london, boat, 10);
    Sale sale4 = new Sale(london, car, 30);
    Sale sale5 = new Sale(cologne, bike, 40);
    Sale sale6 = new Sale(cologne, car, 10);
    Sale sale7 = new Sale(cologne, boat, 10);
    Sale sale8 = new Sale(paris, car, 30);
    Sale sale9 = new Sale(london, boat, 5);
    Sale sale10 = new Sale(cologne, bike, 20);

    List<Sale> saleRecord = List.of(sale1, sale2, sale3, sale4, sale5, sale6, sale7, sale8, sale9, sale10);

    Map<Region, Map<Product, Integer>> RegionProductSale = saleRecord.stream()
            .collect(Collectors.groupingBy(sale -> sale.region() , Collectors.groupingBy(Sale::product, summingInt(Sale::amount)) ));
    public void getMap() {
        System.out.println(RegionProductSale);
    }

}

package Model;

public class Product {
    private String name;
    private Float price;
    private String link;

    public Product(String name, Float price, String link){
        this.name = name;
        this.price = price;
        this.link = link;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(Float price){
        this.price = price;
    }

    public void setLink(String link){this.link = link; }

    public String getName(){
        return name;
    }

    public Float getPrice(){
        return price;
    }

    public String getLink(){return link; }
}

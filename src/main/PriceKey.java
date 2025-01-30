package main;

public class PriceKey extends Key {
    private StringKey name;
    private Float price;

    public PriceKey() {
        super();
        this.name = null;
    }

    public PriceKey(String name, Float price) {
        super();
        this.name = new StringKey(name);
        this.price = price;
    }

    public PriceKey(StringKey name, Float price, boolean is_inf) {
        super();
        if (is_inf)
            name.setInf(true);
        else
            name.setMinusInf(true);
        this.name = name;
        this.price = price;
    }

    public void initializeTree(StringKey left, StringKey right) {
        super.initializeTree(left, right);
    }

    public String getName(){
        return this.name.getName();
    }

    public void updatePrice(Float price) {
        this.price += price;
    }

    /*
    @Override
    public int compareTo(Key o) {
        if (!(o instanceof PriceKey)) {
            return 0;
        }
        PriceKey other = (PriceKey) o;
        if (super.compareTo(other) != 0){
            return super.compareTo(other);
        }else{
            int val = this.price.compareTo(other.price);
            if (val == 0){
                return this.name.compareTo(other.name);
            }
            return val;
        }
    }
     */

    @Override
    public int compareTo(Key o) {
        if (!(o instanceof PriceKey)) {
            return 0;
        }
        PriceKey other = (PriceKey) o;

        if (super.compareTo(other) != 0){
            return super.compareTo(other);
        } else {
            float epsilon = 0.01f;
            if (Math.abs(this.price - other.price) < epsilon) {
                return this.name.compareTo(other.name);
            }
            return Float.compare(this.price, other.price);
        }
    }
}

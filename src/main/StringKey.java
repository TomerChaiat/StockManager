package main;

public class StringKey extends Key implements Comparable<Key> {
    private String name;

    public StringKey() {
        super();
        this.name = null;
    }

    public StringKey(String name) {
        super();
        this.name = name;
    }

    public void initializeTree(StringKey left, StringKey right) {
        super.initializeTree(left, right);
    }

    private String getName(){
        return this.name;
    }

    @Override
    public int compareTo(Key o) {
        if (!(o instanceof StringKey) || o == null || this == null) {
            return 0;
        }
        StringKey other = (StringKey) o;
        if (super.compareTo(other) != 0){
            return super.compareTo(other);
        }else{
            return this.name.compareTo(other.name);
        }
    }
}

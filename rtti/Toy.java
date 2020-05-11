package rtti;

interface HasBatteries {
}

interface Waterproof {
    int waterproof(String s);
}

interface ShootsThings {
    void shootsthings();
}


public class Toy {

    // Comment out the following default
    // constructor to see
    // NoSuchMethodError from (*1*)
    int id;
    private String name = "Toy";
    public boolean male = true;
    private int maleId = 1;

    Toy() {
        System.out.println("Toy()");
    }

    public Toy(int i) {
        System.out.println("Toy(int)");
        this.id = i;
    }

    public Toy(String n) {
        name = n;
    }

    private Toy(boolean m) {
        male = m;
    }

    private Toy(int i, String n) {
        System.out.println("Toy(int,String)");
        this.id = i;
        this.name = n;
    }

    private void print() {
        if (name != null)
            System.out.println("Name: " + name + ", Id: " + id);
    }

    private void setName(String name) {
        System.out.println("Toy setName: " + name);
        this.name = name;
    }

    public final int getI() {
        return id;
    }

    public boolean getMale() {
        return male;
    }

    static class InnerToy1 {

        int id;
        private String name1 = "InnerToy";
        public String name2 = "InnerToy11";

        InnerToy1() {
        }

        InnerToy1(int i) {
            id = i;
        }

        public int setId(int i) {
            int temp = id;
            id = i;
            return temp;
        }
    }

    private class InnerToy2 {

        private int id;

        private String name;

        private boolean male = true;


        InnerToy2() {
            System.out.println("InnerToy2()");
        }

        InnerToy2(String n, int i) {
            id = i;
            name = n;
            System.out.println("InnerToy2(String, int)");

        }

        private int setId(int i) {
            int temp = id;
            id = i;
            System.out.println("ori: " + temp + ", cur: " + id);
            return temp;
        }
    }

}


class FancyToy extends Toy
        implements HasBatteries,
        Waterproof, ShootsThings {
    FancyToy() {
        super(1);
        System.out.println("FancyToy()");
    }

    FancyToy(int i) {
        super(i);
        System.out.println("FancyToy(int)");
    }

    @Override
    public int waterproof(String s) {
        return s.length();
    }

    @Override
    public void shootsthings() {

    }
}

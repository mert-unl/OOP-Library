public abstract class Person {

    private String name;

   abstract  void whoyouare();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Person(String name) {
        this.name = name;
    }
}

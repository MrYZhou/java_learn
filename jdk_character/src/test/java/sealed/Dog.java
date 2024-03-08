package sealed;

public non-sealed class Dog extends Animal {
    @Override
    public void eat() {
        System.out.println("dog eat");
    }
}

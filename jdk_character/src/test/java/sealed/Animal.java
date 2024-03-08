package sealed;

/**
 * sealed jdk17特性
 * sealed 密封类的主要作用就是限制类的继承。
 * 比如我们有 Animal类，Dog 和 Cat 分别继承它，实现了 eat 方法，他们吃的动作是不一样的，但是我们不希望人能继承 Animal，不允许他去继承动物吃的行为，就可以像下面这样通过 sealed 和 permits 关键字限制它是一个密封类，只有猫和狗能够继承它。
 * 需要注意，父类被定义为 sealed 之后，子类必须是 sealed、 non-sealed 或者 final。
 */
public abstract sealed class Animal permits Cat, Dog {

    public abstract void eat();
}

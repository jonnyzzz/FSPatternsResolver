package mooo;

import org.jetbrains.annotations.NotNull;

/**
* Created by Eugene Petrenko (eugene.petrenko@gmail.com)
* Date: 27.03.13 22:28
*/
public abstract class ExactWildcard extends Wildcard {
  private final String myName;

  public ExactWildcard(@NotNull String name) {
    myName = name;
  }

  @NotNull
  public String getName() {
    return myName;
  }

  @Override
  public boolean matches(@NotNull String name) {
    return areSameNames(name, myName);
  }

  protected abstract boolean areSameNames(@NotNull String a, @NotNull String b);

  @Override
  public String toString() {
    return myName;
  }
}

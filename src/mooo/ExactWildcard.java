package mooo;

import org.jetbrains.annotations.NotNull;

/**
* Created by Eugene Petrenko (eugene.petrenko@gmail.com)
* Date: 27.03.13 22:28
*/
public class ExactWildcard extends Wildcard {
  private final String myName;

  ExactWildcard(@NotNull String name) {
    myName = name;
  }

  @Override
  public boolean matches(@NotNull String name) {
    return name.equalsIgnoreCase(myName);
  }

  @Override
  public String toString() {
    return myName;
  }
}

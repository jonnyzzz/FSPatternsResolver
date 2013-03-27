package mooo;

import org.jetbrains.annotations.NotNull;

/**
* Created by Eugene Petrenko (eugene.petrenko@gmail.com)
* Date: 27.03.13 22:28
*/
public abstract class Wildcard {
  public abstract boolean matches(@NotNull String name);
}

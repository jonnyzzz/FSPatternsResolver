package mooo.wildcard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
* Created by Eugene Petrenko (eugene.petrenko@gmail.com)
* Date: 27.03.13 22:28
*/
public abstract class Wildcard {
  private final Collection<Wildcard> myNext = new ArrayList<Wildcard>(1);

  @NotNull
  public Collection<Wildcard> getNext() {
    return myNext;
  }

  public void addNext(@NotNull Wildcard w) {
    myNext.add(w);
  }

  public boolean isLeaf() {
    return getNext().isEmpty();
  }

  public abstract boolean matches(@NotNull String name);
}

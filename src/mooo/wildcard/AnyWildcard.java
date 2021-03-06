package mooo.wildcard;

import org.jetbrains.annotations.NotNull;

/**
* Created by Eugene Petrenko (eugene.petrenko@gmail.com)
* Date: 27.03.13 22:29
*/
public class AnyWildcard extends Wildcard {
  public AnyWildcard() {
    addNext(this);
  }

  @Override
  public boolean matches(@NotNull String name) {
    return true;
  }

  @Override
  public boolean isLeaf() {
    return getNext().size() == 1;
  }

  @Override
  public String toString() {
    return "**";
  }
}

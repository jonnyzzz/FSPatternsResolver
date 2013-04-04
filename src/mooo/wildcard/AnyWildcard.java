package mooo.wildcard;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

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
    Collection<Wildcard> next = getNext();
    return next.size() == 1 && next.contains(this);
  }

  @Override
  public String toString() {
    return "**";
  }
}

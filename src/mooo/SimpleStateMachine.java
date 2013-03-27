package mooo;

import mooo.wildcard.ExactWildcard;
import mooo.wildcard.Wildcard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 27.03.13 23:39
 */
public class SimpleStateMachine implements StateMachine {
  private final Collection<Wildcard> myState;

  public SimpleStateMachine(@NotNull Collection<Wildcard> state) {
    myState = state;
  }

  @Nullable
  public Collection<String> follows() {
    Collection<String> foll = new ArrayList<String>(myState.size());
    for (Wildcard w : myState) {
      if (!(w instanceof ExactWildcard)) return null;
      foll.add(((ExactWildcard) w).getName());
    }
    return foll;
  }

  public boolean matchFiles(@NotNull String fileName) {
    for (Wildcard wildcard : myState) {
      if (!wildcard.getNext().isEmpty()) continue;
      if (wildcard.matches(fileName)) return true;
    }
    return false;
  }

  @Nullable
  public StateMachine advance(@NotNull String dirName) {
    List<Wildcard> nextState = new ArrayList<Wildcard>(myState.size() * 2);
    for (Wildcard wildcard : myState) {
      if (wildcard.matches(dirName)) {
        nextState.addAll(wildcard.getNext());
      }
    }
    if (nextState.isEmpty()) {
      return null;
    }

    return new SimpleStateMachine(nextState);
  }
}

package mooo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 27.03.13 23:39
 */
public class StateMachine {
  private Collection<Wildcard> myState;

  public StateMachine(@NotNull Collection<Wildcard> state) {
    myState = state;
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
    List<Wildcard> nextState = new ArrayList<Wildcard>();
    for (Wildcard wildcard : myState) {
      if (wildcard.matches(dirName)) {
        nextState.addAll(wildcard.getNext());
      }
    }
    if (nextState.isEmpty()) {
      return null;
    }

    return new StateMachine(nextState);
  }


}

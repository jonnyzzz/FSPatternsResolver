package mooo;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 27.03.13 22:49
 */
public class StateMachine {
  private final Map<Wildcard, Wildcard[]> myStates = new HashMap<Wildcard, Wildcard[]>();
  private final List<Wildcard> myState = new LinkedList<Wildcard>();

  public StateMachine(@NotNull List<Wildcard> pattern) {
    if (pattern.isEmpty()) return;

    final Wildcard startState = pattern.get(0);
    myState.add(startState);

    final List<Wildcard> reversed = new ArrayList<Wildcard>(pattern);

    for (Wildcard wildcard : reversed) {
      myStates.put(wildcard, )

    }






  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    final List<Wildcard> visited = new ArrayList<Wildcard>();







    return "StateMachine{" +
            "myWildcards=" + myWildcards +
            '}';
  }
}

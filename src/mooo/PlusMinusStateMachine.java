package mooo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 04.04.13 23:24
 */
public class PlusMinusStateMachine implements StateMachine {
  @NotNull
  private final StateMachine myPlusMachine;
  @NotNull
  private final StateMachine myMinusMachine;

  public PlusMinusStateMachine(@NotNull final StateMachine plusMachine,
                               @NotNull final StateMachine minusMachine) {
    myPlusMachine = plusMachine;
    myMinusMachine = minusMachine;
  }

  @Nullable
  public Collection<String> follows() {
    final Set<String> follows = new HashSet<String>();

    final Collection<String> pf = myPlusMachine.follows();
    if (pf != null) {
      follows.addAll(pf);
    } else {
      //all paths may match, so nothing to filter
      return null;
    }

    final Collection<String> mf = myMinusMachine.follows();
    if (mf != null) {
      follows.removeAll(mf);
    }

    return follows;
  }

  @SuppressWarnings("RedundantIfStatement")
  public boolean matchFiles(@NotNull String fileName) {
    if (!myPlusMachine.matchFiles(fileName))
      return false;

    if (myMinusMachine.matchFiles(fileName))
      return false;

    return true;
  }

  @Nullable
  public StateMachine advance(@NotNull String dirName) {
    StateMachine plus = myPlusMachine.advance(dirName);
    StateMachine minus = myMinusMachine.advance(dirName);
    if (plus == null) return null;
    if (minus == null) return plus;

    return new PlusMinusStateMachine(plus, minus);
  }
}

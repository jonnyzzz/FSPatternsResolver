package mooo;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 26.03.13 23:08
 */
public class PatternResolver {
  private final Wildcards myFactory = new Wildcards();

  @NotNull
  public Set<File> resolveWildcards(@NotNull File root, @NotNull Collection<String> includes) {
    final List<StateMachine> state = new ArrayList<StateMachine>();
    for (String include : includes) {
      state.add(new StateMachine(myFactory.parseWildcard(include)));
    }

    final Set<File> result = new LinkedHashSet<File>();
    visitFS(root, result, state);
    return result;
  }

  private void visitFS(@NotNull File root, @NotNull Set<File> result, @NotNull Collection<StateMachine> state) {
    if (state.isEmpty()) return;

    final File[] allFiles = root.listFiles();
    if (allFiles == null) return;

    final Set<File> dirs = new HashSet<File>();
    for (File path : allFiles) {
      if (path.isDirectory()) {
        dirs.add(path);

        final String name = path.getName();
        final List<StateMachine> nextState = new ArrayList<StateMachine>(state.size());
        for (StateMachine m : state) {
          StateMachine next = m.advance(name);
          if (next != null) {
            nextState.add(next);
          }
        }

        visitFS(path, result, nextState);
      }
    }

    for (File path : allFiles) {
      if (dirs.contains(path)) continue;

      for (StateMachine m : state) {
        if (m.matchFiles(path.getName())) {
          result.add(path);
          break;
        }
      }
    }
  }
}

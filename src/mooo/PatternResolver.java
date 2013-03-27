package mooo;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 26.03.13 23:08
 */
public class PatternResolver {
  private final Wildcards myFactory;

  public PatternResolver(boolean isCaseSensitive) {
    myFactory = new Wildcards(isCaseSensitive);
  }

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

  private boolean visitFollows(@NotNull File root, @NotNull Set<File> result, @NotNull Collection<StateMachine> state) {
    if (state.isEmpty()) return true;

    final Collection<String> follows = new ArrayList<String>();
    for (StateMachine m : state) {
      Collection<String> fs = m.follows();
      if (fs == null) return false;
      follows.addAll(fs);
    }

    for (String follow : follows) {
      final File path = new File(root, follow);
      visitFileSystemEntry(path, result, state);
    }

    return true;
  }

  private void visitFS(@NotNull File root, @NotNull Set<File> result, @NotNull Collection<StateMachine> state) {
    if (state.isEmpty()) return;

    if (visitFollows(root, result, state)) return;

    final File[] allFiles = root.listFiles();
    if (allFiles == null) return;

    for (File path : allFiles) {
      visitFileSystemEntry(path, result, state);
    }
  }

  private void visitFileSystemEntry(@NotNull File path, @NotNull Set<File> result, @NotNull Collection<StateMachine> state) {
    if (path.isFile()) {
      visitFile(path, result, state);
    } else {
      visitDirectory(path, result, state);
    }
  }

  private void visitFile(@NotNull File path, @NotNull Set<File> result, @NotNull Collection<StateMachine> state) {
    for (StateMachine m : state) {
      if (m.matchFiles(path.getName())) {
        result.add(path);
        break;
      }
    }
  }

  private void visitDirectory(@NotNull File path, @NotNull Set<File> result, @NotNull Collection<StateMachine> state) {
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

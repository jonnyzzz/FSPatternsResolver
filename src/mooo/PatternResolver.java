package mooo;

import mooo.wildcard.Wildcard;
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
    final List<Wildcard> state = new ArrayList<Wildcard>();
    for (String include : includes) {
      state.addAll(myFactory.parseWildcard(include));
    }

    final Set<File> result = new LinkedHashSet<File>();
    visitFS(root, result, new SimpleStateMachine(state));
    return result;
  }

  private boolean visitFollows(@NotNull File root, @NotNull Set<File> result, @NotNull StateMachine state) {
    final Collection<String> follows = state.follows();
    if (follows == null) return false;

    for (String follow : follows) {
      final File path = new File(root, follow);
      visitFileSystemEntry(path, result, state);
    }

    return true;
  }

  private void visitFS(@NotNull File root, @NotNull Set<File> result, @NotNull StateMachine state) {
    if (visitFollows(root, result, state)) return;

    final File[] allFiles = root.listFiles();
    if (allFiles == null) return;

    for (File path : allFiles) {
      visitFileSystemEntry(path, result, state);
    }
  }

  private void visitFileSystemEntry(@NotNull File path, @NotNull Set<File> result, @NotNull StateMachine state) {
    final String name = path.getName();

    if (path.isDirectory()) {
      final StateMachine next = state.advance(name);
      if (next != null) {
        visitFS(path, result, next);
      }
    } else if (state.matchFiles(name)) {
      result.add(path);
    }
  }
}

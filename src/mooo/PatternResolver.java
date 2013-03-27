package mooo;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 26.03.13 23:08
 */
public class PatternResolver {


  private void visitFS(@NotNull File root, @NotNull FSVisitor visitor) {
    File[] allFiles = root.listFiles();
    if (allFiles == null) return;

    final Set<File> dirs = new HashSet<File>();
    for (File path : allFiles) {
      if (path.isDirectory()) {
        dirs.add(path);

        final VisitorOutcome outcome = visitor.acceptDirectory(path);

      }
    }

    for (File path : allFiles) {
      if (dirs.contains(path)) continue;
      visitor.acceptFile(path);
    }
  }


  private interface FSVisitor {
    void acceptFile(@NotNull File file);

    VisitorOutcome acceptDirectory(@NotNull File path);
  }

  private class VisitorOutcome {

  }






}

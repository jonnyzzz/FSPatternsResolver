package mooo;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 27.03.13 23:55
 */
public class ResolverTest {

  @Test
  public void testWindows_all() {
    PatternResolver pr = new PatternResolver(true);

    Set<File> files = pr.resolveWildcards(
            new File("C:\\"),
            Arrays.asList("windows/Microsoft.NET/**/*.dll"),
            Collections.<String>emptyList());
    printFiles(files);
  }

  @Test
  public void testWindows_excludes() {
    PatternResolver pr = new PatternResolver(true);

    Set<File> files = pr.resolveWildcards(
            new File("C:\\"),
            Arrays.asList("windows/Microsoft.NET/**/*.dll"),
            Arrays.asList("**/v2.*/**"));
    printFiles(files);

    for (File file : files) {
      Assert.assertFalse(file.getName(), file.getPath().contains("v2."));
    }
  }

  private void printFiles(@NotNull final Set<File> files) {
    for (File file : new TreeSet<File>(files)) {
      System.out.println(file);
    }
  }
}

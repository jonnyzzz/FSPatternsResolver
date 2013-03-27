package mooo;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 27.03.13 23:55
 */
public class ResolverTest {

  @Test
  public void testWindows() {
    PatternResolver pr = new PatternResolver(true);

    Set<File> files = pr.resolveWildcards(new File("C:\\"), Arrays.asList("windows/Microsoft.NET/**/*.dll"));
    System.out.println(files);
  }
}

package mooo;

import mooo.wildcard.Wildcard;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 26.03.13 23:11
 */

public class WildcardsTest {
  @Test
  public void should_generate_right_links_for_w() {
    assertLinks("a/**/b",

            "a -> **",
            "a -> b",
            "** -> **",
            "** -> b",
            "b L ->"
    );
  }

  @Test
  public void should_remove_double_w() {
    assertLinks("a/**/**/**/b",

            "a -> **",
            "a -> b",
            "** -> **",
            "** -> b",
            "b L ->"
    );
  }

  @Test
  public void should_mark_trailing_start_as_leaf() {
    assertLinks("a/**/**/**",
            "a -> **",
            "** L -> **"
    );
  }


  private void assertLinks(@NotNull String pt, @NotNull String... gold) {
    Collection<Wildcard> wd = new Wildcards(true).parseWildcard(pt);

    Set<Wildcard> visited = new HashSet<Wildcard>();
    Queue<Wildcard> queue = new ArrayDeque<Wildcard>(wd);

    List<String> graph = new ArrayList<String>();
    while(!queue.isEmpty()) {
      final Wildcard w = queue.remove();
      if (!visited.add(w)) continue;

      for (Wildcard n : w.getNext()) {
        queue.add(n);
        graph.add( w +(w.isLeaf() ? " L" : "" )+ " -> " + n);
      }
      if (w.getNext().isEmpty()) {
        graph.add( w + " L ->");
      }
    }

    for (String s : graph) {
      System.out.println(s);
    }

    Assert.assertEquals(new ArrayList<String>(Arrays.asList(gold)), graph);
  }
}

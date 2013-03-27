package mooo;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 26.03.13 23:11
 */

public class WildcardsTest {

  @Test
  public void should_parse_basic_wildcards() {
    assertParse("", "");
    assertParse("**", "/**");
    assertParse("**/*", "/**/*");
    assertParse("a/v", "/a/v");
    assertParse("a/v/", "/a/v");
    assertParse("a/v?/", "/a/v.?");
  }

  @Test
  public void should_generate_right_links_for_w() {
    assertLinks("a/**/b", "0 -> 1 2", "1 -> 2", "2 ->");
  }


  private void assertParse(@NotNull String pt, @NotNull String gold) {
    Wildcard[] wd = new Wildcards().parseWildcard(pt);

    StringBuilder sb = new StringBuilder();
    for (Wildcard w : wd) {
      sb.append("/").append(w);
    }
    Assert.assertEquals(gold, sb.toString());
  }

  private void assertLinks(@NotNull String pt, @NotNull String... gold) {
    Wildcard[] wd = new Wildcards().parseWildcard(pt);
    List<Wildcard> list = new ArrayList<Wildcard>(Arrays.asList(wd));

    int i = 0;
    for (Wildcard w : list) {
      StringBuilder sb = new StringBuilder();
      sb.append(list.indexOf(w));
      sb.append(" -> ");
      for (Wildcard n : w.getNext()) {
        sb.append(list.indexOf(n)).append(" ");
      }
      Assert.assertEquals(gold[i++], sb.toString().trim());
    }
  }
}

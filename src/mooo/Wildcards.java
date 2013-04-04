package mooo;

import mooo.wildcard.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 27.03.13 22:29
 */
public class Wildcards {
  private static final String FAKE_WILDCARD_ROOT = "FAKE";
  private final boolean myIsCaseSensitive;

  public Wildcards(boolean isCaseSensitive) {
    myIsCaseSensitive = isCaseSensitive;
  }

  @NotNull
  public Collection<Wildcard> parseWildcard(@NotNull String s) {
    final String[] items = (FAKE_WILDCARD_ROOT + "/" + s)
            //remove extra stars
            .replaceAll("\\*[\\*]+", "**")
                    //normalize slashes
            .replaceAll("[\\\\/]+", "/")
            .replaceAll("\\*\\*(/\\*\\*)+", "**")
            .split("/");

    final List<Wildcard> result = new ArrayList<Wildcard>();
    for (String item : items) {
      if (item.length() == 0) continue;
      result.add(fromElement(item));
    }

    Wildcard[] wildcards = result.toArray(new Wildcard[result.size()]);

    Wildcard next = null;
    Wildcard nextNext = null;
    for(int i = wildcards.length-1; i >= 0; i--) {
      final Wildcard prev = wildcards[i];
      if (next != null) {
        prev.addNext(next);

        if (next instanceof AnyWildcard && nextNext != null) {
          prev.addNext(nextNext);
        }
      }

      nextNext = next;
      next = prev;
    }

    return wildcards[0].getNext();
  }


  private Wildcard fromElement(@NotNull String el) {
    if (el.equals("**")) {
      return new AnyWildcard();
    }

    if (el.contains("?") || el.contains("*")) {
      final String regex = el
              .replace(".", "\\.")
              .replace("?", ".?")
              .replace("*", ".*");

      return new PatternWildcard(Pattern.compile(regex, myIsCaseSensitive ? 0 : Pattern.CASE_INSENSITIVE));
    }

    return myIsCaseSensitive
            ? new ExactCaseSensitiveWildcard(el)
            : new ExactCaseInSensitiveWildcard(el);
  }

}

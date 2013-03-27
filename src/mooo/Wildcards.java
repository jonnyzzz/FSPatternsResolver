package mooo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 27.03.13 22:29
 */
public class Wildcards {
  @NotNull
  public StateMachine parse(@NotNull String pt) {
    return new StateMachine(parseWildcard(pt)[0]);
  }


  @NotNull
  public Wildcard[] parseWildcard(@NotNull String s) {
    final String[] items = s
            //remove extra stars
            .replaceAll("\\*[\\*]+", "**")
                    //normalize slashes
            .replaceAll("[\\\\/]+", "/")
            .split("/");

    final List<Wildcard> result = new ArrayList<Wildcard>();
    for (String item : items) {
      if (item.length() == 0) continue;
      result.add(fromElement(item));
    }

    Wildcard[] wildcards = result.toArray(new Wildcard[result.size()]);

    Wildcard prev = null;
    for(int i = wildcards.length-1; i >= 0; i--) {
      if (prev != null) {
        wildcards[i].setNext(prev);
      }
      prev = wildcards[i];
    }

    return wildcards;
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

      //TODO: include case-check here
      return new PatternWildcard(Pattern.compile(regex));
    }

    return new ExactWildcard(el);
  }

}

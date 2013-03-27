package mooo;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
* Created by Eugene Petrenko (eugene.petrenko@gmail.com)
* Date: 27.03.13 22:28
*/
public class PatternWildcard extends Wildcard {
  private final Pattern myPattern;

  public PatternWildcard(@NotNull final Pattern pattern) {
    myPattern = pattern;
  }

  @Override
  public boolean matches(@NotNull String name) {
    return myPattern.matcher(name).matches();
  }

  @Override
  public String toString() {
    return myPattern.toString();
  }
}

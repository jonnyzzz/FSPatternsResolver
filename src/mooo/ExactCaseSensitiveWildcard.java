package mooo;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 28.03.13 0:19
 */
public class ExactCaseSensitiveWildcard extends ExactWildcard {
  public ExactCaseSensitiveWildcard(@NotNull String name) {
    super(name);
  }

  @Override
  protected boolean areSameNames(@NotNull String a, @NotNull String b) {
    return a.equals(b);
  }
}

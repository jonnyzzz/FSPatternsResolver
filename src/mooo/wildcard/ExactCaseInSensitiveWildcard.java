package mooo.wildcard;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 28.03.13 0:20
 */
public class ExactCaseInSensitiveWildcard extends ExactWildcard {
  public ExactCaseInSensitiveWildcard(@NotNull String name) {
    super(name);
  }

  @Override
  protected boolean areSameNames(@NotNull String a, @NotNull String b) {
    return a.equalsIgnoreCase(b);
  }
}

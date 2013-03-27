package mooo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 28.03.13 0:35
 */
public interface StateMachine {
  @Nullable
  Collection<String> follows();

  boolean matchFiles(@NotNull String fileName);

  @Nullable
  StateMachine advance(@NotNull String dirName);
}

package org.waabox.bomb;

import java.lang.reflect.Method;
import java.util.Set;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/** Intercept methods that matches with the given list of expressions.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class BombMethodInterceptor implements MethodInterceptor {

  /** The Proxied object, it's never null.*/
  private final Object bombed;

  /** The Regex that holds the matches for the methods,
   * can be null if the given expressions are empty.
   */
  private final String regex;

  /** Creates a new instance of the
   * @param instance the instance to be bombed, cannot be null.
   * @param expressions the list of expressions, cannot be null.
   */
  public BombMethodInterceptor(final Object instance,
      final Set<String> expressions) {
    bombed = instance;
    if (expressions.isEmpty()) {
      regex = null;
    } else {
      StringBuilder sb = new StringBuilder();
      sb.append(".*(");
      for (String expression : expressions) {
        sb.append(expression).append('|');
      }
      sb.replace(sb.length() - 1, sb.length(), ").*");
      regex = sb.toString();
    }
  }

  /** {@inheritDoc}.*/
  public Object intercept(
      final Object proxy,
      final Method method,
      final Object[] arguments,
      final MethodProxy methodProxy) throws Throwable {
    if (regex != null && method.getName().matches(regex)) {
      throw new BombException(String.format(
          "Method %s in %s cannot be touch!",
          method.getName(),
          bombed.getClass().getName())
      );
    } else {
      return bombed.getClass()
          .getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(bombed, arguments);
    }
  }

}

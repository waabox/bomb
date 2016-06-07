package org.waabox.bomb;

import java.lang.reflect.Constructor;

import org.objenesis.ObjenesisHelper;

/** Proxy helper that isolates the Proxy generation.
 *
 * Has 2 strategies, the 1st one use sun.misc.Unsafe if could not create the
 * instance, will use Objenesis.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class Proxy {

  /** The unsafe instance method.*/
  private static Object unsafe;

  static {
    try {
      Class<?> klass = Class.forName("sun.misc.Unsafe");

      if (klass.getMethod("allocateInstance", Class.class) == null) {
        throw new Exception("does not have the allocateInstance method, exit");
      }

      Constructor<?> constructor;
      constructor = klass.getDeclaredConstructor();
      constructor.setAccessible(true);

      unsafe = constructor.newInstance();

    } catch (Exception e) {
      unsafe = null;
    }
  }

  /** Creates a new Proxy for the given class.
   * @param proxyClass the proxy class.
   * @return the object or null.#
   */
  public static Object create(final Class<?> proxyClass) {
    if (unsafe != null) {
      return fromUnsafe(proxyClass);
    } else {
      return fromObjenesis(proxyClass);
    }
  }

  /** Creates a new Proxy instance using {@link Unsafe}.
   * @param proxyClass the Proxy class.
   * @return the instance, or null.
   */
  private static Object fromUnsafe(final Class<?> proxyClass) {
    try {
      return unsafe.getClass().getMethod("allocateInstance", Class.class)
          .invoke(unsafe, proxyClass);
    } catch (Exception e) {
      return null;
    }
  }

  /** Creates a new instance using {@link ObjenesisHelper}.
   * @param proxyClass the Proxy class.
   * @return the instance.
   */
  private static Object fromObjenesis(final Class<?> proxyClass) {
    return ObjenesisHelper.newInstance(proxyClass);
  }

}

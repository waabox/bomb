package org.waabox.bomb;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.objenesis.ObjenesisHelper;

/** Proxy helper that isolates the Proxy generation.
 *
 * Has 2 strategies, the 1st one use sun.misc.Unsafe if could not create the
 * instance, will use Objenesis.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class Proxy {

  /** The unsafe instance, it's Object because some JVM could not have the
   * Unsafe class, or could have a protection, etc.
   */
  private static Object unsafe;

  /** The Unsafe method to execute.*/
  private static final String UNSAFE_METHOD = "allocateInstance";

  /** Checks the existence of the sun.misc.Unsafe and tries to initialize it.*/
  static {
    try {
      Class<?> klass = Class.forName("sun.misc.Unsafe");

      if (klass.getMethod(UNSAFE_METHOD, Class.class) == null) {
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
      Method method = unsafe.getClass().getMethod(UNSAFE_METHOD, Class.class);
      return method.invoke(unsafe, proxyClass);
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

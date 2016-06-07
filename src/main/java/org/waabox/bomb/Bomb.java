package org.waabox.bomb;

import java.util.HashSet;
import java.util.Set;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/** The bomb holds a list of matching expression that works as a bomb
 * detonator, if the proxies object matches with any of the given detonator,
 * will raise a {@link BombException}.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class Bomb<T> {

  /** The instance to be bombed!.*/
  private final T instance;

  /** The list of expressions, it's never null. */
  private final Set<String> expressions = new HashSet<>();

  /** Creates a new instance of the {@link Bomb}.
   * @param theInstance the instance to be proxied.
   */
  public Bomb(final T theInstance) {
    instance = theInstance;
  }

  /** Marks all the setters to be bombed!.
   * @return this.
   */
  public Bomb<T> settersDetonator() {
    expressions.add("set");
    return this;
  }

  /** Marks all the getters to be bombed!.
   * @return this.
   */
  public Bomb<T> gettersDetonator() {
    expressions.add("get");
    return this;
  }

  /** Adds a new expression that will mark the Proxy as a bomb detonator!.
   * @param expression the expression, cannot be null.
   * @return this.
   */
  public Bomb<T> detonator(final String expression) {
    expressions.add(expression);
    return this;
  }

  /** Retrieves the Proxy.
   * @return the proxy, never null.
   */
  public T get() {
    return (T) createProxy(
        instance.getClass(),
        new BombMethodInterceptor(instance, expressions));
  }


  /** Creates a new proxy.
   *
   * @param klass the class.
   * @param methodInterceptor the interceptor.
   * @return the proxy instance, never null.
   */
  @SuppressWarnings("unchecked")
  private T createProxy(
      final Class<?> klass,
      final MethodInterceptor methodInterceptor) {

    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(klass);
    enhancer.setCallbackType(methodInterceptor.getClass());

    Class<?> proxyClass = enhancer.createClass();

    Enhancer.registerCallbacks(
        proxyClass, new Callback[] { methodInterceptor }
    );

    return (T) Proxy.create(proxyClass);
  }

}

package org.waabox.bomb;

/** Bomb if a method matches with the expression within a {@link Bomb}.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class BombException extends RuntimeException {

  /** The serial version. */
  private static final long serialVersionUID = 1L;

  /** Creates a new instance of the {@link BombException}.
   * @param message the message.
   */
  public BombException(final String message) {
    super(message);
  }

}

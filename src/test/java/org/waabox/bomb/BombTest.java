package org.waabox.bomb;

import org.junit.Test;
import static org.junit.Assert.*;

public class BombTest {

  @Test public void test_Detonators() {
    Bomb<Hello> bomb = new Bomb<BombTest.Hello>(new Hello("foo"));
    bomb.settersDetonator();
    bomb.gettersDetonator();
    bomb.detonator("fof");
    Hello aHello = bomb.get();

    try {
      aHello.getName();
      fail("Should fail because has an gettersDetonator detonator");
    } catch(BombException be) {
    }

    try {
      aHello.setName("aa");
      fail("Should fail because has an settersDetonator detonator");
    } catch(BombException be) {
    }

    try {
      aHello.fofo();
      fail("Should fail because has an detonator detonator");
    } catch(BombException be) {
    }
  }

  @Test public void test_no_Detonators() {
    Bomb<Hello> bomb = new Bomb<BombTest.Hello>(new Hello("foo"));
    Hello aHello = bomb.get();
    aHello.getName();
    aHello.setName("aa");
    aHello.fofo();
  }

  public static class Hello {

    private String name;

    public Hello(final String aName) {
      name = aName;
    }

    public String getName() {
      return name;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public void fofo() {
      //nothing
    }

  }

}

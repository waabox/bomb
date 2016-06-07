# Bomb
<h1> What is this? </h2>
 
Bomb is an utility that allows you to mark method as innacesible.

<h2> Ok, why? </h2>

Because sometimes, you need to use an object and then by its signature you need to 'loose it controls' so, in order to avoid you to wrap the object, etc etc, you can 'Bomb.it' and expect the UnitTest cover your ass instead of the Production Environment :D

<h2>Ok, I'm crazy enough...Tell me how! </h2>

In this example, you will create a simple java.lang.Object and then, we'll bomb it's hashCode method.

```java
  public class Foo {

    public static void main(String[] args) {
      Bomb<Object> bomb = new Bomb<Object>(new Object());
      bomb.detonator("hash");
      Object bombed = bomb.get();
      // ok, lets bomb it! Hadouuuken!
      bombed.hashCode();
    }
  
  }
```
Once we execute this program, a org.waabox.bomb.BombException will be raised! :D

You will find a better example in: https://github.com/waabox/bomb/blob/master/src/test/java/org/waabox/bomb/BombTest.java

<h2> Maven repository </h2>

You can find bomb within the maven central =D

```xml
<dependency>
  <groupId>org.waabox</groupId>
  <artifactId>bomb</artifactId>
  <version>1.1</version>
</dependency>
```

happy coding!

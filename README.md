# Bomb

`Bomb` is a tiny Java tool that lets you **hard-block specific method calls on a wrapped object**.  
If the method is invoked, it blows up with a `BombException`.  
Useful when you want code to **fail loudly** instead of silently touching APIs it shouldn’t.

---

## What it actually does

- Wraps an existing instance using a dynamic proxy.
- Intercepts method invocations.
- If the method name matches any configured detonator prefix, it throws.
- Otherwise, it surfaces the call to the wrapped instance.

That’s it. No bytecode weaving. No agents. No magic.

---

## Why it exists

Because sometimes “don’t call this” is not enough — you want your code to **explode at runtime** if that happens:

- During refactors, to ban access to legacy methods.
- In tests, to detect accidental use of forbidden APIs.
- In experiments/sandboxes, to expose unexpected control flows.

---

## Install

```xml
<dependency>
  <groupId>org.waabox</groupId>
  <artifactId>bomb</artifactId>
  <version>1.1</version>
</dependency>

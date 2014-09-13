package de.cgan.bottles;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @see http://99-bottles-of-beer.net/
 * @author migu
 */
public class BottlesApp {
  public static void main(String... args) {
    N numBottles = new N(99);
    Singer singer = new Singer(System.out, numBottles);
    numBottles.forEach(singer);
  }

  private static class Singer implements Consumer<N> {
    private static final String WALL = "on the wall";
    private final Bottle bottle = new Bottle("bottle", "s");
    private final PrintStream out;
    private final N numBottles;

    public Singer(PrintStream out, N numBottles) {
      this.out = out;
      this.numBottles = numBottles;
    }

    @Override
    public void accept(N n) {
      bottlesUpperCase(n).f(" %s, ", WALL).bottlesLowerCase(n).f(".").nl();
      if (n.isPositive()) {
        f("Take one down, pass it around, ").bottlesLowerCase(n.dec()).f(" %s.", WALL).nl().nl();
      } else {
        f("Go to the store and buy some more, ").bottlesLowerCase(numBottles).f(" %s.", WALL).nl();
      }
    }

    private Singer bottlesUpperCase(N n) {
      singBottlesOfBeer(n, false);
      return this;
    }

    private Singer bottlesLowerCase(N n) {
      singBottlesOfBeer(n, true);
      return this;
    }

    private Singer singBottlesOfBeer(N n, boolean lowerCase) {
      String num = n.toString();
      if (lowerCase) {
        num = firstLowerCase(num);
      }
      f("%s %s of beer", num, bottle.toString(n.toInt()));
      return this;
    }

    private Singer f(String fmt, Object... args) {
      out.printf(fmt, args);
      return this;
    }

    private Singer nl() {
      out.println();
      return this;
    }

    private String firstLowerCase(String s) {
      return s.substring(0, 1).toLowerCase() + s.substring(1);
    }
  }

  public static class N implements Iterable<N> {
    private final int n;

    public N(int n) {
      this.n = n;
    }

    public N dec() {
      return new N(n - 1);
    }

    public int toInt() {
      return n;
    }

    public boolean isPositive() {
      return n > 0;
    }

    @Override
    public String toString() {
      return n == 0 ? "No more" : String.valueOf(n);
    }

    @Override
    public Iterator<N> iterator() {
      return new NIterator(this);
    }

    private static class NIterator implements Iterator<N> {
      private N current;

      public NIterator(N start) {
        this.current = start;
      }

      @Override
      public boolean hasNext() {
        return current.toInt() >= 0;
      }

      @Override
      public N next() {
        N old = current;
        current = current.dec();
        return old;
      }
    }
  }

  private static class Bottle {
    private final String singular;
    private final String plural;

    public Bottle(String root, String pluralSuffix) {
      this.singular = root;
      this.plural = root + pluralSuffix;
    }

    public String toString(int n) {
      return n == 0 || n > 1 ? plural : singular;
    }
  }
}

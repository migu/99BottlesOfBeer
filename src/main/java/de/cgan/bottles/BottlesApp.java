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
    Num numBottles = new Num(99);
    Singer singer = new Singer(System.out, numBottles);
    numBottles.forEach(singer);
  }

  private static class Singer implements Consumer<Num> {
    private static final String WALL = "on the wall";
    private final Word bottle = new Word("bottle", "s");
    private final PrintStream out;
    private final Num numBottles;

    public Singer(PrintStream out, Num numBottles) {
      this.out = out;
      this.numBottles = numBottles;
    }

    @Override
    public void accept(Num n) {
      bottlesUpperCase(n).f(" %s, ", WALL).bottlesLowerCase(n).f(".").nl();
      if (n.isPositive()) {
        f("Take one down, pass it around, ").bottlesLowerCase(n.dec()).f(" %s.", WALL).nl().nl();
      } else {
        f("Go to the store and buy some more, ").bottlesLowerCase(numBottles).f(" %s.", WALL).nl();
      }
    }

    private Singer bottlesUpperCase(Num n) {
      singBottlesOfBeer(n, false);
      return this;
    }

    private Singer bottlesLowerCase(Num n) {
      singBottlesOfBeer(n, true);
      return this;
    }

    private Singer singBottlesOfBeer(Num n, boolean lowerCase) {
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

  public static class Num implements Iterable<Num> {
    private final int n;

    public Num(int n) {
      this.n = n;
    }

    public Num dec() {
      return new Num(n - 1);
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
    public Iterator<Num> iterator() {
      return new NIterator(this);
    }

    private static class NIterator implements Iterator<Num> {
      private Num current;

      public NIterator(Num start) {
        this.current = start;
      }

      @Override
      public boolean hasNext() {
        return current.toInt() >= 0;
      }

      @Override
      public Num next() {
        Num old = current;
        current = current.dec();
        return old;
      }
    }
  }

  private static class Word {
    private final String singular;
    private final String plural;

    public Word(String root, String pluralSuffix) {
      this.singular = root;
      this.plural = root + pluralSuffix;
    }

    public String toString(int n) {
      return n == 0 || n > 1 ? plural : singular;
    }
  }
}

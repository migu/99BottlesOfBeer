package de.cgan.bottles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import de.cgan.bottles.Bottles.Num;
import de.cgan.bottles.Bottles.Singer;

public class SingerTest {
  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
  Singer singer = new Singer(new PrintStream(bytes), new Num(99));

  @Test
  public void singsLastStanza() {
    singer.accept(new Num(0));

    assertThat(getOutput(), equalTo("No more bottles of beer on the wall, no more bottles of beer.\n"
        + "Go to the store and buy some more, 99 bottles of beer on the wall.\n"));
  }

  @Test
  public void singsStanzaOne() {
    singer.accept(new Num(1));

    assertThat(getOutput(), equalTo("1 bottle of beer on the wall, 1 bottle of beer.\n"
        + "Take one down, pass it around, no more bottles of beer on the wall.\n\n"));
  }

  @Test
  public void singsStanzaTwo() {
    singer.accept(new Num(2));

    assertThat(getOutput(), equalTo("2 bottles of beer on the wall, 2 bottles of beer.\n"
        + "Take one down, pass it around, 1 bottle of beer on the wall.\n\n"));
  }

  private String getOutput() {
    return new String(bytes.toByteArray());
  }
}

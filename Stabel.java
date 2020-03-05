public class Stabel<T> extends Lenkeliste<T> {

  public void leggPaa(T x) {
    //legge til i slutten av listen

    leggTil(x);
  }

  public T taAv() {
    //fjeren fra slutten av listen

    T x = fjern(stoerrelse()-1);
    return x;

  }
}

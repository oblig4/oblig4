class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {

  @Override
  public void sett(int pos, T x) throws UnsupportedOperationException {
    //fordi listen skal være sortert .. skal ikke kunne legge til noe i gitt pos
    UnsupportedOperationException e = new UnsupportedOperationException();
    throw e;
  }

  @Override
  public void leggTil(int pos, T x) throws UnsupportedOperationException {
    //fordi listen skal være sortert .. skal ikke kunne legge til noe i gitt pos
    UnsupportedOperationException e = new UnsupportedOperationException();
    throw e;
  }

  @Override
  public void leggTil(T x) {
    Node ny = new Node(x);
    Node s = start.neste; //start, siden selve start alltid er null

    if (stoerrelse() == 0) {
      start.neste = ny;
      ny.forrige = start;
      ny.neste = slutt;
      slutt.forrige = ny;

      stoerrelse++;

      return;
    }

    else if (x.compareTo(s.innhold) >= 0) {
      while (s.neste != slutt && s.neste.innhold.compareTo(x) < 0) {

        //må passe på at den jeg setter inn skal være mindre enn sin neste
        //men større enn den før

        s = s.neste;
      }

      Node foran = s;
      Node etter = s.neste;
      s.neste = ny;
      ny.neste = etter;
      ny.forrige = foran;

      stoerrelse++;

      return;
    }

    else if (x.compareTo(s.innhold) < 0){

      //trenger ikke while-lokke fordi vi vet allerede at den skal være
      //først i listen, alstå er den nye noden den nye start ..

      start.neste = ny;
      ny.forrige = start;
      ny.neste = s;

      stoerrelse++;

      return;
    }
  }

  @Override
  public T fjern() {

    int pos = stoerrelse-1;

    T innhold = fjern(pos);


    return innhold;
  }
}

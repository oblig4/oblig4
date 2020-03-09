class Lege implements Comparable<Lege> {
  protected String legensNavn;
  protected Lenkeliste<Resept> utskrevedeResepter; //lager en instans av Lenkeliste

  public Lege(String legensNavn) {
    this.legensNavn = legensNavn;
    utskrevedeResepter = new Lenkeliste<Resept>();
  }

  public String hentNavn() {
    return legensNavn;
  }

  @Override
  public String toString() {
    return "Legens navn: " + legensNavn;
  }

  @Override
  public int compareTo(Lege other) {
    return legensNavn.compareTo(other.hentNavn());
  }

  //returnerer listen
  public Lenkeliste<Resept> hentReseptliste() {
    return utskrevedeResepter;
  }

  public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    //leger skal ikke kunne skrive ut resept på narkotiske legemidler
    if (legemiddel instanceof Narkotisk) {
      throw new UlovligUtskrift(this, legemiddel);
    }

    HvitResept nyResept = new HvitResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(nyResept);

    return nyResept;
  }

  public MilitaerResept skrivMilitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk) {
      throw new UlovligUtskrift(this, legemiddel);
    }

    MilitaerResept nyResept = new MilitaerResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(nyResept);

    return nyResept;
  }

  public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk) {
      throw new UlovligUtskrift(this, legemiddel);
    }

    PResept nyResept = new PResept(legemiddel, this, pasient);
    utskrevedeResepter.leggTil(nyResept);

    return nyResept;
  }

  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk) {
      throw new UlovligUtskrift(this, legemiddel);
    }

    BlaaResept nyResept = new BlaaResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(nyResept);

    return nyResept;
  }
  
  public Lenkeliste<Resept> skrivNarkotiskResept(){
    Lenkeliste<Resept> narkotiskResept = new Lenkeliste<>();

    for(Resept resept : utskrevedeResepter){
      if(resept.hentLegemiddel() instanceof Narkotisk){
        narkotiskResept.leggTil(resept);
      }
    }
    return narkotiskResept;
  }
}

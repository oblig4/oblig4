abstract class Legemiddel {
  protected String navn;
  protected double pris;
  protected double virkestoff;
  private static int id = 0;
  protected int legemiddelId;

  public Legemiddel(String navn, double pris, double virkestoff) {
    this.navn = navn;
    this.pris = pris;
    this.virkestoff = virkestoff;
    legemiddelId = id++;
  }

  public int hentId() {
    return legemiddelId;
  }

  public String hentNavn() {
    return navn;
  }

  public double hentPris() {
    return pris;
  }

  public double hentVirkestoff() {
    return virkestoff;
  }

  public void settNyPris(double nyPris) {
    pris = nyPris;
  }

  @Override
  public String toString() {
    return "Legemiddel ID: " + hentId() + "\nNavn: " + navn + "\nPris: " + pris +" kr. "+ "\nHvor mye virkestoff: " + virkestoff + " mg";
  }

}

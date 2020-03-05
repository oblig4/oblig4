abstract class Resept {
  protected Legemiddel legemiddel;
  protected Lege utskrivendeLege;
  protected int pasientId;
  protected int reit;
  protected static int id = 0;
  protected int resptId;


  public Resept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
    this.legemiddel = legemiddel;
    this.utskrivendeLege = utskrivendeLege;
    this.pasientId = pasientId;
    this.reit = reit;
    resptId = id++;

  }

  public int hentId() {
    return resptId;
  }

  public Legemiddel hentLegemiddel() {
    return legemiddel;
  }

  public Lege hentLege() {
    return utskrivendeLege;
  }

  public int hentPasientId() {
    return pasientId;
  }

  public int hentReit() {
    return reit;
  }

  public boolean bruk() {
    if (reit == 0) {
      return false;
    }
    reit--;
    return true;
  }

  abstract public String farge();

  abstract public double prisAaBetale();

  @Override
  public String toString() {
    return "Resept ID: " + hentId() + "\n" + legemiddel.toString() + "\n" + utskrivendeLege.toString() + "\nPasient ID: " + pasientId + "\nReit: " + reit;
  }

}

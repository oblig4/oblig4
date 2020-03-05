class Spesialist extends Lege implements Godkjenningsfritak {
  protected int kontrollID;

  public Spesialist(String legensNavn, int kontrollID) {
    super(legensNavn);
    this.kontrollID = kontrollID;
  }

  @Override
  public int hentKontrollID() {
    return kontrollID;
  }

  @Override
  public String toString() {
    return super.toString() + "\nKontrollID: " + kontrollID;
  }


}

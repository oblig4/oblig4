class Lege {
  protected String legensNavn;

  public Lege(String legensNavn) {
    this.legensNavn = legensNavn;
  }

  public String hentNavn() {
    return legensNavn;
  }

  @Override
  public String toString() {
    return "Legens navn: " + legensNavn;
  }
}

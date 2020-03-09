class PResept extends HvitResept {

  public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient) {
    super(legemiddel, utskrivendeLege, pasientId, 3);
  }

  @Override
  public double prisAaBetale() {
    double nyPris = legemiddel.hentPris()-108;
    if (nyPris >= 0) {
      return nyPris;
    }
    return 0;
  }
}

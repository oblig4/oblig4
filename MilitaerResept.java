class MilitaerResept extends HvitResept {

  public MilitaerResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
    super(legemiddel, utskrivendeLege, pasientId, reit);
  }

  @Override
  public double prisAaBetale() {
    return 0;
  }

}

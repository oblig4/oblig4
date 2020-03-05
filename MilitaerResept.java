class MilitaerResept extends HvitResept {

  public MilitaerResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
    super(legemiddel, utskrivendeLege, pasientId, reit);
  }

  @Override
  public double prisAaBetale() {
    return 0;
  }

}

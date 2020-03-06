class Pasient{
    private String PasientNavn;
    private String foedselnummer;

    static int pasientid = 0;
    Stabel<Resept> resept = new Stabel<>();

    public Pasient(String pn, String fnr){
        PasientNavn = pn;
        foedselnummer = fnr;

        pasientid ++;
    }

    void leggTilResept(Resept nyResept)
    {
        this.resept.leggPaa(nyResept);
    }

    public String hentPasientNavn(){
        return PasientNavn;
    }
    public String hentFoedselnummer(){
        return foedselnummer;
    }
    public int hentPasientId(){
        return pasientid;
    }
}

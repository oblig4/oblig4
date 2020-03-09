class Pasient{
    private String PasientNavn;
    private String foedselnummer;
    
    protected int pasientId;
    private static int teller = 0;
    Stabel<Resept> resept = new Stabel<>();

    public Pasient(String pn, String fnr){
        PasientNavn = pn;
        foedselnummer = fnr;

        pasientId = teller;
        teller ++;
    }

    void leggTilResept(Resept nyResept)
    {
        resept.leggPaa(nyResept);
    }

    public String hentPasientNavn(){
        return PasientNavn;
    }
    public String hentFoedselnummer(){
        return foedselnummer;
    }
    public int hentPasientId(){
        return pasientId;
    }

    public String toString() {
        return "\nPasient navn: " + PasientNavn + 
                "\nFoedselsnummer: " + foedselnummer + 
                "\nPasient ID: " + pasientId;
    }
    
    public Stabel<Resept> hentReseptListe() {
        return resept;
    }
}

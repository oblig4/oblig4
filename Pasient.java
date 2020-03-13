
//
class Pasient{
    private String PasientNavn;
    private String foedselnummer;

    protected int pasientId;
    private static int teller = 0;
    private Stabel<Resept> resept = new Stabel<>(); //liste over reseptene pasientene har fått utskrevet

    public Pasient(String pn, String fnr){//Pasient har et navn og et foedselsnummer-tekststreng
        PasientNavn = pn;
        foedselnummer = fnr;

        pasientId = teller;
        teller ++;
    }

    public void leggTilResept(Resept nyResept) {//legge til nye resepter i listen
        resept.leggPaa(nyResept);
    }

    public String hentPasientNavn(){//henter pasient navn
        return PasientNavn;
    }
    public String hentFoedselnummer(){//henter foedsels nummer
        return foedselnummer;
    }
    public int hentPasientId(){//henter pasient ID
        return pasientId;
    }

    public String toString() {//skriver ut informasjon om pasienten
        return "\nPasient navn: " + PasientNavn +
                "\nFoedselsnummer: " + foedselnummer +
                "\nPasient ID: " + pasientId;
    }

    public Stabel<Resept> hentReseptListe() {//henter resept-listen
        return resept;
    }
}

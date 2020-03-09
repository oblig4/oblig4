import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Legesystem {
  private SortertLenkeliste<Lege> leger;
  private Lenkeliste<Pasient> pasienter;
  private Lenkeliste<Legemiddel> legemidler;
  private String filnavn;
  private static int UtskrevetVannLg = 0;
  private static int UtskrevetMilitaerVannLg = 0;


  public Legesystem(String filnavn) throws FileNotFoundException, UlovligUtskrift {
    leger = new SortertLenkeliste<>();
    pasienter = new Lenkeliste<>();
    legemidler = new Lenkeliste<>();
    this.filnavn = filnavn;

    lesFraFil(filnavn);
    hovedmeny();
  }


  public void lesFraFil(String filnavn) throws FileNotFoundException, UlovligUtskrift {
    //skrivResept-metodene for å opprette Resept objekter
    //hvis et objekt er ugyldig, skal det ikke legges inn i systemet

    Scanner sc;

    try {
      sc = new Scanner(new File(filnavn));
    }
    catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    }

    String hva = sc.nextLine();

    while (sc.hasNextLine()) {
      while (sc.nextLine() != "#") {
        String linje = sc.nextLine();
        String[] info = linje.split(",");
        String navn = info[0];
        String fnr = info[1];

        Pasient pasient = new Pasient(navn, fnr);
        pasienter.leggTil(pasient);
      }

      hva = sc.nextLine();

      while (sc.nextLine() != "#") {
        String linje = sc.nextLine();
        String[] info = linje.split(",");
        String navn = info[0];
        String type = info[1];
        double pris = Double.parseDouble(info[2]);
        double virkestoff = Double.parseDouble(info[3]);
        int styrke = 0;

        if (info.length == 5) {
          styrke = Integer.parseInt(info[4]);
        }

        if (type == "narkotisk") {
          Narkotisk nark = new Narkotisk(navn, pris, virkestoff, styrke);
          legemidler.leggTil(nark);
        }

        if (type == "vanedannende") {
          Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
          legemidler.leggTil(vane);
        }

        if (type == "vanlig") {
          Vanlig van = new Vanlig(navn, pris, virkestoff);
          legemidler.leggTil(van);
        }

      }

      hva = sc.nextLine();

      while (sc.nextLine() != "#") {
        String linje = sc.nextLine();
        String[] info = linje.split(",");
        String navn = info[0];
        int kontrollid = Integer.parseInt(info[1]);

        Lege lege;

        if (kontrollid != 0) {
          lege = new Spesialist(navn, kontrollid);
        }

        else {
          lege = new Lege(navn);
        }

        leger.leggTil(lege);
      }

      hva = sc.nextLine();

      while (sc.nextLine() != "#") {
        String linje = sc.nextLine();
        String[] info = linje.split(",");
        int legemiddelNr = Integer.parseInt(info[0]);
        String legeNavn = info[1];
        int pasientID = Integer.parseInt(info[2]);
        String type = info[3];

        int reit = 0;

        if (info.length == 5) {
          reit = Integer.parseInt(info[4]);
        }


        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;

        //skal finne legen

        for (int i = 0; i < leger.stoerrelse; i++) {
          if (leger.hent(i).hentNavn() == legeNavn) {
            legen = leger.hent(i);
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse; i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse; i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        if (type == "hvit") {
          legen.skrivHvitResept(legemiddelet, pasienten, reit);
        }

        if (type == "blaa") {
          legen.skrivBlaaResept(legemiddelet, pasienten, reit);
        }

        if (type == "millitaer") {
          legen.skrivMilitaerResept(legemiddelet, pasienten, reit);
        }

        if (type == "p") {
          legen.skrivPResept(legemiddelet, pasienten);
        }

      }
    }
    sc.close();
  }

  public void brukerLeggTil() throws UlovligUtskrift {
    System.out.println("Vil du legge til en lege, pasient, resept eller legemiddel? ja/nei");
    Scanner leggTil = new Scanner(System.in);
    String svar = leggTil.next();

    String hva;

    if (svar == "ja") {
      System.out.println("Hva vil du legge til? lege/pasient/resept/legemiddel");
      hva = leggTil.next();
    }

    else {
      return;
    }

    if (hva == "lege") {
      System.out.println("Skriv inn legens navn: ");
      String navn = leggTil.next();
      Lege lege = new Lege(navn);

      leger.leggTil(lege);
    }

    if (hva == "pasient") {
      System.out.println("Skriv inn pasientens navn: ");
      String navn = leggTil.next();
      System.out.println("Skriv inn pasientens fødselsnummer: ");
      String fnr = leggTil.next();

      Pasient pasient = new Pasient(navn, fnr);
      pasienter.leggTil(pasient);
    }

    if (hva == "resept") {
      System.out.println("Hvilken type vil du legge til? hvit/blaa/militaer/p");
      String type = leggTil.next();

      if (type == "hvit") {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;

        //skal finne legen

        System.out.println("Skriv inn legens navn: ");
        String legeNavn = leggTil.next();

        System.out.println("Skriv inn pasientents id: ");
        int pasientID = leggTil.nextInt();

        System.out.println("Skriv inn legemiddelets nummer: ");
        int legemiddelNr = leggTil.nextInt();

        System.out.println("Skriv inn reiten: ");
        int reit = leggTil.nextInt();

        for (int i = 0; i < leger.stoerrelse; i++) {
          if (leger.hent(i).hentNavn() == legeNavn) {
            legen = leger.hent(i);
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse; i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse; i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        legen.skrivHvitResept(legemiddelet, pasienten, reit);
      }

      if (type == "blaa") {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;

        //skal finne legen

        System.out.println("Skriv inn legens navn: ");
        String legeNavn = leggTil.next();

        System.out.println("Skriv inn pasientents id: ");
        int pasientID = leggTil.nextInt();

        System.out.println("Skriv inn legemiddelets nummer: ");
        int legemiddelNr = leggTil.nextInt();

        System.out.println("Skriv inn reiten: ");
        int reit = leggTil.nextInt();


        for (int i = 0; i < leger.stoerrelse; i++) {
          if (leger.hent(i).hentNavn() == legeNavn) {
            legen = leger.hent(i);
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse; i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse; i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        legen.skrivBlaaResept(legemiddelet, pasienten, reit);
      }

      if (type == "militaer") {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;

        //skal finne legen

        System.out.println("Skriv inn legens navn: ");
        String legeNavn = leggTil.next();

        System.out.println("Skriv inn pasientents id: ");
        int pasientID = leggTil.nextInt();

        System.out.println("Skriv inn legemiddelets nummer: ");
        int legemiddelNr = leggTil.nextInt();

        System.out.println("Skriv inn reiten: ");
        int reit = leggTil.nextInt();




        for (int i = 0; i < leger.stoerrelse; i++) {
          if (leger.hent(i).hentNavn() == legeNavn) {
            legen = leger.hent(i);
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse; i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse; i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        legen.skrivMilitaerResept(legemiddelet, pasienten, reit);
      }

      if (type == "p") {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;

        //skal finne legen

        System.out.println("Skriv inn legens navn: ");
        String legeNavn = leggTil.next();

        System.out.println("Skriv inn pasientents id: ");
        int pasientID = leggTil.nextInt();

        System.out.println("Skriv inn legemiddelets nummer: ");
        int legemiddelNr = leggTil.nextInt();

        System.out.println("Skriv inn reiten: ");
        int reit = leggTil.nextInt();




        for (int i = 0; i < leger.stoerrelse; i++) {
          if (leger.hent(i).hentNavn() == legeNavn) {
            legen = leger.hent(i);
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse; i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse; i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        legen.skrivPResept(legemiddelet, pasienten);
      }

    }

    if (hva == "legemiddel") {
      System.out.println("Hvilken type vil du legge til? narkotisk/vanedannende/vanlig: ");
      String type = leggTil.next();

      if (type == "narkotisk") {
        System.out.println("Skriv inn legemiddelets navn: ");
        String navn = leggTil.next();

        System.out.println("Skriv inn prisen: ");
        double pris = leggTil.nextDouble();

        System.out.println("Skriv inn virkestoff: ");
        double virkestoff = leggTil.nextDouble();

        System.out.println("Skriv inn styrke: ");
        int styrke = leggTil.nextInt();

        Narkotisk nark = new Narkotisk(navn, pris, virkestoff, styrke);
        legemidler.leggTil(nark);
      }

      if (type == "vanedannende") {
        System.out.println("Skriv inn legemiddelets navn: ");
        String navn = leggTil.next();

        System.out.println("Skriv inn prisen: ");
        double pris = leggTil.nextDouble();

        System.out.println("Skriv inn virkestoff: ");
        double virkestoff = leggTil.nextDouble();

        System.out.println("Skriv inn styrke: ");
        int styrke = leggTil.nextInt();



        Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
        legemidler.leggTil(vane);

      }

      if (type == "vanlig") {
        System.out.println("Skriv inn legemiddelets navn: ");
        String navn = leggTil.next();

        System.out.println("Skriv inn prisen: ");
        double pris = leggTil.nextDouble();

        System.out.println("Skriv inn virkestoff: ");
        double virkestoff = leggTil.nextDouble();

        Vanlig van = new Vanlig(navn, pris, virkestoff);
        legemidler.leggTil(van);

      }

    }

  }
  
  private void printHovedmeny() {
    System.out.println("Velg kommando ved å skrive inn tilsvarende tall. \n ");
    System.out.println("0: Avslutte programmet. ");
    System.out.println("1: Skrive oversikt over pasienter, leger, legemiddler, og resepter.");
    System.out.println("2: Opprette og legge til nye elementer i systemet. ");
    System.out.println("3: Bruke en gitt resept fra listen til en pasient. ");
    System.out.println("4: Skrive ut forskjellige former for statistikk. ");
    System.out.println("5: Skrive alle data til fil. ");
  }
  
  public void hovedmeny() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Hovedmeny: ");
    printHovedmeny();
    int kommando = scanner.nextInt();

    while (kommando != 0) {

      if (kommando == 1) { // oppgave E3
        skrivUtInfo();
      }
      if (kommando == 2) { // oppgave E4
        brukerLeggTil();
      }
      if (kommando == 3) { // oppgave E5
        brukResept();
      }
      if (kommando == 4) { // oppgave E6
        System.out.println("Printer ut starisikk: ");
        statistikk();
      }
      if (kommando == 5) { // oppgave E8

      }
      kommando = scanner.nextInt();
      printHovedmeny();
    }
  }
  
  public void skrivUtInfo() {
    System.out.println("Skriver ut leger: ");
    for (Leger legeElement : leger) {
      System.out.println(legeElement);
    }

    System.out.println("Skriver ut pasienter og deres resepter: ");
    for (Pasient pasientElement : pasienter) {
      System.out.println(pasientElement);
      for (Resept resept : pasientElement.hentReseptListe()) {
        System.out.println(resept);
      }
    }

    System.out.println("Skriver ut legemiddler: ");
    for (Leger legemidlerElement : legemidler) {
      System.out.println(legemidlerElement);
    }
  }
  
      public void Statistikk(){

        //Totalt antall utskrevne resepter på vanedannende legemidler ??????
        System.out.println("Totalt antall utskrevne resepter på vanedannende legemidler: " + UtskrevetVannLg);

        //Totalt antall utskrevne resepter på narkotiske legemidler ??????
        System.out.println("Vanedannende legemidler utskrevet til militaeret: " + UtskrevetMilitaerVannLg);


        //antall natkotiske resepter per lege
        System.out.println("Antall narkotiske legemidler hver lege har skrevet ut:");
        for(Lege lege : leger){//List av navnene på alle leger
            System.out.println(lege.hentNavn() + ": " + lege.skrivNarkotiskResept().stoerrelse()); //antall uskrevet narkotisk resepter

            
            //antall pasienter som har minst en gyldig resept
            System.out.println("Antall gyldige resepter på narkotiske legemidler hver pasient har:");
            boolean harNarkotisk = false;
            for(Pasient pasient : pasienter){//List av navnene på alle pasienter
                Lenkeliste<Resept> narkotiskeResepter = new Lenkeliste <>(); //lager en lenkeliste av narkotiske resepter
            
                for(Resept resept : pasient.hentReseptListe()){ //hente Resepter er pasient
                    narkotiskeResepter.append(resept);
                    }

                if(narkotiskeResepter.stoerrelse() > 0) {
                    System.out.println(pasient.hentPasientNavn() + ": " + narkotiskeResepter.stoerrelse());
                    harNarkotisk = true;
                }
                if(!harNarkotisk){
                    System.out.println("Ingen har gyldige narkotiske resepter.");
                }
            }
        }
    }


    public void brukResept(){
        for(Pasient pasient : pasienter){
            for(Resept resept : pasient.hentReseptListe()){
                if(resept.hentReit() > 0){
                    resept.bruk();
                    System.out.println("Brukte​ resept paa ​" + resept.hentLegemiddel().hentNavn() + " gjenvaerende reit: " + resept.hentReit());
                }else{
                    System.out.println("Kunne​ ikke bruke resept paa " + resept.hentLegemiddel().hentNavn() + "(​ingen gjenvaerende reit​)");
                }
            }
        }
    }
  
}

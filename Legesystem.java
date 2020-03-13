import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

public class Legesystem {
  private SortertLenkeliste<Lege> leger; //Lenkeliste med Lege-objekter
  private Lenkeliste<Pasient> pasienter; //Lenkeliste med Pasient-objekter
  private Lenkeliste<Legemiddel> legemidler; //Lenkeliste med Legemiddel-objekter
  private String filnavn;

  public Legesystem(String filnavn) throws FileNotFoundException, UlovligUtskrift, InputMismatchException {
    leger = new SortertLenkeliste<>();
    pasienter = new Lenkeliste<>();
    legemidler = new Lenkeliste<>();
    this.filnavn = filnavn;

    lesFraFil(filnavn);
    hovedmeny();
  }

  private void printHovedmeny() {
    //metoden skriver ut valgmulighetene til brukeren, og kalles på i metoden
    //hovedmeny()

    System.out.println("\nHovedmeny: ");
    System.out.println("0: Avslutte programmet. ");
    System.out.println("1: Skrive oversikt over pasienter, leger, legemiddler, og resepter.");
    System.out.println("2: Opprette og legge til nye elementer i systemet. ");
    System.out.println("3: Bruke en gitt resept fra listen til en pasient. ");
    System.out.println("4: Skrive ut forskjellige former for statistikk. ");
    System.out.println("5: Skrive alle data til fil. \n");
  }

  private void printStatistikk() {
    //metoden skriver ut valgmulighetene til brukeren, og kalles på i metoden
    //statistikk()

    System.out.println("Hva vil du se statistikken på? ");
    System.out.println("0: Tilbake til hovedmeny.");
    System.out.println("1: Totalt antall utskrevne resepter på vanedannende legemidler.");
    System.out.println("2: Totalt antall utskrevne resepter på narkotiske legemidler.");
    System.out.println("3: Utskrevne narkotiske resepter per lege.");
    System.out.println("4: Pasienter med gyldig resept på narkotiske legemidler.");
    System.out.println("5: All statisk informasjon.");

  }

  public void hovedmeny() throws UlovligUtskrift, NumberFormatException, FileNotFoundException, InputMismatchException {
    //hovedmenyen. Metoden gjør det mulig for brukeren å de forskjellige
    //tingene som den presenteres for i printHovedmeny()

    Scanner scanner = new Scanner(System.in);

    printHovedmeny();
    System.out.println("Velg kommando ved å skrive inn tilsvarende tall: ");
    int kommando; //deklarerer en variabel som får sin verdi av brukeren

    try {
      System.out.print("> ");
      kommando = scanner.nextInt();
      System.out.println("");

    }
    catch (InputMismatchException e) {
      System.out.println("\nUgyldig input. Avslutter programmet.\n");
      kommando = 0;
    }

    //hvis brukeren taster inn et heltall går man videre til while-løkken
    //hvis ikke avslutter vi programmet


    while (kommando != 0) {

      if (kommando == 1) { // oppgave E3
        skrivUtInfo();
      }
      else if (kommando == 2) { // oppgave E4
        brukerLeggTil();
      }
      else if (kommando == 3) { // oppgave E5
        brukResept();
      }
      else if (kommando == 4) { // oppgave E6
        statistikk();
      }
      else if (kommando == 5) { // oppgave E8
        skrivTilFil();
      }
      else {
        System.out.println("Ugyldig input. Prøv på nytt.");
      }
      printHovedmeny();
      System.out.println("Velg kommando ved å skrive inn tilsvarende tall:");
      try {
        System.out.print("> ");
        kommando = scanner.nextInt();
        System.out.println("");
      }
      catch (InputMismatchException e) {
        System.out.println("\nUgyldig input. Avslutter programmet.\n");
        kommando = 0;
      }
    }

  }

  public void skrivUtInfo() {


    System.out.println("Skriver ut leger: ");
    for (int i = 0; i < leger.stoerrelse(); i++) {
      System.out.println(leger.hent(i));

    }

    System.out.println("\nSkriver ut pasienter og deres resepter: ");
    for (int i = 0; i < pasienter.stoerrelse(); i++) {
      System.out.println(pasienter.hent(i));
      String reseptene = "\nRESEPTUTSKRIFT: \n";
      Stabel<Resept> rListe = pasienter.hent(i).hentReseptListe();



      for (int o = 0; o < rListe.stoerrelse(); o++) {
        if (rListe.hent(o) instanceof PResept) {
          reseptene += "\n" + rListe.hent(o) + "\n";
        }
        else if (rListe.hent(o) instanceof BlaaResept) {
          reseptene += "\n" + rListe.hent(o) + "\n";
        }
        else if (rListe.hent(o) instanceof MilitaerResept) {
          reseptene += "\n" + rListe.hent(o) + "\n";
        }
        else if (rListe.hent(o) instanceof HvitResept) {
          reseptene += "\n" + rListe.hent(o) + "\n";
        }

      }
      if (reseptene.equals("\nRESEPTUTSKRIFT: \n")) {
        reseptene = "";
      }
      System.out.println(reseptene);
    }

    System.out.println("\n Skriver ut legemiddler: ");
    String legemidlene = "\nLEGEMIDDELUTSKRIFT: \n";
    for (int i = 0; i < legemidler.stoerrelse(); i++) {


      if (legemidler.hent(i) instanceof Vanlig) {
        Vanlig legemidlerElement1 = (Vanlig) legemidler.hent(i);
        legemidlene += "\n" + legemidler.hent(i) + "\n";
      }

      else if (legemidler.hent(i) instanceof Narkotisk) {
        Narkotisk legemidlerElement2 = (Narkotisk) legemidler.hent(i);
        legemidlene += "\n" + legemidler.hent(i) + "\n";
      }

      else if (legemidler.hent(i) instanceof Vanedannende) {
        Vanedannende legemidlerElement3 = (Vanedannende) legemidler.hent(i);
        legemidlene += "\n" + legemidler.hent(i) + "\n";
      }
    }
    System.out.println(legemidlene);
  }

  public void statistikk() { //viser statistikk om elementene i systemet


    Lenkeliste<Resept> vanndanResept = new Lenkeliste<>();
    Lenkeliste<Resept> narkoResept = new Lenkeliste<>();

    for (int i = 0; i < leger.stoerrelse(); i++) {//henter reseptliste per lege
      Lenkeliste<Resept> rListe = leger.hent(i).hentReseptliste();


      for (int j = 0; j < rListe.stoerrelse(); j++) {//henter legemiddel i resepter

        if (rListe.hent(j).hentLegemiddel() instanceof Narkotisk) {//hvis det er Narkotisk, legges den i listen til narkoLegemiddel
          narkoResept.leggTil(rListe.hent(j));

        }
        else if (rListe.hent(j).hentLegemiddel() instanceof Vanedannende) {//hvis det er Vanndannende, legges den i listen til vannLegemiddel
          vanndanResept.leggTil(rListe.hent(j));
        }

      }
    }

    Scanner tast = new Scanner(System.in);

    printStatistikk();

    System.out.println("\nTast inn tall som tilsvarer informasjonen du ønsker å se: ");

    int svar;
    //spørre brukeren om input
     //hvis brukeren taster inn et heltall printes ut informasjon
    //hvis ikke printes ut feil melding
    try {
      System.out.print("> ");
      svar = tast.nextInt();
      System.out.println("");

    }
    catch (InputMismatchException e) {
      System.out.println("\nUgyldig input. Går tilbake til hovedmeny.\n");
      return;
    }

    if (svar == 0) {
      return;
    }

    else if (svar == 1) {//Totalt utskrevne resepter med vanndanende legemiddler
      System.out.println("Totalt antall utskrevne resepter på vanedannende legemidler: " + vanndanResept.stoerrelse() + "\n");
    }
    else if (svar == 2) {//Totalt utskrevne resepter med narkotisk legemidler
      System.out.println("Totalt antall utskrevne resepter på narkotiske legemidler: " + narkoResept.stoerrelse() + "\n");
    }
    else if (svar == 3) {
      //resepter med natkotiske legemiddel per lege
       //her lister man opp navnene på alle leger som har skrevet ut minst en resept på narko legemiddel
      System.out.println("Antall narkotiske legemidler hver lege har skrevet ut: \n");
      for(int s = 0; s < leger.stoerrelse(); s++) {
        int n = 0;
        if (leger.hent(s) instanceof Spesialist) {

          Lenkeliste<Resept> liste = leger.hent(s).hentReseptliste();
          for (int r = 0; r < liste.stoerrelse(); r++) {
            if (liste.hent(r).hentLegemiddel() instanceof Narkotisk) {
              n++;

            }
          }
        }
        System.out.println(leger.hent(s).hentNavn() + ": " + n + " narkotiske legemidler");
      }
    }
    else if (svar == 4) {
      // pasienter som har minst en gyldig resept
      System.out.println("Antall gyldige resepter på narkotiske legemidler hver pasient har:\n");
      boolean harNarkotisk = false;
      for(Pasient pasient : pasienter){//List av navnene på alle pasienter
        Lenkeliste<Resept> narkotiskeResepter = new Lenkeliste <>(); //lager en lenkeliste av narkotiske resepter

        Stabel<Resept> resListe = pasient.hentReseptListe();//lager en lenkeliste av resepter til hver pasient

        for(int y = 0; y < resListe.stoerrelse(); y++){//hvis legemiddel per resept er narkotisk settes det i listen
          if (resListe.hent(y).hentLegemiddel() instanceof Narkotisk) {
            narkotiskeResepter.leggTil(resListe.hent(y));
          }
        }

        if(narkotiskeResepter.stoerrelse() > 0) {//hvis listens størrelse er større enn null det er gyldig 
          System.out.println(pasient.hentPasientNavn() + ": " + narkotiskeResepter.stoerrelse());
          harNarkotisk = true;
        }
      }

      if(!harNarkotisk){//hvis ikke gyldig printes en melding 
        System.out.println("\nIngen har gyldige narkotiske resepter.");
      }
    }
    else if (svar == 5) {//printes ut alle statiak informasjon 
      System.out.println("Totalt antall utskrevne resepter på vanedannende legemidler: " + vanndanResept.stoerrelse() + "\n");
      System.out.println("Totalt antall utskrevne resepter på narkotiske legemidler: " + narkoResept.stoerrelse() + "\n");


      // resepter med natkotiske legemiddel per lege
      System.out.println("\nAntall narkotiske legemidler hver lege har skrevet ut: \n");
      for(int s = 0; s < leger.stoerrelse(); s++){
        int n = 0;
        if (leger.hent(s) instanceof Spesialist) {

          Lenkeliste<Resept> liste = leger.hent(s).hentReseptliste();
          for (int r = 0; r < liste.stoerrelse(); r++) {
            if (liste.hent(r).hentLegemiddel() instanceof Narkotisk) {
              n++;

            }
          }
        }
        System.out.println(leger.hent(s).hentNavn() + ": " + n + " narkotiske legemidler");
      }

      // pasienter som har minst en gyldig resept
      System.out.println("\nAntall gyldige resepter på narkotiske legemidler hver pasient har:\n");
      boolean harNarkotisk = false;
      for(Pasient pasient : pasienter){
        Lenkeliste<Resept> narkotiskeResepter = new Lenkeliste <>(); 

        Stabel<Resept> resListe = pasient.hentReseptListe();

        for(int y = 0; y < resListe.stoerrelse(); y++){
          if (resListe.hent(y).hentLegemiddel() instanceof Narkotisk) {
            narkotiskeResepter.leggTil(resListe.hent(y));
          }
        }

        if(narkotiskeResepter.stoerrelse() > 0) {
          System.out.println(pasient.hentPasientNavn() + ": " + narkotiskeResepter.stoerrelse());
          harNarkotisk = true;
        }
      }

      if(!harNarkotisk){
        System.out.println("\nIngen har gyldige narkotiske resepter.");
      }
    }

    else {//hvis det er ugyldig input, går man tilbake til hovedmeny
      System.out.println("Ugyldig input. Går tilbake til hovedmeny.");
      return;
    }
  }


  private void brukResept() {//mulighet om å bruke en resept
    Scanner input = new Scanner(System.in);

    System.out.println("Hvilken pasient vil du se resepter for?");

    int pasientMedReseptNummer = 0; //teller pasienter med resept nummer
    Lenkeliste<Pasient> pasienterMedResept = new Lenkeliste<>(); //list for pasienter som har resept

    for (Pasient pasient : pasienter) { //går gjennom alle pasienter
      if (pasient.hentReseptListe().stoerrelse() >= 0) { //hvis resepter på en pasien er større enn null
        System.out.println(pasientMedReseptNummer + ": " + pasient.hentPasientNavn() + " (fnr " + pasient.hentFoedselnummer() + ")");
        pasienterMedResept.leggTil(pasient); //legger vi pasienten in i lista
        pasientMedReseptNummer++;

      }
    }

      if (pasienterMedResept.stoerrelse() > 0) { //hvis størrelsen til lista er større enn null
        int valgtPasientNummer;
        System.out.print("> ");

        try {
          valgtPasientNummer = Integer.parseInt(input.nextLine()); //spørre man om pasient nr. som input
          System.out.println("");
        }

        catch (NumberFormatException e) {//ugyldig input, tilbak til hovedmeny
          System.out.println("Ugyldig input. Går tilbake til hovedmeny.");
          return;
        }

        if (pasienter.hent(valgtPasientNummer).hentReseptListe().stoerrelse() == 0) {//if pasientens resept størrelse er lik null
          System.out.println("Ingen resepter å bruke.");
          return;
        }

        if (valgtPasientNummer >= 0 && valgtPasientNummer < pasienterMedResept.stoerrelse()) { //hvis input er større eller lik null og mindre enn størrelse til lista
          Pasient valgtPasient = pasienterMedResept.hent(valgtPasientNummer); //henter pasient info

          System.out.println("Valgt pasient: " + valgtPasient.hentPasientNavn() + " (fnr "+ valgtPasient.hentFoedselnummer()+ ")\n" + "Hvilken resept vil du bruke?");

          int reseptNummer = 0; //teller nummeret til resept
          for (Resept resept : valgtPasient.hentReseptListe()) { //velger resept til pasienten
            System.out.println(reseptNummer + ": " + resept.hentLegemiddel().hentNavn() + " (" + resept.hentReit() + " reit)");
            reseptNummer++;
          }

          int valgtReseptNummer;
          System.out.print("> ");

          try {
            valgtReseptNummer = Integer.parseInt(input.nextLine()); //velger resept ved input
            System.out.println("");
          }

          catch (NumberFormatException e) {//catch ugyldige input
            System.out.println("Ugyldig input. Går tilbake til hovedmeny.");
            return;
          }

          try {//trekker fra reit i resept (bruke resept)
            Resept valgtResept = valgtPasient.hentReseptListe().hent(valgtReseptNummer);
            valgtResept.bruk();

            System.out.println("Brukte resept paa " + valgtResept.hentLegemiddel().hentNavn() + ". Antall gjenverende reit: " + valgtResept.hentReit());
            }
          catch(Exception e){
              System.out.println("Ikke gyldig input.");
          }
        }
      }
    }

  private void lesFraFil(String filnavn) throws FileNotFoundException, UlovligUtskrift, NumberFormatException {
    //metoden skal lese inn informasjon fra fil og opprette instanser av
    //Pasient, Lege, Legemiddel og Resept. Disse settes inn i listene vi
    //opprettet i konstruktøren

    Scanner sc = null;

    try {
      sc = new Scanner(new File(filnavn));
    }

    catch (FileNotFoundException e) {
      System.out.println("\nFant ikke fil. Avslutter programmet.\n");
      System.exit(1);
    }

    //prøver å lese av fil og hvis den ikke finnes avslutter vi programmet

    String hva = sc.nextLine(); //linje som vi ikke trenger...

    while (sc.hasNextLine()) {
      String linje = sc.nextLine(); //leser første linje
      while (linje.charAt(0) != '#') {
        //så lenge linje ikke starter med '#' skal vi fortsette å lese av filen
        String[] info = linje.split(",");
        //lager array av det vi leser inn, splitter på ','
        String navn = info[0];
        //her er første element lik navnet på pasienten
        String fnr = info[1];
        //her er andre element lik fødselsnummeret

        Pasient pasient = new Pasient(navn, fnr);
        //oppretter instans av Pasient
        pasienter.leggTil(pasient);
        //objektet legges til i listen med pasienter
        linje = sc.nextLine();
        //leser neste linje - 'hopper' til neste linje
      }

      break;
      //vi vil ut av while-løkken om vi kommer hit
    }

    while (sc.hasNextLine()) {
      String linje = sc.nextLine();
      while (linje.charAt(0) != '#') {
        String[] info = linje.split(",");

        try {
          String navn = info[0];
          String type = info[1];
          double pris = Double.parseDouble(info[2]);
          double virkestoff = Double.parseDouble(info[3]);
          int styrke = 0;
          //må sette den lik noe...

          linje = sc.nextLine();

          if (info.length == 5) {
            styrke = Integer.parseInt(info[4]);
            //ikke alle har styrke
          }

          if (type.equals("narkotisk")) {
            Narkotisk nark = new Narkotisk(navn, pris, virkestoff, styrke);
            legemidler.leggTil(nark);

          }

          else if (type.equals("vanedannende")) {
            Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
            legemidler.leggTil(vane);
          }

          else if (type.equals("vanlig")) {
            Vanlig van = new Vanlig(navn, pris, virkestoff);
            legemidler.leggTil(van);
          }

          //oppretter instanser av de ulike Legemiddel-typene

        }

        catch (NumberFormatException e){
          linje = sc.nextLine();

          //om vi leser inn en string som egentlig skal være int, catcher
          //NumberFormatException og 'hopper' til neste linje

        }
      }
      break;
      //vil ut av while-løkken
    }

    while (sc.hasNextLine()) {
      String linje = sc.nextLine();


      while (linje.charAt(0) != '#') {
        String[] info = linje.split(",");

        try {
          String navn = info[0];
          int kontrollid = Integer.parseInt(info[1]);

          linje = sc.nextLine();

          if (kontrollid != 0) {
            Spesialist lege = new Spesialist(navn, kontrollid);
            leger.leggTil(lege);

            //spesialist om kontrollid er noe annet enn 0

          }

          else {
            Lege lege = new Lege(navn);
            leger.leggTil(lege);

            //lege om kontrollid == 0
          }
        }

        catch (NumberFormatException e) {
          linje = sc.nextLine();

        }
      }
      break;
    }

    while (sc.hasNextLine()) {
      String linje = sc.nextLine();

      while (linje.charAt(0) != '#') {
        String[] info = linje.split(",");

        try {
          int legemiddelNr = Integer.parseInt(info[0]);
          String legeNavn = info[1];
          int pasientID = Integer.parseInt(info[2]);
          String type = info[3];

          int reit = 0;

          if (info.length == 5) {
            reit = Integer.parseInt(info[4]);

            //ikke alle har reit
          }

          Lege legen = null;
          Pasient pasienten = null;
          Legemiddel legemiddelet = null;


          if (pasientID < pasienter.stoerrelse() && legemiddelNr < legemidler.stoerrelse()) {
            //om pasientID er større en antallet pasienter-1 så finnes ikke pasienten
            //det samme gjedler for legemiddelet

            for (int i = 0; i < leger.stoerrelse(); i++) {

              //skal finne legen (objektet)

              if (leger.hent(i).hentNavn().equals(legeNavn)) {
                  legen = leger.hent(i);
              }
            }

            //skal finne pasienten (objektet)

            for (int i = 0; i < pasienter.stoerrelse(); i++) {
              if (pasienter.hent(i).hentPasientId() == pasientID) {
                pasienten = pasienter.hent(i);
              }
            }

            //skal finne legemiddelet (objektet)

            for (int i = 0; i < legemidler.stoerrelse(); i++) {
              if (legemidler.hent(i).hentId() == legemiddelNr) {
                if (legemidler.hent(i) instanceof Vanlig) {
                  legemiddelet = legemidler.hent(i);
                  legemiddelet = (Vanlig) legemiddelet;
                }
                else if (legemidler.hent(i) instanceof Vanedannende) {
                  legemiddelet = legemidler.hent(i);
                  legemiddelet = (Vanedannende) legemiddelet;
                }

                else if (legemidler.hent(i) instanceof Narkotisk) {
                  legemiddelet = legemidler.hent(i);
                  legemiddelet = (Narkotisk) legemiddelet;
                }
              }
            }

            //bruker skrivResept-metodene for å legge til resepter

            if (type.equals("hvit")) {

              try {
                HvitResept hv = legen.skrivHvitResept(legemiddelet, pasienten, reit);

                pasienten.leggTilResept(hv);

              }

              catch (UlovligUtskrift e) {
                //om en vanlig lege prøver å skrive ut resept på narkotisk
                //legemiddel
              }
            }

            else if (type.equals("blaa")) {

              try {
                BlaaResept bl = legen.skrivBlaaResept(legemiddelet, pasienten, reit);

                pasienten.leggTilResept(bl);

              }

              catch (UlovligUtskrift e) {
              }
            }

            else if (type.equals("militaer")) {

              try {
                MilitaerResept ml = legen.skrivMilitaerResept(legemiddelet, pasienten, reit);

                pasienten.leggTilResept(ml);

              }
              catch (UlovligUtskrift e) {

              }
            }

            else if (type.equals("p")) {

              try {
                PResept pr = legen.skrivPResept(legemiddelet, pasienten);

                pasienten.leggTilResept(pr);

              }
              catch (UlovligUtskrift e) {

              }
            }
          }

          try {
            linje = sc.nextLine();
            //sjekker om det finnes en neste linje
          }

          catch (NoSuchElementException e) {
            break;
            //går ut av while-løkken om det ikke er flere linjer å lese
          }

        }

        catch (NumberFormatException e) {
          linje = sc.nextLine();

        }
      }
    }
    sc.close();
    //ferdig med innlesning, 'closer' scanner
  }


  public void brukerLeggTil() throws UlovligUtskrift, NumberFormatException, FileNotFoundException, InputMismatchException {
    Scanner leggTil = new Scanner(System.in);
    String hva; //får verdi av brukeren

    System.out.println("Hva vil du legge til? lege/pasient/resept/legemiddel");
    System.out.print("> ");
    hva = leggTil.next();
    System.out.println("");


    if (!hva.equals("lege") && !hva.equals("pasient") && !hva.equals("resept") && !hva.equals("legemiddel")) {
      System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");
      return;

      //om brukeren skriver noe annet enn forventet så sendes den tilbake
      //til hovedmenyen
    }

    if (hva.equals("lege")) {
      System.out.println("Skriv inn legens navn: ");
      System.out.print("> ");
      String navn = leggTil.next();
      System.out.println("");
      System.out.println("Skriv inn kontrollid: (0 hvis vanlig lege)");

      try {
        System.out.print("> ");
        int kontrollid = leggTil.nextInt();
        System.out.println("");

        //brukeren må skrive inn et heltall

        if (kontrollid != 0) {
          Spesialist spes = new Spesialist(navn, kontrollid);
          leger.leggTil(spes);

          //Spesialist om kontrollid != 0

          System.out.println("Har lagt til legen. Går tilbake til hovedmeny.");

          return;

        }

        Lege lege = new Lege(navn);

        leger.leggTil(lege);

        //Lege om kontrollid == 0

        System.out.println("Har lagt til legen. Går tilbake til hovedmeny.");

      }
      catch (InputMismatchException e) {
        System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
        return;
      }

    }

    else if (hva.equals("pasient")) {

      System.out.println("Skriv inn pasientens navn: ");
      System.out.print("> ");
      String navn = leggTil.next();
      System.out.println("");
      System.out.println("Skriv inn pasientens fødselsnummer: ");
      System.out.print("> ");
      String fnr = leggTil.next();
      System.out.println("");

      Pasient pasient = new Pasient(navn, fnr);
      pasienter.leggTil(pasient);

      System.out.println("Har lagt til pasienten. Går tilbake til hovedmeny.");

    }

    else if (hva.equals("resept")) {
      System.out.println("Hvilken type vil du legge til? hvit/blaa/militaer/p");
      System.out.print("> ");
      String type = leggTil.next();
      System.out.println("");

      if (!type.equals("hvit") && !type.equals("blaa") && !type.equals("militaer") && !type.equals("p")) {
        System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

        return;
      }

      if (type.equals("hvit")) {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;
        int pasientID = 0;
        int legemiddelNr = 0;
        int reit = 0;


        System.out.println("Skriv inn legens navn: ");
        System.out.print("> ");
        String legeNavn = leggTil.next();
        System.out.println("");

        System.out.println("Skriv inn pasientents id: ");

        try {
          System.out.print("> ");
          pasientID = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");

          return;
        }

        System.out.println("Skriv inn legemiddelets nummer: ");

        try {
          System.out.print("> ");
          legemiddelNr = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");

          return;
        }

        System.out.println("Skriv inn reiten: ");
        try {
          System.out.print("> ");
          reit = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        //finner legen ...

        for (int i = 0; i < leger.stoerrelse(); i++) {
          if (leger.hent(i).hentNavn().equals(legeNavn)) {
            if (leger.hent(i) instanceof Spesialist) {
              legen = leger.hent(i);
              legen = (Spesialist) legen;
            }
            else {
              legen = leger.hent(i);
            }
          }
        }

        //hvis vi ikke finner en lege vil det ikke kunne skrives resept

        if (legen == null) {
          System.out.println("\nUgyldig utskivende lege.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse(); i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        //må være gyldig pasient

        if (pasienten == null) {
          System.out.println("Ugyldig pasient.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {

            return;
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse(); i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        //må være gyldig legemiddel

        if (legemiddelet == null) {
          System.out.println("\nUgyldig legemiddel.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            //tilbake til hovedmeny
            return;
          }
        }

        try {
          HvitResept hv = legen.skrivHvitResept(legemiddelet, pasienten, reit);

          pasienten.leggTilResept(hv);

          System.out.println("Har lagt til resepten. Går tilbake til hovedmeny.");
        }

        catch (UlovligUtskrift e) {
          System.out.println("\nUlovlig input. Går tilbake til hovedmenyen.");
          return;
        }

      }

      else if (type.equals("blaa")) {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;
        int pasientID = 0;
        int legemiddelNr = 0;
        int reit = 0;

        System.out.println("Skriv inn legens navn: ");
        System.out.print("> ");
        String legeNavn = leggTil.next();
        System.out.println("");

        System.out.println("Skriv inn pasientents id: ");

        try {
          System.out.print("> ");
          pasientID = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn legemiddelets nummer: ");

        try {
          System.out.print("> ");
          legemiddelNr = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn reiten: ");


        try {
          System.out.print("> ");
          reit = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        for (int i = 0; i < leger.stoerrelse(); i++) {
          if (leger.hent(i).hentNavn().equals(legeNavn)) {
            if (leger.hent(i) instanceof Spesialist) {
              legen = leger.hent(i);
              legen = (Spesialist) legen;
            }
            else {
              legen = leger.hent(i);
            }
          }
        }

        if (legen == null) {
          System.out.println("\nUgyldig utskivende lege.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse(); i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        if (pasienten == null) {
          System.out.println("\nUgyldig pasient.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse(); i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        if (legemiddelet == null) {
          System.out.println("\nUgyldig legemiddel.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        try {
          BlaaResept bl = legen.skrivBlaaResept(legemiddelet, pasienten, reit);

          pasienten.leggTilResept(bl);

          System.out.println("Har lagt til resepten. Går tilbake til hovedmeny.");
        }

        catch (UlovligUtskrift e) {
          System.out.println("\nUlovlig input. Går tilbake til hovedmenyen.");
          return;
        }
      }

      else if (type.equals("militaer")) {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;
        int pasientID = 0;
        int legemiddelNr = 0;
        int reit = 0;


        System.out.println("Skriv inn legens navn: ");
        System.out.print("> ");
        String legeNavn = leggTil.next();

        System.out.println("Skriv inn pasientents id: ");
        try {
          System.out.print("> ");
          pasientID = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn legemiddelets nummer: ");


        try {
          System.out.print("> ");
          legemiddelNr = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn reiten: ");


        try {
          System.out.print("> ");
          reit = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }


        for (int i = 0; i < leger.stoerrelse(); i++) {
          if (leger.hent(i).hentNavn().equals(legeNavn)) {
            if (leger.hent(i) instanceof Spesialist) {
              legen = leger.hent(i);
              legen = (Spesialist) legen;
            }
            else {
              legen = leger.hent(i);
            }
          }
        }

        if (legen == null) {
          System.out.println("\nUgyldig utskivende lege.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse(); i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        if (pasienten == null) {
          System.out.println("\nUgyldig pasient.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse(); i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        if (legemiddelet == null) {
          System.out.println("\nUgyldig legemiddel.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        try {
          MilitaerResept ml = legen.skrivMilitaerResept(legemiddelet, pasienten, reit);

          pasienten.leggTilResept(ml);

          System.out.println("Har lagt til resepten. Går tilbake til hovedmeny.");
        }

        catch (UlovligUtskrift e) {
          System.out.println("\nUlovlig input. Går tilbake til hovedmenyen.");
          return;
        }

      }

      else if (type.equals("p")) {
        Lege legen = null;
        Pasient pasienten = null;
        Legemiddel legemiddelet = null;
        int pasientID = 0;
        int legemiddelNr = 0;
        int reit = 0;


        System.out.println("Skriv inn legens navn: ");
        System.out.print("> ");
        String legeNavn = leggTil.next();
        System.out.println("");

        System.out.println("Skriv inn pasientents id: ");
        try {
          System.out.print("> ");
          pasientID = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          hovedmeny();
        }

        System.out.println("Skriv inn legemiddelets nummer: ");


        try {
          System.out.print("> ");
          legemiddelNr = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn reiten: ");

        try {
          System.out.print("> ");
          reit = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        for (int i = 0; i < leger.stoerrelse(); i++) {
          if (leger.hent(i).hentNavn().equals(legeNavn)) {
            if (leger.hent(i) instanceof Spesialist) {
              legen = leger.hent(i);
              legen = (Spesialist) legen;
            }
            else {
              legen = leger.hent(i);
            }
          }
        }

        if (legen == null) {
          System.out.println("\nUgyldig utskivende lege.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        //skal finne pasienten

        for (int i = 0; i < pasienter.stoerrelse(); i++) {
          if (pasienter.hent(i).hentPasientId() == pasientID) {
            pasienten = pasienter.hent(i);
          }
        }

        if (pasienten == null) {
          System.out.println("\nUgyldig pasient.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        //skal finne legemiddelet

        for (int i = 0; i < legemidler.stoerrelse(); i++) {
          if (legemidler.hent(i).hentId() == legemiddelNr) {
            legemiddelet = legemidler.hent(i);
          }
        }

        if (legemiddelet == null) {
          System.out.println("\nUgyldig legemiddel.");
          System.out.println("Vil du prøve på nytt? ja/nei");
          System.out.print("> ");
          String fortsette = leggTil.next();
          System.out.println("");

          if (!fortsette.equals("ja") && !fortsette.equals("nei")) {
            System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

            return;
          }

          if (fortsette.equals("ja")) {
            brukerLeggTil();
            return;
          }

          else {
            return;
          }
        }

        try {
          PResept pr = legen.skrivPResept(legemiddelet, pasienten);

          pasienten.leggTilResept(pr);

          System.out.println("Har lagt til resepten. Går tilbake til hovedmeny.");
        }

        catch (UlovligUtskrift e) {
          System.out.println("\nUlovlig input. Går tilbake til hovedmenyen.");
          return;
        }
      }
    }

    else if (hva.equals("legemiddel")) {
      System.out.println("Hvilken type vil du legge til? narkotisk/vanedannende/vanlig: ");
      System.out.print("> ");
      String type = leggTil.next();
      System.out.println("");

      if (!type.equals("narkotisk") && !type.equals("vanedannende") && !type.equals("vanlig")) {
        System.out.println("\nUgyldig input. Går tilbake til hovedmeny.");

        return;
      }

      if (type.equals("narkotisk")) {
        double pris = 0;
        double virkestoff = 0;
        int styrke = 0;


        System.out.println("Skriv inn legemiddelets navn: ");
        System.out.print("> ");
        String navn = leggTil.next();
        System.out.println("");

        System.out.println("Skriv inn prisen: ");


        try {
          System.out.print("> ");
          pris = leggTil.nextDouble();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn virkestoff: ");
        try {
          System.out.print("> ");
          virkestoff = leggTil.nextDouble();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn styrke: ");
        try {
          System.out.print("> ");
          styrke = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        Narkotisk nark = new Narkotisk(navn, pris, virkestoff, styrke);
        legemidler.leggTil(nark);

        System.out.println("Har lagt til legemiddelet. Går tilbake til hovedmeny.");
      }

      else if (type.equals("vanedannende")) {
        double pris = 0;
        double virkestoff = 0;
        int styrke = 0;

        System.out.println("Skriv inn legemiddelets navn: ");
        System.out.print("> ");
        String navn = leggTil.next();
        System.out.println("");

        System.out.println("Skriv inn prisen: ");
        try {
          System.out.print("> ");
          pris = leggTil.nextDouble();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn virkestoff: ");
        try {
          System.out.print("> ");
          virkestoff = leggTil.nextDouble();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn styrke: ");
        try {
          System.out.print("> ");
          styrke = leggTil.nextInt();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }


        Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
        legemidler.leggTil(vane);

        System.out.println("Har lagt til legemiddelet. Går tilbake til hovedmeny.");

      }

      else if (type.equals("vanlig")) {
        double pris = 0;
        double virkestoff = 0;
        int styrke = 0;

        System.out.println("Skriv inn legemiddelets navn: ");
        System.out.print("> ");
        String navn = leggTil.next();
        System.out.println("");

        System.out.println("Skriv inn prisen: ");

        try {
          System.out.print("> ");
          pris = leggTil.nextDouble();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        System.out.println("Skriv inn virkestoff: ");
        try {
          System.out.print("> ");
          virkestoff = leggTil.nextDouble();
          System.out.println("");
        }
        catch (InputMismatchException e) {
          System.out.println("\nUgyldig input. Går tilbake til hovedmenyen.");
          return;
        }

        Vanlig van = new Vanlig(navn, pris, virkestoff);
        legemidler.leggTil(van);

        System.out.println("Har lagt til legemiddelet. Går tilbake til hovedmeny.");

      }
    }
  }

  public void skrivTilFil() throws FileNotFoundException {
    Scanner fil = new Scanner(System.in);
    System.out.println("Hva vil du kalle filen? ");
    System.out.print("> ");
    String filnavn = fil.next(); //navn på filen
    File filen = new File(filnavn);

    PrintWriter pw = new PrintWriter(filen); //lar deg skrive til fil

    pw.print("# Pasienter (navn, fnr) \n");

    for (Pasient pas : pasienter) {
      String navn = pas.hentPasientNavn();
      String fnr = pas.hentFoedselnummer();
      pw.print("\n" + navn + "," + fnr + "\n");
    }

    //går gjennom listen med pasienter å skriver inn i filen

    pw.print("\n \n# Legemidler (navn, type, pris, virkestoff, [styrke])\n");

    for (int i = 0; i < legemidler.stoerrelse(); i++) {
      String navn = legemidler.hent(i).hentNavn();
      String typen;
      double pris = legemidler.hent(i).hentPris();
      double virkestoff = legemidler.hent(i).hentVirkestoff();
      int styrke = 0;

      if (legemidler.hent(i) instanceof Narkotisk) {
        typen = "narkotisk";
        Narkotisk legemid1 = (Narkotisk) legemidler.hent(i);
        styrke = legemid1.hentNarkotiskStyrke();
        pw.print("\n" + navn + "," + typen + "," + pris + "," + virkestoff + "," + styrke + "\n");

      }

      else if (legemidler.hent(i) instanceof Vanedannende) {
        typen = "vanedannende";
        Vanedannende legemid2 = (Vanedannende) legemidler.hent(i);
        styrke = legemid2.hentVanedannendeStyrke();
        pw.print("\n" + navn + "," + typen + "," + pris + "," + virkestoff + "," + styrke + "\n");

      }

      else if (legemidler.hent(i) instanceof Vanlig) {
        typen = "vanlig";
        pw.print("\n" + navn + "," + typen + "," + pris + "," + virkestoff + "\n");

      }
    }

    //går gjennom legemidler (listen) for å skrive inn hvert legemiddel
    //inn i filen. Vi bruker 'instanceof' for å finne type

    pw.print("\n \n# Leger (navn, kontrollid / 0 hvis vanlig lege)\n");

    for (int p = 0; p < leger.stoerrelse(); p++) {
      String navnet = leger.hent(p).hentNavn();
      int kontrollid = 0;

      if (leger.hent(p) instanceof Spesialist) {
        Spesialist leg1 = (Spesialist) leger.hent(p);
        kontrollid = leg1.hentKontrollID();
      }

      pw.print("\n" + navnet + "," + kontrollid + "\n");
    }

    //skriver inn hver lege

    pw.print("\n \n# Resepter (legemiddelNummer, legeNavn, pasientID, type, [reit])\n");

    for (int t = 0; t < leger.stoerrelse(); t++) {
      Lenkeliste<Resept> reseptene = leger.hent(t).hentReseptliste();
      String legeNavn = leger.hent(t).hentNavn();

      for (int o = 0; o < reseptene.stoerrelse(); o++) {
        int legemiddelNr = reseptene.hent(o).hentLegemiddel().hentId();
        int pasientID = reseptene.hent(o).hentPasient().hentPasientId();
        String type;
        int reit = 0;

        if (reseptene.hent(o) instanceof HvitResept) {
          type = "hvit";
          reit = reseptene.hent(o).hentReit();

          pw.print("\n" + legemiddelNr + "," + legeNavn + "," + pasientID + "," + type + "," + reit + "\n");

        }

        else if (reseptene.hent(o) instanceof MilitaerResept) {
          type = "militaer";
          reit = reseptene.hent(o).hentReit();

          pw.print("\n" + legemiddelNr + "," + legeNavn + "," + pasientID + "," + type + "," + reit + "\n");

        }

        else if (reseptene.hent(o) instanceof BlaaResept) {
          type = "blaa";
          reit = reseptene.hent(o).hentReit();

          pw.print("\n" + legemiddelNr + "," + legeNavn + "," + pasientID + "," + type + "," + reit + "\n");

        }

        else if (reseptene.hent(o) instanceof PResept) {
          type = "p";

          pw.print("\n" + legemiddelNr + "," + legeNavn + "," + pasientID + "," + type + "\n");

        }
      }

      //reseptene skrives inn. bruker 'instanceof' for å finne typen
    }
    pw.close();
    System.out.println("\nHar skrevet alle data til fil. Går tilbake til hovedmeny.");
  }
}

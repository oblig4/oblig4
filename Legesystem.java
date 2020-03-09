import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Legesystem {
  private SortertLenkeliste<Lege> leger;
  private Lenkeliste<Pasient> pasienter;
  private Lenkeliste<Legemiddel> legemidler;
  private String filnavn;


  public Legesystem(String filnavn) throws FileNotFoundException, UlovligUtskrift {
    leger = new SortertLenkeliste<>();
    pasienter = new Lenkeliste<>();
    legemidler = new Lenkeliste<>();
    this.filnavn = filnavn;

    lesFraFil(filnavn);
    //hovedmeny();
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

}

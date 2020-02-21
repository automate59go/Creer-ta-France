import extensions.CSVFile;

class Main extends Program{

    //Couleurs
    final String ANSI_BLUE = "\u001B[34m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_RESET = "\u001B[0m";
    //Couleurs arrière Plan
    final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    final String ANSI_PURPLE__BACKGROUND = "\u001B[45m";
    final String ANSI_CYAN__BACKGROUND = "\u001B[46m";

    //Variable globale
    int BUDGET = 1500;
    int BONHEUR = 5;
    int ECONOMIE = 5;
    int TECHNOLOGIE = 5;
    Batiments[][] tabBatiment;
    boolean perdu = false;

    void testCreerBatiments(){
        Batiments batiment1 = creerBatiments("maison", 1, 4, 150, 350,'M');
        Batiments batiment2 = creerBatiments("maison", 1, 4, 150, 350,'M');
        assertEquals(toStringBatiments(batiment1),toStringBatiments(batiment2));
        Batiments batiment3 = creerBatiments("maison", 1, 4, 150, 350,'D');
        assertNotEquals(toStringBatiments(batiment1),toStringBatiments(batiment3));
    }

    Batiments creerBatiments(String nom, int indiceL, int indiceC, int revenue, int cout, char c){
        Batiments batiment = new Batiments();
        batiment.indiceColonne = indiceC;
        batiment.indiceLigne = indiceL;
        batiment.nom = nom;
        batiment.revenue = revenue;
        batiment.cout = cout;
        batiment.representation = c;
        batiment.estPose = false;
        return batiment;
    }

    Batiments[] csvFileToTableauBatiments(String fileName){

        CSVFile file = loadCSV(fileName);
        int row = rowCount(file);

        Batiments[] tab = new Batiments[row];

        for(int x = 0; x < row; x++){
            tab[x] = creerBatiments(getCell(file,x,0),stringToInt(getCell(file,x,1)),stringToInt(getCell(file,x,2)),stringToInt(getCell(file,x,3)),stringToInt(getCell(file,x,4)),charAt(getCell(file,x,5),0));
        }
        return tab;
    }


    void phaseBatiments(Batiments[] bdd){

        int choixJoueur = -1;
        String choix;
        if (!tabBatimentVide(bdd)) {
            println("Quel Batiment souhaitez vous poser ? ");
            for (int x = 0; x < length(bdd); x++) {
                if (!bdd[x].estPose) {
                    println(x + " : " + bdd[x].nom + " " + bdd[x].cout + " $");
                }
            }
            println((length(bdd)) + " : Ne pas poser de batiment");

            do {
                print("Votre choix : ");
                choix = readString();
                choixJoueur = stringToIntPerso(choix);
                if (choixJoueur < 0 || choixJoueur > length(bdd)) {
                    println("Votre saisie n'est pas valide, entrez une valeur entre 1 et " + (length(bdd)));
                }
                if (batimentsPoser(choixJoueur,bdd)) {
                    println("Le batiment à déjà été posé, il faut en choisir un de la liste :) ");
                }
            } while (choixJoueur < 0 || choixJoueur > (length(bdd)) || batimentsPoser(choixJoueur, bdd));

            if (choixJoueur == (length(bdd))) {
                println();
            } else {
                placerBatiments(bdd[choixJoueur]);
                BUDGET -= bdd[choixJoueur].cout;
                bdd[choixJoueur].estPose = true;
            }
        }
    }

    void testbatimentsPoser(){
        Batiments[] tab = new Batiments[2];
        tab[0] = creerBatimentsVide();
        tab[1] = creerBatimentsVide();
        tab[1].estPose = true;
        assertTrue(batimentsPoser(1,tab));
        assertFalse(batimentsPoser(0,tab));
    }

    boolean batimentsPoser(int choix, Batiments[] bdd){
        if (!(choix < 0 || choix > (length(bdd)-1))){
            if (bdd[choix].estPose){
                return true;
            }
        }
        return false;
    }

    void testTabBatimentVide(){
        Batiments[] tab = new Batiments[2];
        tab[0] = creerBatimentsVide();
        tab[1] = creerBatimentsVide();
        assertFalse(tabBatimentVide(tab));
        tab[0].estPose = true;
        assertFalse(tabBatimentVide(tab));
        tab[1].estPose = true;
        assertTrue(tabBatimentVide(tab));
    }

    boolean tabBatimentVide(Batiments[] bdd){
        for (int i=0;i<length(bdd);i++){
            if (!bdd[i].estPose){
                return false;
            }
        }
        return true;
    }

    void testCreerBatimentsVide(){
        Batiments vide = creerBatiments("vide", 0, 0, 0, 0,'.');

        assertEquals(toStringBatiments(vide),toStringBatiments(creerBatimentsVide()));
    }

    Batiments creerBatimentsVide(){
        Batiments vide = new Batiments();
        vide.nom = "vide";
        vide.representation = '.';
        vide.cout = 0;
        vide.revenue = 0;
        return vide;
    }

    void testToStringBatiments(){
        Batiments maison = creerBatiments("maison", 1, 4, 150, 350,'M');
        assertEquals("maison 150 350 1 4 M",toStringBatiments(maison));
        assertNotEquals("maison 1 5 5 35 D", toStringBatiments(maison));
    }

    String toStringBatiments (Batiments b){
        return b.nom +" "+ b.revenue +" "+ b.cout +" "+ b.indiceLigne +" "+ b.indiceColonne +" "+ b.representation;
    }

    void placerBatiments(Batiments b){
        tabBatiment[b.indiceLigne][b.indiceColonne] = b;
    }

    void generationMap(){
        tabBatiment = new Batiments[5][5];
        int longTabLig = length(tabBatiment,1);
        int longTabCol = length(tabBatiment,2);
        for (int i = 0; i<longTabLig;i++){
            for (int j=0;j<longTabCol;j++){
                tabBatiment[i][j] = creerBatimentsVide();
            }
        }
    }

    void afficherMap(){
        int longTabLig = length(tabBatiment,1);
        int longTabCol = length(tabBatiment,2);

        for (int i = 0; i<longTabLig;i++){
            print("|        ");
            for (int j=0;j<longTabCol;j++){
                print(" ");
                print(tabBatiment[i][j].representation);
            }
            println();
        }
    }

    void phaseQuestion(Questions[] bddCulture,Questions[] bddLangue,Questions[] bddSience){

        int choixJoueur = -1;
        String choix;

        println("Vous allez répondre a une question pour influancer soit :\n\t\t\t\t\t\t\t(1) La stabilité Economique de votre pays\n\n\t\t\t\t\t\t\t(2) L'avancé Technologique de votre pays\n\n\t\t\t\t\t\t\t(3) Le Bonheur de votre pays\n Que décidez-vous ?");

        do{
            choix = readString();
            choixJoueur = stringToIntPerso(choix);
            if(choixJoueur < 1 || choixJoueur > 3){
                println("Votre saisie n'est pas valide, entrez une valeur entre 1 et 3");
            }

        }while (choixJoueur < 1 || choixJoueur > 3);

        clearScreen();
        cursor(0,0);
        titreHUD();
        print("\n");
        generationHUD();
        print("\n");

        if(quelleQuestion(choixJoueur,bddCulture,bddLangue,bddSience,0)){
            switch(choixJoueur){
                case 1:
                    ECONOMIE++;
                    break;
                case 2:
                    TECHNOLOGIE++;
                    break;
                case 3:
                    BONHEUR++;
                    break;
            }
        }
        else{
            switch(choixJoueur){
                case 1:
                    ECONOMIE--;
                    break;
                case 2:
                    TECHNOLOGIE--;
                    break;
                case 3:
                    BONHEUR--;
                    break;
            }
        }
    }

    void passageTour(){
        println("Prochain Tour dans :");
        print("5");
        delay(1000);
        clearLine();
        cursor(29,0);
        print("4");
        delay(1000);
        clearLine();
        cursor(29,0);
        print("3");
        delay(1000);
        clearLine();
        cursor(29,0);
        print("2");
        delay(1000);
        clearLine();
        cursor(29,0);
        print("1");
        delay(1000);
        clearLine();
        cursor(29,0);
    }

    boolean quelleQuestion(int choixJoueur,Questions[] bddCulture,Questions[] bddLangue,Questions[] bddSience, int nbDeTours){

        if(nbDeTours >= 2){
            println("IL N Y A PLUS DE QUESTION DISPONIBLE");
            return false;
        }

        int ret;

        if(choixJoueur == 1){
            ret = poserQuestion(bddLangue);
            if(ret == -1){
                return quelleQuestion(2,bddCulture,bddLangue,bddSience, ++nbDeTours);
            }
            else if (ret == 0) {
                println(ANSI_RED+"Mauvaise réponse, votre pays en prend un coup.."+ANSI_RESET);
                passageTour();
                return false;
            }
            else if(ret == 1){
                println(ANSI_BLUE+"Bonne réponse ! Super, votre pays devient plus puissant !!"+ANSI_RESET);
                passageTour();
                return true;
            }
        }
        else if(choixJoueur == 2){
            ret = poserQuestion(bddSience);
            if(ret == -1){
                return quelleQuestion(3,bddCulture,bddLangue,bddSience, ++nbDeTours);
            }
            else if (ret == 0) {
                println(ANSI_RED+"Mauvaise réponse, votre pays en prend un coup.."+ANSI_RESET);
                passageTour();
                return false;
            }
            else if(ret == 1){
                println(ANSI_BLUE+"Bonne réponse ! Super, votre pays devient plus puissant !!"+ANSI_RESET);
                passageTour();
                return true;
            }
        }
        else if(choixJoueur == 3){
            ret = poserQuestion(bddCulture);
            if(ret == -1){
                return quelleQuestion(1,bddCulture,bddLangue,bddSience, ++nbDeTours);
            }
            else if (ret == 0) {
                println(ANSI_RED+"Mauvaise réponse, votre pays en prend un coup.."+ANSI_RESET);
                passageTour();
                return false;
            }
            else if(ret == 1){
                println(ANSI_BLUE+"Bonne réponse ! Super, votre pays devient plus puissant !!"+ANSI_RESET);
                passageTour();
                return true;
            }
        }
        return false;
    }

    int poserQuestion(Questions[] tab){

        int len = length(tab);
        int num = alea(len);
        int reponse;
        String choix;

        int compteur = 0;

        while(tab[num].dejaPoser){ // gère les question posé ou non

            compteur++;
            num++;
            if(num >= len){
                num = 0;
            }
            if(compteur == len){
                return -1;
            }
        }
        tab[num].dejaPoser = true;
        println("Voici la question :");
        println(tab[num].question);

        compteur = alea(3);

        for(int i = 0; i < 3; i++){

            if(compteur == 3){
                compteur = 0;
            }

            print("("+ (1+i) +") ");
            switch(compteur){
                case 0:
                    println(tab[num].reponse_1);
                    break;
                case 1:
                    println(tab[num].reponse_2);
                    break;
                case 2:
                    println(tab[num].reponse_3);
                    break;
            }

            compteur++;

        }
        do{
            choix = readString();
            reponse = stringToIntPerso(choix);
            if (reponse<1 || reponse>3){
                println("Veuillez indiquer un nombre comprit entre 1 et 3");
            }
        }while(reponse<1 || reponse>3);

        if((compteur == 1 && reponse == 3) ||
                (compteur == 2 && reponse == 2) ||
                (compteur == 3 && reponse == 1) ){
            return 1;
        }
        return 0;
    }

    void testStringToIntPerso(){
        assertTrue( 42 == stringToIntPerso("42"));
        assertTrue( 599 == stringToIntPerso("  \n\t 599agre"));
        assertTrue( 4 == stringToIntPerso("\n           4afer2"));
        assertTrue( -1 == stringToIntPerso("a"));
        assertTrue( -1 == stringToIntPerso("        a7aezf ef "));
        assertTrue( -1 == stringToIntPerso("    r 1 e a 1 "));
    }

    int stringToIntPerso(String str){
        int ret = 0;
        int i = 0;
        boolean gate = false;
        for(; i < length(str) && (charAt(str,i) == ' ' || charAt(str,i) == '\t' || charAt(str,i) == '\n');i++){
        }
        for(; i < length(str) && (charAt(str,i) >= '0' && charAt(str,i)<='9');i++){
            gate = true;
            ret *= 10;
            ret += (int)charAt(str,i) - (int)'0';
        }
        if(!gate){
            return -1;
        }
        return ret;
    }

    void testCreerQuestions(){
        Questions question1 = creerQuestions("nom","rep1","rep2","rep3");
        Questions question2 = creerQuestions("nom","rep1","rep2","rep3");
        assertEquals(toStringQuestions(question1),toStringQuestions(question2));
    }

    Questions creerQuestions(String questionString, String reponse_1, String reponse_2, String reponse_3){
        Questions question = new Questions();
        question.question = questionString;
        question.reponse_1 = reponse_1;
        question.reponse_2 = reponse_2;
        question.reponse_3 = reponse_3;
        question.dejaPoser = false;

        return question;
    }

    void testToStringQuestions(){
        Questions question = creerQuestions("nom","rep1","rep2","rep3");
        assertEquals("nom rep1 rep2 rep3",toStringQuestions(question));
    }

    String toStringQuestions(Questions question){
        return question.question +" "+ question.reponse_1 +" "+ question.reponse_2 +" "+ question.reponse_3;
    }

    Questions[] csvFileToTableauQuestion(String fileName){

        CSVFile file = loadCSV(fileName);
        int row = rowCount(file);

        Questions[] tab = new Questions[row];

        for(int x = 0; x < row; x++){
            tab[x] = creerQuestions(getCell(file,x,0),getCell(file,x,1),getCell(file,x,2),getCell(file,x,3));
        }

        return tab;
    }

    void testinitialiserBarre(){
        String[] tab = new String[]{" "," "," "," "," "," "," "," "," "," "};
        assertArrayEquals(tab,initialiserBarre());
    }

    String[] initialiserBarre(){
        String[] tab = new String[10];
        for (int i = 0; i<length(tab);i++){
            tab[i] = " ";
        }
        return tab;
    }

    void updateBarre(String[] tab, int objectif){

        if (/*objectif < 11 && objectif > -1*/objectif <= 4) {
            for (int i = 0; i < objectif; i++) {
                tab[i] = ANSI_RED+"="+ANSI_RESET;
            }
        }
        else if (objectif <=10) {
            for (int i = 0; i < objectif; i++) {
                if(objectif<=7){
                    tab[i] = ANSI_BLUE+ "="+ ANSI_RESET;
                }
                else{
                    tab[i] = ANSI_GREEN+ "="+ ANSI_RESET;
                }
            }
        }
        /*else if (objectif > 10){ //Normalement impossible mais on met quand même une sécurité
          for (int i = 0; i<10;i++){
            tab[i] = "=";
          }
        }*/
    }

    void testGanerationBarre(){
        String[] tab = new String[]{"=","=","=","=","=","=","=","=","=","="};
        updateBarre(tab,10);
        String obj = "[";
        for (int i = 0; i<length(tab);i++){
            obj += tab[i];
        }
        obj += "]";
        assertEquals(obj,generationBarre(10));

        tab = new String[]{"=","="," "," ","=","=","=","=","=","="};
        updateBarre(tab,8);
        obj = "[";
        for (int i = 0; i<length(tab);i++){
            obj += tab[i];
        }
        obj += "]";
        assertNotEquals(obj,generationBarre(8));

        tab = new String[]{"=","="," "," "," "," "," "," "," "," "};
        updateBarre(tab,2);
        obj = "[";
        for (int i = 0; i<length(tab);i++){
            obj += tab[i];
        }
        obj += "]";
        assertEquals(obj,generationBarre(2));
    }

    String generationBarre(int objectif){
        String[] tabBarre = initialiserBarre();
        updateBarre(tabBarre,objectif);
        String obj ="[";
        for (int i = 0; i<length(tabBarre);i++){
            obj += tabBarre[i];
        }
        return obj + "]";
    }

    void generationHUD(){
        print( "|\n");  afficherMap();  print("|\n|" + "   Budget " + BUDGET + "          Economie $ " + generationBarre(ECONOMIE) + "   Technologie \uD83E\uDD11  " + generationBarre(TECHNOLOGIE) + "   Bonheur \uD83D\uDE00 " + generationBarre(BONHEUR));
        println("\n");
    }

    void revenu(){
        int longTabLig = length(tabBatiment,1);
        int longTabCol = length(tabBatiment,2);

        for (int i = 0;i<longTabLig;i++){
            for (int j=0;j<longTabCol;j++){
                if (tabBatiment[i][j].representation != '.'){
                    BUDGET += tabBatiment[i][j].revenue;
                }
                if (equals(tabBatiment[i][j].nom,"Ecole") || equals(tabBatiment[i][j].nom,"Faculté")){
                    BUDGET += 100;
                }
                if (equals(tabBatiment[i][j].nom,"Musée") || equals(tabBatiment[i][j].nom,"Théatre")){
                    BONHEUR += 1;
                }
            }
        }
    }

    void cout(){
        int longTabLig = length(tabBatiment,1);
        int longTabCol = length(tabBatiment,2);

        for (int i = 0;i<longTabLig;i++){
            for (int j=0;j<longTabCol;j++){
                if (tabBatiment[i][j].representation != '.'){
                    BUDGET -= (tabBatiment[i][j].cout)/4;
                }
            }
        }
    }
    void testConditionDeVictoire(){
        ECONOMIE = 10;
        assertTrue(conditionDeVictoire());
        ECONOMIE = 5;
        TECHNOLOGIE = 10;
        assertTrue(conditionDeVictoire());
        TECHNOLOGIE = 5;
        BONHEUR = 10;
        assertTrue(conditionDeVictoire());
        BONHEUR = 5;
        assertFalse(conditionDeVictoire());
    }
    boolean conditionDeVictoire(){
        if ((BONHEUR >= 10 || ECONOMIE >= 10 || TECHNOLOGIE >= 10)){
            return true;
        }
        return false;
    }

    void testAlea(){
        int rand = alea(5);
        assertGreaterThanOrEqual(0,rand);
        assertLessThan(5,rand);
        rand = alea(1);
        assertGreaterThanOrEqual(0,rand);
        assertLessThan(1,rand);
    }
    int alea(int MaxNonInclue){//retourne un nombre alea entre 0 et MaxNonInclue-1
        return (int)(random() * MaxNonInclue);
    }

    //Gestion des évênements aléatoires
    void testCreerEvenements(){
        Evenements event1 = creerEvenements("test","desc","0","0","0");
        Evenements event2 = creerEvenements("test","desc","0","0","0");

        assertEquals(toStringEvenement(event1),toStringEvenement(event2));
    }

    Evenements creerEvenements(String nom,String desc, String bonusEco,String malusEco,String maluxBonheur){
        Evenements event = new Evenements();
        event.nom = nom;
        event.desc = desc;
        event.bonusEco = stringToInt(bonusEco);
        event.malusEco = stringToInt(malusEco);
        event.malusBonheur = stringToInt(maluxBonheur);
        event.dejaPoser = false;

        return event;
    }

    void testToStringEvenement(){
        Evenements event = creerEvenements("test","desc","0","0","0");
        assertEquals("test desc 0 0 0",toStringEvenement(event));
    }

    String toStringEvenement(Evenements event){
        return "" + event.nom +" "+ event.desc +" "+ event.bonusEco +" "+ event.malusEco +" "+ event.malusBonheur;
    }

    Evenements[] csvFileToTableauEvenements(String fileName){

        CSVFile file = loadCSV(fileName);
        int row = rowCount(file);
        Evenements[] tab = new Evenements[row];
        for(int x = 0; x < row; x++){
            tab[x] = creerEvenements(getCell(file,x,0),getCell(file,x,1),getCell(file,x,2),getCell(file,x,3),getCell(file,x,4));
        }
        return tab;
    }

    //TODO : à changer avec les csv
    int choiceEventAleatoire(Evenements[] tab){ //retourne -1 si plus aucun evenements disponible
        int len = length(tab);
        int ret = alea(len);
        for(int compteur = 0;tab[ret].dejaPoser; compteur++){
            if(compteur >= len){
                return -1;
            }
            else if(ret < len-1){
                ret++;
            }
            else{
                ret = 0;
            }
        }
        return ret;
    }

    void eventAction(Evenements event){
        BUDGET += event.bonusEco;
        BUDGET -= event.malusEco;
        //println("BONHEUR = "+BONHEUR+"|event.malusBonheur = "+event.malusBonheur+"\nbon-=event = "+ (BONHEUR -= event.malusBonheur));
        BONHEUR -= event.malusBonheur;
    }

    String toStringEvent(Evenements event){
        String eventString = "Oh un nouvel évènement : " + event.nom + " \n" + event.desc;

        if (event.malusBonheur > 0) {
            eventString += "\nVous perdez " + event.malusBonheur + " de bonheur";
        }
        if (event.bonusEco > 0 ) {
            eventString += "\nVous gagnez " + event.bonusEco + " de budget !";
        }
        if (event.malusEco > 0 ) {
            eventString += "\nVous perdez " + event.malusEco + " de budget !";
        }

        return eventString;
    }

    void hudEvent(Evenements event){
        println("#########################################################");
        println(toStringEvent(event));
        println("#########################################################");
    }

    void titreHUD(){
        print(ANSI_BLUE+"#####################################"+ANSI_RESET+"###############"+ANSI_RED+"########################################################\n"+ANSI_RESET +
                ANSI_BLUE+"#   ______                           "+ANSI_RESET+"               "+ANSI_RED+" _______  ______           ______    ______  _______   #\n" +ANSI_RESET+
                ANSI_BLUE+"#  / _____)                          "+ANSI_RESET+" _             "+ANSI_RED+"(_______)(_____ \\    /\\   |  ___ \\  / _____)(_______)  #\n" +ANSI_RESET+
                ANSI_BLUE+"# | /        ____   ____   ____      "+ANSI_RESET+"| |_    ____   "+ANSI_RED+" _____    _____) )  /  \\  | |   | || /       _____     #\n" +ANSI_RESET+
                ANSI_BLUE+"# | |       / ___) / _  ) / _  )    "+ANSI_RESET+"|   _)  / _  |  "+ANSI_RED+"|  ___)  (_____ (  / /\\ \\ | |   | || |      |  ___)    #\n" +ANSI_RESET+
                ANSI_BLUE+"# | \\_____ | |    ( (/ / ( (/ /     "+ANSI_RESET+" | |__ ( ( | |  "+ANSI_RED+"| |            | || |__| || |   | || \\_____ | |_____   #\n" +ANSI_RESET+
                ANSI_BLUE+"#  \\______)|_|     \\____) \\____)   "+ANSI_RESET+"   \\___) \\_||_| "+ANSI_RED+" |_|            |_||______||_|   |_| \\______)|_______)  #\n" +ANSI_RESET+
                ANSI_BLUE+"#                                        "+ANSI_RESET+"               "+ANSI_RED+"                                                   #\n" +ANSI_RESET+
                ANSI_BLUE+"#####################################"+ANSI_RESET+"###############"+ANSI_RED+"########################################################\n"+ANSI_RESET);
    }

    void jouer(){
        println("Voulez-vous jouer ? ");
        print("\t\t\t\t1. Oui je veux commencer une partie\n\t\t\t\t2. Je veux lire les règles\n\t\t\t\t3. Je veux quitter\n");
        print("Choix : ");
        String choice;
        int choix;
        do {
            choice = readString();
            choix = stringToIntPerso(choice);
            if (choix<1 || choix>3) {
                println("Veuillez saisir un nombre entre 1 et 3");
            }
        }while (choix<1 || choix>3);

        do {
            if (choix == 1){
                println("C'est parti !");
            }
            if (choix == 2){
                afficherRègle();
            }
            if (choix == 3){
                perdu = true;
            }
        }while (choix<1 || choix>3);
    }

    void afficherRègle(){
        println("\nJeu développé par François Deroubaix et Alban Sannier");
        println("Voici les règles :\n");
        print("Votre but est d'amener la barre de Technologie, Bonheur et Economie à 10 barres.\nMais attention votre budget est aussi important dès qu'il tombera en dessous de -2500$ la partie sera terminée.\nVous avez accès à une liste de batiments (présente si dessous) qui vous permettra de les placer et d'avoir des bonus.");
        print("\nAutre chose, des évènements aléatoires sont présents pour vous faire perdre, prenez garde !\n\n");
        println("Liste des bâtiments disponibles : ");
        print("\t\t\t\tHotel de ville\tEcole\tFaculté\n\t\t\t\tBoulangerie\tCentrale électrique\tMusée\n\t\t\t\tPolice\tAéroport\tThéatre\n\t\t\t\tRestaurant\n");
        println("Certains de ces bâtiments vous confèrent des bonus non égligeables.\n\n");
        println("Alors êtes-vous prêt ?");
        print("1. Oui je veux commencer une partie\n2. Je veux quitter\n");
        print("Choix : ");
        int choix;
        String choice;
        do {
            choice = readString();
            choix = stringToIntPerso(choice);
            if (choix<1 || choix>2) {
                println("Veuillez saisir un nombre entre 1 et 2");
            }
        }while (choix<1 || choix>2);

        do {
            if (choix == 1){
                println("C'est parti !");
            }
            if (choix == 2){
                perdu = true;
            }
        }while (choix<1 || choix>2);
    }

    void algorithm() {
        clearScreen();//commande bash : "clear"
        cursor(0,0);
        int eventAlea;
        //On charge les BDD
        Questions[] bddCulture = csvFileToTableauQuestion("../ressources/bddCulture.csv");
        Questions[] bddLangue = csvFileToTableauQuestion("../ressources/bddLangue.csv");
        Questions[] bddSience = csvFileToTableauQuestion("../ressources/bddSience.csv");
        Batiments[] bddBatiments = csvFileToTableauBatiments("../ressources/batiment.csv");
        Evenements[] tabEvenements = csvFileToTableauEvenements("../ressources/evenements.csv");
        //Affichage du titre
        titreHUD();
        //On génère la Map
        generationMap();
        //On demande si le joueur souhaite jouer
        jouer();
        while (!perdu && BUDGET > -2500 && BONHEUR > 0 && BONHEUR < 10 && TECHNOLOGIE > 0 && TECHNOLOGIE < 10 && ECONOMIE > 0 && ECONOMIE < 10) {
            revenu();
            cout();
            clearScreen();//commande bash : "clear"
            cursor(0,0);
            //Titre du jeu (header)
            titreHUD();
            print("\n");
            //génération du HUD
            generationHUD();
            print("\n");

            //Section des évenements aléatoire
            if (alea(2) == 1) {
                eventAlea = choiceEventAleatoire(tabEvenements);
                if (eventAlea != -1) {
                    print("\n");
                    eventAction(tabEvenements[eventAlea]);
                    hudEvent(tabEvenements[eventAlea]);
                    print("\n");
                }
            } else {
                print("\n");
            }

            //Phase Batiments
            phaseBatiments(bddBatiments);
            clearScreen();//commande bash : "clear"
            cursor(0,0);
            titreHUD();
            println();
            generationHUD();
            //Phase Question
            phaseQuestion(bddCulture, bddLangue, bddSience);
        }

        clearScreen();//commande bash : "clear"
        cursor(0,0);
        titreHUD();
        print("\n");
        generationHUD();

        if (perdu){
            println("#########################################################");
            println("                  Vous avez abandonné :(");
            println("#########################################################");
        }else if (conditionDeVictoire()){
            println("#########################################################");
            println("                       Victoire");
            println("#########################################################");
        }else{
            println("#########################################################");
            println("                       Défaite");
            println("#########################################################");
        }
        print("Crédit :\nDéveloppé par François Deroubaix et Alban Sannier\nFais avec iJava dans le cadre du Projet d'AP du premier semestre de DUT informatique\n");

    }
}

/*  
 *  Motus
 *  par Ezechiel LEIBOVICI et Steve LABUS
 *  BUT1-E
 */

 import extensions.CSVFile;

 class Motus extends Program{
     // Listes de mots
     final String [] TABFR = new String []{"BOEUF","ORDINATEUR","GARDEBOUE","RIMMEL","GARNIR","PROGRAMMATION","IJAVA","CHOQUE","EFFACER","MODAUX","CROIRE","SYMPATHIE","CUIT","GONDOLE","PROPRE","MASSEUSE","PECTORAL","BRANCHE","FROUSSE","MOIS","OPTIONNEL","BOUCHER","CHEVALET","CANAL","PERLES","SPATULE","DOCUMENT","VAISSELLE","VICTOIRE","ORDINATEUR","FUIR","TRIBU","DEUIL","CONTRIBUTION"};
     final String [] TABEN = new String []{"DESKTOP","MACINTOSH","CHIPSET","EUH"};
     // Codes couleur ANSI
     public static final String ANSI_RESET = "\u001B[0m";
     public static final String ANSI_RED = "\u001B[31m";
     public static final String ANSI_GREEN = "\u001B[32m";
     public static final String ANSI_YELLOW = "\u001B[33m";
     public static final String ANSI_CLEAR = "\033[H\033[2J";
     // Paramètres du jeu
     final char CHAR_NULL = '_';
     final int NB_TOURS = 6;
     final String DATA_PATH = "../ressources/identifiants.csv";
     String z = "";
     int y;
     String [][] tab = loadIdentifiants();
     
 
     // Crée un nouvel objet Lettre
     Lettre newLettre(char lettre, boolean etat){
         Lettre l = new Lettre();
         l.lettre = lettre;
         l.trouve = etat;
         return l;
     }
 
     // Choisit un mot aléatoire et construit un tableau de lettres
     Lettre[] InitMot(){
         Lettre [] rendu;
         int rdm=(int)(random()*10);
         String mot;
         if(rdm==0){ //TABEN
             rdm=(int)(random()*(length(TABEN)));
             mot = TABEN[rdm];
             y=rdm;
             rendu= new Lettre[length(mot)];
             for(int i=0;i<length(mot);i++){
                 rendu[i]= newLettre(charAt(mot, i),false);
             }
         }
         else{ // TABFR
             rdm=(int)(random()*(length(TABFR)));
             mot = TABFR[rdm];
             rendu= new Lettre[length(mot)];
             for(int i=0;i<length(mot);i++){
                 rendu[i]= newLettre(charAt(mot, i),false);
             }
         }
         return rendu;
     }
 
 
     void afficherMot(Lettre[] mot){
         for(int i = 0; i<length(mot); i++){
             if(mot[i].trouve){
                 print(ANSI_GREEN+mot[i].lettre);
             }else{
                 print(ANSI_RED+CHAR_NULL);
             }
         }
         println(ANSI_RESET);
     }

     String divulguerMot(Lettre[] mot){
        String motS = "";
        for(int i = 0; i<length(mot); i++){
            motS = motS + mot[i].lettre;
        }
        return motS;
     }
 
     String compareMot(String mot,Lettre [] tab){
         String rendu="";
         for(int i=0;i<length(tab);i++){
             if(charAt(mot,i)==tab[i].lettre){
                 tab[i].trouve=true;
                 rendu+=ANSI_GREEN+charAt(mot,i);
             }
             else{
                 if(trouverLettre(charAt(mot,i),tab)){
                     rendu+=ANSI_YELLOW+charAt(mot,i);
                 }
                 else{
                     rendu+=ANSI_RED+charAt(mot,i);
                 }
             }
         }
         return rendu+ANSI_RESET;      
     }
 
     boolean trouverLettre(char l, Lettre[] mot){
         for(int i = 0; i<length(mot); i++){
             if(mot[i].lettre == l){
                 return true;
             }
         }
         return false;
     }
         Lettre[] InitMot(String mot){
         Lettre[] rendu = new Lettre[length(mot)];
         for(int i=0;i<length(mot);i++){
             rendu[i] = newLettre(charAt(mot, i),false);
         }
         return rendu;
     }
     void testTrouverLettre(){
         Lettre[] mot = InitMot("BONJOUR");
         assertTrue(trouverLettre('B', mot));
         assertTrue(trouverLettre('R', mot));
         assertTrue(trouverLettre('J', mot));
     }
 
     boolean motEntreValide(String motEntre, Lettre[] mot){
         if(length(motEntre) == length(mot)){
             for(int i = 0; i<length(motEntre); i++){
                 if(!(charAt(motEntre, i) >= 'A' && charAt(motEntre, i) <= 'Z')){
                     return false;
                 }
             }
         }else{
             return false;
         }
         return true;
     }
 
     boolean trouve(Lettre[] mot){
         for(int i = 0; i<length(mot); i++){
             if(!mot[i].trouve){
                 return false;
             }
         }
         return true;
     }
 
 
     int jeu(){
         int compteur = NB_TOURS;
         boolean fini = false;
         Lettre[] mot = InitMot();
         int score = length(mot);
         println("Début du jeu!");
         String motEntre;
         while(!fini){
             afficherMot(mot);
             boolean motvalide = false;
             while(!motvalide){
                 motEntre = toUpperCase(readString());
                 if(motEntreValide(motEntre, mot)){
                     println(compareMot(motEntre,mot));
                     motvalide = true;
                 }else{
                     println("Veillez à entrer un mot de la bonne taille et à n'utiliser que des lettres!");
                     afficherMot(mot);
                 }
             }
             compteur-=1;
             println("Il vous reste "+compteur+" tours!");
             if(trouve(mot)){
                    print(ANSI_CLEAR);
                    afficherMot(mot);
                    score*=(compteur+1);
                    println("Bravo, vous avez trouvé le mot!");
                    println("Votre score est de "+score);
                    fini=true;
             }
             else{
                 if(compteur==0){
                     print(ANSI_CLEAR);
                     println("Desolé, vous avez perdu :/");
                     println("Le mot était: "+divulguerMot(mot));
                     fini=true;
                     score=0;
                 }
             }
         }
         return score;
     }
     boolean loginCorrect(int indice){
         boolean renvoi = false;
         int chance = 3;
         while(!renvoi && chance>0){
             println("Quel est le mot de passe de l'utilisateur " + tab[indice][0]);
             if(equals(tab[indice][1],readString())){
                 renvoi = true;
             }
             chance-=1;
         }
         return(renvoi);
     }
     int connexionUtilisateur(){
         println("Bonjour, quel est votre nom?");
         boolean connecté = false;
         String pseudo;
         int indice = 0;
         while(!connecté){
             pseudo = readString();
             indice = indiceUser(pseudo);
             if(indice<0){
                 println("Oh, un nouvel utilisateur !");
                 println("Si vous souhaitez vous inscrire tapez 1.");
                 println("Sinon appuyez sur une autre touche.");
                 String YoN = readString();
                 if(equals(YoN,"1")){
                     tab = nouvelUtilisateur(pseudo);
                     indice = length(tab)-1;
                     connecté = true;
                 }
                 else{
                     println("Dans ce cas, quel est votre nom ?");
                 }
             }
             else{
                 if(loginCorrect(indice)){
                     connecté = true;
                     print(ANSI_CLEAR);
                     println("Bonjour "+pseudo+"!");
                 }
                 else{
                     println("Mot de passe incorrect :/");
                     println("Quel est votre nom?");
                 }
             }
         }
 
         return(indice);
     }
     void saveId(String[][] tab){
         saveCSV(tab, DATA_PATH);
     }
     String[][] nouvelUtilisateur(String pseudo){
         String mdp = "";
         String [][] renvoi = new String[length(tab)+1][3];
         for(int i=0; i<length(tab);i++){
             renvoi[i][0] = tab[i][0];
             renvoi[i][1] = tab[i][1];
             renvoi[i][2] = tab[i][2];
         }
         renvoi[length(tab)][0]=pseudo;
         boolean verification = false;
         while(!verification){
             println("Quel sera votre mot de passe ?");
                 mdp = readString();
             println("Veuillez le confirmer en le réecrivant: ");
             if(equals(mdp,readString())){
                 verification = true;
             }
             else{
                 println("Mauvais mot de passe, veuillez recommencer: ");
             }
         }
         renvoi[length(tab)][1] = mdp;
         renvoi[length(tab)][2] = "0";
         return renvoi;
     }
     String[][] loadIdentifiants(){
         CSVFile identifiants = loadCSV(DATA_PATH);
         int rowCount = rowCount(identifiants);
         String[][] tab = new String[rowCount][3];
         for(int i = 0; i<rowCount; i++){
             for(int j = 0; j<3; j++){
                 tab[i][j] = getCell(identifiants, i, j);
             }
         }
         return tab;
     }
     void updateScore(String[][] chart, int indice, int score){
         chart[indice][2] = ""+score;
     }
     //Trie le tableau contenant les utilisateur dans l'ordre décroissant en fonction de leur score
     void triTab(){
         String [] tabl; 
         for(int i=0;i<length(tab);i++){
             int max = i;
             for(int j=i;j<length(tab);j+=1){
                 if(stringToInt(tab[j][2])>stringToInt(tab[max][2])){
                     max = j;
                 }
             }
             tabl = new String [] {tab[i][0],tab[i][1],tab[i][2]};
             tab[i] = tab[max];
             tab[max] = tabl;
         }
     }
     void affichageTop10(){
         int max=1000000;
         int indice;
         int nombre;
         if(length(tab)<10){
             nombre = length(tab);
         }
         else{
             nombre = 10;
         }
         int [] indices = new int [nombre];
     
         for(int i=0;i<nombre;i++){
             indice = 0;
             for(int j=0;j<length(tab);j++){
                 if(stringToInt(tab[j][2])>stringToInt(tab[indice][2]) && stringToInt(tab[j][2])<=max){
                     if(stringToInt(tab[j][2])==max){
                         boolean dedans = false;
                         for(int k = 0; k<i;k++){
                             if(j == indices[k]){
                                 dedans = true;
                             }
                         }
                         if(!dedans){
                             indice = j;
                         }
                     }
                     else{
                         indice = j;
                     }                    
                 }
                 max = stringToInt(tab[indice][2]);
                 indices[i] = indice;
             }
         }
         for(int i=0;i<length(indices);i++){
             println(i+1 + ") " + tab[indices[i]][0] + ":" + tab[indices[i]][2]);
         }
     }

     void displayChart(String[][] chart){
         println("Classement des joueurs:");
         for(int i = 0; i<length(chart); i++){
             println(chart[i][0]+": "+chart[i][2]);
         }
         println("-----------------------");
     }
 
     int indiceUser(String pseudo){
         int i = 0;
         int renvoi=-1;
         while(renvoi<0 && i<length(tab)){
             if(equals(tab[i][0],pseudo)){
                 renvoi = i;
             }
             i++;
         }
         return renvoi;
     }

     void algorithm(){
         print(ANSI_CLEAR);
         boolean end = false;
         String info;
         println("Bienvenue sur Motus!");
         int Indice = connexionUtilisateur();
         while(!end){
             println("Faites votre choix parmi ces propositions: ");
             println("1) Nouvelle partie");
             println("2) Affichage du classement");
             println("3) Changement d'utilisateur");
             println("4) Fin du jeu");
             info = readString();
             if(equals(info,"1")){;
                 print(ANSI_CLEAR);
                 updateScore(tab,Indice,jeu());  
             }
             else{
                 if(equals(info,"2")){
                     print(ANSI_CLEAR);
                     triTab();
                     displayChart(tab);
                 }
                 else{
                     if(equals(info,"3")){
                        print(ANSI_CLEAR);
                         println("Saisissez votre nom d'utilisateur pour continuer: ");
                         Indice = connexionUtilisateur();
                     }
                     else{
                         if(equals(info,"4")){
                            print(ANSI_CLEAR);
                             end = true;
                         }
                     }
                 }
             }
         }
         println("À la prochaine !");
         saveId(tab);
     }
 }
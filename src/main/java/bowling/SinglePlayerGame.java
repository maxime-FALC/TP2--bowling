package bowling;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancés successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 * 
 * 
 *                           ATTENTION (TODO)
 * 
 *    Vérifier le principe de deux strike à la suite !!! le nombre de boules
 *    pourrait potentiellement varier !!!
 *    
 *    Traiter le cas des derniers tir (variable comptant le nombre de tours ?)
 */
public class SinglePlayerGame {

    
    /** Score du joueur dont la partie est en cours */
    private int score;
    
    /** Entier qui compte le nombre du tour du joueur */
    private int tour;
    
    /** booléen déterminant si le tir lancé est celui du deuxième tour ou pas */
    private boolean lance_deux;
    
    /** score obtenu après avoir envoyé une boule */
    private int points_tir_un;
    
    /** 
     * nombre de boules restantes à ajouter aux points du strike 
     * Peut valoir :
     *    - 0 (aucun strike en cours)
     *    - 1 (une boule restante à comptabiliser)
     *    - 2 (deux boules restantes à comptabiliser)
     */
    private int nb_boules_Strike = 0;
    
    /** mis à True si un spare a été effectué sur le lancé précédent */
    private boolean spareValide = false;
    
    /** valeur du score mémorisée pour le strike */
    private int scoreStrike;
    
    
    
	/**
	 * Constructeur
	 */
	public SinglePlayerGame() {
            
            /* debut de la partie, le score vaut 0 */
            score = 0;
            
            /* on se situe sur le premier lancé */
            lance_deux = false;
            
            /* le premier tir n'a pas encore été effectué */
            points_tir_un = 0;
            
            /* le tour est le tour N°1 */
            tour = 0;
            
            /* aucun strike et aucun spare n'ont étés effectués */
            nb_boules_Strike =0 ;
            scoreStrike =0;
            
	}

	/**
	 * Cette méthode doit être appelée à chaque lancé de boule
         * Détermine en fonction du numéro de tir le type de coup qui a été joué
         * et s'il faut cloturer le tour ou pas.
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de
	 *        ce lancé
	 */
	public void lancer(int nombreDeQuillesAbattues) {

            /* Appel de la fonction de vérification d'un tir spécial en cours */
            spareOuStrikeEnCours(nombreDeQuillesAbattues);
            
            
            
            /* 
             * traitement de points de ce tir si on est sur les 10 premiers 
             * tours 
             */
            if(!lance_deux && tour <= 10){
                
                /* sur le premier lancé, il y a deux possibilités :
                 *  - tir classique
                 *  - strike
                 */
                if(nombreDeQuillesAbattues != 10){
                    
                    points_tir_un += nombreDeQuillesAbattues;
                    lance_deux = true;
                    
                } else {
                    
                    /* strike effectué */
                    nb_boules_Strike += 2;
                    finTour();
                    
                }
            } else if(lance_deux && tour <= 10){
                
                /* sur le deuxième lancé, il y a deux possibilités :
                 *  - tir classique (la somme des deux boules ne vaut pas 10)
                 *  - spare (la somme des points vaut 10)
                 */
                points_tir_un += nombreDeQuillesAbattues;
                
                if(points_tir_un == 10){
                    /* spare effectué */
                    spareValide = true;
                    finTour();
                    
                } else {
                    
                    /* fin du tour, inscriptions des points marqués */
                    score += points_tir_un;
                    finTour();
                    
                }
                
            }
	}
        
        /**
         * Vérifie si un Spare ou un Strike est en cours, et traite le cas 
         * si nécessaire
         * @param nombreDeQuillesAbattues nombre de quilles renversées sur ce 
         *                                tir                                
         */
        public void spareOuStrikeEnCours(int nombreDeQuillesAbattues){
            /* traitement si un strike ou un spare est en cours */
            if(spareValide){
                /* Appel à la fonction de traitement des Spare */
                spare(nombreDeQuillesAbattues);
                
            } else if(nb_boules_Strike != 0){
                /* Appel à la fonction de traitement des Strike */
                strike(nombreDeQuillesAbattues);
            }
        }
        

	/**
	 * Cette méthode donne le score du joueur
	 *
	 * @return Le score du joueur
	 */
	public int score() {
            return this.score;
	}
        
        
        /**
         * Signale la fin du Tour en mettant les points du tour à 0 et en 
         * réinitialisant le booléen du nombre du tour.
         * Puis incrémente le numéro du tour.
         */
        public void finTour(){
          lance_deux = false;
          points_tir_un = 0;
          tour++;
        }
        
        
        /**
         * Gère le cas d'un spare en ajoutant 10 au score passé en argument.
         * Puis ajoute ces points au score, et ferme le spare
         * 
         * @param nbQuilles entier signalant le nombre de quilles renversées 
         *        sur le nouveau lancé
         */
        public void spare(int nbQuilles){
            score = score + nbQuilles + 10;
            spareValide = false;
        }
        
        
        /**
         * Gère le cas d'un strike en ajoutant au score du strike la valeur
         * passée en argument, puis en décrémentant le nombre de boules 
         * restantes du Strike.
         * 
         * @param nbQuilles entier signalant le nombre de quilles renversées 
         *        sur le nouveau lancé
         */
        public void strike(int nbQuilles){
        switch (nb_boules_Strike) {
            case 1:
                score = score + scoreStrike + nbQuilles + 10;
                scoreStrike = 0;
                break;
            case 2:
                scoreStrike += nbQuilles;
                break;
            default:
                score = score + scoreStrike + nbQuilles + 10;
                scoreStrike = nbQuilles;
                break;
        }
            nb_boules_Strike-- ; 
        }
}

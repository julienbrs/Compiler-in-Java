package fr.ensimag.deca;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl11
 * @date 01/01/2023
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public int getRmax() {
        return rMax;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private int rMax = 16;
    private boolean parallel = false;
    private boolean printBanner = false;
    private boolean parse = false;
    private boolean verification = false;
    private boolean noCheck = false;
    private List<File> sourceFiles = new ArrayList<File>();
    private String string;

    
    public void parseArgs(String[] args) throws CLIException {
        if (args.length == 0) {
            displayUsage();
            System.exit(0);
        }
        Iterator<String> it =  Arrays.asList(args).iterator();
        while(it.hasNext()){
            string = it.next();
            if(string.equals("-b")){
                printBanner = true;
                parallel = false;
                verification = false;
                noCheck = false;
                parse = false;
            }
            else if(string.equals("-P")){
                printBanner = false;
                parallel = true;
            }
            else if(string.equals("-v")){
                printBanner = false;
                verification = true;
            }
            else if(string.equals("-p")){
                printBanner = false;
                parse = true;
            }
            else if(string.equals("-n")){
                printBanner = false;
                noCheck = true;
            }
            else if(string.equals("-d")){
              debug++;  
            }
            else if(string.contains("-r")){
                string =it.next();
                rMax = Integer.parseInt(string);
                if(rMax>16||rMax<4){
                    throw new IllegalArgumentException("Rmax pas valable");
                }
            }
            else {
                sourceFiles.add(new File(string));
                printBanner = false;
            }

        }
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

        
    }

    protected void displayUsage() {
        // throw new UnsupportedOperationException("not yet implemented");
        System.out.println("   -b    (banner)            : affiche une bannière indiquant le nom de l'équipe");
        System.out.println("   -p    (parse)             : arrête decac après l'étape de construction de");
        System.out.println("                               l'arbre, et affiche la décompilation de ce dernier");
        System.out.println("                               (i.e. s'il n'y a qu'un fichier source à");
        System.out.println("                               compiler, la sortie doit être un programme");
        System.out.println("                               deca syntaxiquement correct)");
        System.out.println("   -v    (verification)      : arrête decac après l'étape de vérifications");
        System.out.println("                               (ne produit aucune sortie en l'absence d'erreur)");
        System.out.println("   -n    (no check)          : supprime les tests à l'exécution spécifiés dans");
        System.out.println("                               les points 11.1 et 11.3 de la sémantique de Deca.");
        System.out.println("   -r X  (registers)         : limite les registres banalisés disponibles à");
        System.out.println("                               R0 ... R{X-1}, avec 4 <= X <= 16");
        System.out.println("   -d    (debug)             : active les traces de debug. Répéter");
        System.out.println("                               l'option plusieurs fois pour avoir plus de traces");
        System.out.println("   -P    (parallel)          : s'il y a plusieurs fichiers sources,");
        System.out.println("                               lance la compilation des fichiers en");
        System.out.println("                               parallèle (pour accélérer la compilation)");
        System.out.println("   \u001b[1mN.B. Les options '-p' et '-v' sont incompatibles\u001b[1m");
    }


    public boolean getParse() {
        return parse;
    }

    public boolean getVerification() {
        return verification;
    }

    public boolean getNoCheck() {
        return noCheck;
    }
}

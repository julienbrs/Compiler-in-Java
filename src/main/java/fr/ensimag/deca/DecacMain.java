package fr.ensimag.deca;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl11
 * @date 01/01/2023
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static String setBgColor(int r, int g, int b) {
        return "\u001b[48;2;"+r+";"+g+";"+b+"m";
    }

    public static String setFgColor(int r, int g, int b) {
        return "\u001b[38;2;"+r+";"+g+";"+b+"m";
    }

    public static String resetFgColor() {
        return "\u001b[39m";
    }

    public static String resetBgColor() {
        return "\u001b[49m";
    }

    public static void printBanner() {
        String banner = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n║            ███████████                    ███           █████         █████████  █████                                    ║\n║           ░░███░░░░░███                  ░░░           ░░███         ███░░░░░███░░███                                     ║\n║            ░███    ░███████████  ██████  █████ ██████  ███████      ███     ░░░  ░███                                     ║\n║            ░██████████░░███░░██████░░███░░███ ███░░███░░░███░      ░███          ░███                                     ║\n║            ░███░░░░░░  ░███ ░░░░███ ░███ ░███░███████   ░███       ░███    █████ ░███                                     ║\n║            ░███        ░███    ░███ ░███ ░███░███░░░    ░███ ███   ░░███  ░░███  ░███      █                              ║\n║            █████       █████   ░░██████  ░███░░██████   ░░█████     ░░█████████  ███████████                              ║\n║           ░░░░░       ░░░░░     ░░░░░░   ░███ ░░░░░░     ░░░░░       ░░░░░░░░░  ░░░░░░░░░░░                               ║\n║                                      ███ ░███                                                                             ║\n║    ██████████                        ░░██████  █████████                                     ███ ████                     ║\n║   ░░███░░░░███                        ░░░░░░  ███░░░░░███                                   ░░░ ░░███                     ║\n║    ░███   ░░███  ██████  ██████  ██████      ███     ░░░   ██████  █████████████  ████████  ████ ░███   ██████  ████████  ║\n║    ░███    ░███ ███░░██████░░███░░░░░███    ░███          ███░░███░░███░░███░░███░░███░░███░░███ ░███  ███░░███░░███░░███ ║\n║    ░███    ░███░███████░███ ░░░  ███████    ░███         ░███ ░███ ░███ ░███ ░███ ░███ ░███ ░███ ░███ ░███████  ░███ ░░░  ║\n║    ░███    ███ ░███░░░ ░███  ██████░░███    ░░███     ███░███ ░███ ░███ ░███ ░███ ░███ ░███ ░███ ░███ ░███░░░   ░███      ║\n║    ██████████  ░░██████░░██████░░████████    ░░█████████ ░░██████  █████░███ █████░███████  ██████████░░██████  █████     ║\n║   ░░░░░░░░░░    ░░░░░░  ░░░░░░  ░░░░░░░░      ░░░░░░░░░   ░░░░░░  ░░░░░ ░░░ ░░░░░ ░███░░░  ░░░░░░░░░░  ░░░░░░  ░░░░░      ║\n║                                 █████████                        ████     ████    ░███                                    ║\n║                                ███░░░░░███                      ░░███    ░░███    █████                                   ║\n║                               ███     ░░░ ████████  ████████     ░███     ░███   ░░░░░                                    ║\n║                              ░███        ░░███░░███░░███░░███    ░███     ░███                                            ║\n║                              ░███    █████░███ ░░░  ░███ ░███    ░███     ░███                                            ║\n║                              ░░███  ░░███ ░███      ░███ ░███    ░███     ░███                                            ║\n║                               ░░█████████ █████     ░███████     █████    █████                                           ║\n║                                ░░░░░░░░░ ░░░░░      ░███░░░     ░░░░░    ░░░░░                                            ║\n║                                                     ░███                                                                  ║\n║                                                     █████                                                                 ║\n║                                                    ░░░░░                                                                  ║\n╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝";
        
        // █ : box   char
        // ░ : det   char
        //   : space char

        String textColor = setFgColor(128, 255, 128);
        String shadowColor = setFgColor(128, 255, 128);
        String borderColor = setFgColor(128, 255, 128);
        String backgroundColor = ""; //setBgColor(60, 255, 255);

        String box = textColor + "█" + resetFgColor();
        String dotBox = shadowColor + "░" + resetFgColor();
        String border = borderColor + "║" + resetFgColor();

        String colorBanner = backgroundColor + banner + resetBgColor();
        colorBanner = colorBanner.replace("█", box);
        colorBanner = colorBanner.replace("░", dotBox);
        colorBanner = colorBanner.replace("╔", borderColor + "╔");
        colorBanner = colorBanner.replace("╗", "╗" + resetFgColor());
        colorBanner = colorBanner.replace("╚", borderColor + "╚");
        colorBanner = colorBanner.replace("╝", "╝" + resetFgColor());
        colorBanner = colorBanner.replace("║", border);
        colorBanner = colorBanner.replace("\n", resetBgColor() + "\n" + backgroundColor);

        System.out.println("\n" + colorBanner + "\n");
    }

    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            // System.out.println(" ----------GL11----------");
            printBanner();
            System.exit(0);
           // throw new UnsupportedOperationException("decac -b not yet implemented");
        }
        if (options.getSourceFiles().isEmpty()) {
            // throw new UnsupportedOperationException("decac without argument not yet implemented");
            DecacCompiler compiler = new DecacCompiler(options, new File("src/test/deca/syntax/parser/valid/empty.deca"));
            if (compiler.compile()) {
                error = true;
            }
            System.exit(error ? 1 : 0);
        }
        if (options.getParallel()) {
            ExecutorService parallel =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            try{
                



            } catch(Exception e){
                throw new UnsupportedOperationException("Parallel build not yet implemented");
            }
            
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}

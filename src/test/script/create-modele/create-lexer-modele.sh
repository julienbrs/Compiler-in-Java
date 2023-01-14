#! /bin/bash

# Auteur : gl11

# /!\ /!\ DO NOT RUN IF NOT NEEDED /!\ /!\

# Script de test du lexer.
# Lance tous les tests lexer et se sert des résultats comme modele

purple=$(tput setab 99)
reset=$(tput sgr0)

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

# exit_status_final=0

# declare log_activated=false
# if [ "$2" == "--log" ]; then
#     log_activated=true
# fi

echo "${purple}Création des modèles de tests lexer sensés être invalides....${reset}"
for cas_de_test in $(find src/test/deca/syntax/lexer/invalid -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele/lexer/invalid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        test_lex "$cas_de_test" >"$output_file" 2>&1
    else
        echo "modele_$filename déjà existant"
    fi
done

echo "${purple}Création des modèles de tests lexer sensés être valides....${reset}"
for cas_de_test in $(find src/test/deca/syntax/lexer/valid -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele/lexer/valid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        test_lex "$cas_de_test" >"$output_file" 2>&1
    else
        echo "modele_$filename déjà existant"
    fi
done

# End of the script
# Check if the --exit-status option was passed
# if [ "$1" == "--exit-status" ]; then
#     # Print the exit status
#     echo "The script exited with a status of $?"
#     exit $exit_status
# else
#     exit $exit_status
# fi

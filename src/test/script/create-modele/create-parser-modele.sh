#! /bin/bash

# Auteur : gl11

# /!\ /!\ DO NOT RUN IF NOT NEEDED /!\ /!\

# Script de test du parser.
# Lance tous les tests parser et se sert des résultats comme modele

purple=$(tput setab 99)
reset=$(tput sgr0)

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

exit_status_final=0

declare log_activated=false
if [ "$2" == "--log" ]; then
    log_activated=true
fi

echo "${purple}Création des modèles de tests parser sensés être invalides....${reset}"
for cas_de_test in $(find src/test/deca/syntax/parser/invalid -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele/parser/invalid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        test_synt "$cas_de_test" >$output_file 2>&1
    else
        echo "modele_$filename déjà existant"
    fi

done

echo "${purple}Création des modèles de tests parser sensés être valides....${reset}"
for cas_de_test in $(find src/test/deca/syntax/parser/valid/ -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele/parser/valid/modele_%s.txt" "$filename")

    if [ ! -f "$output_file" ]; then
        test_synt "$cas_de_test" >$output_file 2>&1
    else
        echo "modele_$filename déjà existant"
    fi
    output_file_decomp=$(printf "src/test/script/modele/parser/valid/modele_decomp_%s.txt" "$filename")
    if [ ! -f "$output_file_decomp" ]; then
        decac "$cas_de_test" -p >$output_file_decomp 2>&1
    else
        echo "modele_decompil_$filename déjà existant"
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

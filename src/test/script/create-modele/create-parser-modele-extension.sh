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

#Modèles spécifiques
echo "${purple}Création des modèles de tests parser sensés être invalides....${reset}"

for cas_de_test in $(find src/test/deca/extension/parser/invalid -maxdepth 1 -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele-extension/parser/invalid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        test_synt_ext "$cas_de_test" >$output_file 2>&1
    else
        echo "modele_$filename déjà existant"
    fi
done
#########################################################################################

echo "${purple}Création des modèles de tests parser sensés être valides....${reset}"

#Modèles spécifiques
for cas_de_test in $(find src/test/deca/extension/parser/valid -maxdepth 1 -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele-extension/parser/valid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        test_synt_ext "$cas_de_test" >$output_file 2>&1
    else
        echo "modele_$filename déjà existant"
    fi
done

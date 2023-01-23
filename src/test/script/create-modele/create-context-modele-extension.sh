#! /bin/bash

# Auteur : gl11

# /!\ /!\ DO NOT RUN IF NOT NEEDED /!\ /!\

# Script des tests contextes.
# Lance tous les tests context et se sert des résultats comme modele

purple=$(tput setab 99)
reset=$(tput sgr0)

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

exit_status_final=0

###############################
echo "${purple}Création des modèles de tests context sensés être invalides....${reset}"
for cas_de_test in $(find src/test/deca/extension/context/invalid -maxdepth 1 -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele-extension/context/invalid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        test_context_ext "$cas_de_test" >$output_file 2>&1
    else
        echo "modele_$filename déjà existant"
    fi

done
echo "${purple}Création des modèles de tests context sensés être valides....${reset}"

for cas_de_test in $(find src/test/deca/extension/context/valid/ -maxdepth 1 -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele-extension/context/valid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        test_context_ext "$cas_de_test" >$output_file 2>&1
    else
        echo "modele_$filename déjà existant"
    fi
done

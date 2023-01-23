#! /bin/bash

# Auteur : gl11

# Script de test de la lexicographie.
# On lance tous les tests en rapport avec la lexicographie
# Todo: se servir du log_activated pour echo ou non ?

purple=$(tput setab 99)
reset=$(tput sgr0)

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :

PATH=./src/test/script/launchers:"$PATH"

# exit_status_final=0

# declare log_activated=false
# if [ "$2" == "--log" ]; then
#     log_activated=true
# fi

# Supprime le fichier temporaire dès que le programme finit
cleanup() {
    rm ./src/test/deca/codegen/*.ass
}
trap cleanup EXIT
filename=$(basename "$1")
filename="${filename%.*}"

decac "src/test/deca/codegen/invalid/$filename.deca"
resultat_output=$(ima "./src/test/deca/codegen/invalid/$filename.ass" 2>&1)
echo "Le resultat est: $resultat_output"
output_file=$(printf "src/test/script/modele/codegen/$2/modele_%s.txt" "$filename")
echo "$resultat_output" >"$output_file" 2>1
# if [ "$3" = "OK" ]; then
#     output_file=$(printf "src/test/script/modele/codegen/$2/modele_%s.txt" "$filename")
#     echo "$resultat_output" >"$output_file" 2>1
#     mv "src/test/deca/codegen/temp/$filename.deca" "src/test/deca/codegen/$2/$filename.deca"
# fi

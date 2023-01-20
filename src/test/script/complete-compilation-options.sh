#! /bin/bash

# Auteur : gl11

# Script de test de la lexicographie.
# On lance tous les tests en rapport avec la lexicographie
# Todo: se servir du log_activated pour echo ou non ?

purple=$(tput setab 99)
reset=$(tput sgr0)

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

# Supprime le fichier temporaire dès que le programme finit
cleanup() {
    rm -rf ./src/test/deca/codegen/valid/*.ass
}
trap cleanup EXIT

options=(-b -P -v -p -n)
echo "${purple}Lancement des tests d'options de compilation:${reset}"
for option in "${options[@]}"; do
    rm -f ./src/test/deca/codegen/valid/provided/ln2.ass 2>/dev/null
    resultat_compil=$(decac ./src/test/deca/codegen/perf/provided/ln2.deca $option)

    # On exit pas si ça plante, car on s'est sûrement arrêté avant donc ass n'existe pas
    resultat_ima=$(ima ./src/test/deca/codegen/perf/provided/ln2.ass)

    rm -f ./src/test/deca/codegen/valid/provided/ln2.ass

    fichier_modele="src/test/script/modele/compilations-options/option_${option#-}.txt"

    diff <(echo "$resultat_compil") $fichier_modele
    if [ $? -eq 0 ]; then
        echo "Option de compilation $option OK ✅"
    else
        echo "Option de compilation $option n'a pas le bon résultat❌"

    fi

done

# option avec registre
rm -f ./src/test/deca/codegen/valid/provided/ln2.ass 2>/dev/null
resultat_compil=$(decac ./src/test/deca/codegen/perf/provided/ln2.deca -r 7)
resultat_ima=$(ima ./src/test/deca/codegen/perf/provided/ln2.ass)
fichier_modele="src/test/script/modele/compilations-options/option_r7.txt"
diff <(echo "$resultat_compil") $fichier_modele
if [ $? -eq 0 ]; then
    echo "Option de compilation -r 7 OK ✅"
else
    echo "Option de compilation -r 7 n'a pas le bon résultat❌"

fi
rm -f ./src/test/deca/codegen/valid/provided/ln2.ass

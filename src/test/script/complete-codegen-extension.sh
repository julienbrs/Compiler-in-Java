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

# exit_status_final=0

# declare log_activated=false
# if [ "$2" == "--log" ]; then
#     log_activated=true
# fi

# Supprime le fichier temporaire dès que le programme finit
cleanup() {
    rm -rf ./src/test/deca/codegen/valid/*.ass
}
trap cleanup EXIT

test_codegen_unitaire() {
    # $1 = premier argument, $2 = deuxieme
    exit_status_waited=$2
    filename=$(basename "$1")
    filename="${filename%.*}"

    if [[ $exit_status_waited == "1" ]]; then
        str_res_waited="Echec"
        str_res_not_waited="Succes"
        path_valid="invalid"
    else
        str_res_waited="Succes"
        str_res_not_waited="Echec"
        path_valid="valid"
    fi

    fichier_modele="src/test/script/modele-extension/codegen/"$path_valid"/modele_$filename.txt"
    output_compil=$(decac "./src/test/deca/codegen/$path_valid/$filename.deca" -tab 2>&1)
    result_compil=$?

    # S'il y a une erreur à la compilation:
    if [ "$result_compil" -eq 1 ]; then
        diff <(echo "$output_compil") $fichier_modele
        if [ $? -eq 0 ]; then
            echo "$1: $str_res_waited attendu ✅"
        else
            echo "$1: $str_res_waited mais output non attendu ❌"
        fi

    else

        resultat_output=$(ima ./src/test/deca/codegen/$path_valid/$filename.ass)
        cmd_succed=$?

        if [ "$cmd_succed" -eq "$exit_status_waited" ]; then
            # On regarde maintenant si l'output est le bon:
            diff <(echo "$resultat_output") $fichier_modele
            if [ $? -eq 0 ]; then
                echo "$1: $str_res_waited attendu ✅"
            else
                echo "$1: $str_res_waited mais output non attendu ❌"
            fi
        else
            echo "$1: $str_res_not_waited non attendu ❌"
        fi
    fi

}

# echo "${purple}Lancement des tests sensés être invalides:${reset}"
# for cas_de_test in $(find src/test/deca/codegen/invalid/ -name '*.deca' -not -path "src/test/deca/codegen/invalid/interactif/*" -not -path "src/test/deca/codegen/invalid/provided/*"); do
#     test_codegen_unitaire "$cas_de_test" 1
# done

echo ""

echo "${purple}Lancement des tests sensés être valides:${reset}"
for cas_de_test in $(find src/test/deca/codegen/valid/ -name '*.deca' -not -path "src/test/deca/codegen/valid/interactif/*" -not -path "src/test/deca/codegen/valid/provided/*"); do
    test_codegen_unitaire "$cas_de_test" 0
done

echo ""

# Test interactifs:
echo "${purple}Lancement des tests interactifs valides:${reset}"
decac src/test/deca/codegen/valid/interactif/simple_readint.deca
resultat_output=$(echo 5 | ima src/test/deca/codegen/valid/interactif/simple_readint.ass)

diff <(echo "$resultat_output") "src/test/script/modele-extension/codegen/valid/modele_simple_readint.txt"
if [ $? -eq 0 ]; then
    echo "src/test/deca/codegen/valid/interactif/simple_readint.deca TEST OK ✅"
else
    echo "src/test/deca/codegen/valid/interactif/simple_readint.deca FAILED ❌"
fi

decac src/test/deca/codegen/valid/interactif/simple_readfloat.deca
resultat_output=$(echo 3.14 | ima src/test/deca/codegen/valid/interactif/simple_readfloat.ass)
diff <(echo "$resultat_output") "src/test/script/modele-extension/codegen/valid/modele_simple_readfloat.txt"
if [ $? -eq 0 ]; then
    echo "src/test/deca/codegen/valid/interactif/simple_readfloat.deca TEST OK ✅"
else
    echo "src/test/deca/codegen/valid/interactif/simple_readfloat.deca FAILED ❌"
fi
rm src/test/deca/codegen/valid/interactif/*.ass
# End of the script
# Check if the --exit-status option was passed
# if [ "$1" == "--exit-status" ]; then
#     # Print the exit status
#     echo "The script exited with a status of $exit_status"
#     exit $exit_status
# else
#     exit $exit_status
# fi

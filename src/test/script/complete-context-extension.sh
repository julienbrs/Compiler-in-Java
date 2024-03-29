#! /bin/bash

# Auteur : gl11

# Script des tests context

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
    rm src/main/bin/temporaire_test.txt
}
trap cleanup EXIT

test_context_unitaire() {
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

    fichier_modele="src/test/script/modele-extension/context/"$path_valid"/modele_$filename.txt"

    test_context_ext "$1" >src/main/bin/temporaire_test.txt 2>&1
    result=$?

    if [ "$result" -eq "$exit_status_waited" ]; then
        # On regarde maintenant si l'output est le bon:
        if cmp -s src/main/bin/temporaire_test.txt $fichier_modele; then
            echo "$1: $str_res_waited attendu ✅"
        else
            echo "$1: $str_res_waited mais output non attendu ❌"
        fi
    else
        echo "$1: $str_res_not_waited non attendu ❌"
    fi

}

echo "${purple}Lancement des tests sensés être invalides:${reset}"
for cas_de_test in $(find src/test/deca/extension/context/invalid/ -name '*.deca'); do
    test_context_unitaire "$cas_de_test" 1
done

echo ""

echo "${purple}Lancement des tests sensés être valides:${reset}"
for cas_de_test in $(find src/test/deca/extension/context/valid/ -name '*.deca'); do
    test_context_unitaire "$cas_de_test" 0
done

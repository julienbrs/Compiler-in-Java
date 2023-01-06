#! /bin/bash

# Auteur : gl11

# /!\ /!\ DO NOT RUN IF NOT NEEDED /!\ /!\

# Script de test du parser.
# Lance tous les tests parser et se sert des résultats comme modele

purple=$(tput setab 99)
reset=$(tput sgr0)

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

exit_status_final=0

declare log_activated=false
if [ "$2" == "--log" ]; then
    log_activated=true
fi


test_parser_unitaire () {
    # $1 = premier argument, $2 = deuxieme
    exit_status_waited=$2

    if [[ $exit_status_waited == "1" ]]; then
        str_res_waited="Echec"
        str_res_not_waited="Succes"
    else
        str_res_waited="Succes"
        str_res_not_waited="Echec"
    fi

    test_lex "$1" > /dev/null 2>&1
    result=$?

    if [ "$result" -eq "$exit_status_waited" ]; then
        echo "$1: $str_res_waited attendu ✅"
    else
        echo "$1: $str_res_not_waited non attendu ❌"
    fi

}    


echo "${purple}Lancement des tests sensés être invalides:${reset}"
for cas_de_test in $(find src/test/deca/syntax/invalid/parser/ -name '*.deca')
    do
        filename=$(basename "$cas_de_test")
        filename="${filename%.*}"
        test_synt "$cas_de_test" > src/test/script/modele/parser/modele_$filename.txt 2>&1
    done


echo "${purple}Lancement des tests sensés être valides:${reset}"
for cas_de_test in $(find src/test/deca/syntax/valid/parser/ -name '*.deca')
    do
        filename=$(basename "$cas_de_test")
        filename="${filename%.*}"
        test_synt "$cas_de_test" > src/test/script/modele/parser/modele_$filename.txt 2>&1
done



# End of the script
# Check if the --exit-status option was passed
if [ "$1" == "--exit-status" ]; then
    # Print the exit status
    echo "The script exited with a status of $exit_status"
    exit $exit_status
else
    exit $exit_status
fi

#! /bin/bash

# Auteur : gl11

# Script de test du parser.
# On lance tous les tests en rapport avec le parser

purple=$(tput setab 99)
cyan=$(tput setab 6)
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

echo "${purple}Lancement des tests divers:${reset}"

# Test avec option -n:
echo "${cyan}Lancement des tests avec option -n:${reset}"
decac -n src/test/deca/codegen/op_get_no_check.deca
resultat_output=$(echo 5 | ima src/test/deca/codegen/op_get_no_check.ass)

diff <(echo "$resultat_output") "src/test/script/modele/divers/modele_op_get_no_check.txt"
if [ $? -eq 0 ]; then
    echo "src/test/deca/codegen/valid/op_get_no_check.deca TEST OK ✅"
else
    echo "src/test/deca/codegen/valid/op_get_no_check.deca FAILED ❌"
fi

echo "${cyan}Lancement d'un test contexte invalide via stdin:${reset}"

resultat=$(echo "class A  {
    int x = 5;
}

class A  {
    float f = 12.5;
}" | src/test/script/launchers/test_context 2>&1)

if echo "$resultat" | grep -q '<unknown>:5:0: The class "A" is already declared : rule 1.3'; then
    echo "invalid_reading_from_stdin TEST OK ✅"
else
    echo "invalid_reading_from_stdin TEST FAILED ❌"
fi

# End of the script
# Check if the --exit-status option was passed
# if [ "$1" == "--exit-status" ]; then
#     # Print the exit status
#     echo "The script exited with a status of $exit_status"
#     exit $exit_status
# else
#     exit $exit_status
# fi

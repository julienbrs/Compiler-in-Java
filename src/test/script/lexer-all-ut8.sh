#!/bin/bash

# Script de test des caractères utf-8 la lexicographie.

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

# On test uniquement les caractères utiles (pas les caractères de controle
# ou qui ne s'affichent pas correctement).
start=32
end=126

exit_status=0

for i in $(seq $start $end); do
    char=$(printf \\$(printf %o $i))

    echo -n "$char" > test-temp.deca

    result=$(test_lex test-temp.deca 2>&1)
    
    output=""
    if [[ $result =~ "Error" ]]; then
        if [[ $result =~ "no token, no LocationException" ]]; then
            output="Character $char returned error"
        else
            echo "Character $char returned Fatal Error"
            echo "Fatal Error: Unknown error happened"
            exit_status=-1
            break
        fi
    else
        output="Character $char returned $result"
    fi

    line=$((i - start + 1))
    expected_output=$(sed "${line}q;d" src/test/script/modele/modele-lexer-utf-8.txt)

    if [[ "$output" != "$expected_output" ]]; then
        echo "Error: Output for character $char does not match expected output"
        exit_status=1
        break
    fi


done

rm test-temp.deca

# Check if the --exit-status option was passed
if [ "$1" == "--exit-status" ]; then
    # Print the exit status
    echo "The script exited with a status of $exit_status"
    exit $exit_status
else
    exit $exit_status
fi

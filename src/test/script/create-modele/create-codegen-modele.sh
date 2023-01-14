#! /bin/bash

# Auteur : gl11

# /!\ /!\ DO NOT RUN IF NOT NEEDED /!\ /!\

# Script de test pour codegen.
# Lance tous les tests codegen et se sert des résultats comme modele

purple=$(tput setab 99)
reset=$(tput sgr0)

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

# exit_status_final=0

# declare log_activated=false
# if [ "$2" == "--log" ]; then
#     log_activated=true
# fi

echo "${purple}Création des modèles de tests codegen sensés être invalides....${reset}"
for cas_de_test in $(find src/test/deca/codegen/invalid/ -name '*.deca' -not -path "src/test/deca/codegen/invalid/interactif/*" -not -path "src/test/deca/codegen/invalid/provided/*"); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele/codegen/invalid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        output_compil=$(decac "./src/test/deca/codegen/invalid/$filename.deca" 2>&1)
        # Si la compilation s'est bien passée mais que l'erreur sera après:
        if [ $? -eq 1 ]; then
            resultat_output=$(ima src/test/deca/codegen/invalid/$filename.ass)
            "$resultat_output" >"$output_file" 2>1
        else
            "$output_compil" >"$output_file" 2>1
        fi
    else
        echo "modele_$filename déjà existant"
    fi
done

echo "${purple}Création des modèles de tests codegen sensés être valides....${reset}"
for cas_de_test in $(find src/test/deca/codegen/valid/ -name '*.deca' -not -path "src/test/deca/codegen/valid/interactif/*" -not -path "src/test/deca/codegen/valid/provided/*"); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele/codegen/valid/modele_%s.txt" "$filename")

    if [ ! -f "$output_file" ]; then
        output_compil=$(decac "./src/test/deca/codegen/valid/$filename.deca" 2>&1)
        # Si la compilation s'est bien passée mais que l'erreur sera après:
        if [ $? -eq 0 ]; then
            resultat_output=$(ima src/test/deca/codegen/valid/$filename.ass)

            echo "$resultat_output" >"$output_file" 2>1

        else
            echo "$output_compil" >"$output_file" 2>1
        fi
    else
        echo "modele_$filename déjà existant"
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

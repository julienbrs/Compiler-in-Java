#! /bin/bash

# Auteur : gl11

# /!\ /!\ DO NOT RUN IF NOT NEEDED /!\ /!\

# Script pour créer les modèles codegen lancés à partir de l'extension
# Ces modèles comprennent les modèles classiques et les modèles spécifiques
# à l'extension

purple=$(tput setab 99)
reset=$(tput sgr0)

cleanup() {
    rm -r src/test/deca/codegen/valid/*.ass
    rm -r src/test/deca/codegen/invalid/*.ass
}
trap cleanup EXIT

# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

# echo "${purple}Création des modèles de tests codegen spécifiques sensés être invalides....${reset}"
# ## Tests specifiques à l'extension
# for cas_de_test in $(find src/test/deca/extension/codegen/invalid/ -maxdepth 1 -name '*.deca'); do
#     filename=$(basename "$cas_de_test")
#     filename="${filename%.*}"
#     output_file=$(printf "src/test/script/modele-extension/codegen/invalid/modele_%s.txt" "$filename")
#     if [ ! -f "$output_file" ]; then
#         output_compil=$(decac "./src/test/deca/codegen/invalid/$filename.deca" -tab 2>&1)
#         output_compil="$(echo "$output_compil" | sed 's#.*src#src#')"
#         # Si la compilation s'est bien passée mais que l'erreur sera après:
#         if [ $? -eq 0 ]; then
#             resultat_output=$(ima src/test/deca/codegen/invalid/$filename.ass)
#             resultat_output="$(echo "$resultat_output" | sed 's#.*src#src#')"
#             echo "$resultat_output" >"$output_file" 2>&1
#         else
#             echo "$output_compil" >"$output_file" 2>&1
#         fi
#     else
#         echo "modele_$filename déjà existant"
#     fi
# done

######## specifique
echo "${purple}.. tests spécifiques valides${reset}"
for cas_de_test in $(find src/test/deca/extension/codegen/valid/ -maxdepth 1 -name '*.deca'); do
    filename=$(basename "$cas_de_test")
    filename="${filename%.*}"
    output_file=$(printf "src/test/script/modele-extension/codegen/valid/modele_%s.txt" "$filename")
    if [ ! -f "$output_file" ]; then
        output_compil=$(decac -tab "./src/test/deca/extension/codegen/valid/$filename.deca" 2>&1)
        output_compil="$(echo "$output_compil" | sed 's#.*src#src#')"

        # Si la compilation s'est bien passée mais que l'erreur sera après:
        if [ $? -eq 0 ]; then
            resultat_output=$(ima src/test/deca/extension/codegen/valid/$filename.ass)
            resultat_output="$(echo "$resultat_output" | sed 's#.*src#src#')"

            echo "$resultat_output" >"$output_file" 2>&1

        else
            echo "$output_compil" >"$output_file" 2>&1
        fi
    else
        echo "modele_$filename déjà existant"
    fi
done

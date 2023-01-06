#! /bin/sh

# Auteur : gl11
# Version initiale : 01/01/2023

# Test minimaliste de la syntaxe des tokens.
# On lance test_synt sur un fichier valide, et les tests invalides.

cd "$(dirname "$0")"/../../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

# exemple de définition d'une fonction
test_synt_invalide () {
    # $1 = premier argument.
    if test_synt "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:"
    then
        echo "Echec attendu pour test_synt sur $1."
    else
        echo "Succes inattendu de test_synt sur $1."
        exit 1
    fi
}    

for cas_de_test in src/test/deca/syntax/invalid/provided/*.deca
do
    test_synt_invalide "$cas_de_test"
done


if test_synt src/test/deca/syntax/valid/hello.deca 2>&1 | \
    grep -q -e ':[0-9][0-9]*:'
then
    echo "Echec inattendu pour test_synt"
    exit 1
else
    echo "Succes attendu de test_synt"
fi

#!/bin/bash
start=32
end=126

for i in $(seq $start $end); do
    char=$(printf \\$(printf %o $i))

    echo -n "$char" > test-temp.deca

    result=$(../../script/launchers/test_lex test-temp.deca 2>&1)
    
    output=""
    if [[ $result =~ "Error" ]]; then
        if [[ $result =~ "no token, no LocationException" ]]; then
            output="Character $char returned error"
        else
            echo "Character $char returned Fatal Error"
            echo "Fatal Error: Unknown error happened"
            exit -1
        fi
    else
        output="Character $char returned $result"
    fi

    line=$((i - start + 1))
    expected_output=$(sed "${line}q;d" modele-utf-8.txt)

    if [[ "$output" != "$expected_output" ]]; then
        echo "Error: Output for character $char does not match expected output"
        exit 1
    fi


done

rm test-temp.deca

exit 0
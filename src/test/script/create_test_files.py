#!/usr/bin/env python3

"""
A script that create test files
"""

import os

ROOT_PROJECT_NAME = "gl11"
SEARCH_DIR = "src/main/java/fr/ensimag/deca/tree"
TARGET_DIR = "src/test/java/fr/ensimag/deca/context"

def get_functions(file_path):
    """
    Reads a file and finds all function names that contain "verify"
    """
    functions = []
    with open(file_path, 'r') as file:
        lines = file.readlines()
        for line in lines:
            if "verify" in line and "public" in line and "abstract" not in line:
                # split line on '('
                function_name = line.split("(")[0]
                # split on ' '
                function_name = function_name.split(" ")[-1]
                functions.append(function_name)
    return functions

def get_file_content(test_file_name):
    """
    Return template content for test files
    """
    content = [
        "import static org.junit.jupiter.api.Assertions.*;",
        "import org.junit.jupiter.api.Test;",
        "",
        "/**",
        " * ",
        " */",
        "",
        f"public class {test_file_name.split('.')[0]} " + '{',
        "\t@Test",
        "\tpublic void test() {",
        "\t\t// TESTTODO",
        "\t}",
        "}"
    ]
    return '\n'.join(content)

if __name__ == "__main__":
    # make sure we are in the root project directory
    if os.getcwd().split('/')[-1] != ROOT_PROJECT_NAME:
        print(f"Error: Please run the script from the root project directory")
        exit(1)

    # Iterate through all files in the search directory
    for root, dirs, files in os.walk(SEARCH_DIR):
        for file in files:
            file_path = os.path.join(root, file)
            functions = get_functions(file_path)
            for function in functions:
                test_file_name = TARGET_DIR + "/Test_" + function + "_" + file
                # make sure the file is empty or doesn't exists
                try:
                    if os.path.getsize(test_file_name) != 0:
                        response = input(f"File {test_file_name} is not empty. Do you want to overwrite it? (y/n)")
                        if response == "y":
                            with open(test_file_name, 'w') as test_file:
                                test_file.write(get_file_content(file))
                except FileNotFoundError:
                    with open(test_file_name, 'w') as test_file:
                                test_file.write(get_file_content(file))

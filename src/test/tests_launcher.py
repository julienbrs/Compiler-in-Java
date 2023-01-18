"""
Tout les tests.
"""

import os
import subprocess

# pylint: disable=invalid-name, subprocess-run-check

# @modif : si on veut afficher les informations sur les tests ou pas
LOG = False

SB = "\033[1m"      # Start Bold text
EB = "\033[0m"      # End Bold text
SR = "\033[31m"     # Start Red text
ER = "\033[0m"      # End Red text
START_SEQ = "//# "   # Means the line is part of output
ROOT_PROJECT_NAME = "gl11"

# @modif : les répertoires de tests qui seront parcourus
TEST_FOLDERS = [
    "src/test/deca/context/",
    "src/test/deca/syntax/lexer/provided/",
    "src/test/deca/syntax/parser/provided/",
    "src/test/deca/syntax/parser/valid/",
]

# @modif : les fichiers de tests que l'on souhaite exécuter
FILES_TO_INCLUDE = [
    #"src/test/deca/syntax/parser/valid/ineq/ineq_float_leq_string.deca",
    #"src/test/deca/syntax/parser/valid/empty.deca",
    #"src/test/deca/codegen/valid/addition_float_int.deca",
]

# @modif : les fichiers de tests que l'on ne souhaite pas exécuter
FILES_TO_EXCLUDE = [
    "src/test/deca/context/valid/decoration-test.deca",
    "src/test/deca/syntax/parser/provided/moitie.deca"
]

class Test(object):
    """
    Un test a :
        - un dossier (path)
        - un nom
        - une extension
        - un type (unit, non regression, ...)
        - le code source du programme testé
        - la sortie attendue
    """
    def __init__(self, nom_with_path, type_test, code, output, launchers):
        self.path = os.path.dirname(nom_with_path) + '/'
        self.nom, self.extension = os.path.splitext(os.path.basename(nom_with_path))
        self.type_test = type_test
        self.code = code
        self.output = output
        self.launchers = launchers

    def prettyprint(self, print_sep) -> str:
        """Pretty print the test"""
        s = f"{SB}Test({EB}{self.nom}{SB}): type({EB}{self.type_test}{SB}){EB}"
        if LOG:
            s += f"\n{SB}Code:{EB}"
            for code_line in self.code:
                s += "\n| " + code_line.replace('\n', '')
            s += f"\n{SB}Output:{EB}"
            for output_line in self.output:
                s += "\n| " + output_line.replace('\n', '')

            if print_sep:
                s += '-' * 80
        return s

    def run_test(self):
        """Runs myself"""

        # s'il n'y a pas d'output défini /!\
        if not self.output:
            # s += "| " + SR + "OUTPUT A DEFINIR" + ER
            print("⚠️  ", f"{SB}Test({EB}{self.nom}{SB}): type({EB}{self.type_test}{SB}){EB}", end='')
            print('\t' + SR + "OUTPUT A DEFINIR" + ER)
            return

        test_filename_with_path = self.path + self.nom + self.extension

        # Run the command and capture error output
        result = subprocess.run([self.launchers[0], test_filename_with_path], stdout=subprocess.PIPE, stderr=subprocess.PIPE)

        # Access the error output
        error_output = result.stderr.decode()

        # IL Y A UNE ERREUR :
        if result.returncode != 0:
            # ET CE N'EST PAS L'ERREUR ATTENDUE
            # error_output.split("gl11")[1][1:].strip()
            if error_output.strip() != ''.join(self.output).strip():
                print("❌ ", end='')
            # MAIS C'EST L'ERREUR ATTENDUE
            else:
                print("✅ ", end='')
            print(self.prettyprint(False))
            if LOG:
                print(SB + "Actual:" + EB)
                print("| " + error_output.strip())
        # IL N'Y A PAS D'ERREUR
        else:
            if "decac" in self.launchers:
                test_filename_compiled_with_path = test_filename_with_path.replace(".deca", ".ass")
                result = subprocess.run(["ima", test_filename_compiled_with_path],stdout=subprocess.PIPE, stderr=subprocess.PIPE)

            actual_output = result.stdout.decode()
            actual_output_without_log = '\n'.join((line for line in actual_output.split('\n') if not line.startswith("DEBUG")))

            # MAIS ON PAS LA SORTIE ATTENDUE
            if actual_output_without_log.strip() != ''.join(self.output).strip():
                print("❌ ", end='')
            # ET ON A BIEN LA SORTIE ATTENDUE
            else:
                print("✅ ", end='')
            print(self.prettyprint(False))
            if LOG:
                print(SB + "Actual:" + EB)
                print('\n'.join(("| " + l for l in actual_output_without_log.strip().split('\n'))))

        if LOG:
            print('-' * 80)


def create_test(test_filename_with_path: str) -> Test:
    """docstring"""
    with open(test_filename_with_path, 'r', encoding="utf-8") as test_file:
        code = []
        output = []
        for line in test_file:
            if line.startswith(START_SEQ):
                output.append(line.split(START_SEQ)[-1])
            elif not line.startswith("//") and line.strip() != '':
                code.append(line)
    launchers = []
    if "lexer" in test_filename_with_path:
        launchers.append("test_lex")
    if "parser" in test_filename_with_path:
        launchers.append("test_synt")
    if "context" in test_filename_with_path:
        launchers.append("test_context")
    if "codegen" in test_filename_with_path:
        launchers.append("decac")

    return Test(test_filename_with_path, "systeme", code, output, launchers)


def main():
    """Main function"""

    tests = []

    # make sure we are in the root project directory
    if os.getcwd().split('/')[-1] != ROOT_PROJECT_NAME:
        print("Error: Please run the script from the root project directory")
        exit(1)

    # Iterate through all files in the search directory
    for test_folder in TEST_FOLDERS:
        for root, dirs, files in os.walk(test_folder):
            for filename in files:
                filename_with_path = os.path.join(root, filename)
                # print("filename =", filename)
                # print("filename_with_path =", filename_with_path)
                if not str(filename_with_path).endswith(".ass") and \
                   not filename_with_path in FILES_TO_EXCLUDE:
                    tests.append(create_test(filename_with_path))
    for filename_with_path in FILES_TO_INCLUDE:
        if not filename_with_path.endswith(".ass") and \
           not filename_with_path in FILES_TO_EXCLUDE:
            tests.append(create_test(filename_with_path))

    actual_folder = ROOT_PROJECT_NAME
    for test in tests:
        if test.path != actual_folder:
            actual_folder = test.path
            print("\033[7m" + actual_folder + "\033[0m")
        test.run_test()


if __name__ == "__main__":
    main()

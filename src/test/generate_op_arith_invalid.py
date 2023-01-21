def main():
    types = ["eq", "ineq", "mult", "somme", "unary"]
    
    for t1 in types:
        for t2 in types:
            if t1 == t2:
                continue
            code = [
                f"// Test de la règle (3.39) : on ne peut pas cast de {t2} vers {t1}."
            ]
            code.append("{")
            code.append(f"\t{t1} var1;");
            code.append(f"\t{t2} var2;");
            code.append(f"\tvar1 = ({t1})(var2);")
            code.append("}")
            code.append("\n// Résultat :")
            code.append("//# a\n")
            with open(f"expr_cast_{t2}_to_{t1}.deca", 'w', encoding="utf-8") as f:
                f.write('\n'.join(code))

if __name__ == "__main__":
    main()


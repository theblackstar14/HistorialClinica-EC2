#!/usr/bin/env python3
import os
import argparse

def agregar_archivos_a_texto(ruta, archivo_salida, extensiones=None):
    """
    Recorre recursivamente `ruta` y vuelca el contenido de todos los archivos
    (filtrados por `extensiones`, si se indica) en `archivo_salida`.
    """
    with open(archivo_salida, 'w', encoding='utf-8') as outf:
        for root, dirs, files in os.walk(ruta):
            for nombre in files:
                if extensiones and not any(nombre.lower().endswith(ext.lower()) for ext in extensiones):
                    continue
                ruta_completa = os.path.join(root, nombre)
                outf.write(f"\n\n-- Archivo: {ruta_completa} \n")
                try:
                    with open(ruta_completa, 'r', encoding='utf-8', errors='ignore') as f:
                        outf.write(f.read())
                except Exception as e:
                    outf.write(f"[ERROR al leer este archivo: {e}]\n")

def main():
    parser = argparse.ArgumentParser(
        description="Concatena en un solo .txt todo el contenido de una carpeta (recursivo)."
    )
    parser.add_argument(
        "input", nargs="?", default=".",
        help="Carpeta o archivo raíz (por defecto, el directorio actual)."
    )
    parser.add_argument(
        "-o", "--output", default="todo_el_codigo.txt",
        help="Fichero de salida (por defecto, todo_el_codigo.txt)."
    )
    parser.add_argument(
        "-e", "--extensiones", nargs="+",
        help="Extensiones a incluir (ej: .py .txt). Si no se indica, incluye todo."
    )
    args = parser.parse_args()

    agregar_archivos_a_texto(args.input, args.output, args.extensiones)
    print(f"✅ Contenido guardado en '{args.output}'")

if __name__ == "__main__":
    main()

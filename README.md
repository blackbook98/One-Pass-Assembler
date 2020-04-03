# One-Pass-Assembler

A simple one-pass assembler in Java that scans the given source program only once and translates syntax and mnemonics into their numerical equivalents.

The program does not support:

    1)program blocks
    2)EQU
    3)LITTAB
    4)expressions in operands


The source.DAT file consists of the assembly level language program and the OPTAB.DAT consists of opcodes.
The intermediate processing and output will be shown in SYMTAB.DAT, EXTDEF.DAT and EXTREF.DAT respectively.

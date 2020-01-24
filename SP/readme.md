# Assembler Pass 1

## INPUT
 - input.asm

## OUTPUT
 - ic file
 - symbol table
 - literal table
 - pool table
 
 
# Assembler Pass 2

## INPUT
 - ic file
 - symbol table
 - literal table
 - pool table
 
# OUTPUT
 - machine code file
 
 
# Macro Pre processor Pass 1

## INPUT
 - asm file containing macro definition and call
 
## OUTPUT
 - MNT
 - MDT
 - KPDT
 - IC FILE ( containing only call )

# Macro Pre processor Pass 2

## INPUT 
 - MNT
 - MDT
 - KPDT
 - ASM FILE ( containing only call )
 
## OUTPUT
 - ASM File ( containing expanded macro code )

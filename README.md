Simple assembler for a made up line-based assembly language called MAL.

Opcodes: All opcodes (instruction names) are either 3 (e.g., ADD), 4 (e.g., COPY) or 5 (e.g., LOADN) case-sensitive alphabetic characters long, entirely in upper-case.
Numeric constants: Numeric constants must be positive integer values, written in decimal notation, in the range 0-255.
Commas: A comma is used to separate two operands in those instructions that have two operands. There may be whitespace before and/or after a comma or no whitespace at all. 
Labels: There are no labels in a MAL program.
Instruction addresses start at ROM address 0 and each instruction occupies either 1 or 2 8-bit addresses. Data addresses start at RAM address 0 and each data value occupies 16 bits.

## INPUT
	- cnt.l

## OUTPUT
	- no of lines
	- no of words
	- no of characters
	- no of uppercase characters
	- no of lowercase characters
	- no of digits
	- no of special characters
	
## HOW TO RUN?
```
lex cnt.l
cc lex.yy.c
./a.out
```

## What is in cnt.l?

cnt.l file has mainly 3 sections 
	- first section consists of include files and global variables
	- the second section is the rule section
		- here we write regular expressions and rules
			- rules are the things which we do when a regular expression matches with the input stream characters
			 for ex.
			 ```\n```(the reg ex) will match all the newlines and lc++ (the rule) will increment the lc variable value
	- the third section is where the yywrap() and main() functions are present
		- the main function has a call to yylex()

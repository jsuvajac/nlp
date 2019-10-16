/*
  File Name: doc.flex
  JFlex specification for the articele document tokenization
*/
   
%%
   
%class Lexer
%type Token
%line
%column
    
%eofval{
  return null;
%eofval};

LineTerminator = \r|\n|\r\n
WhiteSpace     = [ \t\f]
digit = [0-9]
number = ("+"|"-")?{digit}+
letter = [a-zA-Z]
word = ({letter}|{digit})+
hyphanated = {word}(\-{word})+(\'|\'{word})*
apostrophized = {letter}+((\'{letter}+)|\')+
lable=\$[A-Z]+

%%
   
LA{digit}{6}-{digit}{4} { return new Token(Token.DOCSTR, yytext(), yyline, yycolumn); }
{lable}            { return new Token(Token.LABLE, yytext(), yyline, yycolumn); }
{hyphanated}       { return new Token(Token.HYPH, yytext(), yyline, yycolumn); }
{apostrophized}    { return new Token(Token.APOS, yytext(), yyline, yycolumn); }
{word}             { return new Token(Token.WORD, yytext(), yyline, yycolumn); }
{LineTerminator}   { return new Token(Token.NEWLINE, yytext(), yyline, yycolumn); }
{WhiteSpace}+      { /* skip whitespace */ }   
{number}           { return new Token(Token.NUM, yytext(), yyline, yycolumn); }
.                  { return new Token(Token.PUNCTUATION, yytext(), yyline, yycolumn); }

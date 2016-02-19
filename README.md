# Chess---Client
Create a simple chess app that get the pieces' movement from the server.


Piece position format: <piece code><horizontal position><vertical position>[space]<piece code><horizontal position><vertical position>[space]<piece code><horizontal position><vertical position> etc...

Note: there are only 10 pieces on this application
Piece's code:
K: White King
Q: White Queen
B: White Bishop
N: White Knight
R: White Rook
k: Black King
q: Black Queen
b: Black Bishop
n: Black Knight
r: Black Rook

Example of data sent will be:
Ka1 Qg3 Be6 Ne4 Rd1 kg6 qa4 bf5 ng1 rf3


And the board format as follow:

 |a|b|c|d|e|f|g|h
---|---|---|---|---|---|---|---|---
8 | | | | | | | | 
8 | | | | | | | | 
8 | | | | | | | | 
8 | | | | | | | | 
8 | | | | | | | | 
7<br />
6         B   K <br />
5           b <br />
4 q       N <br />
3           r Q <br />
2 <br />
1 K     R     n <br />
  a b c d e f g h <br />

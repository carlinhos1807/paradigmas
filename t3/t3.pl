% 1.Defina um predicado odd(N) que seja verdadeiro se N for um número ímpar

odd(N) :- A is mod(N,2), A = 1.

% 2.Defina um predicado recursivo hasN(L,N) 
% que seja verdadeiro se L for uma lista de N elementos.

tamanho_lista([],0).
tamanho_lista(L,N) :-
  L = [_|T],
  tamanho_lista(T,N1),
  N is (1+N1).

hasN(L,N) :- tamanho_lista(L,X),N = X.

% 3.Defina um predicado recursivo inc99(L1,L2), de forma 
% que L2 seja uma lista com todos os elementos de L1 acrescidos da constante 99

inc99([],[]).
inc99(L1,L2) :- 
   L1 = [H|T],
   inc99(T,L3),
   D is H + 99,
   L2 = [D|L3].

% 4.Defina um predicado recursivo incN(L1,L2,N), 
% de forma que L2 seja uma lista com todos os elementos de L1 acrescidos da constante N.

incN([],[],_).
incN(L1,L2,N) :- 
   L1 = [H|T],
   incN(T,L3,N),
   D is H + N,
   L2 = [D|L3].

% 5.Defina um predicado recursivo comment(L1,L2), de forma que L1 seja uma lista de strings e L2 
% tenha todos os elementos de L1 concatenados com o prefixo "%%".

comment([],[]).
comment(L1,L2) :- 
   L1 = [H|T],
   comment(T,L3),
   string_concat("%%",H,X),
   atom_string(Y,X),
   L2 = [Y|L3].

% 6.Defina um predicado recursivo onlyEven(L1,L2), de forma que L2 
% seja uma lista só com os elementos pares de L1.

onlyEven([],[]).
onlyEven(L1,L2) :- 
   L1 = [H|T],
   onlyEven(T,L3),
   A is mod(H,2),
   A = 0,
   L2 = [H|L3].

onlyEven(L1,L2) :-
   L1 = [_|T],
   onlyEven(T,L3),
   L2 = L3.
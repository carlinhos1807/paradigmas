% 1.Defina um predicado odd(N) que seja verdadeiro se N for um numero impar

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
% seja uma lista so com os elementos pares de L1.

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

% 7.Defina um predicado recursivo countdown(N,L), de forma que L seja uma lista com os numeros [N, N-1, N-2, .., 1], 
% sendo N um numero positivo.

countdown(1,[1]).
countdown(N,L) :-
   N > 0,
   C is N - 1,
   countdown(C,L3),
   L = [N|L3].

% 8.Defina um predicado recursivo nRandoms(N,L), de forma que L seja uma lista com N numeros gerados aleatoriamente.   

nRandoms(0,[]).
nRandoms(N,L) :-
   N > 0,
   C is N - 1,
   nRandoms(C,L3),
   random(0,1000,X),
   L = [X|L3].

% 9.Defina um predicado recursivo potN0(N,L), de forma que L seja uma lista de potencias de 2, com expoentes de N a 0. 

potN0(0,[1]).
potN0(N,L) :-
   N > 0,
   C is N - 1,
   potN0(C,L3),
   pow(2,N,D),
   L = [D|L3].

% 10.Defina um predicado recursivo zipmult(L1,L2,L3), de forma que cada elemento da lista L3 seja o produto dos elementos de L1 e L2 na mesma posicao do elemento de L3.

zipmult([],[],[]).
zipmult(L1,L2,L3) :-
   L1 = [H|T],
   L2 = [A|B],
   zipmult(T,B,LN),
   D is H*A,
   L3 = [D|LN].

% 11.Defina um predicado recursivo potencias(N,L), de forma que L seja uma lista com as N primeiras potencias de 2, sendo a primeira 2^0 e assim por diante

potencias2(A,A,[]).
potencias2(X,N1,LN) :-
   X < N1,
   C is X+1,
   potencias2(C,N1,L3),
   pow(2,X,D),
   LN = [D|L3].

potencias(0,[]).
potencias(N,L) :-
   potencias2(0,N,L).

% 12.Defina um predicao recursivo cedulas(V,L1,L2), que receba um valor V e uma lista L1 de cedulas com valores em Reais (R$), 
% em ordem decrescente, e obtenha a lista L2 decompondo o valor V em 0 ou mais cedulas de cada tipo. 

cedulas(_,[],[]).
cedulas(V,L1,L2) :-
   L1 = [H|T],
   A is div(V,H),
   C is mod(V,H),
   cedulas(C,T,L3),
   L2 = [A|L3].
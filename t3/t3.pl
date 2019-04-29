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
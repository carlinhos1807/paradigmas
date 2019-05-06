pobre(bernardo).
pobre(bia).
pobre(pedro).
pobre(maria).
insano(adriano).
insano(maria).

localizacao(pedro,sexta,apartamento).
localizacao(pedro,terca,sm).
localizacao(pedro,quarta,poa).
localizacao(pedro,quinta,sm).
localizacao(pedro,segunda,sm).
localizacao(caren,sexta,apartamento).
localizacao(caren,quarta,poa).
localizacao(caren,quinta,sm).
localizacao(caren,terca,poa).
localizacao(caren,segunda,poa).
localizacao(henrique,quinta,apartamento).
localizacao(henrique,sexta,apartamento).
localizacao(henrique,quarta,apartamento).
localizacao(henrique,terca,poa).
localizacao(henrique,segunda,apartamento).
localizacao(bia,quarta,poa).
localizacao(bia,quinta,sm).
localizacao(bia,sexta,apartamento).
localizacao(bia,terca,poa).
localizacao(bia,segunda,apartamento).
localizacao(adriano,quarta,sm).
localizacao(adriano,quinta,apartamento).
localizacao(adriano,sexta,apartamento).
localizacao(adriano,terca,apartamento).
localizacao(adriano,segunda,apartamento).
localizacao(alice,quarta,poa).
localizacao(alice,quinta,apartamento).
localizacao(alice,sexta,apartamento).
localizacao(alice,terca,poa).
localizacao(alice,segunda,apartamento).
localizacao(bernardo,quarta,poa).
localizacao(bernardo,quinta,sm).
localizacao(bernardo,sexta,apartamento).
localizacao(bernardo,terca,sm).
localizacao(bernardo,segunda,sm).
localizacao(maria,quinta,sm).
localizacao(maria,quarta,sm).
localizacao(maria,terca,sm).
localizacao(maria,sexta,apartamento).
localizacao(maria,segunda,apartamento).

relacao(anita,bernardo).
relacao(bernardo,caren).
relacao(anita,pedro).
relacao(pedro,alice).
relacao(alice,henrique).
relacao(henrique,maria).
relacao(maria,adriano).
relacao(adriano,caren).

dinheiro(X) :- pobre(X).
insanidade(X) :- insano(X).
ciumes(X) :- relacao(anita,Y),relacao(X,Y),X \= anita.
ciumes(X) :- relacao(anita,Y),relacao(Y,X),X \= anita.

motivo(X) :- ciumes(X).
motivo(X) :- insanidade(X).
motivo(X) :- dinheiro(X).

roubou_arma(X) :- localizacao(X,quinta,poa), X \= bernardo.
roubou_arma(X) :- localizacao(X,quarta,sm), X \= bernardo.
roubou_arma(X) :- localizacao(X,quarta,apartamento).
roubou_arma(X) :- localizacao(X,quinta,apartamento).
estava_apartamento(X) :- localizacao(X,quinta,apartamento).
estava_apartamento(X) :- localizacao(X,sexta,apartamento).
roubou_chave(X) :- localizacao(X,segunda,sm), X \= bia.
roubou_chave(X) :- localizacao(X,terca,poa), X \= bia.

acesso(X) :- roubou_arma(X), roubou_chave(X), estava_apartamento(X).

assassino(X) :- motivo(X), acesso(X),!.
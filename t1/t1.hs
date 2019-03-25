import Data.Char

--1.Crie uma função isVowel :: Char -> Bool que verifique se um caracter é uma vogal ou não.

isVowel :: Char -> Bool
isVowel c
      | c == 'a' = True
      | c == 'e' = True
      | c == 'i' = True
      | c == 'o' = True
      | c == 'u' = True
      | c == 'A' = True
      | c == 'E' = True
      | c == 'I' = True
      | c == 'O' = True
      | c == 'U' = True
      | otherwise = False

--2.Escreva uma função addComma, que adicione uma vírgula no final de cada string contida numa lista.

addComma :: [String] -> [String]
addComma s = map(\p -> p ++ ",")s

--3.Crie uma função htmlListItems :: [String] -> [String]
--que receba uma lista de strings e retorne outra lista contendo as strings formatadas como itens de lista em HTML.

--Com funcao anonima:

htmlListItems :: [String] -> [String]
htmlListItems l = map(\p -> "<LI>" ++ p ++ "<LI>")l

--Sem funcao anonima:

formata :: [String] -> [String]
formata s = map(\p -> "<LI>" ++ p ++ "<LI>")s
htmlListItems2 :: [String] -> [String]
htmlListItems2 l1 = formata l1

--4.Defina uma função que receba uma string e produza outra retirando as vogais.

--Com funcao anonima:

delvowel :: String -> String
delvowel s = filter(\p -> p /= 'a' && p /= 'e'  && p /= 'i'  && p /= 'o'  && p /= 'u'  && p /= 'A'  && p /= 'E'  && p /= 'I'  && p /= 'O'  && p /= 'U' )s

--Sem funcao anonima:

filtraVog :: String -> String
filtraVog s1 = filter(\p -> p /= 'a' && p /= 'e'  && p /= 'i'  && p /= 'o'  && p /= 'u'  && p /= 'A'  && p /= 'E'  && p /= 'I'  && p /= 'O'  && p /= 'U' )s1
delvowel2 :: String -> String
delvowel2 s = filtraVog s
import Data.Char

--1.Crie uma fun��o isVowel :: Char -> Bool que verifique se um caracter � uma vogal ou n�o.

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

--2.Escreva uma fun��o addComma, que adicione uma v�rgula no final de cada string contida numa lista.

addComma :: [String] -> [String]
addComma s = map(\p -> p ++ ",")s
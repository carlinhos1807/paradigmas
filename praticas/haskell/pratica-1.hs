import Data.List

--1.
sumSquares :: Int -> Int -> Int
sumSquares x y = x^2 + y^2

--2.
hasEqHeads :: [Int] -> [Int] -> Bool
hasEqHeads l1 l2 = (head l1) == (head l2)

--3.
adicionaSuper :: [String] -> [String]
adicionaSuper s = map(\p -> "Super" ++ p) s


--4.

espacosNum :: String -> Int
espacosNum s = length(filter(\p -> p ==' ')s)

--5.
calcula :: [Int] -> [Int]
calcula x = map(\p -> 3*p^2 + (div 2 p) + 1)x

--6.
negativos :: [Int] -> [Int]
negativos x = filter(\p -> p < 0)x

--7.
intervalo :: [Int] -> [Int]
intervalo x = filter(\p -> p >= 1 && p <=100)x

--8.
testeIdade :: [Int] -> Int -> [Int]
testeIdade n1 n2 = filter(\p -> (n2 - p) > 1980)n1

--9.
pares :: [Int] -> [Int]
pares x = filter(\p -> mod p 2 == 0)x

--10.
conta2 :: String -> Bool
conta2 a = length a > 0
charFound :: Char -> String -> Bool
charFound c1 s1 = conta2(filter (\p -> p == c1)s1)

--11. 


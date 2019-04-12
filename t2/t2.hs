import Text.Printf
import Data.Char

type Point     = (Float,Float)
type Rect      = (Point,Float,Float)
type Circle    = (Point,Float)


-------------------------------------------------------------------------------
-- Paletas
-------------------------------------------------------------------------------

rgbPalette :: Int -> [(Int,Int,Int)]
rgbPalette n = take n $ cycle [(255,0,0),(0,255,0),(0,0,255)]

greenPalette :: Int ->Int-> [(Int,Int,Int)]
greenPalette n h = [(0,10*i+h,0) | i <- [0..(n-1)] ]


-------------------------------------------------------------------------------
-- Geração de retângulos em suas posições
-------------------------------------------------------------------------------
genRectsInLine :: Int ->Float->Float-> [Rect]
genRectsInLine n i y= [((i+(m*gap),y),w,h) | m <- [0..fromIntegral (n-1)]]
  where (w,h) = (50,50)
        gap = 60

calcula_indice :: Int -> Int -> Int
calcula_indice n t = if n`mod` t == 0 then (n `div` t) else ((n`div`t)+1)

get_draw :: Int -> Int-> [Rect]
get_draw n c = concat [if m == calcula_indice n c then genRectsInLine (n -(m-1)*c) 90.0 (100 + 90.0*fromIntegral(m))
else genRectsInLine c 90.0 (100 + 90.0*fromIntegral(m))| m <- [1..(calcula_indice n c)]]

get_cores :: Int ->Int-> [(Int,Int,Int)]
get_cores x y = concat[ if m == calcula_indice x y then greenPalette (x -(m-1)*y) ((m-1)*20 + 80)
else greenPalette y ((m-1)*20 + 80)|m <- [1..(calcula_indice x y)]]
-------------------------------------------------------------------------------
-- Strings SVG
-------------------------------------------------------------------------------

-- Gera string representando retângulo SVG 
-- dadas coordenadas e dimensoes do retângulo e uma string com atributos de estilo
svgRect :: Rect -> String -> String 
svgRect ((x,y),w,h) style = 
  printf "<rect x='%.3f' y='%.3f' width='%.2f' height='%.2f' style='%s' />\n" x y w h style

-- String inicial do SVG
svgBegin :: Float -> Float -> String
svgBegin w h = printf "<svg width='%.2f' height='%.2f' xmlns='http://www.w3.org/2000/svg'>\n" w h 

-- String final do SVG
svgEnd :: String
svgEnd = "</svg>"

-- Gera string com atributos de estilo para uma dada cor
-- Atributo mix-blend-mode permite misturar cores
svgStyle :: (Int,Int,Int) -> String
svgStyle (r,g,b) = printf "fill:rgb(%d,%d,%d); mix-blend-mode: screen;" r g b

-- Gera strings SVG para uma dada lista de figuras e seus atributos de estilo
-- Recebe uma funcao geradora de strings SVG, uma lista de círculos/retângulos e strings de estilo
svgElements :: (a -> String -> String) -> [a] -> [String] -> String
svgElements func elements styles = concat $ zipWith func elements styles

-------------------------------------------------------------------------------
-- Função principal que gera arquivo com imagem SVG
-------------------------------------------------------------------------------
main :: IO ()
main = do
  writeFile "caseX.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgRect rects (map svgStyle palette)
        rects = get_draw nrects nrects_linha
        palette = get_cores nrects nrects_linha
        inicial = 5.0
        nrects = 25
        nrects_linha = 20
        (w,h) = (1920,1080) -- width,height da imagem SVG
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

circlesPalette :: Int ->Int-> [(Int,Int,Int)]
circlesPalette n h = [(30*i+h,10*i+h,30*i) | i <- [0..(n-1)] ]

-------------------------------------------------------------------------------
-- Geração de retângulos em suas posições
-------------------------------------------------------------------------------
genRectsInLine :: Int ->Float->Float-> [Rect]
genRectsInLine n i y= [((i+(m*gap),y),w,h) | m <- [0..fromIntegral (n-1)]]
  where (w,h) = (50,50)
        gap = 60
-------------------------------------------------------------------------------
-- Strings SVG
-------------------------------------------------------------------------------

-- Gera string representando retângulo SVG 
-- dadas coordenadas e dimensoes do retângulo e uma string com atributos de estilo
svgRect :: Rect -> String -> String 
svgRect ((x,y),w,h) style = 
  printf "<rect x='%.3f' y='%.3f' width='%.2f' height='%.2f' style='%s' />\n" x y w h style

-- Gera string representando circulo SVG
svgCircle :: Circle -> String -> String 
svgCircle ((x,y),w) style = 
  printf "<circle cx='%.3f' cy='%.3f' r='%.2f' style='%s' />\n" x y w style

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

-- Calcula numero de linhas
calcula_indice :: Int -> Int -> Int
calcula_indice n t = if n`mod` t == 0 then (n `div` t) else ((n`div`t)+1)

-- Gera linhas de retangulos
get_rects :: Int -> Int-> [Rect]
get_rects n c = concat [if m == calcula_indice n c then genRectsInLine (n -(m-1)*c) 90.0 (100 + 90.0*fromIntegral(m))
else genRectsInLine c 90.0 (100 + 90.0*fromIntegral(m))| m <- [0..((calcula_indice n c)-1)]]

-- Gera as cores para cada retangulo
get_color_rects :: Int ->Int-> [(Int,Int,Int)]
get_color_rects x y = concat[ if m == calcula_indice x y then greenPalette (x -(m-1)*y) ((m-1)*20 + 80)
else greenPalette y ((m-1)*20 + 80)|m <- [1..(calcula_indice x y)]]

-------------------------------------------------------------------------------
-- Funcao que gera case 1
-------------------------------------------------------------------------------
genCase1 :: IO ()
genCase1 = do
  writeFile "case1.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgRect rects (map svgStyle palette)
        rects = get_rects nrects nrects_por_linha
        palette = get_color_rects nrects nrects_por_linha
        nrects = 50
        nrects_por_linha = 10
        (w,h) = (1920,1080) -- width,height da imagem SVG

-- Converte graus para radianos
radiano :: Float -> Float
radiano n = (n * pi) / 180

-- Gera uma lista de angulos
lista_trigonometrica :: Int->Float -> [Float]
lista_trigonometrica n a = [radiano(a*m) | m <- [0..fromIntegral(n-1)]]

-- Gera circulos
get_circles :: Int -> Float -> Float -> Float ->[Circle]
get_circles n a b r= [(((a+5*r*cos(m)),(b+5*r*sin(m))),r)| m <- lista_trigonometrica n (360.0 / fromIntegral(n))]

-- Gera cor para cada circulo
get_color_circles :: Int -> Int-> [(Int,Int,Int)]
get_color_circles n h = circlesPalette n h

-------------------------------------------------------------------------------
-- Funcao que gera case 2
-------------------------------------------------------------------------------
genCase2 :: IO ()
genCase2 = do
  writeFile "case2.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgCircle circles (map svgStyle palette)
        circles = get_circles ncircles x_centro y_centro raio_cada_circulo
        palette = get_color_circles ncircles ht
        -- Cordenadas do centro da circunferencia relativa a cada circulo
        x_centro = 80.0
        y_centro = 80.0
        raio_cada_circulo = 10.0
        ncircles = 12
        ht = 0
        (w,h) = (1920,1080) -- width,height da imagem SVG

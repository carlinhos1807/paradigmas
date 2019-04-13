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

get_rects :: Int -> Int-> [Rect]
get_rects n c = concat [if m == calcula_indice n c then genRectsInLine (n -(m-1)*c) 90.0 (100 + 90.0*fromIntegral(m))
else genRectsInLine c 90.0 (100 + 90.0*fromIntegral(m))| m <- [1..(calcula_indice n c)]]

get_color_rects :: Int ->Int-> [(Int,Int,Int)]
get_color_rects x y = concat[ if m == calcula_indice x y then greenPalette (x -(m-1)*y) ((m-1)*20 + 80)
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
genCase1 :: IO ()
genCase1 = do
  writeFile "case1.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgRect rects (map svgStyle palette)
        rects = get_rects nrects nrects_linha
        palette = get_color_rects nrects nrects_linha
        inicial = 5.0
        nrects = 50
        nrects_linha = 10
        (w,h) = (1920,1080) -- width,height da imagem SVG

svgCircle :: Circle -> String -> String 
svgCircle ((x,y),w) style = 
  printf "<circle cx='%.3f' cy='%.3f' r='%.2f' style='%s' />\n" x y w style

radiano :: Float -> Float
radiano n = (n * pi) / 180
lista_trigonometrica :: Int->Float -> [Float]
lista_trigonometrica n a = [radiano(a*m) | m <- [0..fromIntegral(n-1)]]
get_circles :: Int -> Float -> Float -> Float ->[Circle]
get_circles n a b r= [(((a+5*r*cos(m)),(b+5*r*sin(m))),r)| m <- lista_trigonometrica n (360.0 / fromIntegral(n))]

get_color_circles :: Int -> [(Int,Int,Int)]
get_color_circles n = rgbPalette n

genCase2 :: IO ()
genCase2 = do
  writeFile "case2.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgCircle circles (map svgStyle palette)
        circles = get_circles ncircles x_centro y_centro raio_cada_circulo
        palette = get_color_circles ncircles
        x_centro = 80.0
        y_centro = 80.0
        raio_cada_circulo = 10.0
        ncircles = 12
        (w,h) = (1920,1080) -- width,height da imagem SVG
gen_senoide :: Int ->Float->Float-> Float ->Float ->[Circle]
gen_senoide n t x_inicio amplitude r = [if sin(ang*m+3) > 0 then ((x_inicio+ang*m*amplitude,amplitude/2 - sin(ang*m+3)*amplitude+100+2*amplitude*t),r)
else if sin(ang*m+3) < 0 then ((x_inicio+ang*m*amplitude,(-1*sin(ang*m+3))*amplitude + amplitude/2+100+2*amplitude*t),r) 
else ((x_inicio+ang*m*amplitude,amplitude + (amplitude/2)+100+2*amplitude*t),r) | m <- [0..fromIntegral(n-1)]]
     where 
          ang = (7.28 / fromIntegral(n))

calcula_sobrepostos :: Float->Float->Float->[Circle]
calcula_sobrepostos x y r = [if m == 1 then ((x,y),r) else if m == 2 then ((x+r,y),r) else ((x+r/2,y-r),r) | m <-[1..3]]

get_sobrepostos :: Float -> Float -> Float -> Int ->[Circle]
get_sobrepostos x y r n = concat[if(m `mod`2 == 1) then calcula_sobrepostos ((fromIntegral(m+1)/2)*x) y r else if m == 1 then calcula_sobrepostos x y r 
else calcula_sobrepostos ((fromIntegral(m) / 2)*x) (2*y) r| m <-[1..(n)]]

calcula_cor_sobrepostos :: Int -> [(Int,Int,Int)]
calcula_cor_sobrepostos n = [if(m == 1) then (255,0,0) else if (m==2) then (0,255,0) else (0,0,255) | m <-[1..3]]
get_color_sobrepostos :: Int -> [(Int,Int,Int)]
get_color_sobrepostos n = concat[calcula_cor_sobrepostos m | m <- [1..(n)]]

genCase3 :: IO ()
genCase3 = do
  writeFile "case3.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgCircle circles (map svgStyle palette)
        circles = get_sobrepostos pos_x pos_y raio nelement
        palette = get_color_sobrepostos (3*nelement)
        pos_x = 160.0
        pos_y = 160.0
        raio = 50.0
        nelement = 6
        (w,h) = (1920,1080) -- width,height da imagem SVG


get_senoide :: Int -> Int-> Float-> Float ->Float ->[Circle]
get_senoide n n_l x_inicio amplitude r = concat[gen_senoide n z x_inicio amplitude r | z <-[0..fromIntegral(n_l-1)]]

calcula_cores_senoide :: Int ->Int->Int-> [(Int,Int,Int)]
calcula_cores_senoide x y z = [if(z `elem`[1,4..70]) then (80+10*i,0,0) else if(z `elem`[2,5..50]) then(0,80+10*i,0) else (0,0,80+10*i) | i <- [0..(x-1)] ]

get_color_senoide :: Int -> Int -> [(Int,Int,Int)]
get_color_senoide ncircles_linha n_l = concat[calcula_cores_senoide ncircles_linha n_l m | m <- [1..(n_l)]]
genCase4 :: IO ()
genCase4 = do
  writeFile "case4.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgCircle circles (map svgStyle palette)
        circles = get_senoide ncircles_linha nlinhas 80.0 55.0 20.0
        palette = get_color_senoide ncircles_linha nlinhas
        ncircles_linha = 16
        nlinhas = 5
        (w,h) = (1920,1080) -- width,height da imagem SVG
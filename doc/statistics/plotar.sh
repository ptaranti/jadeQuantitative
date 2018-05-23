rm *.png

rm final_e1.cvs

rm final_e2.cvs

./concatenador.sh

cat plotar1.cnf | gnuplot

cat plotar2.cnf | gnuplot

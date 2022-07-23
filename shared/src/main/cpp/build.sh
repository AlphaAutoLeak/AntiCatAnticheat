x86_64-w64-mingw32-g++ -Wall -DBUILD_DLL -O2 -std=c++11 -m64 -c anticatanticheat.cpp -o anticatanticheat.o
x86_64-w64-mingw32-g++ -shared anticatanticheat.o -o luohuayu.dll -s -m64 -static -static-libgcc -static-libstdc++ -luser32
rm a.out
rm anticatanticheat.o
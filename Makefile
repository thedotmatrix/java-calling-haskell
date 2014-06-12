default: 
all:
# The following line works fine for compiling & linking the haskell code and FFI exports into a STATIC shared library on linux
# Most of the options are self explanitory except for the -l and -optl-Wl,-rpath usage
# For some reason, GHC needs to be included and dynamically linked when the library is made
# The -l specifies the installed GHC library, and the -optl-Wl,-rpath tells the linker where to find the library
# The only dynamically linked dependencies at the moment are GHC and some minor linux things
# Note: Some statically linked libraries may have licenses that could affect distribution
linux:
	ghc --make -no-hs-main -shared -static -lHSrts-ghc7.4.1 -optl-Wl,-rpath,/usr/lib/ghc/ haskell/Simple.hs -o bin/libsimple.so

windows:
	ghc --make -no-hs-main -shared -static -fno-shared-implib -lHSrts-ghc7.6.3 -optl-Wl,-rpath,"/cygdrive/c/Program Files (x86)/Haskell Platform/2013.2.0.0/lib" haskell/Simple.hs -o bin/simple.dll

clean:
	rm -f haskell/*.o haskell/*.h haskell/*.hi *.log

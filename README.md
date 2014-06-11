java-calling-haskell
====================
Small test application of calling Haskell function natively from Java. 
For my own archives / possibly of use to others trying to do something similar.

The "pipe" = JNI > C > Haskell
------------------------------
The JNA library allows me to avoid writing any native / C code. This is quite desirable in a project that is already in two languages.

The "package" = XML
-------------------
We decided against converting Haskell data structures into C-structs, then into java classes. It involved a lot of implementation work that is comparable to getting object serialization working.
Object serialization using a lanaguage such as XML, JSON, YAML, etc, is much more portable than converting everything to byte arrays and back. It is also human readable, arguably easier to debug.
This also hardcodes in a backup plan if the JNI/FFI pipe gets to complicated. We could easily switch to another communication medium like a web or rpc socket.

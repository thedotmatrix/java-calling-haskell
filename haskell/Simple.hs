module Simple where

import Foreign.C.Types
import Foreign.C.String
import Foreign.Marshal.Alloc
import Foreign.Ptr
import Text.XML.HXT.Arrow.Pickle
import Text.XML.HXT.Core

data Person = Person 	{ 	
							name :: String, 
							age :: Int,
							children :: [Person]
						}
instance XmlPickler Person where xpickle = xpPerson
xpPerson ::	PU Person
xpPerson = 
	xpElem "PERSON" $
	xpWrap 	( uncurry3 Person
			, \ t ->	(	name t, 
							age t,
							children t
						)
		) $
	xpTriple	(xpAttr "NAME" 	xpText)
				(xpAttr "AGE" 	xpPrim)
				(xpList xpickle)
{- WORKING VERSION
instance XmlPickler Person where xpickle = xpPerson
xpPerson ::	PU Person
xpPerson = 
	xpElem "PERSON" $
	xpWrap 	( \ ((n,a)) -> Person n a
			, \t ->	(	name t, 
						age t
					)
		) $
	xpPair	(xpAttr "NAME" 	xpText)
			(xpAttr "AGE" 	xpPrim)
-}

jack = Person "Jack Smith" 6 []
jill = Person "Jill Smith" 9 []
alice = Person "Alice Smith" 38 [jack]
bob = Person "Bob Smith" 42 [jill]
john = Person "John Smith" 90 [alice, bob]


getjohn_hs :: IO CString
getjohn_hs = newCString (showPickled [] john)

helloworld_hs :: IO CString
helloworld_hs = newCString "Hello World!"

free_hs :: CString -> IO ()
free_hs p = free p

foreign export ccall getjohn_hs :: IO CString
foreign export ccall helloworld_hs :: IO CString
foreign export ccall free_hs :: CString -> IO ()
